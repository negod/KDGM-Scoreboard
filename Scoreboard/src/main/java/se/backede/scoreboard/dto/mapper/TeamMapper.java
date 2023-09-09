/*
 */
package se.backede.scoreboard.dto.mapper;

import se.backede.scoreboard.dto.TeamDto;
import se.backede.scoreboard.entity.Team;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class TeamMapper {

    public static TeamDto mapToDto(Team team) {
        return TeamDto.builder()
                .id(team.getId())
                .name(team.getName())
                .build();
    }

    public static Team mapToEntity(TeamDto teamDto) {
        Team team = new Team();
        team.setId(teamDto.getId());
        team.setName(teamDto.getName());
        return team;
    }

}
