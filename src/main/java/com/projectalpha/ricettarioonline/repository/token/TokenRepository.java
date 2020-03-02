package com.projectalpha.ricettarioonline.repository.token;

import com.projectalpha.ricettarioonline.models.Token;
import com.projectalpha.ricettarioonline.models.Utente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TokenRepository extends CrudRepository<Token, Long> {

    @Query("SELECT T FROM Token AS T WHERE T.utente = ?1 AND T.validToken = true")
    List<Token> findByUtenteAndValidToken(Utente utente);

}
