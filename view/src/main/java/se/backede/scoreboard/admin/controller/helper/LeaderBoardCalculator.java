/*
 */
package se.backede.scoreboard.admin.controller.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        Map<Integer, MatchResult> mappedResults = new HashMap<>();

        if (results != null && matches != null) {
            List<MatchResult> pairMatchResultswithResults = pairMatchResultswithResults(matches, results);

            //fix this The order that corresponds to a game
            int order = 0;
            for (MatchResult match : pairMatchResultswithResults) {
                mappedResults.put(order, match);
                order++;
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
                PlayerResult build = PlayerResult.builder().player(result.getPlayer()).scoreValue(result.getScoreValue()).build();
                playerResults.add(build);
            }
        }

        List<TeamResult> teamResults = new ArrayList<>();

        for (Player player : match.getTeam1().getPlayers()) {
            List<PlayerResult> playerResultForPlayer = getPlayerResultForPlayer(player, playerResults);
            TeamResult team = TeamResult.builder().results(playerResultForPlayer).team(match.getTeam1()).build();
            Long calculateTeamScore = calculateTeamScore(team);
            team.setCalculatedTeamScore(calculateTeamScore);
            teamResults.add(team);
        }

        for (Player player : match.getTeam2().getPlayers()) {
            List<PlayerResult> playerResultForPlayer = getPlayerResultForPlayer(player, playerResults);
            TeamResult team = TeamResult.builder().results(playerResultForPlayer).team(match.getTeam2()).build();
            Long calculateTeamScore = calculateTeamScore(team);
            team.setCalculatedTeamScore(calculateTeamScore);
            teamResults.add(team);
        }

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

}
