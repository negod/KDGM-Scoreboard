/*
 */
package se.backede.scoreboard.dto.mapper;

import java.util.Optional;
import se.backede.scoreboard.dto.PlayerDto;
import se.backede.scoreboard.entity.Player;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class PlayerMapper {
    
    public static PlayerDto mapToDto(Player player) {
        
        PlayerDto playerDto = PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .nickName(player.getNickName())
                .build();
        
        Optional.ofNullable(player.getTeam()).ifPresent(team -> {
            playerDto.setTeam(TeamMapper.mapToDto(team));
        });
        
        return playerDto;
    }
    
    public static Player mapToEntity(PlayerDto playerDto) {
        
        Player player = Player.builder()
                .id(playerDto.getId())
                .name(playerDto.getName())
                .nickName(playerDto.getNickName())
                .build();
        
        if (playerDto.getTeam() != null) {
            player.setTeam(TeamMapper.mapToEntity(playerDto.getTeam()));
        }
        
        return player;
    }
    
}
