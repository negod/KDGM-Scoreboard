/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.backede.scoreboard.backend.service.player;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author joaki
 */
@Path("/player")
public class PlayerService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        // Return a list of users in JSON format
        // You can implement this part according to your needs
        return Response.ok("[]").build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(String userJson) {
        // Create a new user based on the provided JSON data
        // You can implement this part according to your needs
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(String userJson) {
        // Create a new user based on the provided JSON data
        // You can implement this part according to your needs
        return Response.status(Response.Status.CREATED).build();
    }

}
