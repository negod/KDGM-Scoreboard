/*
 */
package se.backede.scoreboard.player;

import se.backede.scoreboard.common.AbstractMapper;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class PlayerMapper extends AbstractMapper<PlayerDto, PlayerEntity> {

    @Override
    public PlayerDto mapToDto(PlayerEntity player) {

        return PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .nickName(player.getNickName())
                .build();
    }

    @Override
    public PlayerEntity mapToEntity(PlayerDto playerDto) {

        return PlayerEntity.builder()
                .id(playerDto.getId())
                .name(playerDto.getName())
                .nickName(playerDto.getNickName())
                .build();
    }

}
