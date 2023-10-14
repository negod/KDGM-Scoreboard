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
import se.backede.scoreboard.admin.resources.dto.Match;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Path("/rest-service/match")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(baseUri = "http://192.168.2.140:8083")
public interface MatchRestClient {

    @GET
    List<Match> getAll();

    @GET
    Match getById(String id);

    @PUT
    Match update(Match dto);

    @POST
    Match create(Match dto);

    @DELETE
    @Path("{id}")
    Boolean delete(@PathParam(value = "id") String id);

    @GET
    @Path("/competition/{competitionId}")
    List<Match> getByCompetition(@PathParam(value = "competitionId") String id);

}
