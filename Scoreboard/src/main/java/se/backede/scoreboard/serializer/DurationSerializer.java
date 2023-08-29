/*
 */
package se.backede.scoreboard.serializer;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import java.time.Duration;
import jakarta.json.stream.JsonGenerator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class DurationSerializer implements JsonbSerializer<Duration> {

    @Override
    public void serialize(Duration duration, JsonGenerator generator, SerializationContext sc) {
        Logger.getLogger(DurationSerializer.class.getName()).log(Level.INFO, "Serializer");
        /*if (t != null) {
            jg.write("time", t.toString());
            sc.serialize("time", jg);
        }
        jg.write("time", "");
        sc.serialize("time", jg);*/

        long seconds = duration.getSeconds();
        int nanos = duration.getNano();
        double totalSeconds = seconds + (nanos / 1e9);

        generator.write(totalSeconds);
    }

}
