/*
 */
package se.backede.scoreboard.admin.mockdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import se.backede.scoreboard.admin.resources.dto.Player;
import se.backede.scoreboard.admin.resources.dto.PlayerResult;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class PlayerResultMock {

    public static PlayerResult getPlayerResult(Long scoreValue) {
        return PlayerResult.builder()
                .scoreValue(scoreValue)
                .player(PlayerMock.getPlayer())
                .build();
    }

    public static PlayerResult getPlayerResult(Long scoreValue, Player player) {
        return PlayerResult.builder()
                .scoreValue(scoreValue)
                .player(player)
                .build();
    }

    public static PlayerResult getPlayerResult(Player player) {
        Random random = new Random();
        return PlayerResult.builder()
                .scoreValue(random.nextLong(100) + 1)
                .player(player)
                .build();
    }

    public static List<PlayerResult> getPlayerResultList(int listSize) {
        List<PlayerResult> plauerResults = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < listSize; i++) {
            plauerResults.add(getPlayerResult(random.nextLong(100) + 1));
        }
        return plauerResults;
    }
}
