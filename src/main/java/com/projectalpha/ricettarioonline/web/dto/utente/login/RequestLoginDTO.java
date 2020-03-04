package com.projectalpha.ricettarioonline.web.dto.utente.login;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class RequestLoginDTO {

    private UtenteLoginDTO loggingUser;

    public RequestLoginDTO(UtenteLoginDTO loggingUser) {
        this.loggingUser = loggingUser;
    }

    public RequestLoginDTO() {}


    public UtenteLoginDTO getLoggingUser() {
        return loggingUser;
    }

    public void setLoggingUser(UtenteLoginDTO loggingUser) {
        this.loggingUser = loggingUser;
    }

    public static Map<String, String> validateForLogin(RequestLoginDTO requestLoginDTO) {
        Map<String, String> errors = new HashMap<>();

        if(requestLoginDTO == null) {
            errors.put("ALL", "I dati sono incongruenti");
            return errors;
        }

        if(requestLoginDTO.loggingUser == null) {
            errors.put("ALL", "Si è verificato un errore, contattare l'amministrazione");
        }

        return errors;
    }

}
