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
import se.backede.scoreboard.admin.resources.dto.CompetitionGame;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Path("/rest-service/competitiongame")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(baseUri = "http://192.168.2.140:8083")
//@RegisterRestClient(baseUri = "https://08f7998b-377a-4e55-bf0f-a34286b8a290.mock.pstmn.io")
public interface CompetitionGameRestClient {

    @GET
    List<CompetitionGame> getAll();

    @GET
    CompetitionGame getById(String id);

    @PUT
    CompetitionGame update(CompetitionGame dto);

    @POST
    CompetitionGame create(CompetitionGame dto);

    @DELETE
    @Path("{id}")
    Boolean delete(@PathParam(value = "id") String id);

}
