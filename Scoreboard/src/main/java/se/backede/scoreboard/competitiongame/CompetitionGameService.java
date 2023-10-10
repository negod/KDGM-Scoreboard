/*
 */
package se.backede.scoreboard.competitiongame;

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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.backede.scoreboard.common.service.GenericDto;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Path("competitiongame")
@ApplicationScoped
public class CompetitionGameService {

    @Inject
    CompetitionGameDao dao;

    final CompetitionGameMapper mapper = new CompetitionGameMapper();

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Getting all of class {0}", this.getClass().getSimpleName());
        return (Response) dao.getAll().map(x -> {
            List<CompetitionGameDto> dto = mapper.mapToDtoList((List) x);
            return Response.ok(dto).build();
        }).orElse(Response.noContent().build());
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam(value = "id") String id) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Getting {0} by id {1}", new Object[]{this.getClass().getSimpleName(), id});
        return (Response) dao.getById(id).map(x -> {
            GenericDto dto = mapper.mapToDto((CompetitionGameEntity) x);
            return Response.ok(dto).build();
        }).orElse(Response.noContent().build());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(CompetitionGameDto item) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Creating {0} values {1}", new Object[]{this.getClass().getSimpleName(), item.toString()});
        CompetitionGameEntity entity = mapper.mapToEntity(item);
        return (Response) dao.create(entity).map(x -> {
            GenericDto dto = mapper.mapToDto((CompetitionGameEntity) x);
            return Response.created(uriInfo.getAbsolutePath()).entity(dto).build();
        }).orElse(Response.serverError().build());
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(CompetitionGameDto item) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Updating {0} values {1}", new Object[]{this.getClass().getSimpleName(), item.toString()});
        CompetitionGameEntity entity = mapper.mapToEntity(item);
        entity = mapper.mapToEntity(item);
        return (Response) dao.update(entity).map(x -> {
            CompetitionGameDto dto = mapper.mapToDto((CompetitionGameEntity) x);
            return Response.ok(dto).build();
        }).orElse(Response.notModified().build());
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam(value = "id") String id) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Deleting {0} Id: {1}", new Object[]{this.getClass().getSimpleName(), id});
        return (Response) dao.delete(id).map(x -> {
            return Response.ok(x).build();
        }).orElse(Response.serverError().build());
    }

}
