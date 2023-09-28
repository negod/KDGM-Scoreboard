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
import se.backede.scoreboard.admin.resources.CompetitionRestClient;
import se.backede.scoreboard.admin.resources.dto.Competition;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@ApplicationScoped
public class CompetitionRestClientController implements GenericRestClient<Competition> {

    @Inject
    @RestClient
    private CompetitionRestClient client;

    @Override
    public Optional<List<Competition>> getAll() {
        try {
            return Optional.ofNullable(client.getAll());
        } catch (Exception e) {
            Logger.getLogger(CompetitionRestClientController.class.getName()).log(Level.SEVERE, "Error when getting all in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Competition> getById(String id) {
        try {
            return Optional.ofNullable((Competition) client.getById(id));
        } catch (WebApplicationException e) {
            Logger.getLogger(CompetitionRestClientController.class.getName()).log(Level.SEVERE, "Error when getting by id in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Competition> update(Competition dto) {
        try {
            return Optional.ofNullable((Competition) client.update(dto));
        } catch (WebApplicationException e) {
            Logger.getLogger(CompetitionRestClientController.class.getName()).log(Level.SEVERE, "Error when updating in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Competition> create(Competition dto) {
        try {
            return Optional.ofNullable((Competition) client.create(dto));
        } catch (WebApplicationException e) {
            Logger.getLogger(CompetitionRestClientController.class.getName()).log(Level.SEVERE, "Error when creating in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Boolean> delete(String id) {
        try {
            return Optional.ofNullable(client.delete(id));
        } catch (WebApplicationException e) {
            Logger.getLogger(CompetitionRestClientController.class.getName()).log(Level.SEVERE, "Error when deleting all in RestClient", e);
            return Optional.empty();
        }
    }

}
