/*
 */
package se.backede.scoreboard.admin.resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import se.backede.scoreboard.admin.resources.dto.TeamName;
import se.backede.scoreboard.admin.resources.dto.Team;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Path("/rest-service/team")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "restEndpoint")
public interface TeamRestClient {

    @GET
    List<Team> getAll();

    @GET
    Team getById(String id);

    @PUT
    Team update(Team dto);

    @POST
    Team create(Team dto);

    @DELETE
    @Path("{id}")
    Boolean delete(@PathParam(value = "id") String id);

    @GET
    @Path("/teamname")
    List<TeamName> getAllTeamNames();

}
