/*
 */
package se.backede.scoreboard.result;

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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;
import java.util.Optional;
import se.backede.scoreboard.common.GenericMapper;
import se.backede.scoreboard.common.dao.GenericCrudDao;
import se.backede.scoreboard.common.service.AbstractCrudService;

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
    @Path("game/{gameId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResultbyGame(@PathParam(value = "gameId") String gameId) {

        return (Response) dao.getResultsByGame(gameId).map(results -> {

            List<ResultDto> resultByGameDtoList = mapper.mapToDtoList(results);
            return Response.ok(resultByGameDtoList).build();

        }).orElse(Response.serverError().build());

    }

}