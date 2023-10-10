/*
 */
package se.backede.scoreboard.competition;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import se.backede.scoreboard.common.AbstractMapper;
import se.backede.scoreboard.competitiongame.CompetitionGameEntity;
import se.backede.scoreboard.competitiongame.CompetitionGamePKEntity;
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
                .matchList(new ArrayList<>())
                .competitionGameList(new ArrayList<>())
                .build();

        CompetitionGamePKEntity cgePK = CompetitionGamePKEntity.builder()
                .competitionId(dto.getId()).build();

        switch (competitionLists) {
            case ALL_LISTS:
                if (dto.getGames() != null) {
                    for (GameDto gameDto : dto.getGames()) {
                        CompetitionGameEntity cge = CompetitionGameEntity.builder()
                                .competition(build)
                                .game(GameEntity.builder()
                                        .id(gameDto.getId())
                                        .name(gameDto.getName())
                                        .gametype(gameDto.getGametype())
                                        .build()).build();
                        cgePK.setGameId(gameDto.getId());
                        cge.setCompetitionGamePK(cgePK);
                        build.getCompetitionGameList().add(cge);
                    }

                }
                if (dto.getTeams() != null) {
                    List<TeamEntity> teamsList = dto.getTeams().stream()
                            .map(teamMapper::mapToEntity)
                            .collect(Collectors.toList());
                    build.setTeamList(teamsList);
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
                .started(entity.isStarted())
                .games(new ArrayList<>())
                .teams(new ArrayList<>())
                .build();

        switch (competitionLists) {
            case ALL_LISTS:
                if (entity.getCompetitionGameList() != null) {
                    for (CompetitionGameEntity competitionGameEntity : entity.getCompetitionGameList()) {
                        GameDto dto = GameDto.builder()
                                .gametype(competitionGameEntity.getGame().getGametype())
                                .id(competitionGameEntity.getGame().getId())
                                .name(competitionGameEntity.getGame().getName())
                                .gameOrder(competitionGameEntity.getCompetitionGamePK().getGameOrder())
                                .build();
                        build.getGames().add(dto);

                    }
                }

                if (entity.getTeamList() != null) {
                    List<TeamDto> teamsList = entity.getTeamList().stream()
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
