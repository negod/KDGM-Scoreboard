/*
 */
package se.backede.scoreboard.admin.mockdata;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import se.backede.scoreboard.admin.resources.dto.Game;
import se.backede.scoreboard.admin.resources.dto.GameType;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class GameMock {

    public static Game getGame() {

        return Game.builder()
                .id(UUID.randomUUID().toString())
                .gametype(GameType.TIME)
                .name(UUID.randomUUID().toString())
                .build();
    }

    public static Game getGame(GameType gameType) {

        return Game.builder()
                .id(UUID.randomUUID().toString())
                .gametype(gameType)
                .name(UUID.randomUUID().toString())
                .build();
    }

    public static List<Game> getGames(int listSize) {
        List<Game> games = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            games.add(Game.builder()
                    .id(UUID.randomUUID().toString())
                    .gametype(GameType.NONE)
                    .name(UUID.randomUUID().toString())
                    .build());
        }

        return games;

    }

}
