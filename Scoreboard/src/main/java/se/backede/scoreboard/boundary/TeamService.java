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
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import se.backede.scoreboard.control.TeamDao;
import se.backede.scoreboard.entity.Team;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Path("team")
@ApplicationScoped
public class TeamService {

    @Inject
    TeamDao dao;

    @Context
    private UriInfo uriInfo;

    @GET
    public Response getTeams() {
        return (Response) dao.getAll().map(x -> {
            return Response.ok(x).build();
        }).orElse(Response.noContent().build());
    }

    @GET
    @Path("{id}")
    public Response getTeambyIdD(@PathParam(value = "id") String id) {

        return (Response) dao.getTeamById(id).map(x -> {
            return Response.ok(x).build();
        }).orElse(Response.noContent().build());

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTeam(Team team) {

        return (Response) dao.createTeam(team).map(x -> {
            return Response.created(uriInfo.getAbsolutePath()).entity(x).build();
        }).orElse(Response.serverError().build());

    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTeam(Team team) {

        return (Response) dao.updateTeam(team).map(x -> {
            return Response.ok(x).build();
        }).orElse(Response.notModified().build());

    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteTeam(@PathParam(value = "id") String id) {

        return (Response) dao.deleteTeam(id).map(x -> {
            return Response.ok().build();
        }).orElse(Response.serverError().build());
    }

}
