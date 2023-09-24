/*
 */
package se.backede.scoreboard.result;

import jakarta.persistence.Convert;
import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.common.service.GenericDto;
import se.backede.scoreboard.converter.DurationConverter;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto extends GenericDto {

    private String player;
    private Integer score;
    @Convert(converter = DurationConverter.class)
    private Duration time;

}
