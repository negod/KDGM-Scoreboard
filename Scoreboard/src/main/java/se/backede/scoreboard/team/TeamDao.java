/*
 */
package se.backede.scoreboard.team;

import se.backede.scoreboard.player.PlayerDao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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
public class TeamDao extends AbstractCrudDao<TeamEntity> {


    @Override
    public Class getLoggerClass() {
        return this.getClass();
    }

    @Override
    public Class getEntityClass() {
        return TeamEntity.class;
    }

    public Optional<List<TeamNameEntity>> getAllTeamNames() {
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<TeamNameEntity> cq = cb.createQuery(TeamNameEntity.class);
            TypedQuery<TeamNameEntity> query = getEntityManager().createQuery(cq);
            return Optional.ofNullable(new ArrayList<>(query.getResultList()));

        } catch (Exception e) {
            Logger.getLogger(getLoggerClass().getName()).log(Level.SEVERE, "Error when getting all {0} ", TeamNameEntity.class.getSimpleName());
        }
        return Optional.empty();
    }

}
