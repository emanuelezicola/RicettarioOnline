package com.projectalpha.ricettarioonline.web.restcontroller;

import com.projectalpha.ricettarioonline.models.Token;
import com.projectalpha.ricettarioonline.models.Utente;
import com.projectalpha.ricettarioonline.service.token.TokenService;
import com.projectalpha.ricettarioonline.service.utente.UtenteService;
import com.projectalpha.ricettarioonline.utils.Status;
import com.projectalpha.ricettarioonline.web.dto.RequestDTO;
import com.projectalpha.ricettarioonline.web.dto.ResponseDTO;
import com.projectalpha.ricettarioonline.web.dto.utente.RegistrazioneUtenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import static com.projectalpha.ricettarioonline.utils.StringUtility.*;

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
     * Metodo che registra un nuovo utente.
     * @param requestDTO oggetto che contiene un body con le informazioni inserite dall'utente ed un token null
     * @return una response contenente uno status (BAD_REQUEST, INTERNAL_SERVER_ERROR, OK), un token (null oppure valorizzato)
     * e un body contenente una lista di errori o un oggetto utente.
     */
    @PostMapping("/registrazione")
    @Produces({MediaType.APPLICATION_JSON})
    public ResponseDTO registraNuovoUtente(@RequestBody RequestDTO requestDTO) {
        //Controllo che la response sia ok
        List<String> errors = RequestDTO.validateForRegistration(requestDTO);
        if (!errors.isEmpty()) {
            return new ResponseDTO(Status.BAD_REQUEST, null, errors);
        }

        //Controllo che il campo body della response sia ok.
        //Salto il controllo del token perchè in registrazione non esiste ancora il token.
        RegistrazioneUtenteDTO registrazioneUtenteDTO = (RegistrazioneUtenteDTO) requestDTO.getBody();
        errors = RegistrazioneUtenteDTO.validateDTO(registrazioneUtenteDTO);
        if (!errors.isEmpty()) {
            return new ResponseDTO(Status.BAD_REQUEST, null, errors);

        }

        //Pulisco la lista degli errori per sicurezza. Ora bisogna controllare che le informazioni siano inserite in modo corretto.
        errors.clear();

        //Controllo la mail
        if (!isValidEmail(registrazioneUtenteDTO.getEmail())) {
            errors.add("La mail inserita non è una mail valida. Prego inserire una mail corretta");
        }

        //Controllo il nome
        if (!containsOnlyLetters(registrazioneUtenteDTO.getNome())) {
            errors.add("Il nome inserito contiene caratteri non ammessi come numeri e caratteri speciali. " +
                    "Prego inserire solo caratteri alfabetici");
        }

        //Controllo il cognome
        if (!containsOnlyLetters(registrazioneUtenteDTO.getCognome())) {
            errors.add("Il cognome inserito contiene caratteri non ammessi come numeri e caratteri speciali. " +
                    "Prego inserire solo caratteri alfabetici");
        }

        //Controllo le password
        String password = new String(Base64.getDecoder().decode(registrazioneUtenteDTO.getPassword()));
        if (password.length() < 8) {
            errors.add("La password inserita è minore di otto caratteri");
        }

        if (!containsOnlyLettersAndNumbers(password)) {
            errors.add("La password contiene caratteri speciali non ammessi");
        }

        String passwordRepeat = new String(Base64.getDecoder().decode(registrazioneUtenteDTO.getPasswordRepeat()));
        if (!passwordRepeat.equals(password)) {
            errors.add("La password di conferma non coincide con la password inserita precedentemente");
        }

        if (!errors.isEmpty()) {
            return new ResponseDTO(Status.BAD_REQUEST, null, errors);
        }

        errors.clear();

        //controllo se ci sono utenti registrati con quella email
        Utente utenteByMail = utenteService.findByEmail(registrazioneUtenteDTO.getEmail());

        if (utenteByMail != null) {
            errors.add("La mail inserita è già in uso. Prego inserire una nuova email");
            return new ResponseDTO(Status.BAD_REQUEST, null, errors);
        }

        //Salvo l'utente sul DB
        Utente utenteDaRegistrare = registrazioneUtenteDTO.convertDTOToUtente();
        utenteDaRegistrare.setDataRegistrazione(LocalDate.now());
        utenteService.inserisciNuovo(utenteDaRegistrare);
        utenteByMail = utenteService.findByEmail(utenteDaRegistrare.getEmail());

        Token token = new Token(LocalDateTime.now(), LocalDateTime.now().plusHours(2), true, utenteByMail);
        tokenService.inserisciToken(token);
        List<Token> validTokens = tokenService.caricaTokenByUtente(utenteByMail);

        if(validTokens == null || validTokens.isEmpty()) {
            return new ResponseDTO(Status.INTERNAL_SERVER_ERROR, null, "Si è verificato un errore, contattare l'amministrazione");
        }
        token = validTokens.get(0);

        return new ResponseDTO(Status.OK, token, utenteByMail);
    }
}
