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
import se.backede.scoreboard.view.resources.PlayerRestClient;
import se.backede.scoreboard.view.resources.dto.Player;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 * @param <Player>
 */
@ApplicationScoped
public class PlayerRestClientController implements GenericRestClient<Player> {

    @Inject
    @RestClient
    private PlayerRestClient client;

    @Override
    public Optional<List<Player>> getAll() {
        try {
            return Optional.ofNullable(client.getAll());
        } catch (WebApplicationException e) {
            Logger.getLogger(PlayerRestClientController.class.getName()).log(Level.SEVERE, "Error when getting all in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Player> getById(String id) {
        try {
            return Optional.ofNullable((Player) client.getById(id));
        } catch (WebApplicationException e) {
            Logger.getLogger(PlayerRestClientController.class.getName()).log(Level.SEVERE, "Error when getting by id in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Player> update(Player dto) {
        try {
            return Optional.ofNullable((Player) client.update(dto));
        } catch (WebApplicationException e) {
            Logger.getLogger(PlayerRestClientController.class.getName()).log(Level.SEVERE, "Error when updating in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Player> create(Player dto) {
        try {
            return Optional.ofNullable((Player) client.create(dto));
        } catch (WebApplicationException e) {
            Logger.getLogger(PlayerRestClientController.class.getName()).log(Level.SEVERE, "Error when creating in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Boolean> delete(String id) {
        try {
            return Optional.ofNullable(client.delete(id));
        } catch (WebApplicationException e) {
            Logger.getLogger(PlayerRestClientController.class.getName()).log(Level.SEVERE, "Error when deleting all in RestClient", e);
            return Optional.empty();
        }
    }

}
