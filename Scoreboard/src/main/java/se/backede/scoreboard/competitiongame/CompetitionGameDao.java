/*
 */
package se.backede.scoreboard.competitiongame;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.backede.scoreboard.common.dao.AbstractCrudDao;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@ApplicationScoped
@Transactional
public class CompetitionGameDao {

    public Class getLoggerClass() {
        return this.getClass();
    }

    public Class getEntityClass() {
        return CompetitionGameEntity.class;
    }

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    public Optional<CompetitionGameEntity> validate(CompetitionGameEntity entity) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<CompetitionGameEntity>> violations = validator.validate(entity);

        if (violations.isEmpty()) {
            return Optional.of(entity);
        } else {
            for (ConstraintViolation<CompetitionGameEntity> violation : violations) {
                Logger.getLogger(getLoggerClass().getName()).log(Level.SEVERE, "Found constraint violation on {0} constarint {1} ", new Object[]{getEntityClass().getSimpleName(), violation.getMessage()});
            }
            return Optional.empty();
        }

    }

    public Optional<List<CompetitionGameEntity>> getAll() {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<CompetitionGameEntity> cq = cb.createQuery(getEntityClass());
            TypedQuery<CompetitionGameEntity> query = em.createQuery(cq);
            return Optional.ofNullable(new ArrayList<>(query.getResultList()));

        } catch (Exception e) {
            Logger.getLogger(getLoggerClass().getName()).log(Level.SEVERE, "Error when getting all {0} ", getEntityClass().getSimpleName());
        }
        return Optional.empty();
    }

    public Optional<CompetitionGameEntity> create(CompetitionGameEntity entity) {
        try {

            Optional<CompetitionGameEntity> validatedEntity = validate(entity);
            if (validatedEntity.isPresent()) {
                em.persist(validatedEntity.get());
                return Optional.ofNullable(validatedEntity.get());
            } else {
                return Optional.empty();
            }

        } catch (Exception e) {
            Logger.getLogger(getLoggerClass().getName()).log(Level.SEVERE, "Error when persisting {0} values {1} ", new Object[]{getEntityClass().getSimpleName(), entity.toString()});
            return Optional.empty();
        }

    }

    public Optional<CompetitionGameEntity> getById(String id) {
        try {
            CompetitionGameEntity entity = (CompetitionGameEntity) em.find(getEntityClass(), id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            Logger.getLogger(getLoggerClass().getName()).log(Level.SEVERE, "Error when getting {0} by Id: {1}", new Object[]{getEntityClass().getSimpleName(), id});
            return Optional.empty();
        }
    }

    public Optional<Boolean> delete(String id) {
        try {

            CompetitionGameEntity entityToRemove = (CompetitionGameEntity) em.find(getEntityClass(), id);
            em.remove(entityToRemove);

            return Optional.of(Boolean.TRUE);
        } catch (Exception e) {
            Logger.getLogger(getLoggerClass().getName()).log(Level.SEVERE, "Error when deleting {0} with Id: {1}", new Object[]{getEntityClass().getSimpleName(), id});
        }

        return Optional.empty();
    }

    public Optional<CompetitionGameEntity> update(CompetitionGameEntity entity) {
        try {
            Optional<CompetitionGameEntity> validatedEntity = validate(entity);
            if (validatedEntity.isPresent()) {
                em.merge(entity);
                em.flush();
                return Optional.of(entity);
            } else {
                return Optional.empty();
            }

        } catch (Exception e) {
            Logger.getLogger(getLoggerClass().getName()).log(Level.SEVERE, "Error when updating {0} with values: {1}", new Object[]{getEntityClass().getSimpleName(), entity.toString()});
        }

        return Optional.empty();

    }

}
