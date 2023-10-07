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

    public Optional<List<ResultEntity>> getResultsByMatch(String gameId) {
        Logger.getLogger(ResultDao.class.getName()).log(Level.INFO, "Getting Results By Match {0}", new Object[]{gameId});
        TypedQuery<ResultEntity> query = getEntityManager().createNamedQuery(ResultConstants.QUERY_GET_BY_COMPETITION, ResultEntity.class);
        query.setParameter("matchId", gameId);
        return Optional.ofNullable(query.getResultList());
    }

}
