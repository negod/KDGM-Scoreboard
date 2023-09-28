/*
 */
package se.backede.scoreboard.competition;

import java.util.List;
import java.util.stream.Collectors;
import se.backede.scoreboard.common.AbstractMapper;
import se.backede.scoreboard.game.GameDto;
import se.backede.scoreboard.game.GameEntity;
import se.backede.scoreboard.game.GameMapper;
import se.backede.scoreboard.team.TeamDto;
import se.backede.scoreboard.team.TeamEntity;
import se.backede.scoreboard.team.TeamMapper;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class CompetitionMapper extends AbstractMapper<CompetitionDto, CompetitionEntity> {

    TeamMapper teamMapper = new TeamMapper();
    GameMapper gameMapper = new GameMapper();

    @Override
    public CompetitionEntity mapToEntity(CompetitionDto dto) {
        CompetitionEntity build = CompetitionEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .competitionDate(dto.getCompetitionDate())
                .build();

        List<GameEntity> gamesList = dto.getGames().stream()
                .map(gameMapper::mapToEntity)
                .collect(Collectors.toList());

        List<TeamEntity> teamsList = dto.getTeams().stream()
                .map(teamMapper::mapToEntity)
                .collect(Collectors.toList());

        build.setGames(gamesList.stream().collect(Collectors.toSet()));
        build.setTeams(teamsList.stream().collect(Collectors.toSet()));

        return build;
    }

    @Override
    public CompetitionDto mapToDto(CompetitionEntity entity) {
        CompetitionDto build = CompetitionDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .competitionDate(entity.getCompetitionDate())
                .build();

        List<GameDto> gamesList = entity.getGames().stream()
                .map(gameMapper::mapToDto)
                .collect(Collectors.toList());

        List<TeamDto> teamsList = entity.getTeams().stream()
                .map(teamMapper::mapToDto)
                .collect(Collectors.toList());

        build.setGames(gamesList);
        build.setTeams(teamsList);

        return build;
    }

}
