/*
 */
package se.backede.scoreboard.result;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.backede.scoreboard.common.GenericMapper;
import se.backede.scoreboard.common.dao.GenericCrudDao;
import se.backede.scoreboard.common.dao.GenericEntity;
import se.backede.scoreboard.common.service.AbstractCrudService;
import se.backede.scoreboard.common.service.GenericDto;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Path("result")
@ApplicationScoped
public class ResultService extends AbstractCrudService<ResultDto, ResultEntity> {

    @Inject
    ResultDao dao;

    GenericMapper<ResultDto, ResultEntity> mapper = new ResultMapper();

    @Override
    public GenericMapper<ResultDto, ResultEntity> getMapper() {
        return mapper;
    }

    @Override
    public Class getLoggerClass() {
        return this.getClass();
    }

    @Override
    public GenericCrudDao getDao() {
        return dao;
    }

    @GET
    @Path("match/{matchId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResultbyMatch(@PathParam(value = "matchId") String matchId) {

        return (Response) dao.getResultsByMatch(matchId).map(results -> {

            List<ResultDto> resultByMatchDtoList = mapper.mapToDtoList(results);
            return Response.ok(resultByMatchDtoList).build();

        }).orElse(Response.serverError().build());

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response create(ResultDto item) {
        Logger.getLogger(getLoggerClass().getName()).log(Level.INFO, "Creating {0} values {1}", new Object[]{getLoggerClass().getSimpleName(), item.toString()});
        GenericEntity entity = dao.mapToEntity(item);
        return (Response) getDao().create(entity).map(x -> {
            GenericDto dto = getMapper().mapToDto((ResultEntity) x);
            return Response.created(getUriInfo().getAbsolutePath()).entity(dto).build();
        }).orElse(Response.serverError().build());
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response update(ResultDto item) {
        Logger.getLogger(getLoggerClass().getName()).log(Level.INFO, "Updating {0} values {1}", new Object[]{getLoggerClass().getSimpleName(), item.toString()});
        GenericEntity entity = dao.mapToEntity(item);
        return (Response) getDao().update(entity).map(x -> {
            GenericDto dto = getMapper().mapToDto((ResultEntity) x);
            return Response.ok(dto).build();
        }).orElse(Response.notModified().build());
    }

}
