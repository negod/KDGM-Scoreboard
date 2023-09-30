package se.backede.scoreboard.player;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import se.backede.scoreboard.common.dao.AbstractCrudDao;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@ApplicationScoped
@Transactional
public class PlayerDao extends AbstractCrudDao<PlayerEntity> {

    @Override
    public Class getLoggerClass() {
        return this.getClass();
    }

    @Override
    public Class getEntityClass() {
        return PlayerEntity.class;
    }

}
