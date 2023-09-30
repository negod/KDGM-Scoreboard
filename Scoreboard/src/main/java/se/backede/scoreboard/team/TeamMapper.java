/*
 */
package se.backede.scoreboard.team;

import java.util.Optional;
import se.backede.scoreboard.common.AbstractMapper;
import se.backede.scoreboard.player.PlayerMapper;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class TeamMapper extends AbstractMapper<TeamDto, TeamEntity> {

    private final PlayerMapper PLAYER_MAPPER = new PlayerMapper();

    @Override
    public TeamDto mapToDto(TeamEntity team) {
        TeamDto teamDto = TeamDto.builder()
                .id(team.getId())
                .name(team.getName())
                .build();

        Optional.ofNullable(team.getPlayers()).ifPresent(teams -> {
            teamDto.setPlayers(PLAYER_MAPPER.mapToDtoList(teams));
        });

        return teamDto;
    }

    @Override
    public TeamEntity mapToEntity(TeamDto teamDto) {

        TeamEntity teamEntity = TeamEntity.builder()
                .id(teamDto.getId())
                .name(teamDto.getName())
                .build();

        Optional.ofNullable(teamDto.getPlayers()).ifPresent(teams -> {
            teamEntity.setPlayers(PLAYER_MAPPER.mapToEntityList(teams));
        });

        return teamEntity;

    }

}
