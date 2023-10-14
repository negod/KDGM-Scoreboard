/*
 */
package se.backede.scoreboard.admin.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import se.backede.scoreboard.admin.controller.helper.LeaderBoardCalculator;
import se.backede.scoreboard.admin.resources.dto.Competition;
import se.backede.scoreboard.admin.resources.dto.Game;
import se.backede.scoreboard.admin.resources.dto.GameType;
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
@Getter
@Setter
@Named("teamLeaderBoardController")
@ViewScoped
public class TeamLeaderBoardController implements Serializable {

    private Competition selectedCompetition;

    @Inject
    ViewCompetitionController viewCompetitionController;

    List<Result> allResults = new ArrayList<>();

    List<Match> matches;

    Match selectedMatch;

    Map<String, List<MatchResult>> results = new HashMap<>();

    @PostConstruct
    public void init() {
        updateData();
    }

    public GameType getGameType(String gameId) {
        Game game = viewCompetitionController.getGameController().getItemById(gameId);
        return game.gametype;
    }

    public void updateData() {

        selectedCompetition = viewCompetitionController.getSelectedCompetition();

        viewCompetitionController.getMatch().getMatchClient().getByCompetitionId(selectedCompetition.getId()).ifPresent(m -> {
            matches = m;
            allResults.clear();
            m.forEach(match -> viewCompetitionController
                    .getResultController()
                    .getResultClient()
                    .getByMatch(match.getId()).ifPresent(allResults::addAll));
        });

        results = LeaderBoardCalculator.mapMatchesAndResultsAndGroupByGame(matches, allResults).orElse(new HashMap<>());

    }

    public Optional<Result> getPlayerResult(String matchId, String playerId) {

        List<MatchResult> resultsForSelectedGame = results.get(viewCompetitionController.getGamesIndex().activeIndexGameId());

        return resultsForSelectedGame.stream()
                .filter(matchResult -> matchResult.getMatchId().equals(matchId))
                .flatMap(matchResult -> matchResult.getTeamResults().stream())
                .flatMap(teamResult -> teamResult.getResults().stream())
                .filter(result -> result.getPlayer().getId().equals(playerId))
                .findFirst();
    }

    public void upsertResults(List<Player> players, Match match) {

        players.forEach(player -> {

            Optional<Result> playerResult = getPlayerResult(match.getId(), player.getId());

            Result result = Result.builder()
                    .id(playerResult.map(Result::getId).orElse(null))
                    .matchId(match.getId())
                    .player(player)
                    .scoreValue(player.getScore())
                    .build();

            if (playerResult.isPresent()) {
                viewCompetitionController.getResultController().getResultClient().update(result);
            } else {
                viewCompetitionController.getResultController().getResultClient().create(result);
            }
        });

        updateData();

    }

    public void saveSelectedItem() {

        upsertResults(selectedMatch.getTeam1().getPlayers(), selectedMatch);
        upsertResults(selectedMatch.getTeam2().getPlayers(), selectedMatch);

        updateData();
        PrimeFaces.current().executeScript("PF('manageItemDialog').hide()");

    }

}
