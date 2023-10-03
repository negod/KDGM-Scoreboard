/*
 */
package se.backede.scoreboard.result;

import jakarta.persistence.Convert;
import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.common.service.GenericDto;
import se.backede.scoreboard.competition.CompetitionDto;
import se.backede.scoreboard.converter.DurationConverter;
import se.backede.scoreboard.game.GameDto;
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

    private PlayerDto player;
    private GameDto game;
    private CompetitionDto competition;
    private Integer score;
    @Convert(converter = DurationConverter.class)
    private Duration time;

}
