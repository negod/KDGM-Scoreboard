/*
 */
package se.backede.scoreboard.control;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
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

            TypedQuery<Game> createNamedQuery = em.createNamedQuery(GameConstants.QUERY_UPDATE_GAME, Game.class);
            createNamedQuery.setParameter(GameConstants.TABLE_COLUMN_ID, game.getId());
            createNamedQuery.setParameter(GameConstants.TABLE_COLUMN_NAME, game.getName());
            createNamedQuery.setParameter(GameConstants.TABLE_COLUMN_GAMETYPE, game.getGametype());
            int executeUpdate = createNamedQuery.executeUpdate();

            return Optional.ofNullable(game);
        } catch (ConstraintViolationException e) {
            e.getConstraintViolations().forEach(err -> Logger.getLogger(GameDao.class.getName()).log(Level.SEVERE, "Constraint violation", e.toString()));
            return Optional.empty();
        } catch (Exception e) {
            Logger.getLogger(GameDao.class.getName()).log(Level.SEVERE, "Error when updating gameI", e);
            return Optional.empty();
        }
    }

}
