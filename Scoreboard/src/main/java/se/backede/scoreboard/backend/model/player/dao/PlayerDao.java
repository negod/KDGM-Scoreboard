package se.backede.scoreboard.backend.model.player.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.backede.scoreboard.backend.common.PlayerConstants;
import se.backede.scoreboard.backend.model.player.Player;

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
        Logger.getLogger(PlayerDao.class.getName()).log(Level.INFO, "Getting all players");

        try {
            return Optional.ofNullable(em.createNamedQuery(PlayerConstants.QUERY_GET_ALL_PLAYERS, Player.class).getResultList());
        } catch (Exception e) {
            Logger.getLogger(PlayerDao.class.getName()).log(Level.SEVERE, "Error when getting all players", e);
        }
        return Optional.empty();
    }

    public Optional<Player> createPlayer(Player player) {
        Logger.getLogger(PlayerDao.class.getName()).log(Level.INFO, "Creating player", player.toString());

        try {
            em.persist(player);
            return Optional.of(player);
        } catch (Exception e) {
            Logger.getLogger(PlayerDao.class.getName()).log(Level.SEVERE, "Error when persisting player", e);
            return Optional.empty();
        }
    }

    public Optional<Player> getPlayerById(String id) {
        Logger.getLogger(PlayerDao.class.getName()).log(Level.INFO, "Gettting player with ID", id);

        try {
            Player find = em.find(Player.class, id);
            return Optional.ofNullable(find);
        } catch (Exception e) {
            Logger.getLogger(PlayerDao.class.getName()).log(Level.SEVERE, "Error when getting player by ID", e);
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<Boolean> deletePlayer(String id) {
        Logger.getLogger(PlayerDao.class.getName()).log(Level.INFO, "Deleting player with ID", id);

        try {

            Player playerToRemove = em.find(Player.class, id);

            Logger.getLogger(PlayerDao.class.getName()).log(Level.INFO, "Player to Remove", playerToRemove.toString());

            em.remove(playerToRemove);
            em.flush();

            Logger.getLogger(PlayerDao.class.getName()).log(Level.INFO, "Flushing");

            return Optional.of(Boolean.TRUE);
        } catch (Exception e) {
            Logger.getLogger(PlayerDao.class.getName()).log(Level.SEVERE, "Error when deleting player", e);
        }

        return Optional.empty();

    }

    public Optional<Player> updatePlayer(Player player) {
        Logger.getLogger(PlayerDao.class.getName()).log(Level.INFO, "Updating player", player.toString());
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
