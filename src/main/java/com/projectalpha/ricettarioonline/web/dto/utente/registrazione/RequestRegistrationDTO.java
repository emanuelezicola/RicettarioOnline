package com.projectalpha.ricettarioonline.web.dto.utente.registrazione;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class RequestRegistrationDTO {

    private RegistrazioneUtenteDTO utenteDaRegistrare;

    public RequestRegistrationDTO(RegistrazioneUtenteDTO body) {
        this.utenteDaRegistrare = body;
    }

    public RequestRegistrationDTO() {
    }

    public RegistrazioneUtenteDTO getUtenteDaRegistrare() {
        return utenteDaRegistrare;
    }

    public void setUtenteDaRegistrare(RegistrazioneUtenteDTO utenteDaRegistrare) {
        this.utenteDaRegistrare = utenteDaRegistrare;
    }


    public static Map<String, String> validate(RequestRegistrationDTO requestRegistrationDTO) {
        Map<String, String> errors = new HashMap<>();

        if(requestRegistrationDTO == null || requestRegistrationDTO.utenteDaRegistrare == null) {
            errors.put("ALL", "I dati sono incongruenti");
        }
        return errors;
    }

    public static Map<String, String> validateForRegistration(RequestRegistrationDTO requestRegistrationDTO) {
        Map<String, String> errors = new HashMap<>();

        if(requestRegistrationDTO == null || requestRegistrationDTO.utenteDaRegistrare == null) {
            errors.put("ALL", "I dati sono incongruenti");
        }
        return errors;
    }

}
