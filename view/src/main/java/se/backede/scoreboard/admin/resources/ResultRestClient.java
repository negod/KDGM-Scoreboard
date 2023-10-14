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
import se.backede.scoreboard.admin.resources.dto.Result;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Path("/rest-service/result")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(baseUri = "http://192.168.2.140:8083")
public interface ResultRestClient {

    @GET
    List<Result> getAll();

    @GET
    Result getById(String id);

    @PUT
    Result update(Result dto);

    @POST
    Result create(Result dto);

    @DELETE
    @Path("{id}")
    Boolean delete(@PathParam(value = "id") String id);

    @GET
    @Path("/match/{matchId}")
    List<Result> getByMatch(@PathParam(value = "matchId") String id);

}
