/*
 */
package se.backede.scoreboard.view.commons;

import jakarta.ws.rs.PathParam;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 * @param <T>
 */
public interface GenericRestClient<T> {

    Optional<List<T>> getAll();

    Optional<T> getById(String id);

    Optional<T> update(T dto);

    Optional<T> create(T dto);

    Optional<Boolean> delete(@PathParam(value = "id") String id);

}
