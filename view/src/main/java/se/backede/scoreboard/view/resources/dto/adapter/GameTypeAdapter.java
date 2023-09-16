/*
 */
package se.backede.scoreboard.view.resources.dto.adapter;

import jakarta.json.bind.adapter.JsonbAdapter;
import se.backede.scoreboard.view.resources.dto.GameType;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class GameTypeAdapter implements JsonbAdapter<GameType, String> {

    @Override
    public String adaptToJson(GameType value) throws Exception {
        System.out.println("GameType to String " + value);
        return value.name();
    }

    @Override
    public GameType adaptFromJson(String value) throws Exception {
        System.out.println("GameType to Object " + value.toString());
        return GameType.valueOf(value.toUpperCase());
    }

}
