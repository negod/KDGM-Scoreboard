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
import se.backede.scoreboard.admin.resources.ResultRestClient;
import se.backede.scoreboard.admin.resources.dto.Result;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@ApplicationScoped
public class ResultRestClientController implements GenericRestClient<Result> {

    @Inject
    @RestClient
    private ResultRestClient client;

    @Override
    public Optional<List<Result>> getAll() {
        try {
            return Optional.ofNullable(client.getAll());
        } catch (WebApplicationException e) {
            Logger.getLogger(ResultRestClientController.class.getName()).log(Level.SEVERE, "Error when getting all in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Result> getById(String id) {
        try {
            return Optional.ofNullable((Result) client.getById(id));
        } catch (WebApplicationException e) {
            Logger.getLogger(ResultRestClientController.class.getName()).log(Level.SEVERE, "Error when getting by id in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Result> update(Result dto) {
        try {
            return Optional.ofNullable((Result) client.update(dto));
        } catch (WebApplicationException e) {
            Logger.getLogger(ResultRestClientController.class.getName()).log(Level.SEVERE, "Error when updating in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Result> create(Result dto) {
        try {
            return Optional.ofNullable((Result) client.create(dto));
        } catch (WebApplicationException e) {
            Logger.getLogger(ResultRestClientController.class.getName()).log(Level.SEVERE, "Error when creating in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Boolean> delete(String id) {
        try {
            return Optional.ofNullable(client.delete(id));
        } catch (WebApplicationException e) {
            Logger.getLogger(ResultRestClientController.class.getName()).log(Level.SEVERE, "Error when deleting all in RestClient", e);
            return Optional.empty();
        }
    }

    public Optional<List<Result>> getByMatch(String competitionId) {
        try {
            return Optional.ofNullable(client.getByMatch(competitionId));
        } catch (WebApplicationException e) {
            Logger.getLogger(MatchRestClientController.class.getName()).log(Level.SEVERE, "Error when deleting all in RestClient", e);
            return Optional.empty();
        }
    }

}
