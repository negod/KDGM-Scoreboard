/*
 */
package se.backede.scoreboard.admin.controller.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import se.backede.scoreboard.admin.resources.dto.Game;
import se.backede.scoreboard.admin.resources.dto.Match;
import se.backede.scoreboard.admin.resources.dto.Team;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class MatchHelper {

    public static Map<String, List<Match>> getMatchesGroupedOnGameId(List<Match> matches) {
        return matches.stream()
                .collect(Collectors.groupingBy(match -> match.getGame().getId()));
    }

    public static Map<String, List<Match>> createMatches(List<Game> games, List<Team> teams) {

        Map<String, List<Match>> matches = new HashMap<>();

        for (Game game : games) {
            List<Match> generateMatches = generateMatches(teams, game);
            for (Match generateMatche : generateMatches) {
                generateMatche.setGame(game);
            }
            matches.put(game.getId(), generateMatches);
        }
        return matches;

    }

    public static List<Match> generateMatches(List<Team> teams, Game game) {
        List<Match> matches = new ArrayList<>();
        List<Team> rotationList = new LinkedList<>(teams);

        int totalRounds = teams.size() - 1;
        if (teams.size() % 2 != 0) {
            rotationList.add(new Team("BYE"));  // Adding a placeholder for an idle team.
            totalRounds++;
        }

        for (int i = 0; i < totalRounds; i++) {
            for (int j = 0; j < rotationList.size() / 2; j++) {
                Team team1 = rotationList.get(j);
                Team team2 = rotationList.get(rotationList.size() - 1 - j);

                // Don't add matches with the placeholder "BYE".
                if (!team1.getName().equals("BYE") && !team2.getName().equals("BYE")) {
                    matches.add(Match.builder().team1(team1).team2(team2).game(game).order(j).build());
                }
            }

            // Rotate teams for the next round
            Team lastTeam = rotationList.remove(rotationList.size() - 1);
            rotationList.add(1, lastTeam);
        }
        return matches;
    }

}
