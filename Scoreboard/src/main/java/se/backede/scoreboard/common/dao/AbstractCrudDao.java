/*
 */
package se.backede.scoreboard.common.dao;

import jakarta.interceptor.Interceptors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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
import se.backede.scoreboard.exception.ExceptionHandlingInterceptor;
import se.backede.scoreboard.exception.HandleException;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 * @param <E>
 */
@HandleException
@Interceptors(ExceptionHandlingInterceptor.class)
public abstract class AbstractCrudDao<E extends GenericEntity> implements GenericCrudDao<E> {

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    public abstract Class getLoggerClass();

    public abstract Class getEntityClass();

    public Optional<E> validate(E entity) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<E>> violations = validator.validate(entity);

        if (violations.isEmpty()) {
            return Optional.of(entity);
        } else {
            for (ConstraintViolation<E> violation : violations) {
                Logger.getLogger(getLoggerClass().getName()).log(Level.SEVERE, "Found constraint violation on {0} constarint {1} ", new Object[]{getEntityClass().getSimpleName(), violation.getMessage()});
            }
            return Optional.empty();
        }

    }

    @Override
    public Optional<List<E>> getAll() {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<E> cq = cb.createQuery(getEntityClass());
            TypedQuery<E> query = em.createQuery(cq);
            return Optional.ofNullable(new ArrayList<>(query.getResultList()));

        } catch (Exception e) {
            Logger.getLogger(getLoggerClass().getName()).log(Level.SEVERE, "Error when getting all {0} ", getEntityClass().getSimpleName());
        }
        return Optional.empty();
    }

    @Override
    public Optional<E> create(E entity) {
        try {

            entity.setId(UUID.randomUUID().toString());
            Optional<E> validatedEntity = validate(entity);
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

    @Override
    public Optional<E> getById(String id) {
        try {
            E entity = (E) em.find(getEntityClass(), id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            Logger.getLogger(getLoggerClass().getName()).log(Level.SEVERE, "Error when getting {0} by Id: {1}", new Object[]{getEntityClass().getSimpleName(), id});
            return Optional.empty();
        }
    }

    @Override
    public Optional<Boolean> delete(String id) {
        try {

            E entityToRemove = (E) em.find(getEntityClass(), id);
            em.remove(entityToRemove);

            return Optional.of(Boolean.TRUE);
        } catch (Exception e) {
            Logger.getLogger(getLoggerClass().getName()).log(Level.SEVERE, "Error when deleting {0} with Id: {1}", new Object[]{getEntityClass().getSimpleName(), id});
        }

        return Optional.empty();
    }

    @Override
    public Optional<E> update(E entity) {
        try {
            Optional<E> validatedEntity = validate(entity);
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
