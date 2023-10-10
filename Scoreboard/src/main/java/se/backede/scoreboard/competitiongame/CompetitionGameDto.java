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

    private String competition;
    private String game;
    private Integer gameOrder;

}
