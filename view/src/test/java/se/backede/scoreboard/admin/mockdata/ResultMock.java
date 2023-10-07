/*
 */
package se.backede.scoreboard.admin.mockdata;

import java.util.Random;
import java.util.UUID;
import se.backede.scoreboard.admin.resources.dto.Player;
import se.backede.scoreboard.admin.resources.dto.Result;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class ResultMock {

    public static Result getResult() {
        Random random = new Random();
        return Result.builder()
                .id(UUID.randomUUID().toString())
                .matchId(UUID.randomUUID().toString())
                .player(PlayerMock.getPlayer())
                .scoreValue(random.nextLong(100) + 1)
                .build();

    }
    
     public static Result getResult(String matchId, Player player) {
        Random random = new Random();
        return Result.builder()
                .id(UUID.randomUUID().toString())
                .matchId(UUID.randomUUID().toString())
                .player(PlayerMock.getPlayer())
                .scoreValue(random.nextLong(100) + 1)
                .build();

    }

}
