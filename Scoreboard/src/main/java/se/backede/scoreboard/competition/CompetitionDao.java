/*
 */
package se.backede.scoreboard.competition;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.backede.scoreboard.common.dao.AbstractCrudDao;

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

    public Optional<CompetitionEntity> update(CompetitionEntity competition) {
        try {
            CompetitionEntity find = getEntityManager().find(CompetitionEntity.class, competition.getId());

            if (competition.getName() != null) {
                find.setName(competition.getName());
            }

            if (competition.getCompetitionDate() != null) {
                find.setCompetitionDate(competition.getCompetitionDate());
            }

            getEntityManager().merge(find);
            return Optional.ofNullable(find);
        } catch (Exception e) {
            Logger.getLogger(CompetitionDao.class.getName()).log(Level.SEVERE, "Error when updating competition", e);
            return Optional.empty();
        }
    }

}
