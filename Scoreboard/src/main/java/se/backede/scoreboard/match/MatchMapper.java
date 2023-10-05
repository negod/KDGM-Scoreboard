/*
 */
package se.backede.scoreboard.match;

import se.backede.scoreboard.common.AbstractMapper;
import se.backede.scoreboard.competition.CompetitionMapper;
import se.backede.scoreboard.game.GameMapper;
import se.backede.scoreboard.team.TeamMapper;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class MatchMapper extends AbstractMapper<MatchDto, MatchEntity> {

    TeamMapper TEAM_MAPPER = new TeamMapper();
    GameMapper GAME_MAPPER = new GameMapper();
    CompetitionMapper COMPETITION_MAPPER = new CompetitionMapper();

    @Override
    public MatchEntity mapToEntity(MatchDto dto) {
        return MatchEntity.builder()
                .id(dto.getId())
                .order(dto.getOrder())
                .team1(TEAM_MAPPER.mapToEntity(dto.getTeam1()))
                .team2(TEAM_MAPPER.mapToEntity(dto.getTeam2()))
                .game(GAME_MAPPER.mapToEntity(dto.getGame()))
                .competition(COMPETITION_MAPPER.mapToEntity(dto.getCompetition()))
                .build();
    }

    @Override
    public MatchDto mapToDto(MatchEntity entity) {
        return MatchDto.builder()
                .id(entity.getId())
                .order(entity.getOrder())
                .team1(TEAM_MAPPER.mapToDto(entity.getTeam1()))
                .team2(TEAM_MAPPER.mapToDto(entity.getTeam2()))
                .game(GAME_MAPPER.mapToDto(entity.getGame()))
                .competition(COMPETITION_MAPPER.mapToDto(entity.getCompetition()))
                .build();

    }

}
