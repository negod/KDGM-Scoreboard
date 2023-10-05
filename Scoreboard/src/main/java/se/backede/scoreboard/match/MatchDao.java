/*
 */
package se.backede.scoreboard.match;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.backede.scoreboard.common.constants.ResultConstants;
import se.backede.scoreboard.common.dao.AbstractCrudDao;
import se.backede.scoreboard.competition.CompetitionEntity;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@ApplicationScoped
@Transactional
public class MatchDao extends AbstractCrudDao<MatchEntity> {

    @Override
    public Class getLoggerClass() {
        return this.getClass();
    }

    @Override
    public Class getEntityClass() {
        return MatchEntity.class;
    }

    public Optional<List<MatchEntity>> getMatchByCompetition(String competitionId) {
        Logger.getLogger(MatchDao.class.getName()).log(Level.INFO, "Getting Matches By Competition {0}", new Object[]{competitionId});
        TypedQuery<MatchEntity> query = getEntityManager().createNamedQuery(ResultConstants.QUERY_GET_BY_COMPETITION, MatchEntity.class);
        query.setParameter("competition", CompetitionEntity.builder().id(competitionId).build());
        return Optional.ofNullable(query.getResultList());
    }

}
