/*
 */
package se.backede.scoreboard.boundary;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import se.backede.scoreboard.control.GameDao;
import se.backede.scoreboard.entity.Game;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */

@Path("game")
@ApplicationScoped
public class GameService {
    
    @Inject
    GameDao dao;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGames() {
        return (Response) dao.getAll().map(x -> {
            return Response.ok(x).build();
        }).orElse(Response.noContent().build());
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGamebyIdD(@PathParam(value = "id") String id) {

        return (Response) dao.getGameById(id).map(x -> {
            return Response.ok(x).build();
        }).orElse(Response.noContent().build());

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGame(Game game) {

        return (Response) dao.createGame(game).map(x -> {
            return Response.created(uriInfo.getAbsolutePath()).entity(x).build();
        }).orElse(Response.serverError().build());

    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGame(Game game) {

        return (Response) dao.updateGame(game).map(x -> {
            return Response.ok(x).build();
        }).orElse(Response.notModified().build());

    }



    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGame(@PathParam(value = "id") String id) {

        return (Response) dao.deleteGame(id).map(x -> {
            return Response.ok(x).build();
        }).orElse(Response.serverError().build());
    }
    
}
