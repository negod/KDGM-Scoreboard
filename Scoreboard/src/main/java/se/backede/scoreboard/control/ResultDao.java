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
import se.backede.scoreboard.common.constants.ResultConstants;
import se.backede.scoreboard.entity.Game;
import se.backede.scoreboard.entity.Player;
import se.backede.scoreboard.entity.Result;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@ApplicationScoped
@Transactional
public class ResultDao {

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    public ResultDao() {
    }

    public Optional<List<Result>> getAll() {

        try {
            return Optional.ofNullable(em.createNamedQuery(ResultConstants.QUERY_GET_ALL_RESULTS, Result.class).getResultList());
        } catch (Exception e) {
            Logger.getLogger(ResultDao.class.getName()).log(Level.SEVERE, "Error when getting all results", e);
        }
        return Optional.empty();
    }

    public Optional<Result> createResult(Result result) {

        try {
            em.persist(result);
            return Optional.of(result);
        } catch (Exception e) {
            Logger.getLogger(ResultDao.class.getName()).log(Level.SEVERE, "Error when persisting result", e);
            return Optional.empty();
        }
    }

    public Optional<Result> getResultById(String id) {

        try {
            Result find = em.find(Result.class, id);
            return Optional.ofNullable(find);
        } catch (Exception e) {
            Logger.getLogger(ResultDao.class.getName()).log(Level.SEVERE, "Error when getting result by ID", e);
            return Optional.empty();
        }
    }

    public Optional<Boolean> deleteResult(String id) {

        try {

            Result resultToRemove = em.find(Result.class, id);

            em.remove(resultToRemove);

            return Optional.of(Boolean.TRUE);
        } catch (Exception e) {
            Logger.getLogger(ResultDao.class.getName()).log(Level.SEVERE, "Error when deleting result", e);
        }

        return Optional.empty();

    }

    public Optional<Result> updateResult(Result result) {
        try {
            Result find = em.find(Result.class, result.getId());

            if (result.getGame() != null) {
                Game game = em.find(Game.class, result.getGame().getId());
                find.setGame(game);
            }

            if (result.getPlayer() != null) {
                Player player = em.find(Player.class, result.getPlayer().getId());
                find.setPlayer(player);
            }

            if (result.getScore() != null) {
                find.setScore(result.getScore());
            }

            if (result.getTime() != null) {
                find.setTime(result.getTime());
            }

            em.merge(find);
            return Optional.ofNullable(find);
        } catch (Exception e) {
            Logger.getLogger(ResultDao.class.getName()).log(Level.SEVERE, "Error when deleting result", e);
            return Optional.empty();
        }
    }

}
