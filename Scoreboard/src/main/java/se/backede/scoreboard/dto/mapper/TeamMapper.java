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

}
