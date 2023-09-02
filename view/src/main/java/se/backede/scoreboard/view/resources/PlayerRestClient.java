/*
 */
package se.backede.scoreboard.view.resources;

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
import se.backede.scoreboard.view.resources.dto.Player;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Path("/rest-service/player")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(baseUri = "http://192.168.2.140:8083")
public interface PlayerRestClient {

    @GET
    List<Player> getAll();

    @GET
    Player getById(String id);

    @PUT
    Player update(Player dto);

    @POST
    Player create(Player dto);

    @DELETE
    @Path("{id}")
    Boolean delete(@PathParam(value = "id") String id);

}
