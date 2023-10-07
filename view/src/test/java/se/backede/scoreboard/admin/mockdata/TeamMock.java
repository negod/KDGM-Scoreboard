/*
 */
package se.backede.scoreboard.admin.mockdata;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import se.backede.scoreboard.admin.resources.dto.Player;
import se.backede.scoreboard.admin.resources.dto.Team;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class TeamMock {

    public static Team getTeam() {
        return Team.builder()
                .id(UUID.randomUUID().toString())
                .name(UUID.randomUUID().toString())
                .players(PlayerMock.getPlayerList(2))
                .build();
    }

    public static Team getTeam(List<Player> players) {
        return Team.builder()
                .id(UUID.randomUUID().toString())
                .name(UUID.randomUUID().toString())
                .players(players)
                .build();
    }

    public static List<Team> getTeams(int listSize) {
        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            teams.add(getTeam());
        }
        return teams;
    }

}
