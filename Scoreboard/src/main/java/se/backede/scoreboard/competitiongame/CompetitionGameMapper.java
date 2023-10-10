/*
 */
package se.backede.scoreboard.competitiongame;

import java.util.ArrayList;
import java.util.List;
import se.backede.scoreboard.competition.CompetitionMapper;
import se.backede.scoreboard.game.GameMapper;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class CompetitionGameMapper {

    GameMapper GAME_MAPPER = new GameMapper();
    CompetitionMapper COMPETITOIN_MAPPER = new CompetitionMapper();

    public List<CompetitionGameDto> mapToDtoList(List<CompetitionGameEntity> entityList) {
        List<CompetitionGameDto> dtoList = new ArrayList<>();
        entityList.forEach((CompetitionGameEntity entity) -> {
            dtoList.add(mapToDto(entity));
        });
        return dtoList;
    }

    public List<CompetitionGameEntity> mapToEntityList(List<CompetitionGameDto> dtoList) {
        List<CompetitionGameEntity> entityList = new ArrayList<>();
        dtoList.forEach((CompetitionGameDto dto) -> {
            entityList.add(mapToEntity(dto));
        });
        return entityList;
    }

    public CompetitionGameEntity mapToEntity(CompetitionGameDto dto) {
        return CompetitionGameEntity.builder()
                .competition(COMPETITOIN_MAPPER.mapToEntity(dto.getCompetition()))
                .game(GAME_MAPPER.mapToEntity(dto.getGame()))
                .competitionGamePK(CompetitionGamePKEntity.builder()
                        .competitionId(dto.getCompetition().getId())
                        .gameId(dto.getGame().getId())
                        .gameOrder(dto.getGameOrder())
                        .build()
                )
                .build();
    }

    public CompetitionGameDto mapToDto(CompetitionGameEntity entity) {
        return CompetitionGameDto.builder()
                .competition(COMPETITOIN_MAPPER.mapToDto(entity.getCompetition()))
                .game(GAME_MAPPER.mapToDto(entity.getGame()))
                .id(entity.getCompetitionGamePK().getCompetitionId())
                .gameOrder(entity.competitionGamePK.getGameOrder())
                .build();
    }

}
