package com.projectalpha.ricettarioonline.web.dto.utente;

import com.projectalpha.ricettarioonline.models.Utente;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public static List<String> validateDTO(RegistrazioneUtenteDTO registrazioneUtenteDTO) {
        List<String> errors = new ArrayList<>();

        if(registrazioneUtenteDTO == null) {
            errors.add("Ci sono incongruenze nei dati");
            return errors;
        }

        if(StringUtils.isBlank(registrazioneUtenteDTO.nome)) {
            errors.add("Il nome inserito non può essere vuoto");
        }

        if(StringUtils.isBlank(registrazioneUtenteDTO.cognome)) {
            errors.add("Il cognome inserito non può essere vuoto");
        }

        if(StringUtils.isBlank(registrazioneUtenteDTO.email)) {
            errors.add("La mail inserita può essere vuota");
        }

        if(StringUtils.isBlank(registrazioneUtenteDTO.password)) {
            errors.add("La password inserita non può essere vuota");
        }

        if(StringUtils.isBlank(registrazioneUtenteDTO.passwordRepeat)) {
            errors.add("La conferma della password non può essere vuota");
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
