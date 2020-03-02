package com.projectalpha.ricettarioonline.models;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@SuppressWarnings("unused")
@Entity
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private LocalDate dataRegistrazione;


    public Utente(Long id, String nome, String cognome, String email, String password, LocalDate dataRegistrazione) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.dataRegistrazione = dataRegistrazione;
    }

    public Utente(String nome, String cognome, String email, String password, LocalDate dataRegistrazione) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.dataRegistrazione = dataRegistrazione;
    }

    public Utente() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDataRegistrazione() {
        return dataRegistrazione;
    }

    public void setDataRegistrazione(LocalDate dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if(obj instanceof Utente) {
            Utente utenteDaConfrontare = (Utente) obj;
            if(StringUtils.isBlank(utenteDaConfrontare.email)) {
                return false;
            }
            return utenteDaConfrontare.email.equals(this.email);
        }
        return false;
    }
}
