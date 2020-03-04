package com.projectalpha.ricettarioonline.web.dto.utente.logout;

import com.projectalpha.ricettarioonline.models.Token;

@SuppressWarnings("unused")
public class RequestLogoutDTO {

    private Token token;
    private UtenteLogoutDTO loggingOutUser;

    public RequestLogoutDTO() {}

    public RequestLogoutDTO(Token token, UtenteLogoutDTO loggingOutUser) {
        this.token = token;
        this.loggingOutUser = loggingOutUser;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public UtenteLogoutDTO getLoggingOutUser() {
        return loggingOutUser;
    }

    public void setLoggingOutUser(UtenteLogoutDTO loggingOutUser) {
        this.loggingOutUser = loggingOutUser;
    }
}
