package com.projectalpha.ricettarioonline.utils;

import com.projectalpha.ricettarioonline.models.Token;

import java.time.LocalDateTime;

@SuppressWarnings("unused")
public class TokenUtility {


    public static boolean isValidToken(Token token) {

        if (!token.getValidToken()) {
            return false;
        }
        return token.getDataScadenza().isAfter(LocalDateTime.now());

    }

    public static Token aggiornaScadenzaToken(Token token) {
        token.setDataScadenza(LocalDateTime.now().plusHours(2));
        return token;
    }
}
