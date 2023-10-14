/*
 */
package se.backede.scoreboard.match;

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

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Path("match")
@ApplicationScoped
public class MatchService extends AbstractCrudService<MatchDto, MatchEntity> {

    @Inject
    MatchDao dao;

    GenericMapper<MatchDto, MatchEntity> mapper = new MatchMapper();

    @Override
    public GenericMapper<MatchDto, MatchEntity> getMapper() {
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
    public Response getMatchByCompetition(@PathParam(value = "competitionId") String competitionId) {

        return (Response) dao.getMatchByCompetition(competitionId).map(results -> {

            List<MatchDto> resultByCompetitionDtoList = mapper.mapToDtoList(results);
            return Response.ok(resultByCompetitionDtoList).build();

        }).orElse(Response.serverError().build());

    }

}
