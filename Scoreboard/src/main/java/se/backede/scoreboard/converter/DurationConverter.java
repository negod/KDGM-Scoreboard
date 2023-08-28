/*
 */
package se.backede.scoreboard.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.postgresql.util.PGInterval;
import se.backede.scoreboard.control.GameDao;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Converter(autoApply = true)
public class DurationConverter implements AttributeConverter<Duration, PGInterval> {

    @Override
    public PGInterval convertToDatabaseColumn(Duration duration) {
        Logger.getLogger(DurationConverter.class.getName()).log(Level.INFO, "Duration converter to Column");
        if (duration != null) {
            long toMillis = duration.toMillis();
            PGInterval interval = new PGInterval();
            interval.setSeconds(toMillis / 1000);
        }
        return new PGInterval(0, 0, 0, 0, 0, 0);
    }

    @Override
    public Duration convertToEntityAttribute(PGInterval pgInterval) {
        Logger.getLogger(DurationConverter.class.getName()).log(Level.INFO, "Duration converter to Attribute");
        if (pgInterval != null) {
            Logger.getLogger(DurationConverter.class.getName()).log(Level.INFO, "Interval is not null seconds... -> {0}", new Object[]{pgInterval.getSeconds()});
            Double seconds = pgInterval.getSeconds();
            return Duration.ofMillis(seconds.longValue() * 1000);
        }
        Logger.getLogger(DurationConverter.class.getName()).log(Level.INFO, "Interval is null");
        return Duration.ofMillis(0);
    }
}
