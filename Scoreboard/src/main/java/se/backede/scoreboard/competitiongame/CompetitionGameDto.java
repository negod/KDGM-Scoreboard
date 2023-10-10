/*
 */
package se.backede.scoreboard.competitiongame;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class CompetitionGameDto extends GenericDto {

    private CompetitionDto competition;
    private GameDto game;
    private Integer gameOrder;

}
