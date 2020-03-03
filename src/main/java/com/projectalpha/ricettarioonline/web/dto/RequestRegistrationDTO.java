package com.projectalpha.ricettarioonline.web.dto;

import com.projectalpha.ricettarioonline.models.Token;
import com.projectalpha.ricettarioonline.web.dto.utente.RegistrazioneUtenteDTO;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class RequestRegistrationDTO {

    private Token token;
    private RegistrazioneUtenteDTO utenteDaRegistrare;

    public RequestRegistrationDTO(Token token, RegistrazioneUtenteDTO body) {
        this.token = token;
        this.utenteDaRegistrare = body;
    }

    public RequestRegistrationDTO() {
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public RegistrazioneUtenteDTO getUtenteDaRegistrare() {
        return utenteDaRegistrare;
    }

    public void setUtenteDaRegistrare(RegistrazioneUtenteDTO utenteDaRegistrare) {
        this.utenteDaRegistrare = utenteDaRegistrare;
    }


    public static List<String> validate(RequestRegistrationDTO requestRegistrationDTO) {
        List<String> errors = new ArrayList<>();

        if(requestRegistrationDTO == null || requestRegistrationDTO.token == null || requestRegistrationDTO.utenteDaRegistrare == null) {
            errors.add("I dati sono incongruenti");
        }
        return errors;
    }

    public static List<String> validateForRegistration(RequestRegistrationDTO requestRegistrationDTO) {
        List<String> errors = new ArrayList<>();

        if(requestRegistrationDTO == null || requestRegistrationDTO.utenteDaRegistrare == null) {
            errors.add("I dati sono incongruenti");
        }
        return errors;
    }

}
