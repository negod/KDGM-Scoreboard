/*
 */
package se.backede.scoreboard.admin.controller.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import se.backede.scoreboard.admin.resources.dto.Match;
import se.backede.scoreboard.admin.resources.dto.MatchResult;
import se.backede.scoreboard.admin.resources.dto.Player;
import se.backede.scoreboard.admin.resources.dto.Result;
import se.backede.scoreboard.admin.resources.dto.Team;
import se.backede.scoreboard.admin.resources.dto.TeamLeaderBoard;
import se.backede.scoreboard.admin.resources.dto.TeamResult;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class LeaderBoardCalculator {

    private static final Logger LOGGER = Logger.getLogger(LeaderBoardCalculator.class.getName());

    public static Optional<Map<String, List<TeamLeaderBoard>>> groupByTeamResultsByGame(Map<String, List<MatchResult>> results, List<String> gameIds) {

        Map<String, List<TeamLeaderBoard>> teamScores = new HashMap<>();
        for (String gameId : gameIds) { //För varje game
            Map<String, Long> aggregatedTeamScores = new HashMap<>();
            LOGGER.log(Level.INFO, "Looping Game {0}", new Object[]{gameId});

            for (MatchResult matchResult : results.get(gameId)) { // Gå igenom alla Matchresults, där alla teamens poäng redan är kalkylerad

                LOGGER.log(Level.INFO, "MatchResult for Game {0} and Match {1}", new Object[]{matchResult.getGameId(), matchResult.getMatchId()});

                for (TeamResult teamResult : matchResult.getTeamResults()) {// Lägg teamets namn som nyckel och lägg till teamets poäng

                    LOGGER.log(Level.INFO, "TeamResult for Team {0} in Match {1} with calculated score {2}", new Object[]{teamResult.getTeam().getName(), matchResult.getMatchId(), teamResult.getCalculatedTeamScore()});

                    aggregatedTeamScores.compute(teamResult.getTeam().getName(), (key, currentScore) -> {
                        if (currentScore == null) {
                            LOGGER.log(Level.INFO, "Current SCore for Team {0} is {1}", new Object[]{teamResult.getTeam().getName(), teamResult.getCalculatedTeamScore()});
                            return teamResult.getCalculatedTeamScore();
                        } else {
                            LOGGER.log(Level.INFO, "Current Score for Team {0} is {1}", new Object[]{teamResult.getTeam().getName(), currentScore + teamResult.getCalculatedTeamScore()});
                            return currentScore + teamResult.getCalculatedTeamScore();
                        }
                    });
                }
            }

            List<TeamLeaderBoard> teamLeaderBoards = aggregatedTeamScores.entrySet().stream()
                    .map(entry -> TeamLeaderBoard.builder()
                    .teamName(entry.getKey())
                    .teamScore(entry.getValue())
                    .build())
                    .collect(Collectors.toList());

            teamScores.put(gameId, teamLeaderBoards);
        }
        return Optional.ofNullable(teamScores);
    }

    public static Optional<Map<String, List<MatchResult>>> mapMatchesAndResultsAndGroupByGame(List<Match> matches, List<Result> results) {
        LOGGER.log(Level.INFO, "Size of Matches {0}", matches.size());

        if (results == null || matches == null) {
            return Optional.empty();
        }

        List<MatchResult> pairMatchResultsWithResults = pairMatchResultsWithResults(matches, results);

        Map<String, List<MatchResult>> groupedMap = pairMatchResultsWithResults.stream()
                .collect(Collectors.groupingBy(matchResult -> matchResult.getGameId()));

        return Optional.of(groupedMap);
    }

    public static List<MatchResult> pairMatchResultsWithResults(List<Match> matches, List<Result> results) {
        return matches.stream()
                .map(match -> pairResultsWithMatchAndCreateMatchResult(match, results))
                .collect(Collectors.toList());
    }

    public static MatchResult pairResultsWithMatchAndCreateMatchResult(Match match, List<Result> results) {

        List<Result> playerResults = results.stream()
                .filter(result -> result.getMatchId().equals(match.getId()))
                .collect(Collectors.toList());

        TeamResult team1Results = buildTeamResult(match.getTeam1(), playerResults);
        TeamResult team2Results = buildTeamResult(match.getTeam2(), playerResults);

        return MatchResult.builder()
                .matchId(match.getId())
                .gameId(match.getGame().getId())
                .order(match.getOrder())
                .teamResults(Arrays.asList(team1Results, team2Results))
                .build();
    }

    public static TeamResult buildTeamResult(Team team, List<Result> playerResults) {
        List<Result> teamPlayerResults = team.getPlayers().stream()
                .flatMap(player -> getPlayerResultForPlayer(player, playerResults).stream())
                .collect(Collectors.toList());

        Optional<Long> calculatedScore = calculateTeamScore(teamPlayerResults);

        return TeamResult.builder()
                .results(teamPlayerResults)
                .team(team)
                .calculatedTeamScore(calculatedScore.orElse(0L))
                .build();
    }

    public static List<Result> getPlayerResultForPlayer(Player player, List<Result> results) {
        return results.stream()
                .filter(result -> result.getPlayer().getId().equals(player.getId()))
                .collect(Collectors.toList());
    }

    public static Optional<Long> calculateTeamScore(List<Result> teamPlayerResults) {
        long sum = teamPlayerResults.stream()
                .map(Result::getScoreValue)
                .filter(score -> score != null) // Filter out null scores
                .mapToLong(Long::longValue)
                .sum();

        return (sum != 0) ? Optional.of(sum) : Optional.empty();
    }

}
