package com.projectalpha.ricettarioonline.service.token;


import com.projectalpha.ricettarioonline.models.Token;
import com.projectalpha.ricettarioonline.models.Utente;
import com.projectalpha.ricettarioonline.repository.token.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public List<Token> listAllToken() {
        return (List<Token>) tokenRepository.findAll();
    }

    @Override
    public List<Token> caricaTokenByUtente(Utente utente) {
        return tokenRepository.findByUtenteAndValidToken(utente);
    }

    @Override
    public Token caricaToken(long id) {
        Optional<Token> opt = tokenRepository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public void modificaToken(Token token) {
        tokenRepository.save(token);
    }

    @Override
    public void inserisciToken(Token token) {
        tokenRepository.save(token);
    }

    @Override
    public void eliminaToken(Token token) {
        tokenRepository.delete(token);
    }
}
