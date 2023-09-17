/*
 */
package se.backede.scoreboard.view.resources.dto;

import lombok.Getter;
import lombok.Setter;
import se.backede.scoreboard.view.commons.GenericDto;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
public class Game extends GenericDto {

    public String name;
    public GameType gametype;

    

}
