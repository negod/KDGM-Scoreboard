/*
 */
package se.backede.scoreboard.player;

import java.util.Optional;
import se.backede.scoreboard.common.AbstractMapper;
import se.backede.scoreboard.team.TeamMapper;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class PlayerMapper extends AbstractMapper<PlayerDto, PlayerEntity> {

    private final TeamMapper TEAM_MAPPER = new TeamMapper();

    @Override
    public PlayerDto mapToDto(PlayerEntity player) {

        PlayerDto playerDto = PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .nickName(player.getNickName())
                .build();

        Optional.ofNullable(player.getTeam()).ifPresent(team -> {
            playerDto.setTeam(TEAM_MAPPER.mapToDto(team));
        });

        return playerDto;
    }

    @Override
    public PlayerEntity mapToEntity(PlayerDto playerDto) {

        PlayerEntity player = PlayerEntity.builder()
                .id(playerDto.getId())
                .name(playerDto.getName())
                .nickName(playerDto.getNickName())
                .build();

        if (playerDto.getTeam() != null) {
            player.setTeam(TEAM_MAPPER.mapToEntity(playerDto.getTeam()));
        }

        return player;
    }

}
