/*
 */
package se.backede.scoreboard.admin.controller.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import se.backede.scoreboard.admin.mockdata.ModelMock;
import se.backede.scoreboard.admin.mockdata.ResultMock;
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
public class LeaderBoardCalculatorTest {

    public LeaderBoardCalculatorTest() {
    }

    /**
     * Test of mapMatchesAndResultsAndGroupByGame method, of class
     * LeaderBoardCalculator.
     */
    //@Test
    public void testMapMatchResults() {
        System.out.println("mapMatchResults");

        int expectedOrder1 = 0;
        int expectedOrder2 = 1;

        Match match1 = ModelMock.getMatch(2, expectedOrder1);
        Match match2 = ModelMock.getMatch(2, expectedOrder2);

        List<Match> matches = new ArrayList<>();
        matches.add(match1);
        matches.add(match2);

        List<Player> team1PlayersMatch1 = match1.getTeam1().getPlayers();
        List<Player> team2PlayersMatch1 = match1.getTeam2().getPlayers();

        List<Player> team1PlayersMatch2 = match2.getTeam1().getPlayers();
        List<Player> team2PlayersMatch2 = match2.getTeam2().getPlayers();

        List<Result> results = new ArrayList<>();
        List<Result> expetedResults = new ArrayList<>();

        // Add results for the first match
        for (Player player : team1PlayersMatch1) {
            Result result = ResultMock.getResult(match1.getId(), player);
            expetedResults.add(result);
            results.add(result);
        }

        for (Player player : team2PlayersMatch2) {
            Result result = ResultMock.getResult(match1.getId(), player);
            expetedResults.add(result);
            results.add(result);
        }

        // Add results for the second match
        for (Player player : team1PlayersMatch2) {
            Result result = ResultMock.getResult(match2.getId(), player);
            expetedResults.add(result);
            results.add(result);

        }

        for (Player player : team2PlayersMatch2) {
            Result result = ResultMock.getResult(match2.getId(), player);
            expetedResults.add(result);
            results.add(result);
        }

        Optional<Map<String, List<MatchResult>>> matchresults = LeaderBoardCalculator.mapMatchesAndResultsAndGroupByGame(matches, results);

        //We have 2 matches ( match1 and match2 )and should only have 2 Match results
        assertEquals(2, matchresults.get().size());

        assertTrue(matchresults.get().get(expectedOrder1) != null);
        assertTrue(matchresults.get().get(expectedOrder2) != null);

        //Each team should have 2 results, one for each player we have 2 teams so it should be 4
//        assertEquals(2, matchresults.get().get(expectedOrder1).getTeamResults().size());
//        assertEquals(2, matchresults.get().get(expectedOrder2).getTeamResults().size());
    }

    /**
     * Test of pairMatchResultswithResults method, of class
     * LeaderBoardCalculator.
     */
    @Test
    public void testPairMatchResultswithResults() {
        System.out.println("pairMatchResultswithResults");

        Match match1 = ModelMock.getMatch(2, 0);
        Match match2 = ModelMock.getMatch(2, 1);

        List<Match> matches = new ArrayList<>();
        matches.add(match1);
        matches.add(match2);

        List<Player> team1PlayersMatch1 = match1.getTeam1().getPlayers();
        List<Player> team2PlayersMatch1 = match1.getTeam2().getPlayers();

        List<Player> team1PlayersMatch2 = match2.getTeam1().getPlayers();
        List<Player> team2PlayersMatch2 = match2.getTeam2().getPlayers();

        List<Result> results = new ArrayList<>();
        List<Result> expetedResults = new ArrayList<>();

        // Add results for the first match
        for (Player player : team1PlayersMatch1) {
            Result result = ResultMock.getResult(match1.getId(), player);
            expetedResults.add(result);
            results.add(result);
        }

        for (Player player : team2PlayersMatch2) {
            Result result = ResultMock.getResult(match1.getId(), player);
            expetedResults.add(result);
            results.add(result);
        }

        // Add results for the second match
        for (Player player : team1PlayersMatch2) {
            Result result = ResultMock.getResult(match2.getId(), player);
            expetedResults.add(result);
            results.add(result);

        }

        for (Player player : team2PlayersMatch2) {
            Result result = ResultMock.getResult(match2.getId(), player);
            expetedResults.add(result);
            results.add(result);
        }

        List<MatchResult> matchresults = LeaderBoardCalculator.pairMatchResultsWithResults(matches, results);

        //We have 2 matches ( match1 and match2 )and should only have 2 Match results
        assertEquals(2, matchresults.size());

        assertTrue(matchresults.stream().anyMatch(matchResult -> match1.getId().equals(matchResult.getMatchId())));
        assertTrue(matchresults.stream().anyMatch(matchResult -> match2.getId().equals(matchResult.getMatchId())));

        //Each team should have 2 results, one for each player we have 2 teams so it should be 4
        assertEquals(2, matchresults.get(0).getTeamResults().size());
        assertEquals(2, matchresults.get(1).getTeamResults().size());

    }

    /**
     * Test of pairResultsWithMatchAndCreateMatchResult method, of class
     * LeaderBoardCalculator.
     */
    @Test
    public void testPairResultWithMatches() {
        System.out.println("pairResultWithMatches");

        Match match1 = ModelMock.getMatch(2, 0);
        Match match2 = ModelMock.getMatch(2, 1);

        List<Player> team1PlayersMatch1 = match1.getTeam1().getPlayers();
        List<Player> team2PlayersMatch1 = match1.getTeam2().getPlayers();

        List<Player> team1PlayersMatch2 = match2.getTeam1().getPlayers();
        List<Player> team2PlayersMatch2 = match2.getTeam2().getPlayers();

        List<Result> results = new ArrayList<>();
        List<Result> expetedResults = new ArrayList<>();

        // Add results for the first match
        for (Player player : team1PlayersMatch1) {
            Result result = ResultMock.getResult(match1.getId(), player);
            expetedResults.add(result);
            results.add(result);
        }

        for (Player player : team2PlayersMatch2) {
            Result result = ResultMock.getResult(match1.getId(), player);
            expetedResults.add(result);
            results.add(result);
        }

        // Add results for the second match
        for (Player player : team1PlayersMatch2) {
            results.add(ResultMock.getResult(match2.getId(), player));
        }

        for (Player player : team2PlayersMatch2) {
            results.add(ResultMock.getResult(match2.getId(), player));
        }

        MatchResult result = LeaderBoardCalculator.pairResultsWithMatchAndCreateMatchResult(match1, results);

        //This should only
        assertEquals(result.getMatchId(), match1.getId());

        //We should have 1 result for each player in the team. 
        List<TeamResult> teamResults = result.getTeamResults();
        assertEquals(2, teamResults.size());

    }

    /**
     * Test of getPlayerResultForPlayer method, of class LeaderBoardCalculator.
     */
    @Test
    public void testGetPlayerResultForPlayer() {
        System.out.println("getPlayerResultForPlayer");
        TeamResult team1 = ModelMock.getTeamResult(2);
        TeamResult team2 = ModelMock.getTeamResult(2);

        int expectedListSizeAll = 4;
        List<Result> allResults = new ArrayList<>();
        allResults.addAll(team1.getResults());
        allResults.addAll(team2.getResults());

        assertEquals(expectedListSizeAll, allResults.size());

        Result selectedResult = team1.getResults().get(0);
        Player selectedPlayer = selectedResult.getPlayer();

        int expectedPlayerResultListSize = 1;

        List<Result> results = LeaderBoardCalculator.getPlayerResultForPlayer(selectedPlayer, allResults);

        assertEquals(expectedPlayerResultListSize, results.size());
        assertEquals(selectedPlayer.getId(), results.get(0).getPlayer().getId());

    }

    /**
     * Test of calculateTeamScore method, of class LeaderBoardCalculator.
     */
    @Test
    public void testCalculateTeamScore() {
        System.out.println("calculateTeamScore");

        TeamResult team1 = ModelMock.getTeamResult(2);
        TeamResult team2 = ModelMock.getTeamResult(2);

        //calaulate the expected results for team 1
        Long expResultTeam1 = 0L;
        for (Result playerResult : team1.getResults()) {
            expResultTeam1 += playerResult.getScoreValue();
        }

        //calaulate the expected results for team 2
        Long expResultTeam2 = 0L;
        for (Result playerResult : team2.getResults()) {
            expResultTeam2 += playerResult.getScoreValue();
        }

        Optional<Long> resultTeam1 = LeaderBoardCalculator.calculateTeamScore(team1.getResults());
        Optional<Long> resultTeam2 = LeaderBoardCalculator.calculateTeamScore(team2.getResults());

        assertTrue(resultTeam1.isPresent());
        assertTrue(resultTeam2.isPresent());

        assertEquals(expResultTeam1, resultTeam1.get());
        assertEquals(expResultTeam2, resultTeam2.get());
    }

}
