/*
 */
package se.backede.scoreboard.result;

import se.backede.scoreboard.common.AbstractMapper;
import se.backede.scoreboard.competition.CompetitionMapper;
import se.backede.scoreboard.game.GameMapper;
import se.backede.scoreboard.player.PlayerMapper;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class ResultMapper extends AbstractMapper<ResultDto, ResultEntity> {

    PlayerMapper PLAYER_MAPPER = new PlayerMapper();
    GameMapper GAME_MAPPER = new GameMapper();
    CompetitionMapper COMPETITION_MAPPER = new CompetitionMapper();

    @Override
    public ResultEntity mapToEntity(ResultDto dto) {
        return ResultEntity.builder()
                .id(dto.getId())
                .score(dto.getScore())
                .time(dto.getTime())
                .player(PLAYER_MAPPER.mapToEntity(dto.getPlayer()))
                .game(GAME_MAPPER.mapToEntity(dto.getGame()))
                .competition(COMPETITION_MAPPER.mapToEntity(dto.getCompetition()))
                .build();
    }

    @Override
    public ResultDto mapToDto(ResultEntity entity) {
        return ResultDto.builder()
                .id(entity.getId())
                .score(entity.getScore())
                .time(entity.getTime())
                .player(PLAYER_MAPPER.mapToDto(entity.getPlayer()))
                .game(GAME_MAPPER.mapToDto(entity.getGame()))
                .competition(COMPETITION_MAPPER.mapToDto(entity.getCompetition()))
                .build();

    }

}
