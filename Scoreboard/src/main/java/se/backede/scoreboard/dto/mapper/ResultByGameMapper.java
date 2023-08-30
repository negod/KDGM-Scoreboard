/*
 */
package se.backede.scoreboard.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import se.backede.scoreboard.dto.ResultByGameDto;
import se.backede.scoreboard.entity.Result;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class ResultByGameMapper {

    public static ResultByGameDto getResultByGameDto(Result result) {
        return ResultByGameDto.builder()
                .score(result.getScore())
                .time(result.getTime())
                .player(result.getPlayer().getId())
                .build();
    }

    public static List<ResultByGameDto> getResultByGameDtoList(List<Result> resultList) {

        List<ResultByGameDto> results = new ArrayList<>();
        for (Result result : resultList) {
            results.add(getResultByGameDto(result));
        }
        return results;

    }

}
