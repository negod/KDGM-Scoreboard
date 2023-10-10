/*
 */
package se.backede.scoreboard.game;

import se.backede.scoreboard.common.AbstractMapper;
import se.backede.scoreboard.game.GameEntity;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class GameMapper extends AbstractMapper<GameDto, GameEntity> {

    @Override
    public GameEntity mapToEntity(GameDto dto) {
        return GameEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .gametype(dto.getGametype())
                .build();
    }

    @Override
    public GameDto mapToDto(GameEntity entity) {
        return GameDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .gametype(entity.getGametype())
                .gameOrder(null)
                .build();
    }

}
