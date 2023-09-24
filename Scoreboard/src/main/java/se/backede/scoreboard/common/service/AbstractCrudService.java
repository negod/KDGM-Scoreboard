/*
 */
package se.backede.scoreboard.common.service;

import jakarta.interceptor.Interceptors;
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
import se.backede.scoreboard.common.dao.GenericEntity;
import se.backede.scoreboard.common.GenericMapper;
import se.backede.scoreboard.common.dao.GenericCrudDao;
import se.backede.scoreboard.exception.ExceptionHandlingInterceptor;
import se.backede.scoreboard.exception.HandleException;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 * @param <D>
 * @param <E>
 */

@HandleException
@Interceptors(ExceptionHandlingInterceptor.class)
public abstract class AbstractCrudService<D extends GenericDto, E extends GenericEntity> {

    @Context
    private UriInfo uriInfo;

    public abstract GenericMapper<D, E> getMapper();

    public abstract Class getLoggerClass();

    public abstract GenericCrudDao getDao();

    public UriInfo getUriInfo() {
        return uriInfo;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        Logger.getLogger(getLoggerClass().getName()).log(Level.INFO, "Getting all of class {0}", getLoggerClass().getSimpleName());
        return (Response) getDao().getAll().map(x -> {
            List<D> dto = getMapper().mapToDtoList((List) x);
            return Response.ok(dto).build();
        }).orElse(Response.noContent().build());
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam(value = "id") String id) {
        Logger.getLogger(getLoggerClass().getName()).log(Level.INFO, "Getting {0} by id {1}", new Object[]{getLoggerClass().getSimpleName(), id});
        return (Response) getDao().getById(id).map(x -> {
            GenericDto dto = getMapper().mapToDto((E) x);
            return Response.ok(dto).build();
        }).orElse(Response.noContent().build());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(D item) {
        Logger.getLogger(getLoggerClass().getName()).log(Level.INFO, "Creating {0} values {1}", new Object[]{getLoggerClass().getSimpleName(), item.toString()});
        GenericEntity entity = getMapper().mapToEntity(item);
        return (Response) getDao().create(entity).map(x -> {
            GenericDto dto = getMapper().mapToDto((E) x);
            return Response.created(uriInfo.getAbsolutePath()).entity(dto).build();
        }).orElse(Response.serverError().build());
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(D item) {
        Logger.getLogger(getLoggerClass().getName()).log(Level.INFO, "Updating {0} values {1}", new Object[]{getLoggerClass().getSimpleName(), item.toString()});
        GenericEntity entity = getMapper().mapToEntity(item);
        return (Response) getDao().update(entity).map(x -> {
            GenericDto dto = getMapper().mapToDto((E) x);
            return Response.ok(dto).build();
        }).orElse(Response.notModified().build());
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam(value = "id") String id) {
        Logger.getLogger(getLoggerClass().getName()).log(Level.INFO, "Deleting {0} Id: {1}", new Object[]{getLoggerClass().getSimpleName(), id});
        return (Response) getDao().delete(id).map(x -> {
            return Response.ok(x).build();
        }).orElse(Response.serverError().build());
    }

}
