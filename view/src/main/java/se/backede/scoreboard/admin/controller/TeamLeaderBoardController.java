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
    ViewCompetitionController view;

    List<Result> allResults = new ArrayList<>();

    List<Match> matches;

    Match selectedMatch;

    Map<String, List<MatchResult>> results = new HashMap<>();

    @PostConstruct
    public void init() {

        selectedCompetition = view.getSelectedCompetition();
        updateData();

        Optional<Map<String, List<MatchResult>>> matchresults = LeaderBoardCalculator.mapMatchResults(matches, allResults);

        if (matchresults.isPresent()) {
            results = matchresults.get();
        }

    }

    public GameType getGameType(String gameId) {
        Game game = view.getGame().getItemById(gameId);
        return game.gametype;
    }

    public void updateData() {

        view.getMatch().getMatchClient().getByCompetitionId(selectedCompetition.getId()).ifPresent(m -> {
            matches = m;
            for (Match match : m) {
                view.getResult().getResultClient().getByMatch(match.getId()).ifPresent(r -> {
                    allResults.addAll(r);
                });
            }
        });

        Optional<Map<String, List<MatchResult>>> matchresults = LeaderBoardCalculator.mapMatchResults(matches, allResults);

        if (matchresults.isPresent()) {
            results = matchresults.get();
        }

    }

    public Optional<PlayerResult> playerAlreadyHasResult(String matchId, String playerId) {
        return results.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream())
                .filter(matchResult -> matchId.equals(matchResult.getId()))
                .flatMap(matchResult -> matchResult.getTeamResults().stream())
                .flatMap(teamResult -> teamResult.getResults().stream())
                .filter(playerResult -> playerId.equals(playerResult.getPlayer().getId()))
                .findFirst();
    }

    public void upsertResults(List<Player> players, Match match) {

        for (Player team1Player : players) {

            Optional<PlayerResult> playerResult = playerAlreadyHasResult(match.getId(), team1Player.getId());

            if (playerResult.isPresent()) {

                Result result = Result.builder()
                        .matchId(match.getId())
                        .id(playerResult.get().getResultId())
                        .player(team1Player)
                        .scoreValue(team1Player.getScore())
                        .build();

                view.getResult().getResultClient().update(result);

            } else {
                Result result = Result.builder()
                        .matchId(match.getId())
                        .player(team1Player)
                        .scoreValue(team1Player.getScore())
                        .build();

                view.getResult().getResultClient().create(result);
            }

        }

    }

    public void saveSelectedItem() {

        upsertResults(selectedMatch.getTeam1().getPlayers(), selectedMatch);
        upsertResults(selectedMatch.getTeam2().getPlayers(), selectedMatch);

        updateData();
        PrimeFaces.current().executeScript("PF('manageItemDialog').hide()");

    }

}
