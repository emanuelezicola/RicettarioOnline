package com.projectalpha.ricettarioonline.service.utente;


import com.projectalpha.ricettarioonline.models.Utente;

import java.util.List;

@SuppressWarnings("unused")
public interface UtenteService {

    List<Utente> listAllUtenti() ;

    Utente caricaSingoloUtente(long id);

    void aggiorna(Utente utenteInstance);

    void inserisciNuovo(Utente utenteInstance);

    void rimuovi(Utente utenteInstance);

    List<Utente> findByExample(Utente example);

    Utente eseguiAccesso(String username, String password);

    Utente findByEmail(String email);

}
