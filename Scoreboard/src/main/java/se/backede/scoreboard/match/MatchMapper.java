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
                .team1Id(TEAM_MAPPER.mapToEntity(dto.getTeam1()))
                .team2Id(TEAM_MAPPER.mapToEntity(dto.getTeam2()))
                .gameId(GAME_MAPPER.mapToEntity(dto.getGame()))
                .competitionId(COMPETITION_MAPPER.mapToEntity(dto.getCompetition()))
                .matchOrder(dto.getOrder())
                .build();
    }
    
    @Override
    public MatchDto mapToDto(MatchEntity entity) {
        return MatchDto.builder()
                .id(entity.getId())
                .team1(TEAM_MAPPER.mapToDto(entity.getTeam1Id()))
                .team2(TEAM_MAPPER.mapToDto(entity.getTeam2Id()))
                .game(GAME_MAPPER.mapToDto(entity.getGameId()))
                .competition(COMPETITION_MAPPER.mapToDto(entity.getCompetitionId()))
                .order(entity.getMatchOrder())
                .build();
        
    }
    
}
