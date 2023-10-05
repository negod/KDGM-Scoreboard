/*
 */
package se.backede.scoreboard.result;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import se.backede.scoreboard.common.GenericMapper;
import se.backede.scoreboard.common.dao.GenericCrudDao;
import se.backede.scoreboard.common.service.AbstractCrudService;
import se.backede.scoreboard.match.MatchDto;

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
    @Path("competition/{competitionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResultbyGame(@PathParam(value = "competitionId") String competitionId) {

        return (Response) dao.getResultsByCompetition(competitionId).map(results -> {

            List<ResultDto> resultByCompetitionDtoList = mapper.mapToDtoList(results);
            return Response.ok(resultByCompetitionDtoList).build();

        }).orElse(Response.serverError().build());

    }

}
