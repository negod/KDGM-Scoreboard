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
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.backede.scoreboard.control.ResultDao;
import se.backede.scoreboard.dto.ResultByGameDto;
import se.backede.scoreboard.dto.mapper.ResultByGameMapper;
import se.backede.scoreboard.entity.Result;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Path("result")
@ApplicationScoped
public class ResultService {

    @Inject
    ResultDao dao;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResults() {
        return (Response) dao.getAll().map(x -> {
            return Response.ok(x).build();
        }).orElse(Response.serverError().build());
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResultbyIdD(@PathParam(value = "id") String id) {

        return (Response) dao.getResultById(id).map(x -> {
            return Response.ok(x).build();
        }).orElse(Response.serverError().build());

    }

    @GET
    @Path("game/{gameId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResultbyGame(@PathParam(value = "gameId") String gameId) {

        return (Response) dao.getResultsByGame(gameId).map(results -> {

            List<ResultByGameDto> resultByGameDtoList = ResultByGameMapper.getResultByGameDtoList(results);
            return Response.ok(resultByGameDtoList).build();

        }).orElse(Response.serverError().build());

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createResult(Result result) {

        Optional<String> gameId = Optional.of(result.getGame().getId());
        Optional<String> playerId = Optional.of(result.getPlayer().getId());

        if (gameId.isPresent() && playerId.isPresent()) {
            if (!gameId.isEmpty() && !playerId.isEmpty()) {

                return (Response) dao.createResult(result).map(x -> {
                    return Response.created(uriInfo.getAbsolutePath()).entity(x).build();
                }).orElse(Response.serverError().build());

            }
        }

        return Response.status(Status.BAD_REQUEST).build();

    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateResult(Result result) {

        return (Response) dao.updateResult(result).map(x -> {
            return Response.ok(x).build();
        }).orElse(Response.serverError().build());

    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteResult(@PathParam(value = "id") String id) {

        return (Response) dao.deleteResult(id).map(x -> {
            return Response.ok().build();
        }).orElse(Response.serverError().build());
    }

}
