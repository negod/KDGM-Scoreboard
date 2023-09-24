/*
 */
package se.backede.scoreboard.competition;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
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

}
