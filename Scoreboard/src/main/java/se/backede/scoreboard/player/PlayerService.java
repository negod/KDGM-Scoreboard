package se.backede.scoreboard.player;

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
@Path("player")
@ApplicationScoped
public class PlayerService extends AbstractCrudService<PlayerDto, PlayerEntity> {

    @Inject
    PlayerDao dao;

    GenericMapper<PlayerDto, PlayerEntity> mapper = new PlayerMapper();

    @Override
    public GenericMapper<PlayerDto, PlayerEntity> getMapper() {
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
