/*
 */
package se.backede.scoreboard.game;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.backede.scoreboard.common.constants.GameConstants;
import se.backede.scoreboard.common.dao.AbstractCrudDao;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@ApplicationScoped
@Transactional
public class GameDao extends AbstractCrudDao<GameEntity> {

    @Override
    public Class getLoggerClass() {
        return this.getClass();
    }

    @Override
    public Class getEntityClass() {
        return GameEntity.class;
    }

//    @Override
//    public Optional<GameEntity> update(GameEntity game) {
//        try {
//
//            TypedQuery<GameEntity> createNamedQuery = getEntityManager().createNamedQuery(GameConstants.QUERY_UPDATE_GAME, GameEntity.class);
//            createNamedQuery.setParameter(GameConstants.TABLE_COLUMN_ID, game.getId());
//            createNamedQuery.setParameter(GameConstants.TABLE_COLUMN_NAME, game.getName());
//            createNamedQuery.setParameter(GameConstants.TABLE_COLUMN_GAMETYPE, game.getGametype());
//            int executeUpdate = createNamedQuery.executeUpdate();
//
//            return Optional.ofNullable(game);
//        } catch (ConstraintViolationException e) {
//            e.getConstraintViolations().forEach(err -> Logger.getLogger(GameDao.class.getName()).log(Level.SEVERE, "Constraint violation", e.toString()));
//            return Optional.empty();
//        } catch (Exception e) {
//            Logger.getLogger(GameDao.class.getName()).log(Level.SEVERE, "Error when updating gameI", e);
//            return Optional.empty();
//        }
//    }

}
