package com.projectalpha.ricettarioonline.web.dto;

import com.projectalpha.ricettarioonline.models.Token;
import com.projectalpha.ricettarioonline.utils.Status;

@SuppressWarnings("unused")
public class ResponseDTO {

    private Status status;

    private Token token;

    private Object body;

    public ResponseDTO(Status status, Token token, Object body) {
        this.status = status;
        this.token = token;
        this.body = body;
    }

    public ResponseDTO() {
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
