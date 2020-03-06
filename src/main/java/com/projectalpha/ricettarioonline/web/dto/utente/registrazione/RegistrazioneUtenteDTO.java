package com.projectalpha.ricettarioonline.web.dto.utente.registrazione;

import com.projectalpha.ricettarioonline.exceptions.DecodingPasswordException;
import com.projectalpha.ricettarioonline.models.Utente;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.projectalpha.ricettarioonline.utils.StringUtility.containsOnlyLetters;
import static com.projectalpha.ricettarioonline.utils.StringUtility.containsOnlyLettersAndNumbers;
import static com.projectalpha.ricettarioonline.utils.StringUtility.isValidEmail;

@SuppressWarnings("unused")
public class RegistrazioneUtenteDTO {

    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String passwordRepeat;

    public RegistrazioneUtenteDTO(String nome, String cognome, String email, String password, String passwordRepeat) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.passwordRepeat = passwordRepeat;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    /**
     * Metodo statico che controlla se i campi del DTO sono null o vuoti.
     * @param registrazioneUtenteDTO contiene il DTO con in campi inseriti dall'utente in pagina.
     * @return una List<String> contente gli eventuali errori riscontrati.
     */
    public static Map<String, String> validateDTO(RegistrazioneUtenteDTO registrazioneUtenteDTO) throws DecodingPasswordException {
        Map<String, String> errors = new HashMap<>();

        if(registrazioneUtenteDTO == null) {
            errors.put("ALL", "Ci sono incongruenze nei dati");
            return errors;
        }

        //Controllo null pointer o campi vuoti
        if(StringUtils.isBlank(registrazioneUtenteDTO.nome)) {
            errors.put("NOME", "Il nome inserito non può essere vuoto");
        }

        if(StringUtils.isBlank(registrazioneUtenteDTO.cognome)) {
            errors.put("COGNOME", "Il cognome inserito non può essere vuoto");
        }

        if(StringUtils.isBlank(registrazioneUtenteDTO.email)) {
            errors.put("EMAIL", "La mail inserita può essere vuota");
        }

        if(StringUtils.isBlank(registrazioneUtenteDTO.password)) {
            errors.put("PASSWORD", "La password inserita non può essere vuota");
        }

        if(StringUtils.isBlank(registrazioneUtenteDTO.passwordRepeat)) {
            errors.put("PASSWORD_CONF", "La conferma della password non può essere vuota");
        }
        //Fine controllo null pointer o campi vuoti

        //Controllo integrità campi
        //Controllo la mail
        if (!isValidEmail(registrazioneUtenteDTO.email)) {
            errors.put("EMAIL", "La mail inserita non è una mail valida. Prego inserire una mail corretta");
        }

        //Controllo il nome
        if (!containsOnlyLetters(registrazioneUtenteDTO.nome)) {
            errors.put("NOME", "Il nome inserito contiene caratteri non ammessi come numeri e caratteri speciali. " +
                    "Prego inserire solo caratteri alfabetici");
        }

        //Controllo il cognome
        if (!containsOnlyLetters(registrazioneUtenteDTO.cognome)) {
            errors.put("COGNOME", "Il cognome inserito contiene caratteri non ammessi come numeri e caratteri speciali. " +
                    "Prego inserire solo caratteri alfabetici");
        }

        String password;
        //Controllo le password
        try {
            password = new String(Base64.getDecoder().decode(registrazioneUtenteDTO.password));
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
            throw new DecodingPasswordException("Errore durante la decodifica della password");
        }
        if (password.length() < 8) {
            errors.put("PASSWORD", "La password inserita è minore di otto caratteri");
        }

        if (!containsOnlyLettersAndNumbers(password)) {
            errors.put("PASSWORD", "La password contiene caratteri speciali non ammessi");
        }

        String passwordRepeat;
        try {
            passwordRepeat = new String(Base64.getDecoder().decode(registrazioneUtenteDTO.passwordRepeat));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new DecodingPasswordException("Errore durante la decodifica della password");
        }
        if (!passwordRepeat.equals(password)) {
            errors.put("PASSWORD_CONF", "La password di conferma non coincide con la password inserita precedentemente");
        }

        return errors;
    }

    /**
     * Metodo che converte da DTO a Utente
     * @return un utente con i valori del DTO. La data di creazione deve essere valorizzata altrove.
     */
    public Utente convertDTOToUtente() {
        return new Utente(nome, cognome, email, password, null);
    }
}
