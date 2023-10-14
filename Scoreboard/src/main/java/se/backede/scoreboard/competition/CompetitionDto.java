/*
 */
package se.backede.scoreboard.competition;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.common.service.GenericDto;
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
public class CompetitionDto extends GenericDto {

    private Date competitionDate;
    private String name;
    private List<GameDto> games;
    private List<TeamDto> teams;
    private Boolean started;

}
