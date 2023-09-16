/*
 */
package se.backede.scoreboard.view.resources.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import se.backede.scoreboard.view.commons.GenericRestClient;
import se.backede.scoreboard.view.resources.GameRestClient;
import se.backede.scoreboard.view.resources.dto.Game;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@ApplicationScoped
public class GameRestClientController implements GenericRestClient<Game> {

    @Inject
    @RestClient
    private GameRestClient client;

    @Override
    public Optional<List<Game>> getAll() {
        try {
            return Optional.ofNullable(client.getAll());
        } catch (Exception e) {
            Logger.getLogger(GameRestClientController.class.getName()).log(Level.SEVERE, "Error when getting all in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Game> getById(String id) {
        try {
            return Optional.ofNullable((Game) client.getById(id));
        } catch (WebApplicationException e) {
            Logger.getLogger(GameRestClientController.class.getName()).log(Level.SEVERE, "Error when getting by id in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Game> update(Game dto) {
        try {
            return Optional.ofNullable((Game) client.update(dto));
        } catch (WebApplicationException e) {
            Logger.getLogger(GameRestClientController.class.getName()).log(Level.SEVERE, "Error when updating in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Game> create(Game dto) {
        try {
            return Optional.ofNullable((Game) client.create(dto));
        } catch (WebApplicationException e) {
            Logger.getLogger(GameRestClientController.class.getName()).log(Level.SEVERE, "Error when creating in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Boolean> delete(String id) {
        try {
            return Optional.ofNullable(client.delete(id));
        } catch (WebApplicationException e) {
            Logger.getLogger(GameRestClientController.class.getName()).log(Level.SEVERE, "Error when deleting all in RestClient", e);
            return Optional.empty();
        }
    }

}
