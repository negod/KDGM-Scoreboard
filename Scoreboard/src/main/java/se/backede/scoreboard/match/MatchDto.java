/*
 */
package se.backede.scoreboard.match;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.common.service.GenericDto;
import se.backede.scoreboard.competition.CompetitionDto;
import se.backede.scoreboard.game.GameDto;
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
@ToString
public class MatchDto extends GenericDto {

    private TeamDto team1;
    private TeamDto team2;
    private GameDto game;
    private CompetitionDto competition;

}
