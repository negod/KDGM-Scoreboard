/*
 */
package se.backede.scoreboard.common;

import se.backede.scoreboard.common.dao.GenericEntity;
import se.backede.scoreboard.common.service.GenericDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public abstract class AbstractMapper<D extends GenericDto, E extends GenericEntity> implements GenericMapper<D, E> {

    @Override
    public List<D> mapToDtoList(List<E> entityList) {
        List<D> dtoList = new ArrayList<>();
        entityList.forEach((E entity) -> {
            dtoList.add(mapToDto(entity));
        });
        return dtoList;
    }

    @Override
    public List<E> mapToEntityList(List<D> dtoList) {
        List<E> entityList = new ArrayList<>();
        dtoList.forEach((D dto) -> {
            entityList.add(mapToEntity(dto));
        });
        return entityList;
    }

}
