package com.projectalpha.ricettarioonline.web.dto;

import com.projectalpha.ricettarioonline.models.Token;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class RequestDTO {

    private Token token;
    private Object body;

    public RequestDTO(Token token, Object body) {
        this.token = token;
        this.body = body;
    }

    public RequestDTO() {
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }


    public static List<String> validate(RequestDTO requestDTO) {
        List<String> errors = new ArrayList<>();

        if(requestDTO == null || requestDTO.token == null || requestDTO.body == null) {
            errors.add("I dati sono incongruenti");
        }
        return errors;
    }

    public static List<String> validateForRegistration(RequestDTO requestDTO) {
        List<String> errors = new ArrayList<>();

        if(requestDTO == null || requestDTO.body == null) {
            errors.add("I dati sono incongruenti");
        }
        return errors;
    }

}
