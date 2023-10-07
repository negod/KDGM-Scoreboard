/*
 */
package se.backede.scoreboard.admin.mockdata;

import java.util.List;
import se.backede.scoreboard.admin.resources.dto.PlayerResult;
import se.backede.scoreboard.admin.resources.dto.Team;
import se.backede.scoreboard.admin.resources.dto.TeamResult;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class TeamResultMock {

    public static TeamResult getTeamResult() {

        return TeamResult.builder()
                .results(PlayerResultMock.getPlayerResultList(4))
                .team(TeamMock.getTeam())
                .build();

    }

    public static TeamResult getTeamResult(List<PlayerResult> playerResults) {
        return TeamResult.builder()
                .results(playerResults)
                .team(TeamMock.getTeam())
                .build();

    }

    public static TeamResult getTeamResult(List<PlayerResult> playerResults, Team team) {
        return TeamResult.builder()
                .results(playerResults)
                .team(team)
                .build();

    }

}
