/*
 */
package se.backede.scoreboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.common.GenericDto;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@SuperBuilder
public class PlayerDto extends GenericDto {

    private String name;
    private TeamDto team;

}
