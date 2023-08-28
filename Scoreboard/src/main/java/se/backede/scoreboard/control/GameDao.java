/*
 */
package se.backede.scoreboard.control;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.backede.scoreboard.common.constants.GameConstants;
import se.backede.scoreboard.entity.Game;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@ApplicationScoped
@Transactional
public class GameDao {

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    public Optional<List<Game>> getAll() {

        try {
            return Optional.ofNullable(em.createNamedQuery(GameConstants.QUERY_GET_ALL_GAMES, Game.class).getResultList());
        } catch (Exception e) {
            Logger.getLogger(GameDao.class.getName()).log(Level.SEVERE, "Error when getting all games", e);
        }
        return Optional.empty();
    }

    public Optional<Game> createGame(Game game) {

        try {
            em.persist(game);
            return Optional.of(game);
        } catch (Exception e) {
            Logger.getLogger(GameDao.class.getName()).log(Level.SEVERE, "Error when persisting game", e);
            return Optional.empty();
        }
    }

    public Optional<Game> getGameById(String id) {

        try {
            Game find = em.find(Game.class, id);
            return Optional.ofNullable(find);
        } catch (Exception e) {
            Logger.getLogger(GameDao.class.getName()).log(Level.SEVERE, "Error when getting game by ID", e);
            return Optional.empty();
        }
    }

    public Optional<Boolean> deleteGame(String id) {

        try {

            Game gameToRemove = em.find(Game.class, id);

            em.remove(gameToRemove);

            return Optional.of(Boolean.TRUE);
        } catch (Exception e) {
            Logger.getLogger(GameDao.class.getName()).log(Level.SEVERE, "Error when deleting game", e);
        }

        return Optional.empty();

    }

    public Optional<Game> updateGame(Game game) {
        try {
            Game find = em.find(Game.class, game.getId());

            if (game.getName() != null) {
                find.setName(game.getName());
            }
            if (game.getGametype() != null) {
                find.setGametype(game.getGametype());
            }

            em.merge(find);
            return Optional.ofNullable(find);
        } catch (Exception e) {
            Logger.getLogger(GameDao.class.getName()).log(Level.SEVERE, "Error when deleting game", e);
            return Optional.empty();
        }
    }

}
