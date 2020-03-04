package com.projectalpha.ricettarioonline.web.dto.utente.login;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class UtenteLoginDTO {

    private String email;
    private String password;

    public UtenteLoginDTO() {}

    public UtenteLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
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

    public static Map<String, String> validate(UtenteLoginDTO utenteLoginDTO) {
        Map<String, String> errors = new HashMap<>();

        if(utenteLoginDTO == null) {
            errors.put("ALL", "I dati sono incongruenti");
            return errors;
        }

        if(StringUtils.isBlank(utenteLoginDTO.email)) {
            errors.put("EMAIL", "La mail inserita non è valida");
        }

        if(StringUtils.isBlank(utenteLoginDTO.password)) {
            errors.put("PASSWORD", "La password inserita non è valida");
        }

        return errors;
    }

}
