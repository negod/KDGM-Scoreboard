/*
 */
package se.backede.scoreboard.control;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.backede.scoreboard.common.constants.PlayerConstants;
import se.backede.scoreboard.common.constants.TeamConstants;
import se.backede.scoreboard.entity.Player;
import se.backede.scoreboard.entity.Team;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@ApplicationScoped
@Transactional
public class TeamDao {

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @Inject
    PlayerDao playerDao;

    public TeamDao() {
    }

    public Optional<List<Team>> getAll() {

        try {
            return Optional.ofNullable(em.createNamedQuery(TeamConstants.QUERY_GET_ALL_TEAMS, Team.class).getResultList());
        } catch (Exception e) {
            Logger.getLogger(TeamDao.class.getName()).log(Level.SEVERE, "Error when getting all teams", e);
        }
        return Optional.empty();
    }

    public Optional<Team> createTeam(Team team) {

        try {
            em.persist(team);
            return Optional.of(team);
        } catch (Exception e) {
            Logger.getLogger(TeamDao.class.getName()).log(Level.SEVERE, "Error when persisting team", e);
            return Optional.empty();
        }
    }

    public Optional<Team> getTeamById(String id) {

        try {
            Team find = em.find(Team.class, id);
            return Optional.ofNullable(find);
        } catch (Exception e) {
            Logger.getLogger(TeamDao.class.getName()).log(Level.SEVERE, "Error when getting team by ID", e);
            return Optional.empty();
        }
    }

    public Optional<Boolean> deleteTeam(String id) {

        try {
            Team teamToRemove = em.find(Team.class, id);

            for (Player player : teamToRemove.getPlayers()) {
                playerDao.removeTeam(player.getId());
            }

            teamToRemove.setPlayers(null);
            em.remove(teamToRemove);
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

    public Optional<Team> updateTeam(Team team) {
        try {

            TypedQuery<Team> createNamedQuery = em.createNamedQuery(TeamConstants.QUERY_UPDATE_TEAM, Team.class);
            createNamedQuery.setParameter(TeamConstants.TABLE_COLUMN_NAME, team.getName());
            createNamedQuery.setParameter(TeamConstants.TABLE_COLUMN_ID, team.getId());
            int executeUpdate = createNamedQuery.executeUpdate();

            return Optional.ofNullable(team);
        } catch (Exception e) {
            Logger.getLogger(TeamDao.class.getName()).log(Level.SEVERE, "Error when deleting team {0}", new Object[]{team.toString()});
            return Optional.empty();
        }
    }

    public Optional<Team> addPlayer(String teamId, String playerId) {
        try {
            Team team = em.find(Team.class, teamId);
            Player player = em.find(Player.class, playerId);

            team.getPlayers().add(player);

            em.merge(team);
            return Optional.ofNullable(team);
        } catch (Exception e) {
            Logger.getLogger(TeamDao.class.getName()).log(Level.SEVERE, "Error when deleting player to Team", e);
            return Optional.empty();
        }
    }

}
