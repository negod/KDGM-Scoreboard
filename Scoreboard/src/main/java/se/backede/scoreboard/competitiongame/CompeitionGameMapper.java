/*
 */
package se.backede.scoreboard.competitiongame;

import se.backede.scoreboard.common.AbstractMapper;
import se.backede.scoreboard.competition.CompetitionMapper;
import se.backede.scoreboard.game.GameMapper;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class CompeitionGameMapper extends AbstractMapper<CompetitionGameDto, CompetitionGameEntity> {
    
    GameMapper GAME_MAPPER = new GameMapper();
    CompetitionMapper COMPETITOIN_MAPPER = new CompetitionMapper();
    
    @Override
    public CompetitionGameEntity mapToEntity(CompetitionGameDto dto) {
//        return CompetitionGameEntity.builder()
//                .id(dto.getId())
//                .game(GAME_MAPPER.mapToEntity(dto.getGame()))
//                .competition(COMPETITOIN_MAPPER.mapToEntityWithOptions(dto.getCompetition(), CompetitionMapper.INCLUDE_LISTS.NO_LISTS))
//                .build();

        return CompetitionGameEntity.builder()
                .competition(dto.getCompetition())
                .game(dto.getGame())
                .gameOrder(dto.getGameOrder())
                .id(dto.getId())
                .build();
    }
    
    @Override
    public CompetitionGameDto mapToDto(CompetitionGameEntity entity) {
//        return CompetitionGameDto.builder()
//                .id(entity.getId())
//                .game(GAME_MAPPER.mapToDto(entity.getGame()))
//                .competition(COMPETITOIN_MAPPER.mapToDtoWithOptions(entity.getCompetition(), CompetitionMapper.INCLUDE_LISTS.NO_LISTS))
//                .build();

        return CompetitionGameDto.builder()
                .competition(entity.getCompetition())
                .game(entity.getGame())
                .gameOrder(entity.getGameOrder())
                .id(entity.getId())
                .build();
    }
    
}
