/*
 */
package se.backede.scoreboard.result;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.backede.scoreboard.common.constants.ResultConstants;
import se.backede.scoreboard.common.dao.AbstractCrudDao;
import se.backede.scoreboard.game.GameEntity;
import se.backede.scoreboard.player.PlayerEntity;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@ApplicationScoped
@Transactional
public class ResultDao extends AbstractCrudDao<ResultEntity> {

    @Override
    public Class getLoggerClass() {
        return this.getClass();
    }

    @Override
    public Class getEntityClass() {
        return ResultEntity.class;
    }

    @Override
    public Optional<ResultEntity> create(ResultEntity result) {

        try {

            GameEntity game = getEntityManager().find(GameEntity.class, result.getGame().getId());
            PlayerEntity player = getEntityManager().find(PlayerEntity.class, result.getPlayer().getId());

            result.setPlayer(player);
            result.setGame(game);

            getEntityManager().persist(result);
            return Optional.of(result);
        } catch (Exception e) {
            Logger.getLogger(ResultDao.class.getName()).log(Level.SEVERE, "Error when persisting Entity Result {0} Exception {1}", new Object[]{result.toString(), e});
            return Optional.empty();
        }
    }

    @Override
    public Optional<ResultEntity> update(ResultEntity result) {
        try {
            ResultEntity find = getEntityManager().find(ResultEntity.class, result.getId());

            if (result.getGame() != null) {
                GameEntity game = getEntityManager().find(GameEntity.class, result.getGame().getId());
                find.setGame(game);
            }

            if (result.getPlayer() != null) {
                PlayerEntity player = getEntityManager().find(PlayerEntity.class, result.getPlayer().getId());
                find.setPlayer(player);
            }

            if (result.getScore() != null) {
                find.setScore(result.getScore());
            }

            if (result.getTime() != null) {
                find.setTime(result.getTime());
            }

            getEntityManager().merge(find);
            return Optional.ofNullable(find);
        } catch (Exception e) {
            Logger.getLogger(ResultDao.class.getName()).log(Level.SEVERE, "Error when updating result", e);
            return Optional.empty();
        }
    }

    public Optional<List<ResultEntity>> getResultsByGame(String gameId) {
        Logger.getLogger(ResultDao.class.getName()).log(Level.INFO, "Getting Results By Game {0}", new Object[]{gameId});
        TypedQuery<ResultEntity> query = getEntityManager().createNamedQuery(ResultConstants.QUERY_GET_BY_GAME, ResultEntity.class);
        query.setParameter("game", GameEntity.builder().id(gameId).build());
        return Optional.ofNullable(query.getResultList());

    }

}
