/*
 */
package se.backede.scoreboard.view.resources.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    private GameType gameType;
    private String name;

}
