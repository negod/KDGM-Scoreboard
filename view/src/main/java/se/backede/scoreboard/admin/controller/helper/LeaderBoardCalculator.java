/*
 */
package se.backede.scoreboard.admin.controller.helper;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import se.backede.scoreboard.admin.resources.dto.Game;
import se.backede.scoreboard.admin.resources.dto.GameType;
import static se.backede.scoreboard.admin.resources.dto.GameType.SCORE;
import static se.backede.scoreboard.admin.resources.dto.GameType.TIME;
import se.backede.scoreboard.admin.resources.dto.Match;
import se.backede.scoreboard.admin.resources.dto.MatchResult;
import se.backede.scoreboard.admin.resources.dto.Player;
import se.backede.scoreboard.admin.resources.dto.PlayerLeaderBoard;
import se.backede.scoreboard.admin.resources.dto.Result;
import se.backede.scoreboard.admin.resources.dto.ScoreCalculation;
import se.backede.scoreboard.admin.resources.dto.Team;
import se.backede.scoreboard.admin.resources.dto.TeamLeaderBoard;
import se.backede.scoreboard.admin.resources.dto.TeamResult;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class LeaderBoardCalculator {
    
    private static final Logger LOGGER = Logger.getLogger(LeaderBoardCalculator.class.getName());
    
    public static List<TeamLeaderBoard> calculateTotalTeamLeaderBoard(Map<String, List<TeamLeaderBoard>> teamScoresOrderedByGameIds, Map<String, Game> gameIds) {
        
        Map<String, Long> totalScores = new HashMap<>();
        
        for (String gameId : gameIds.keySet()) {
            GameType gameType = gameIds.get(gameId).getGametype();
            List<TeamLeaderBoard> leaderBoards = teamScoresOrderedByGameIds.get(gameId);
            
            if (gameType == GameType.TIME) {
                leaderBoards.sort(Comparator.comparingLong(TeamLeaderBoard::getTeamScore)); // Lowest time is best
            } else if (gameType == GameType.SCORE) {
                leaderBoards.sort(Comparator.comparingLong(TeamLeaderBoard::getTeamScore).reversed()); // Highest score is best
            }
            
            for (int i = 0; i < leaderBoards.size(); i++) {
                TeamLeaderBoard board = leaderBoards.get(i);
                long pointsToAdd = (i < 10) ? 100 - i * 10 : 0;  // Teams after 10th place get 0 points
                totalScores.merge(board.getTeamName(), pointsToAdd, Long::sum);
            }
        }
        
        return totalScores.entrySet().stream()
                .map(entry -> TeamLeaderBoard.builder()
                .teamName(entry.getKey())
                .teamScore(entry.getValue())
                .build())
                .sorted(Comparator.comparingLong(TeamLeaderBoard::getTeamScore).reversed()) // Sort descending to have the highest score first
                .collect(Collectors.toList());
    }
    
    public static List<PlayerLeaderBoard> calculateTotalPlayerLeaderBoard(Map<String, List<PlayerLeaderBoard>> playerScoresOrderedByGameIds, Map<String, Game> gameIds) {
        
        Map<String, Long> totalScores = new HashMap<>();
        Map<String, String> teamNames = new HashMap<>();
        
        for (String gameId : gameIds.keySet()) {
            GameType gameType = gameIds.get(gameId).getGametype();
            List<PlayerLeaderBoard> leaderBoards = playerScoresOrderedByGameIds.get(gameId);
            
            if (gameType == GameType.TIME) {
                leaderBoards.sort(Comparator.comparingLong(PlayerLeaderBoard::getScore)); // Lowest time is best
            } else if (gameType == GameType.SCORE) {
                leaderBoards.sort(Comparator.comparingLong(PlayerLeaderBoard::getScore).reversed()); // Highest score is best
            }
            
            for (int i = 0; i < leaderBoards.size(); i++) {
                PlayerLeaderBoard board = leaderBoards.get(i);
                teamNames.putIfAbsent(board.getPlayerName(), board.getTeamName());
                long pointsToAdd = (i < 10) ? 100 - i * 10 : 0;  // Teams after 10th place get 0 points
                totalScores.merge(board.getPlayerName(), pointsToAdd, Long::sum);
            }
        }
        
        return totalScores.entrySet().stream()
                .map(entry -> PlayerLeaderBoard.builder()
                .playerName(entry.getKey())
                .teamName(teamNames.get(entry.getKey()))
                .score(entry.getValue())
                .build())
                .sorted(Comparator.comparingLong(PlayerLeaderBoard::getScore).reversed()) // Sort descending to have the highest score first
                .collect(Collectors.toList());
    }
    
    public static Optional<Map<String, List<TeamLeaderBoard>>> calculateTeamResultsAndGroupByGame(Map<String, List<MatchResult>> results, Map<String, Game> gameIds) {
        
        if (results.isEmpty()) {
            return Optional.empty();
        }
        
        Map<String, List<TeamLeaderBoard>> teamScores = new HashMap<>();
        for (String gameId : gameIds.keySet()) {
            Map<String, Long> aggregatedTeamScores = new HashMap<>();
            Map<String, Long> totalResultCounts = new HashMap<>(); // Track the total number of Result objects for each team

            for (MatchResult matchResult : results.get(gameId)) {
                for (TeamResult teamResult : matchResult.getTeamResults()) {
                    aggregatedTeamScores.compute(teamResult.getTeam().getName(), (key, currentScore) -> {
                        if (currentScore == null) {
                            return teamResult.getCalculatedTeamScore();
                        } else {
                            return currentScore + teamResult.getCalculatedTeamScore();
                        }
                    });

                    // Increment the total Result count for each team
                    totalResultCounts.merge(teamResult.getTeam().getName(), (long) teamResult.getResults().size(), Long::sum);
                }
            }
            
            ScoreCalculation calculationType = gameIds.get(gameId).getCalculationtype();
            if (calculationType == ScoreCalculation.AVERAGE) {
                // Beräkna genomsnittspoängen för varje lag och uppdatera 'aggregatedTeamScores'
                for (Map.Entry<String, Long> entry : aggregatedTeamScores.entrySet()) {
                    String teamName = entry.getKey();
                    Long totalScore = entry.getValue();

                    // Hämta det totala antalet resultat för laget
                    Long count = totalResultCounts.get(teamName);
                    
                    if (count != null && count > 0) {
                        // Beräkna genomsnittet. Vi behöver konvertera summan och antalet till double för att undvika förlust av information vid division.
                        double average = (double) totalScore / count;
                        // Du kan välja att avrunda värdet här eller behålla det som en double beroende på dina krav
                        long roundedAverage = Math.round(average);
                        aggregatedTeamScores.put(teamName, roundedAverage);
                    }
                }
            }
            
            List<TeamLeaderBoard> teamLeaderBoards = aggregatedTeamScores.entrySet().stream()
                    .map(entry -> TeamLeaderBoard.builder()
                    .teamName(entry.getKey())
                    .teamScore(entry.getValue())
                    .build())
                    .collect(Collectors.toList());
            
            switch (gameIds.get(gameId).getGametype()) {
                case SCORE:
                    teamLeaderBoards.sort(Comparator.comparingLong(TeamLeaderBoard::getTeamScore).reversed());
                    break;
                case TIME:
                    teamLeaderBoards.sort(Comparator.comparingLong(TeamLeaderBoard::getTeamScore));
                    break;
                default:
                    throw new AssertionError();
            }
            
            teamScores.put(gameId, teamLeaderBoards);
        }
        return Optional.ofNullable(teamScores);
    }
    
    public static Optional<Map<String, List<PlayerLeaderBoard>>> calculateTotalPlayerResultsAndGroupByGame(Map<String, List<MatchResult>> results, Map<String, Game> gameIds) {
        if (results.isEmpty()) {
            return Optional.empty();
        }
        
        Map<String, List<PlayerLeaderBoard>> playerScores = new HashMap<>();
        for (String gameId : gameIds.keySet()) {
            Map<String, Long> aggregatedPlayerScores = new HashMap<>();
            Map<String, Long> totalResultCounts = new HashMap<>(); // För att hålla reda på antalet matcher varje spelare har deltagit i

            Map<String, String> playerTeams = new HashMap<>();
            
            for (MatchResult matchResult : results.get(gameId)) {
                for (TeamResult teamResult : matchResult.getTeamResults()) {
                    for (Result playerResult : teamResult.getResults()) {
                        String playerName = playerResult.getPlayer().getName();
                        playerTeams.put(playerName, teamResult.getTeam().getName());
                        // Summera spelarens poäng
                        aggregatedPlayerScores.compute(playerName, (key, currentScore) -> {
                            if (currentScore == null) {
                                return (playerResult.getScoreValue() == null) ? 0L : playerResult.getScoreValue();
                            } else {
                                return (playerResult.getScoreValue() == null) ? currentScore : currentScore + playerResult.getScoreValue();
                            }
                        });

                        // Öka antalet resultat för spelaren
                        totalResultCounts.merge(playerName, 1L, Long::sum); // antar att varje Result representerar en match
                    }
                }
            }

            // Kontrollera om vi behöver beräkna genomsnittspoängen
            ScoreCalculation calculationType = gameIds.get(gameId).getCalculationtype();
            if (calculationType == ScoreCalculation.AVERAGE) {
                for (Map.Entry<String, Long> entry : aggregatedPlayerScores.entrySet()) {
                    String playerName = entry.getKey();
                    Long totalScore = entry.getValue();

                    // Hämta det totala antalet resultat för spelaren
                    Long count = totalResultCounts.get(playerName);

                    // Säkerställa att vi inte delar med noll, vilket skulle orsaka en ArithmeticException.
                    if (count != null && count > 0) {
                        double average = (double) totalScore / count; // Konvertera till double för precision vid division.
                        long roundedAverage = Math.round(average); // Avrunda till närmaste hela tal.
                        aggregatedPlayerScores.put(playerName, roundedAverage);
                    } else {
                        // Om en spelare av någon anledning har noll matcher spelade, kan du hantera det enligt ditt val.
                        // Exempelvis, sätta genomsnittspoängen till 0.
                        aggregatedPlayerScores.put(playerName, 0L);
                    }
                }
            }

            // Skapa ledartavlan för spelare
            List<PlayerLeaderBoard> playerLeaderBoards = aggregatedPlayerScores.entrySet().stream()
                    .map(entry -> PlayerLeaderBoard.builder()
                    .playerName(entry.getKey())
                    .teamName(playerTeams.get(entry.getKey()))
                    .score(entry.getValue()) // 'score' kan vara totala eller genomsnittliga poäng beroende på 'calculationType'
                    .build())
                    .collect(Collectors.toList());

            // Sortera listan baserat på spelets typ
            switch (gameIds.get(gameId).getGametype()) {
                case SCORE:
                    playerLeaderBoards.sort(Comparator.comparingLong(PlayerLeaderBoard::getScore).reversed());
                    break;
                case TIME:
                    playerLeaderBoards.sort(Comparator.comparingLong(PlayerLeaderBoard::getScore)); // antar att lägre tid är bättre
                    break;
                default:
                    throw new AssertionError("Okänd game type: " + gameIds.get(gameId).getGametype());
            }
            
            playerScores.put(gameId, playerLeaderBoards);
        }
        return Optional.ofNullable(playerScores);
    }
    
    public static Optional<Map<String, List<MatchResult>>> mapMatchesAndResultsAndGroupByGame(List<Match> matches, List<Result> results) {
        
        if (results == null || matches == null) {
            return Optional.empty();
        }
        
        List<MatchResult> pairMatchResultsWithResults = pairMatchResultsWithResults(matches, results);
        
        Map<String, List<MatchResult>> groupedMap = pairMatchResultsWithResults.stream()
                .collect(Collectors.groupingBy(matchResult -> matchResult.getGameId()));
        
        return Optional.of(groupedMap);
    }

    /**
     * Creates a MatchResults based on a list of teams and all results for the
     * competition
     *
     * @param matches
     * @param results
     * @return
     */
    public static List<MatchResult> pairMatchResultsWithResults(List<Match> matches, List<Result> results) {
        return matches.stream()
                .map(match -> pairResultsWithMatchAndCreateMatchResult(match, results))
                .collect(Collectors.toList());
    }

    /**
     * Filters all results on the team and the players in the team and creates a
     * Match result for tha team
     *
     * @param match
     * @param results
     * @return
     */
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

    /**
     * Builds a Team result based om the players in the team
     *
     * @param team The team with players
     * @param results All the results
     * @return
     */
    public static TeamResult buildTeamResult(Team team, List<Result> results) {
        List<Result> teamPlayerResults = team.getPlayers().stream()
                .flatMap(player -> getPlayerResultForPlayer(player, results).stream())
                .collect(Collectors.toList());
        
        Optional<Long> calculatedScore = calculateTeamScore(teamPlayerResults);
        
        return TeamResult.builder()
                .results(teamPlayerResults)
                .team(team)
                .calculatedTeamScore(calculatedScore.orElse(0L))
                .build();
    }

    /**
     * Gets all results for a specific player
     *
     * @param player The player to fiter the reults on
     * @param results The results to be filtered
     * @return
     */
    public static List<Result> getPlayerResultForPlayer(Player player, List<Result> results) {
        return results.stream()
                .filter(result -> result.getPlayer().getId().equals(player.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Calulates the total score value from the Resultslist
     *
     * @param results A list of Rssults
     * @return
     */
    public static Optional<Long> calculateTeamScore(List<Result> results) {
        long sum = results.stream()
                .map(Result::getScoreValue)
                .filter(score -> score != null) // Filter out null scores
                .mapToLong(Long::longValue)
                .sum();
        
        return (sum != 0) ? Optional.of(sum) : Optional.empty();
    }
    
}
