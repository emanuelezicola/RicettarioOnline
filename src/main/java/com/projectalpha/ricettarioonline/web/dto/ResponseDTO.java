package com.projectalpha.ricettarioonline.web.dto;

import com.projectalpha.ricettarioonline.models.Token;

@SuppressWarnings("unused")
public class ResponseDTO {

    private Token token;

    private Object body;

    public ResponseDTO(Token token, Object body) {
        this.token = token;
        this.body = body;
    }

    public ResponseDTO() {
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
}
