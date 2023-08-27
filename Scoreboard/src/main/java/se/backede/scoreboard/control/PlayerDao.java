package se.backede.scoreboard.control;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.backede.scoreboard.common.constants.PlayerConstants;
import se.backede.scoreboard.entity.Player;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@ApplicationScoped
@Transactional
public class PlayerDao {

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    public PlayerDao() {
    }

    public Optional<List<Player>> getAll() {

        try {
            return Optional.ofNullable(em.createNamedQuery(PlayerConstants.QUERY_GET_ALL_PLAYERS, Player.class).getResultList());
        } catch (Exception e) {
            Logger.getLogger(PlayerDao.class.getName()).log(Level.SEVERE, "Error when getting all players", e);
        }
        return Optional.empty();
    }

    public Optional<Player> createPlayer(Player player) {

        try {
            em.persist(player);
            return Optional.of(player);
        } catch (Exception e) {
            Logger.getLogger(PlayerDao.class.getName()).log(Level.SEVERE, "Error when persisting player", e);
            return Optional.empty();
        }
    }

    public Optional<Player> getPlayerById(String id) {

        try {
            Player find = em.find(Player.class, id);
            return Optional.ofNullable(find);
        } catch (Exception e) {
            Logger.getLogger(PlayerDao.class.getName()).log(Level.SEVERE, "Error when getting player by ID", e);
            return Optional.empty();
        }
    }

    public Optional<Boolean> deletePlayer(String id) {

        try {

            Player playerToRemove = em.find(Player.class, id);

            em.remove(playerToRemove);

            return Optional.of(Boolean.TRUE);
        } catch (Exception e) {
            Logger.getLogger(PlayerDao.class.getName()).log(Level.SEVERE, "Error when deleting player", e);
        }

        return Optional.empty();

    }

    public Optional<Player> updatePlayer(Player player) {
        try {
            Player find = em.find(Player.class, player.getId());
            find.setName(player.getName());
            em.merge(find);
            return Optional.ofNullable(find);
        } catch (Exception e) {
            Logger.getLogger(PlayerDao.class.getName()).log(Level.SEVERE, "Error when deleting player", e);
            return Optional.empty();
        }
    }

}
