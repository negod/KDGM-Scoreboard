/*
 */
package se.backede.scoreboard.admin.mockdata;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import se.backede.scoreboard.admin.resources.dto.Competition;
import se.backede.scoreboard.admin.resources.dto.Game;
import se.backede.scoreboard.admin.resources.dto.Team;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class CompetitionMock {

    public static Competition getCompetition() {

        return Competition.builder()
                .id(UUID.randomUUID().toString())
                .competitionDate(new Date())
                .games(GameMock.getGames(3))
                .teams(TeamMock.getTeams(2))
                .build();

    }

    public static Competition getCompetition(List<Game> games, List<Team> teams) {

        return Competition.builder()
                .id(UUID.randomUUID().toString())
                .competitionDate(new Date())
                .games(games)
                .teams(teams)
                .build();

    }

}
