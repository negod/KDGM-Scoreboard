/*
 */
package se.backede.scoreboard.admin.controller.helper;

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
import se.backede.scoreboard.admin.resources.dto.PlayerLeaderBoard;
import se.backede.scoreboard.admin.resources.dto.PlayerResult;
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

    public static Optional<Map<String, List<TeamLeaderBoard>>> calculateTeamResultsAndGroupByGame(Map<String, List<MatchResult>> results, List<String> gameIds) {

        Map<String, List<TeamLeaderBoard>> teamScores = new HashMap<>();
        for (String gameId : gameIds) { //För varje game
            Map<String, Long> aggregatedTeamScores = new HashMap<>();

            for (MatchResult matchResult : results.get(gameId)) { // Gå igenom alla Matchresults, där alla teamens poäng redan är kalkylerad
                for (TeamResult teamResult : matchResult.getTeamResults()) {// Lägg teamets namn som nyckel och lägg till teamets poäng
                    aggregatedTeamScores.compute(teamResult.getTeam().getName(), (key, currentScore) -> {
                        if (currentScore == null) {
                            return teamResult.getCalculatedTeamScore();
                        } else {
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

    public static Optional<Map<String, List<PlayerLeaderBoard>>> calculateTotalPlayerREsultsAndGroupByGame(Map<String, List<MatchResult>> results, List<String> gameIds) {

        Map<String, List<PlayerLeaderBoard>> playerScores = new HashMap<>();
        for (String gameId : gameIds) { //För varje game
            Map<String, Long> aggregatedPlayerScores = new HashMap<>();

            for (MatchResult matchResult : results.get(gameId)) { // Gå igenom alla Matchresults, där alla teamens poäng redan är kalkylerad
                for (TeamResult teamResult : matchResult.getTeamResults()) {// Lägg teamets namn som nyckel och lägg till teamets poäng
                    for (Result playerResult : teamResult.getResults()) {

                        aggregatedPlayerScores.compute(playerResult.getPlayer().getName(), (key, currentScore) -> {
                            if (currentScore == null) {
                                if (playerResult.getScoreValue() == null) {
                                    return 0L;
                                } else {
                                    return playerResult.getScoreValue();
                                }
                            } else {
                                if (playerResult.getScoreValue() == null) {
                                    return playerResult.getScoreValue();
                                } else {
                                    return currentScore + playerResult.getScoreValue();
                                }
                            }
                        });

                    }

                }
            }

            List<PlayerLeaderBoard> playerScoresList = aggregatedPlayerScores.entrySet().stream()
                    .map(entry -> PlayerLeaderBoard.builder()
                    .playerName(entry.getKey())
                    .score(entry.getValue())
                    .build())
                    .collect(Collectors.toList());

            playerScores.put(gameId, playerScoresList);
        }
        return Optional.ofNullable(playerScores);
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
