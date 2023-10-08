/*
 */
package se.backede.scoreboard.admin.resources.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import se.backede.scoreboard.admin.commons.GenericRestClient;
import se.backede.scoreboard.admin.resources.CompetitionGameRestClient;
import se.backede.scoreboard.admin.resources.dto.CompetitionGame;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@ApplicationScoped
public class CompetitionGameRestClientController implements GenericRestClient<CompetitionGame> {

    @Inject
    @RestClient
    private CompetitionGameRestClient client;

    @Override
    public Optional<List<CompetitionGame>> getAll() {
        try {
            return Optional.ofNullable(client.getAll());
        } catch (Exception e) {
            Logger.getLogger(CompetitionGameRestClientController.class.getName()).log(Level.SEVERE, "Error when getting all in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<CompetitionGame> getById(String id) {
        try {
            return Optional.ofNullable((CompetitionGame) client.getById(id));
        } catch (WebApplicationException e) {
            Logger.getLogger(CompetitionGameRestClientController.class.getName()).log(Level.SEVERE, "Error when getting by id in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<CompetitionGame> update(CompetitionGame dto) {
        try {
            return Optional.ofNullable((CompetitionGame) client.update(dto));
        } catch (WebApplicationException e) {
            Logger.getLogger(CompetitionGameRestClientController.class.getName()).log(Level.SEVERE, "Error when updating in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<CompetitionGame> create(CompetitionGame dto) {
        try {
            return Optional.ofNullable((CompetitionGame) client.create(dto));
        } catch (WebApplicationException e) {
            Logger.getLogger(CompetitionGameRestClientController.class.getName()).log(Level.SEVERE, "Error when creating in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Boolean> delete(String id) {
        try {
            return Optional.ofNullable(client.delete(id));
        } catch (WebApplicationException e) {
            Logger.getLogger(CompetitionGameRestClientController.class.getName()).log(Level.SEVERE, "Error when deleting all in RestClient", e);
            return Optional.empty();
        }
    }

}
