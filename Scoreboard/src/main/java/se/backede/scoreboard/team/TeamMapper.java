/*
 */
package se.backede.scoreboard.team;

import se.backede.scoreboard.common.AbstractMapper;
import se.backede.scoreboard.team.TeamEntity;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class TeamMapper extends AbstractMapper<TeamDto, TeamEntity> {

    @Override
    public TeamDto mapToDto(TeamEntity team) {
        return TeamDto.builder()
                .id(team.getId())
                .name(team.getName())
                .build();
    }

    @Override
    public TeamEntity mapToEntity(TeamDto teamDto) {
        TeamEntity team = new TeamEntity();
        team.setId(teamDto.getId());
        team.setName(teamDto.getName());
        return team;
    }

}
