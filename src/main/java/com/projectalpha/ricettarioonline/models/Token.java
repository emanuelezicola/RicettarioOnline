package com.projectalpha.ricettarioonline.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@SuppressWarnings("unused")
@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime dataCreazione;

    /**
     * La data di scadenza è sempre la data di creazione più due ore
     */
    private LocalDateTime dataScadenza;
    private Boolean validToken;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    public Token() {
    }

    public Token(LocalDateTime dataCreazione, LocalDateTime dataScadenza, Boolean validToken, Utente utente) {
        this.dataCreazione = dataCreazione;
        this.dataScadenza = dataScadenza;
        this.validToken = validToken;
        this.utente = utente;
    }

    public Token(Long id, LocalDateTime dataCreazione, LocalDateTime dataScadenza, Boolean validToken, Utente utente) {
        this.id = id;
        this.dataCreazione = dataCreazione;
        this.dataScadenza = dataScadenza;
        this.validToken = validToken;
        this.utente = utente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(LocalDateTime dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public LocalDateTime getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDateTime dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public Boolean getValidToken() {
        return validToken;
    }

    public void setValidToken(Boolean validToken) {
        this.validToken = validToken;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

}
