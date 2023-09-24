/*
 */
package se.backede.scoreboard.game;

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
@Path("game")
@ApplicationScoped
public class GameService extends AbstractCrudService<GameDto, GameEntity> {

    @Inject
    GameDao dao;

    final GenericMapper mapperr = new GameMapper();

    @Override
    public GenericMapper<GameDto, GameEntity> getMapper() {
        return mapperr;
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
