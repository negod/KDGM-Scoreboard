/*
 */
package se.backede.scoreboard.result;

import se.backede.scoreboard.match.*;
import se.backede.scoreboard.common.AbstractMapper;
import se.backede.scoreboard.player.PlayerMapper;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class ResultMapper extends AbstractMapper<ResultDto, ResultEntity> {

    PlayerMapper PLAYER_MAPPER = new PlayerMapper();
    MatchMapper MATCH_MAPPER = new MatchMapper();

    @Override
    public ResultEntity mapToEntity(ResultDto dto) {
        return ResultEntity.builder()
                .id(dto.getId())
                .playerId(PLAYER_MAPPER.mapToEntity(dto.getPlayer()))
                .scoreValue(dto.getScoreValue())
                .build();
    }

    @Override
    public ResultDto mapToDto(ResultEntity entity) {
        return ResultDto.builder()
                .id(entity.getId())
                .player(PLAYER_MAPPER.mapToDto(entity.getPlayerId()))
                .matchId(entity.getMatchId().getId())
                .scoreValue(entity.getScoreValue())
                .build();
    }

}
