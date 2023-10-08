/*
 */
package se.backede.scoreboard.competitiongame;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.common.service.GenericDto;
import se.backede.scoreboard.competition.CompetitionDto;
import se.backede.scoreboard.game.GameDto;

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
public class CompetitionGameDto extends GenericDto {

//    private CompetitionDto competition;
//    private GameDto game;
    private String game;
    private String competition;
    private Integer gameOrder;

}
