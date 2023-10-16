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
import se.backede.scoreboard.admin.resources.TeamRestClient;
import se.backede.scoreboard.admin.resources.dto.TeamName;
import se.backede.scoreboard.admin.resources.dto.Team;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@ApplicationScoped
public class TeamRestClientController implements GenericRestClient<Team> {

    @Inject
    @RestClient
    private TeamRestClient client;

    @Override
    public Optional<List<Team>> getAll() {
        try {
            return Optional.ofNullable(client.getAll());
        } catch (WebApplicationException e) {
            Logger.getLogger(TeamRestClientController.class.getName()).log(Level.SEVERE, "Error when getting all in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Team> getById(String id) {
        try {
            return Optional.ofNullable((Team) client.getById(id));
        } catch (WebApplicationException e) {
            Logger.getLogger(TeamRestClientController.class.getName()).log(Level.SEVERE, "Error when getting by id in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Team> update(Team dto) {
        try {
            return Optional.ofNullable((Team) client.update(dto));
        } catch (WebApplicationException e) {
            Logger.getLogger(TeamRestClientController.class.getName()).log(Level.SEVERE, "Error when updating in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Team> create(Team dto) {
        try {
            return Optional.ofNullable((Team) client.create(dto));
        } catch (WebApplicationException e) {
            Logger.getLogger(TeamRestClientController.class.getName()).log(Level.SEVERE, "Error when creating in RestClient", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Boolean> delete(String id) {
        try {
            return Optional.ofNullable(client.delete(id));
        } catch (WebApplicationException e) {
            Logger.getLogger(TeamRestClientController.class.getName()).log(Level.SEVERE, "Error when deleting all in RestClient", e);
            return Optional.empty();
        }
    }

    public Optional<List<TeamName>> getAllTeamNames() {
        try {
            return Optional.ofNullable(client.getAllTeamNames());
        } catch (WebApplicationException e) {
            Logger.getLogger(TeamRestClientController.class.getName()).log(Level.SEVERE, "Error when deleting all in RestClient", e);
            return Optional.empty();
        }
    }

}
