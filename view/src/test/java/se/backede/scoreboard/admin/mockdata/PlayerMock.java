/*
 */
package se.backede.scoreboard.admin.mockdata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import se.backede.scoreboard.admin.resources.dto.Player;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class PlayerMock {

    public static Player getPlayer() {

        return Player.builder()
                .id(UUID.randomUUID().toString())
                .name(UUID.randomUUID().toString())
                .nickName(UUID.randomUUID().toString())
                .updated(new Date())
                .build();
    }

    public static List<Player> getPlayerList(int listSize) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            players.add(getPlayer());
        }
        return players;
    }

}
