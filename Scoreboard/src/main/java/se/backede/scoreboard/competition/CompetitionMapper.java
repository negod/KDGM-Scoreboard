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

    public enum INCLUDE_LISTS {
        ALL_LISTS, NO_LISTS;
    }

    @Override
    public CompetitionEntity mapToEntity(CompetitionDto dto) {
        return mapToEntityWithOptions(dto, INCLUDE_LISTS.ALL_LISTS);
    }

    public CompetitionEntity mapToEntityWithOptions(CompetitionDto dto, INCLUDE_LISTS competitionLists) {
        CompetitionEntity build = CompetitionEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .competitionDate(dto.getCompetitionDate())
                .started(dto.getStarted())
                .build();

        switch (competitionLists) {
            case ALL_LISTS:
                if (dto.getGames() != null) {
                    List<GameEntity> gamesList = dto.getGames().stream()
                            .map(gameMapper::mapToEntity)
                            .collect(Collectors.toList());

                    build.setGames(gamesList.stream().collect(Collectors.toSet()));
                }
                if (dto.getTeams() != null) {
                    List<TeamEntity> teamsList = dto.getTeams().stream()
                            .map(teamMapper::mapToEntity)
                            .collect(Collectors.toList());

                    build.setTeams(teamsList.stream().collect(Collectors.toSet()));
                }
                break;
            case NO_LISTS:
                break;
            default:
                throw new AssertionError();
        }

        return build;
    }

    @Override
    public CompetitionDto mapToDto(CompetitionEntity entity) {
        return mapToDtoWithOptions(entity, INCLUDE_LISTS.ALL_LISTS);
    }

    public CompetitionDto mapToDtoWithOptions(CompetitionEntity entity, INCLUDE_LISTS competitionLists) {
        CompetitionDto build = CompetitionDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .competitionDate(entity.getCompetitionDate())
                .started(entity.getStarted())
                .build();

        switch (competitionLists) {
            case ALL_LISTS:
                if (entity.getGames() != null) {
                    List<GameDto> gamesList = entity.getGames().stream()
                            .map(gameMapper::mapToDto)
                            .collect(Collectors.toList());

                    build.setGames(gamesList);
                }

                if (entity.getTeams() != null) {
                    List<TeamDto> teamsList = entity.getTeams().stream()
                            .map(teamMapper::mapToDto)
                            .collect(Collectors.toList());

                    build.setTeams(teamsList);
                }
                break;
            case NO_LISTS:
                break;
            default:
                throw new AssertionError();
        }

        return build;
    }

}
