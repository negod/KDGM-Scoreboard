/*
 */
package se.backede.scoreboard.team;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
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

}
