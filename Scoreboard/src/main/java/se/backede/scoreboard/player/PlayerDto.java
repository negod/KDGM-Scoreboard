/*
 */
package se.backede.scoreboard.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.common.service.GenericDto;
import se.backede.scoreboard.team.TeamDto;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto extends GenericDto {

    private String name;
    private String nickName;
    private TeamDto team;

}
