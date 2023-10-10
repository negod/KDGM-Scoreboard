/*
 */
package se.backede.scoreboard.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.common.service.GenericDto;
import se.backede.scoreboard.match.MatchDto;
import se.backede.scoreboard.player.PlayerDto;

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
public class ResultDto extends GenericDto {

    private MatchDto match;
    private PlayerDto player;
    private Long scoreValue;

}
