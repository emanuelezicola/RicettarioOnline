package com.projectalpha.ricettarioonline.web.restcontroller;

import com.projectalpha.ricettarioonline.exceptions.DecodingPasswordException;
import com.projectalpha.ricettarioonline.models.Token;
import com.projectalpha.ricettarioonline.models.Utente;
import com.projectalpha.ricettarioonline.service.token.TokenService;
import com.projectalpha.ricettarioonline.service.utente.UtenteService;
import com.projectalpha.ricettarioonline.utils.TokenUtility;
import com.projectalpha.ricettarioonline.web.dto.utente.LoggedUserDTO;
import com.projectalpha.ricettarioonline.web.dto.utente.login.RequestLoginDTO;
import com.projectalpha.ricettarioonline.web.dto.utente.login.UtenteLoginDTO;
import com.projectalpha.ricettarioonline.web.dto.utente.logout.RequestLogoutDTO;
import com.projectalpha.ricettarioonline.web.dto.utente.registrazione.RequestRegistrationDTO;
import com.projectalpha.ricettarioonline.web.dto.ResponseDTO;
import com.projectalpha.ricettarioonline.web.dto.utente.registrazione.RegistrazioneUtenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;
import static com.projectalpha.ricettarioonline.utils.TokenUtility.*;


@RestController
@RequestMapping("utente")
@CrossOrigin(origins = "http://localhost:4200")
public class UtenteResource {

    private final UtenteService utenteService;
    private final TokenService tokenService;

    @Autowired
    public UtenteResource(UtenteService utenteService, TokenService tokenService) {
        this.utenteService = utenteService;
        this.tokenService = tokenService;
    }

    /**
     * Metodo che registra un nuovo utente. Se il processo va a buon fine, viene restituito l'oggetto Utente appena registrato
     * e un oggetto Token con il quale effettuare l'accesso.
     * @param requestRegistrationDTO oggetto che contiene un body con le informazioni inserite dall'utente ed un token null
     * @return una response contenente uno status, un token (null oppure valorizzato)
     * e un body contenente una lista di errori o un oggetto utente.
     */
    @PostMapping("/registrazione")
    @Produces({MediaType.APPLICATION_JSON})
    public ResponseEntity<ResponseDTO> registraNuovoUtente(@RequestBody RequestRegistrationDTO requestRegistrationDTO) throws DecodingPasswordException {
        //Controllo che la response sia ok
        Map<String, String> errors = RequestRegistrationDTO.validateForRegistration(requestRegistrationDTO);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO(null, errors), BAD_REQUEST);
        }

        //Controllo che il campo body della response sia ok.
        RegistrazioneUtenteDTO registrazioneUtenteDTO = requestRegistrationDTO.getUtenteDaRegistrare();
        errors = RegistrazioneUtenteDTO.validateDTO(registrazioneUtenteDTO);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO(null, errors), BAD_REQUEST);
        }

        errors.clear();

        //controllo se ci sono utenti registrati con quella email
        Utente utenteByMail = utenteService.findByEmail(registrazioneUtenteDTO.getEmail());

        if (utenteByMail != null) {
            errors.put("EMAIL", "La mail inserita è già associata ad un account. Prego inserire un'altra email");
            return new ResponseEntity<>(new ResponseDTO(null, errors), BAD_REQUEST);
        }

        //Salvo l'utente sul DB
        Utente utenteDaRegistrare = registrazioneUtenteDTO.convertDTOToUtente();
        utenteDaRegistrare.setDataRegistrazione(LocalDate.now());
        utenteService.inserisciNuovo(utenteDaRegistrare);
        utenteByMail = utenteService.findByEmail(utenteDaRegistrare.getEmail());

        //Creo un nuovo token perchè un nuovo utente sicuramente non avrà token sul DB.
        Token token = new Token(LocalDateTime.now(), LocalDateTime.now().plusHours(2), true, utenteByMail);
        tokenService.inserisciToken(token);
        List<Token> validTokens = tokenService.caricaTokenByUtente(utenteByMail);

        if(validTokens == null || validTokens.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO(null,
                    "Si è verificato un errore, contattare l'amministrazione" ), INTERNAL_SERVER_ERROR);
        }
        token = validTokens.get(0);

        return new ResponseEntity<>(new ResponseDTO(token, utenteByMail), OK);
    }


    /**
     * Metodo che esegue il login. Se il login va a buon fine, viene ricaricato un token se ancora valido
     * oppure ne viene creato un nuovo token.
     * @param requestLoginDTO DTO che contiene i dati della maschera di login.
     * @return La response contenente il token e l'utente loggato.
     */
    @PostMapping("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public ResponseEntity<ResponseDTO> doLogin(@RequestBody RequestLoginDTO requestLoginDTO) {

        //Controllo che ciò che arriva nel body della request non sia vuoto
        Map<String, String> errors = RequestLoginDTO.validateForLogin(requestLoginDTO);

        if(!errors.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO(null, errors), BAD_REQUEST);
        }

        errors.clear();

        //Controllo che ciò che arriva dentro l'oggetto contenuto nel body della request non sia vuoto
        UtenteLoginDTO utenteLoginDTO = requestLoginDTO.getLoggingUser();
        errors = UtenteLoginDTO.validate(utenteLoginDTO);

        if(!errors.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO(null, errors), BAD_REQUEST);
        }

        //Cerco l'utente con le credenziali fornite
        Utente utenteByEmailAndPassword = utenteService.eseguiAccesso(utenteLoginDTO.getEmail(), utenteLoginDTO.getPassword());

        //Se è null non esiste nessun utente con le credenziali fornite
        if(utenteByEmailAndPassword == null) {
            return new ResponseEntity<>(new ResponseDTO(null, "Non esiste nessun utente con le credenziali inserite"), NOT_FOUND);
        }


        List<Token> tokens = tokenService.caricaTokenByUtente(utenteByEmailAndPassword);
        Token token;

        if(tokens == null || tokens.isEmpty()) {
            token = new Token(LocalDateTime.now(), LocalDateTime.now().plusHours(2), true, utenteByEmailAndPassword);
            tokenService.inserisciToken(token);
            //Adesso il token esiste sicuramente
            token = tokenService.caricaTokenByUtente(utenteByEmailAndPassword).get(0);
        } else {
            //Se esiste almeno un token controllo che sia valido filtrando la lista dei token e prendendo il primo
            Optional<Token> optionalToken = tokens.stream().filter( tokenItem -> {
                    //Controllo che il token sia valido, se non lo è gli setto validTOken a false e lo salvo
                    if(!isValidToken(tokenItem)) {
                        tokenItem.setValidToken(false);
                        tokenService.modificaToken(tokenItem);
                        return false;
                    }
                    return true;
            }).findFirst();
            //Se esiste un token dal filtro sopra allora lo prendo, altrimenti ne creo uno nuovo.
            token = optionalToken.orElseGet(() -> optionalToken.orElse(null));

            //Il token va aggiornato con la scadenza dopo due ore rispetto
            if(token != null) {
                aggiornaScadenzaToken(token);
                tokenService.modificaToken(token);
                token = tokenService.caricaTokenByUtente(utenteByEmailAndPassword).get(0);
            } else {
                token = new Token(LocalDateTime.now(), LocalDateTime.now().plusHours(2), true, utenteByEmailAndPassword);
                tokenService.inserisciToken(token);
                token = tokenService.caricaTokenByUtente(utenteByEmailAndPassword).get(0);
            }
        }

        LoggedUserDTO loggedUserDTO = new LoggedUserDTO(utenteByEmailAndPassword.getId(), utenteByEmailAndPassword.getNome(),
                utenteByEmailAndPassword.getCognome(), utenteByEmailAndPassword.getEmail());


        return new ResponseEntity<>(new ResponseDTO(token, loggedUserDTO), OK);
    }


    /**
     * Metodo che esegue il logout di un utente a partire dal suo token valido. Se il token non è valido si riceve un 401 Unauthorized.
     * Se il token è valido, allora si recuperano tutti i token validi per quell'utente e li si invalidano.
     * @param requestLogoutDTO contiene le informazioni riguardo il token dell'utente collegato.
     * @return Una response contenente token null e Una risposta di successo. In caso di errore un codice di errore e una spiegazione.
     */
    @PostMapping("/logout")
    @Produces({MediaType.APPLICATION_JSON})
    public ResponseEntity<ResponseDTO> doLogout(@RequestBody RequestLogoutDTO requestLogoutDTO) {

        //Controllo che l'oggetto che arriva da FE sia valorizzato correttamente
        Map<String, String> errors = RequestLogoutDTO.validate(requestLogoutDTO);
        if(!errors.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO(null, errors), BAD_REQUEST);
        }

        //Prendo il token e controllo che sia valido.
        Token token = requestLogoutDTO.getToken();
        if(token == null || token.getId() == null) {
            return new ResponseEntity<>(new ResponseDTO(null, "Non si è autorizzati a questo servizio"), UNAUTHORIZED);
        }

        Token tokenByDB = tokenService.caricaToken(token.getId());
        if(!TokenUtility.isValidToken(tokenByDB)) {
            return new ResponseEntity<>(new ResponseDTO(null, "Non si è autorizzati a questo servizio"), UNAUTHORIZED);
        }

        //Recupero l'utente dal Token
        Utente utenteDelToken = tokenByDB.getUtente();
        if(utenteDelToken == null) {
            return new ResponseEntity<>(new ResponseDTO(null, "I dati sono incongruenti"), BAD_REQUEST);
        }

        //Recupero tutti i token attivi dell'utente e li invalido.
        List<Token> tokensForUser = tokenService.caricaTokenByUtente(utenteDelToken);
        tokensForUser.forEach(tokenItem -> {
            tokenItem.setValidToken(false);
            tokenService.modificaToken(tokenItem);
        });

        return new ResponseEntity<>(new ResponseDTO(null, "Logout avvenuto con successo"), OK);
    }
}