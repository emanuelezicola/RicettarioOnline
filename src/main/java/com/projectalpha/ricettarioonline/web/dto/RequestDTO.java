package com.projectalpha.ricettarioonline.web.dto;

import com.projectalpha.ricettarioonline.models.Token;

@SuppressWarnings("unused")
public abstract class RequestDTO {

    protected Token token;

    public RequestDTO() {
    }

    public RequestDTO(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
