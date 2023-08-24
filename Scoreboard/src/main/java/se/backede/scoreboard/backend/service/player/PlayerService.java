/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.backede.scoreboard.backend.service.player;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayers() {

        List<Player> all = dao.getAll();

        Logger.getLogger(PlayerService.class.getName()).log(Level.INFO, "testing");

        return Response.ok(all).build();
    }

}
