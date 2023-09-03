package se.backede.scoreboard.boundary;

import se.backede.scoreboard.control.PlayerDao;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.backede.scoreboard.dto.PlayerDto;
import se.backede.scoreboard.dto.mapper.PlayerMapper;
import se.backede.scoreboard.entity.Player;

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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayers() {
        return (Response) dao.getAll().map(fetchedPlayers -> {

            List<PlayerDto> players = new ArrayList<>();

            for (Player player : fetchedPlayers) {
                players.add(PlayerMapper.mapToDto(player));
            }

            return Response.ok(players).build();
        }).orElse(Response.noContent().build());
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayerbyIdD(@PathParam(value = "id") String id) {

        return (Response) dao.getPlayerById(id).map(fetchedPlayer -> {
            return Response.ok(PlayerMapper.mapToDto(fetchedPlayer)).build();
        }).orElse(Response.noContent().build());

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPlayer(Player player) {

        return (Response) dao.createPlayer(player).map(x -> {
            return Response.created(uriInfo.getAbsolutePath()).entity(x).build();
        }).orElse(Response.serverError().build());

    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePlayer(Player player) {
        return (Response) dao.updatePlayer(player).map(fetchedPlayer -> {
            return Response.ok(PlayerMapper.mapToDto(fetchedPlayer)).build();
        }).orElse(Response.notModified().build());

    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlayer(@PathParam(value = "id") String id) {

        return (Response) dao.deletePlayer(id).map(x -> {
            return Response.ok(x).build();
        }).orElse(Response.serverError().build());
    }

}
