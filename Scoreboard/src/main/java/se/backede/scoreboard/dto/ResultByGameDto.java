/*
 */
package se.backede.scoreboard.dto;

import jakarta.persistence.Convert;
import java.time.Duration;
import lombok.Builder;
import lombok.Data;
import se.backede.scoreboard.converter.DurationConverter;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Data
@Builder
public class ResultByGameDto {

    private String player;
    private Integer score;
    @Convert(converter = DurationConverter.class)
    private Duration time;

}
