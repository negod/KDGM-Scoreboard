/*
 */
package se.backede.scoreboard.control;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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

            em.remove(teamToRemove);

            return Optional.of(Boolean.TRUE);
        } catch (Exception e) {
            Logger.getLogger(TeamDao.class.getName()).log(Level.SEVERE, "Error when deleting team", e);
        }

        return Optional.empty();

    }

    public Optional<Team> updateTeam(Team team) {
        try {
            Team find = em.find(Team.class, team.getId());

            if (team.getName() != null) {
                find.setName(team.getName());
            }

            em.merge(find);
            return Optional.ofNullable(find);
        } catch (Exception e) {
            Logger.getLogger(TeamDao.class.getName()).log(Level.SEVERE, "Error when deleting team {0}", new Object[]{team.toString()});
            return Optional.empty();
        }
    }

    public Optional<Team> addPlayer(String teamId, String playerId) {
        try {
            Team team = em.find(Team.class, teamId);
            Player player = em.find(Player.class, playerId);

            player.setTeam(team);
            team.getPlayers().add(player);

            em.merge(team);
            return Optional.ofNullable(team);
        } catch (Exception e) {
            Logger.getLogger(TeamDao.class.getName()).log(Level.SEVERE, "Error when adding player to Team", e);
            return Optional.empty();
        }
    }

}
