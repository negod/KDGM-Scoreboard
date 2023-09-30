/*
 */
package se.backede.scoreboard.team;

import se.backede.scoreboard.player.PlayerDao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import se.backede.scoreboard.common.dao.AbstractCrudDao;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@ApplicationScoped
@Transactional
public class TeamDao extends AbstractCrudDao<TeamEntity> {

    @Inject
    PlayerDao playerDao;

    @Override
    public Class getLoggerClass() {
        return this.getClass();
    }

    @Override
    public Class getEntityClass() {
        return TeamEntity.class;
    }

}
