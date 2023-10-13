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
import se.backede.scoreboard.admin.resources.dto.GameMatch;
import se.backede.scoreboard.admin.resources.dto.Match;
import se.backede.scoreboard.admin.resources.dto.Team;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class MatchHelper {

    public static Map<String, GameMatch> getMatchesGroupedOnGameId(List<Match> matches) {
        Map<String, GameMatch> indexedMatches = new HashMap<>();

        Map<Game, List<Match>> matchesMap = matches.stream().collect(Collectors.groupingBy(Match::getGame));

        List<GameMatch> gameMatches = matchesMap.entrySet().stream()
                .map(entry -> GameMatch.builder()
                .game(entry.getKey())
                .matches(entry.getValue())
                .build())
                .collect(Collectors.toList());

        //Fixa nedan
        for (GameMatch gameMatche : gameMatches) {
            indexedMatches.put(gameMatche.getGame().getId(), gameMatche);
        }

        return indexedMatches;
    }

    public static Map<String, GameMatch> createMatches(List<Game> games, List<Team> teams) {

        Map<String, GameMatch> matches = new HashMap<>();

        for (Game game : games) {
            List<Match> generateMatches = generateMatches(teams);

            for (Match generateMatche : generateMatches) {
                generateMatche.setGame(game);
            }

            GameMatch build = GameMatch.builder().game(game).matches(generateMatches).build();
            matches.put(game.getId(), build);
        }
        return matches;

    }

    public static List<Match> generateMatches(List<Team> teams) {
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
                    matches.add(Match.builder().team1(team1).team2(team2).build());
                }
            }

            // Rotate teams for the next round
            Team lastTeam = rotationList.remove(rotationList.size() - 1);
            rotationList.add(1, lastTeam);
        }
        return matches;
    }

}
