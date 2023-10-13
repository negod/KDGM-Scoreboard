/*
 */
package se.backede.scoreboard.admin.controller.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.backede.scoreboard.admin.resources.dto.Match;
import se.backede.scoreboard.admin.resources.dto.MatchResult;
import se.backede.scoreboard.admin.resources.dto.Player;
import se.backede.scoreboard.admin.resources.dto.PlayerResult;
import se.backede.scoreboard.admin.resources.dto.Result;
import se.backede.scoreboard.admin.resources.dto.TeamResult;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class LeaderBoardCalculator {
    
    public static Map<Integer, MatchResult> mapMatchResults(List<Match> matches, List<Result> results) {
        
        Logger.getLogger(LeaderBoardCalculator.class.getName()).log(Level.INFO, "Size of Matches {0}", new Object[]{matches.size()});
        
        Map<Integer, MatchResult> mappedResults = new HashMap<>();
        
        if (results != null && matches != null) {
            List<MatchResult> pairMatchResultswithResults = pairMatchResultswithResults(matches, results);

            //fix this The order that corresponds to a game
            for (MatchResult match : pairMatchResultswithResults) {
                mappedResults.put(match.getMatch().getOrder(), match);
            }
        }
        
        return mappedResults;
    }
    
    public static List<MatchResult> pairMatchResultswithResults(List<Match> matches, List<Result> results) {
        
        List<MatchResult> matchResults = new ArrayList<>();
        for (Match match : matches) {
            matchResults.add(pairResultWithMatches(match, results));
        }
        return matchResults;
    }
    
    public static MatchResult pairResultWithMatches(Match match, List<Result> results) {
        
        List<PlayerResult> playerResults = new ArrayList<>();
        for (Result result : results) {
            if (result.getMatchId().equals(match.getId())) {
                PlayerResult build = PlayerResult.builder()
                        .player(result.getPlayer())
                        .scoreValue(result.getScoreValue())
                        .resultId(result.getId())
                        .build();
                playerResults.add(build);
            }
        }
        
        List<TeamResult> teamResults = new ArrayList<>();

        //Team 1
        List<PlayerResult> extractedplayerResultsForTeam1 = new ArrayList<>();
        for (Player player : match.getTeam1().getPlayers()) {
            extractedplayerResultsForTeam1.addAll(getPlayerResultForPlayer(player, playerResults));
        }
        
        TeamResult team1Results = TeamResult.builder().results(extractedplayerResultsForTeam1).team(match.getTeam1()).build();
        Long calculateTeamScore = calculateTeamScore(team1Results);
        team1Results.setCalculatedTeamScore(calculateTeamScore);
        teamResults.add(team1Results);

        //Team 2
        List<PlayerResult> extractedplayerResultsForTeam2 = new ArrayList<>();
        for (Player player : match.getTeam2().getPlayers()) {
            extractedplayerResultsForTeam2.addAll(getPlayerResultForPlayer(player, playerResults));
        }
        
        TeamResult team2results = TeamResult.builder().results(extractedplayerResultsForTeam2).team(match.getTeam2()).build();
        Long calculateTeam2Score = calculateTeamScore(team2results);
        team2results.setCalculatedTeamScore(calculateTeam2Score);
        teamResults.add(team2results);
        
        Map<Integer, MatchResult> matchResultMap = new HashMap<>();
        
        return MatchResult.builder().match(match).teamResults(teamResults).build();
        
    }
    
    public static List<PlayerResult> getPlayerResultForPlayer(Player player, List<PlayerResult> results) {
        List<PlayerResult> playerResults = new ArrayList<>();
        for (PlayerResult result : results) {
            if (result.getPlayer().getId().equals(player.getId())) {
                playerResults.add(result);
            }
        }
        return playerResults;
    }
    
    public static Long calculateTeamScore(TeamResult teamResult) {
        Long totalScore = 0L;
        for (PlayerResult result : teamResult.getResults()) {
            totalScore += result.getScoreValue();
        }
        return totalScore;
    }
    
    public void saveSelectedItem() {
        
    }
    
}
