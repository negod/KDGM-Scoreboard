/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.backede.scoreboard.backend.service.player;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Optional;
import se.backede.scoreboard.backend.model.player.Player;
import se.backede.scoreboard.backend.model.player.dao.PlayerDao;

/**
 *
 * @author joaki
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

        return (Response) dao.getAll().map(x -> {
            return Response.ok(x).build();
        }).orElse(Response.noContent().build());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPlayer(Player player) {

        return (Response) dao.createPlayer(player).map(x -> {
            return Response.created(uriInfo.getAbsolutePath()).build();
        }).orElse(Response.serverError().build());
    }

}
