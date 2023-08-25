package se.backede.scoreboard.backend.service.player;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.backede.scoreboard.backend.model.player.Player;
import se.backede.scoreboard.backend.model.player.dao.PlayerDao;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Path("player")
@ApplicationScoped
public class PlayerService {

    @Inject
    PlayerDao dao;

    @Context
    private UriInfo uriInfo;

    @GET
    public Response getPlayers() {
        return (Response) dao.getAll().map(x -> {
            return Response.ok(x).build();
        }).orElse(Response.noContent().build());
    }

    @GET
    @Path("/{id}")
    public Response getPlayerbyIdD(@PathParam(value = "id") String id) {

        return (Response) dao.getPlayerById(id).map(x -> {
            return Response.ok(x).build();
        }).orElse(Response.noContent().build());

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPlayer(Player player) {

        return (Response) dao.createPlayer(player).map(x -> {
            return Response.created(uriInfo.getAbsolutePath()).entity(x).build();
        }).orElse(Response.serverError().build());

    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePlayer(Player player) {

        return (Response) dao.updatePlayer(player).map(x -> {
            return Response.ok(x).build();
        }).orElse(Response.notModified().build());

    }

    @DELETE
    @Path("/{id}")
    public Response deletePlayer(@PathParam(value = "id") String id) {
        Logger.getLogger(PlayerService.class.getName()).log(Level.FINEST, "Deleting player with ID", id);

        return (Response) dao.deletePlayer(id).map(x -> {
            return Response.ok().build();
        }).orElse(Response.serverError().build());
    }

}
