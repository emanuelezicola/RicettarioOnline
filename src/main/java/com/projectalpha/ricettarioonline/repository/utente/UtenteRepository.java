package com.projectalpha.ricettarioonline.repository.utente;

import com.projectalpha.ricettarioonline.models.Utente;
import org.springframework.data.repository.CrudRepository;

public interface UtenteRepository extends CrudRepository<Utente, Long> {

    Utente findByEmailAndPassword(String email, String password);

    Utente findByEmail(String email);

}
