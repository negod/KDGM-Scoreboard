/*
 */
package se.backede.scoreboard.admin.resources.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import se.backede.scoreboard.admin.commons.GenericDto;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Team extends GenericDto {

    private String name;

}
