/*
 */
package se.backede.scoreboard.common;

import se.backede.scoreboard.common.dao.GenericEntity;
import se.backede.scoreboard.common.service.GenericDto;
import java.util.List;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 * @param <D>
 * @param <E>
 */
public interface GenericMapper<D extends GenericDto, E extends GenericEntity> {

    public List<D> mapToDtoList(List<E> entotyList);

    public List<E> mapToEntityList(List<D> dtoList);

    public E mapToEntity(D dto);

    public D mapToDto(E entity);

}
