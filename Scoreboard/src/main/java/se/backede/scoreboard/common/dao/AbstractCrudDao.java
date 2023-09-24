/*
 */
package se.backede.scoreboard.common.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 * @param <E>
 */
public abstract class AbstractCrudDao<E extends GenericEntity> implements GenericCrudDao<E> {

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    public abstract Class getLoggerClass();

    public abstract Class getEntityClass();

    @Override
    public Optional<List<E>> getAll() {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<E> cq = cb.createQuery(getEntityClass());
            TypedQuery<E> query = em.createQuery(cq);
            return Optional.ofNullable(new ArrayList<>(query.getResultList()));

        } catch (Exception e) {
            Logger.getLogger(getLoggerClass().getName()).log(Level.SEVERE, "Error when getting all [0] ", getEntityClass().getName());
        }
        return Optional.empty();
    }

    @Override
    public Optional<E> create(E entity) {
        try {
            em.persist(entity);
            return Optional.of(entity);
        } catch (Exception e) {
            Logger.getLogger(getLoggerClass().getName()).log(Level.SEVERE, "Error when persisting [0] ", getEntityClass().getName());
            return Optional.empty();
        }

    }

    @Override
    public Optional<E> getById(String id) {
        try {
            E entity = (E) em.find(getEntityClass(), id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            Logger.getLogger(getLoggerClass().getName()).log(Level.SEVERE, "Error when getting {0} by Id: {1}", new Object[]{getEntityClass().getName(), id});
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
            Logger.getLogger(getLoggerClass().getName()).log(Level.SEVERE, "Error when deleting {0} with Id{1}", new Object[]{getEntityClass().getName(), id});
        }

        return Optional.empty();
    }

    @Override
    public abstract Optional<E> update(E entity);

}
