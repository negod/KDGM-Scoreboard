/*
 */
package se.backede.scoreboard.admin.mockdata;

import java.util.UUID;
import se.backede.scoreboard.admin.resources.dto.Match;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class MatchMock {

    public static Match getMatch() {

        return Match.builder()
                .id(UUID.randomUUID().toString())
                .team1(TeamMock.getTeam())
                .team2(TeamMock.getTeam())
                .game(GameMock.getGame())
                .competition(CompetitionMock.getCompetition())
                .order(0)
                .build();

    }

}
