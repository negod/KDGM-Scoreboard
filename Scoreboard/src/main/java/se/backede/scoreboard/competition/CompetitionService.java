/*
 */
package se.backede.scoreboard.competition;

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
@Path("competition")
@ApplicationScoped
public class CompetitionService extends AbstractCrudService<CompetitionDto, CompetitionEntity> {

    @Inject
    CompetitionDao dao;

    GenericMapper<CompetitionDto, CompetitionEntity> mapper = new CompetitionMapper();

    @Override
    public GenericMapper<CompetitionDto, CompetitionEntity> getMapper() {
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
