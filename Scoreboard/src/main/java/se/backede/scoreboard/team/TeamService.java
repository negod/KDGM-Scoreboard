/*
 */
package se.backede.scoreboard.team;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.backede.scoreboard.common.GenericMapper;
import se.backede.scoreboard.common.dao.GenericCrudDao;
import se.backede.scoreboard.common.service.AbstractCrudService;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Path("team")
@ApplicationScoped
public class TeamService extends AbstractCrudService<TeamDto, TeamEntity> {

    @Inject
    TeamDao dao;

    GenericMapper<TeamDto, TeamEntity> mapper = new TeamMapper();

    @Override
    public GenericMapper<TeamDto, TeamEntity> getMapper() {
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

    // TODO
    @PUT
    @Path("{TeamId}/{playerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlayer(@PathParam(value = "TeamId") String teamId, @PathParam(value = "playerId") String playerId) {

        return (Response) dao.addPlayer(teamId, playerId).map(x -> {
            return Response.ok(x).build();
        }).orElse(Response.notModified().build());

    }

}
