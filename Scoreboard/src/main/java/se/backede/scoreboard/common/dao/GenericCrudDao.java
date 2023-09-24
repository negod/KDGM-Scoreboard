/*
 */
package se.backede.scoreboard.common.dao;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 * @param <T>
 */
@Transactional
public interface GenericCrudDao<E extends GenericEntity> {

    public Optional<List<E>> getAll();

    public Optional<E> create(E game);

    public Optional<E> getById(String id);

    public Optional<Boolean> delete(String id);

    public Optional<E> update(E game);

}
