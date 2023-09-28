package se.backede.scoreboard.player;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.backede.scoreboard.common.constants.PlayerConstants;
import se.backede.scoreboard.common.dao.AbstractCrudDao;
import se.backede.scoreboard.team.TeamEntity;

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

    public Optional<PlayerEntity> removeTeam(String playerId) {

        getEntityManager().createNamedQuery(PlayerConstants.QUERY_DELETE_TEAM, PlayerEntity.class)
                .setParameter(PlayerConstants.TABLE_COLUMN_ID, playerId)
                .executeUpdate();
        getEntityManager().flush();

        return Optional.ofNullable(getEntityManager().find(PlayerEntity.class, playerId));

    }

    /*@Override
    public Optional<PlayerEntity> update(PlayerEntity player) {
        try {

            TeamEntity team = null;

            if (player.getTeam() != null) {
                team = getEntityManager().find(TeamEntity.class, player.getTeam().getId());
            }

            TypedQuery<PlayerEntity> createNamedQuery = getEntityManager().createNamedQuery(PlayerConstants.QUERY_UPDATE_PLAYER, PlayerEntity.class);
            createNamedQuery.setParameter(PlayerConstants.TABLE_COLUMN_NAME, player.getName());
            createNamedQuery.setParameter(PlayerConstants.TABLE_COLUMN_ID, player.getId());
            createNamedQuery.setParameter(PlayerConstants.TABLE_COLUMN_TEAM, team);
            createNamedQuery.setParameter(PlayerConstants.TABLE_COLUMN_NICK_NAME, player.getNickName());
            int executeUpdate = createNamedQuery.executeUpdate();

            return Optional.ofNullable(player);
        } catch (ConstraintViolationException e) {
            e.getConstraintViolations().forEach(err -> Logger.getLogger(PlayerDao.class.getName()).log(Level.SEVERE, "Constraint violation", e.toString()));
            return Optional.empty();
        } catch (Exception e) {
            Logger.getLogger(PlayerDao.class.getName()).log(Level.SEVERE, "Error when updating player", e);
            return Optional.empty();
        }

    }*/

}
