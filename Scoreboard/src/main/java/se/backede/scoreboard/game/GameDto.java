/*
 */
package se.backede.scoreboard.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.common.service.GenericDto;

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
public class GameDto extends GenericDto {

    private String name;
    private GameType gametype;
    private Integer gameOrder;
    private String rules;
    private ScoreCalculation calculationtype;

}
