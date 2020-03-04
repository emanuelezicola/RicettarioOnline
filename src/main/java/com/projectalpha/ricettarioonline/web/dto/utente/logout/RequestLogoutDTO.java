package com.projectalpha.ricettarioonline.web.dto.utente.logout;

import com.projectalpha.ricettarioonline.models.Token;
import com.projectalpha.ricettarioonline.web.dto.RequestDTO;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class RequestLogoutDTO extends RequestDTO {


    public RequestLogoutDTO() {
        super();
    }

    public RequestLogoutDTO(Token token) {
        super(token);
    }

    public static Map<String, String> validate(RequestLogoutDTO requestLogoutDTO) {
        Map<String, String> errors = new HashMap<>();

        if(requestLogoutDTO == null || requestLogoutDTO.token == null) {
            errors.put("ALL", "I dati sono incongruenti");
            return errors;
        }

        return errors;
    }

}