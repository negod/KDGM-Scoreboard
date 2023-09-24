/*
 */
package se.backede.scoreboard.common.service;

import se.backede.scoreboard.common.dao.GenericCrudDao;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 * @param <D>
 */
public interface GenericCrudService<D extends GenericDto> {

    public GenericCrudDao getDao();

    public Response getAll();

    public Response getById(String id);

    public Response create(D item);

    public Response update(D item);

    public Response delete(String id);

}
