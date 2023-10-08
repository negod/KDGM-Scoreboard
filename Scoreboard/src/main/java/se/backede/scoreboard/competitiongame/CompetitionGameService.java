/*
 */
package se.backede.scoreboard.competitiongame;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import se.backede.scoreboard.common.service.AbstractCrudService;
import se.backede.scoreboard.common.dao.GenericCrudDao;
import se.backede.scoreboard.common.GenericMapper;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Path("competitiongame")
@ApplicationScoped
public class CompetitionGameService extends AbstractCrudService<CompetitionGameDto, CompetitionGameEntity> {

    @Inject
    CompetitionGameDao dao;

    final GenericMapper mapper = new CompeitionGameMapper();

    @Override
    public GenericMapper<CompetitionGameDto, CompetitionGameEntity> getMapper() {
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
