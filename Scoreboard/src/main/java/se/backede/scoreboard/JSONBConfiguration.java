/*
 */
package se.backede.scoreboard;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.backede.scoreboard.serializer.DurationSerializer;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Provider
public class JSONBConfiguration implements ContextResolver<Jsonb> {

    private Jsonb jsonb;

    public JSONBConfiguration() {
        Logger.getLogger(JSONBConfiguration.class.getName()).log(Level.INFO, "JSONB configuration");
        JsonbConfig config = new JsonbConfig().withFormatting(true).withSerializers(new DurationSerializer());

        jsonb = JsonbBuilder.create(config);
    }

    @Override
    public Jsonb getContext(Class<?> type) {
        return jsonb;
    }

}
