/*
 */
package se.backede.scoreboard.team;

import se.backede.scoreboard.player.PlayerDao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.backede.scoreboard.common.constants.TeamConstants;
import se.backede.scoreboard.common.dao.AbstractCrudDao;
import se.backede.scoreboard.player.PlayerEntity;

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

    @Override
    public Optional<Boolean> delete(String id) {

        Logger.getLogger(TeamDao.class.getName()).log(Level.INFO, "Deleting team");

        try {

            TeamEntity teamToRemove = getEntityManager().find(TeamEntity.class, id);

//            for (PlayerEntity player : teamToRemove.getPlayers()) {
//                playerDao.removeTeam(player.getId());
//            }
            for (PlayerEntity player : teamToRemove.getPlayers()) {
                player.setTeam(null);
            }

            getEntityManager().flush();

            getEntityManager().remove(teamToRemove);
            return Optional.of(Boolean.TRUE);

        } catch (ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            for (ConstraintViolation<?> violation : violations) {
                Logger.getLogger(TeamDao.class.getName()).log(Level.SEVERE, "Constraint Violation: {0} - {1}", new Object[]{violation.getPropertyPath(), violation.getMessage()});
            }
        } catch (Exception e) {
            Logger.getLogger(TeamDao.class.getName()).log(Level.SEVERE, "Error when deleting team", e);
        }

        return Optional.empty();

    }

    /*@Override
    public Optional<TeamEntity> update(TeamEntity team) {
        try {

            TypedQuery<TeamEntity> createNamedQuery = getEntityManager().createNamedQuery(TeamConstants.QUERY_UPDATE_TEAM, TeamEntity.class);
            createNamedQuery.setParameter(TeamConstants.TABLE_COLUMN_NAME, team.getName());
            createNamedQuery.setParameter(TeamConstants.TABLE_COLUMN_ID, team.getId());
            int executeUpdate = createNamedQuery.executeUpdate();

            return Optional.ofNullable(team);
        } catch (Exception e) {
            Logger.getLogger(TeamDao.class.getName()).log(Level.SEVERE, "Error when deleting team {0}", new Object[]{team.toString()});
            return Optional.empty();
        }
    }*/
    public Optional<TeamEntity> addPlayer(String teamId, String playerId) {
        try {
            TeamEntity team = getEntityManager().find(TeamEntity.class, teamId);
            PlayerEntity player = getEntityManager().find(PlayerEntity.class, playerId);

            team.getPlayers().add(player);

            getEntityManager().merge(team);
            return Optional.ofNullable(team);
        } catch (Exception e) {
            Logger.getLogger(TeamDao.class.getName()).log(Level.SEVERE, "Error when deleting player to Team", e);
            return Optional.empty();
        }
    }

}
