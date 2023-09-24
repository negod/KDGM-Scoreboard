/*
 */
package se.backede.scoreboard.result;

import se.backede.scoreboard.common.AbstractMapper;
import se.backede.scoreboard.result.ResultDto;
import se.backede.scoreboard.result.ResultEntity;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class ResultMapper extends AbstractMapper<ResultDto, ResultEntity> {

    @Override
    public ResultEntity mapToEntity(ResultDto dto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ResultDto mapToDto(ResultEntity entity) {
        return ResultDto.builder()
                .score(entity.getScore())
                .time(entity.getTime())
                .player(entity.getPlayer().getId())
                .build();
    }

}
