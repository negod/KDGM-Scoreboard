/*
 */
package se.backede.scoreboard.team;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.common.service.GenericDto;
import se.backede.scoreboard.player.PlayerDto;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeamDto extends GenericDto {

    private String name;
    private List<PlayerDto> players;

}
