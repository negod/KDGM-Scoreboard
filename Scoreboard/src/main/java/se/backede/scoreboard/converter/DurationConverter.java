/*
 */
package se.backede.scoreboard.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.sql.SQLException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            try {
                long hours = duration.toHours();
                long minutes = duration.minusHours(hours).toMinutes();
                long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();
                long milliseconds = duration.minusHours(hours).minusMinutes(minutes).minusSeconds(seconds).toMillis();

                String pgIntervalString = hours + " hours " + minutes + " minutes " + seconds + " seconds " + milliseconds + " milliseconds";

                return new PGInterval(pgIntervalString);
            } catch (SQLException ex) {
                Logger.getLogger(DurationConverter.class.getName()).log(Level.SEVERE, "Error when converting Duration to PGInterval", ex);
            }
        }
        return new PGInterval();
    }

    @Override
    public Duration convertToEntityAttribute(PGInterval pgInterval) {
        if (pgInterval != null) {

            long hours = pgInterval.getHours();
            long minutes = pgInterval.getMinutes();
            long seconds = (long) pgInterval.getSeconds();
            long microseconds = pgInterval.getMicroSeconds();

            long totalSeconds = (hours * 60 * 60) + (minutes * 60) + seconds;
            int nanos = (int) TimeUnit.MICROSECONDS.toNanos(microseconds);

            return Duration.ofSeconds(totalSeconds, nanos);

        }
        return Duration.ofMillis(0);
    }
}
