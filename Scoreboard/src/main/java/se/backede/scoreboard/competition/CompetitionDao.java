/*
 */
package se.backede.scoreboard.competition;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.backede.scoreboard.common.constants.CompetitionGameConstants;
import se.backede.scoreboard.common.dao.AbstractCrudDao;
import se.backede.scoreboard.competitiongame.CompetitionGameEntity;
import se.backede.scoreboard.match.MatchEntity;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@ApplicationScoped
@Transactional
public class CompetitionDao extends AbstractCrudDao<CompetitionEntity> {

    @Override
    public Class getLoggerClass() {
        return this.getClass();
    }

    @Override
    public Class getEntityClass() {
        return CompetitionEntity.class;
    }

    @Override
    public Optional<CompetitionEntity> update(CompetitionEntity entity) {
        try {

            if (entity.getCompetitionGameList() != null) {

                for (CompetitionGameEntity competitionGameEntity : entity.getCompetitionGameList()) {

                    try {
                        TypedQuery<CompetitionGameEntity> query = getEntityManager().createNamedQuery(CompetitionGameConstants.QUERY_MATCH_BY_COMPETITION_AND_GAME, CompetitionGameEntity.class);
                        query.setParameter("competitionId", competitionGameEntity.getCompetition().getId());
                        query.setParameter("gameId", competitionGameEntity.getGame().getId());

                        CompetitionGameEntity competitionGame = query.getSingleResult();
                        competitionGameEntity.getCompetitionGamePK().setId(competitionGame.getCompetitionGamePK().getId());
                        competitionGameEntity.getCompetitionGamePK().setUpdatedDate(competitionGame.getCompetitionGamePK().getUpdatedDate());
                    } catch (Exception e) {
                        competitionGameEntity.getCompetitionGamePK().setId(UUID.randomUUID().toString());
                        competitionGameEntity.getCompetitionGamePK().setUpdatedDate(new Date());
                    }
                }

            }

            Optional<CompetitionEntity> validatedEntity = validate(entity);
            if (validatedEntity.isPresent()) {

                getEntityManager().merge(entity);
                getEntityManager().flush();
                return Optional.of(entity);
            } else {
                return Optional.empty();
            }

        } catch (Exception e) {
            Logger.getLogger(getLoggerClass().getName()).log(Level.SEVERE, "Error when updating {0} with values: {1} ERROR {2}", new Object[]{getEntityClass().getSimpleName(), entity.toString(), e});
        }

        return Optional.empty();

    }

    @Override
    public Optional<CompetitionEntity> create(CompetitionEntity entity) {
        try {

            entity.setId(UUID.randomUUID().toString());

            if (entity.getCompetitionGameList() != null) {
                for (CompetitionGameEntity competitionGameEntity : entity.getCompetitionGameList()) {
                    competitionGameEntity.getCompetitionGamePK().setId(UUID.randomUUID().toString());
                    competitionGameEntity.getCompetitionGamePK().setUpdatedDate(new Date());
                }
            }

            Optional<CompetitionEntity> validatedEntity = validate(entity);
            if (validatedEntity.isPresent()) {
                getEntityManager().persist(validatedEntity.get());
                return Optional.ofNullable(validatedEntity.get());
            } else {
                return Optional.empty();
            }

        } catch (Exception e) {
            Logger.getLogger(getLoggerClass().getName()).log(Level.SEVERE, "Error when persisting {0} values {1} ", new Object[]{getEntityClass().getSimpleName(), entity.toString()});
            return Optional.empty();
        }

    }

}
