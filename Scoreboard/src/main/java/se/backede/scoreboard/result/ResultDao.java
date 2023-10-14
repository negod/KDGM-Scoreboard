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
import se.backede.scoreboard.match.MatchEntity;
import se.backede.scoreboard.player.PlayerMapper;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@ApplicationScoped
@Transactional
public class ResultDao extends AbstractCrudDao<ResultEntity> {

    PlayerMapper PLAYER_MAPPER = new PlayerMapper();

    @Override
    public Class getLoggerClass() {
        return this.getClass();
    }

    @Override
    public Class getEntityClass() {
        return ResultEntity.class;
    }

    public Optional<List<ResultEntity>> getResultsByMatch(String matchId) {
        Logger.getLogger(ResultDao.class.getName()).log(Level.INFO, "Getting Results By Match {0}", new Object[]{matchId});
        TypedQuery<ResultEntity> query = getEntityManager().createNamedQuery(ResultConstants.QUERY_GET_BY_MATCH, ResultEntity.class);
        query.setParameter("matchId", matchId);
        return Optional.ofNullable(query.getResultList());
    }

    public ResultEntity mapToEntity(ResultDto dto) {

        MatchEntity match = getEntityManager().find(MatchEntity.class, dto.getMatchId());

        return ResultEntity.builder()
                .id(dto.getId())
                .playerId(PLAYER_MAPPER.mapToEntity(dto.getPlayer()))
                .scoreValue(dto.getScoreValue())
                .matchId(match)
                .build();
    }

}
