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
        if (dto != null) {
            return GameEntity.builder()
                    .id(dto.getId())
                    .name(dto.getName())
                    .gametype(dto.getGametype())
                    .rules(dto.getRules())
                    .calculationtype(dto.getCalculationtype())
                    .build();
        }
        return null;
    }
    
    @Override
    public GameDto mapToDto(GameEntity entity) {
        if (entity != null) {
            return GameDto.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .gametype(entity.getGametype())
                    .gameOrder(null)
                    .rules(entity.getRules())
                    .calculationtype(entity.getCalculationtype())
                    .build();
        }
        return null;
    }
    
}
