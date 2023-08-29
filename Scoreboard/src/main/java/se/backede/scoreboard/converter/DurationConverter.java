/*
 */
package se.backede.scoreboard.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.Duration;
import org.postgresql.util.PGInterval;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Converter(autoApply = true)
public class DurationConverter implements AttributeConverter<Duration, PGInterval> {

    @Override
    public PGInterval convertToDatabaseColumn(Duration duration) {
        if (duration != null) {
            long toMillis = duration.toMillis();
            PGInterval interval = new PGInterval();
            interval.setSeconds(toMillis / 1000);
        }
        return new PGInterval(0, 0, 0, 0, 0, 0);
    }

    @Override
    public Duration convertToEntityAttribute(PGInterval pgInterval) {
        if (pgInterval != null) {

            Double seconds = pgInterval.getSeconds();
            Integer minutes = pgInterval.getMinutes();
            Integer hours = pgInterval.getHours();

            Duration ofMillis = Duration.ofMillis(seconds.longValue() * 1000);
            ofMillis.plusMinutes(minutes);
            ofMillis.plusHours(hours);

        }
        return Duration.ofMillis(0);
    }
}
