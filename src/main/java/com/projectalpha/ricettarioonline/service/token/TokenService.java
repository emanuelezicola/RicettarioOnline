package com.projectalpha.ricettarioonline.service.token;


import com.projectalpha.ricettarioonline.models.Token;
import com.projectalpha.ricettarioonline.models.Utente;

import java.util.List;

public interface TokenService {

    List<Token> listAllToken();

    List<Token> caricaTokenByUtente(Utente utente);

    Token caricaToken(long id);

    void modificaToken(Token token);

    void inserisciToken(Token token);

    void eliminaToken(Token token);
}
