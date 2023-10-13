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
import se.backede.scoreboard.admin.controller.helper.LeaderBoardCalculator;
import se.backede.scoreboard.admin.resources.dto.Competition;
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
    ViewCompetitionController view;

    List<Result> allResults = new ArrayList<>();

    List<Match> matches;

    Match selectedMatch;

    Map<Integer, MatchResult> results = new HashMap<>();

    @PostConstruct
    public void init() {

        selectedCompetition = view.getSelectedCompetition();
        updateData();
        results = LeaderBoardCalculator.mapMatchResults(matches, allResults);

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

        Map<Integer, MatchResult> mapMatchResults = LeaderBoardCalculator.mapMatchResults(matches, allResults);
        results = mapMatchResults;
    }

    public Optional<PlayerResult> playerAlreadyHasResult(String matchId, String playerId) {
        for (MatchResult value : results.values()) {
            if (value.getId() == null) {
                return Optional.empty();
            }
            if (value.getId().equals(matchId)) {
                for (TeamResult teamResult : value.getTeamResults()) {
                    for (PlayerResult result : teamResult.getResults()) {
                        if (result.getPlayer().getId().equals(playerId)) {
                            if (result.getResultId() == null) {
                                return Optional.empty();
                            }
                            return Optional.of(result);
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }

    public void saveSelectedItem() {

        for (Player team1Player : selectedMatch.getTeam1().getPlayers()) {

            Optional<PlayerResult> playerResult = playerAlreadyHasResult(selectedMatch.getId(), team1Player.getId());

            if (playerResult.isPresent()) {

                Result result = Result.builder()
                        .matchId(selectedMatch.getId())
                        .id(playerResult.get().getResultId())
                        .player(team1Player)
                        .scoreValue(team1Player.getScore())
                        .build();

                view.getResult().getResultClient().update(result);

            } else {
                Result result = Result.builder()
                        .matchId(selectedMatch.getId())
                        .player(team1Player)
                        .scoreValue(team1Player.getScore())
                        .build();

                view.getResult().getResultClient().create(result);
            }

        }

        for (Player team2Player : selectedMatch.getTeam2().getPlayers()) {

            Optional<PlayerResult> playerResult = playerAlreadyHasResult(selectedMatch.getId(), team2Player.getId());

            if (playerResult.isPresent()) {

                Result result = Result.builder()
                        .matchId(selectedMatch.getId())
                        .id(playerResult.get().getResultId())
                        .player(team2Player)
                        .scoreValue(team2Player.getScore())
                        .build();

                view.getResult().getResultClient().update(result);

            } else {
                Result result = Result.builder()
                        .matchId(selectedMatch.getId())
                        .player(team2Player)
                        .scoreValue(team2Player.getScore())
                        .build();

                view.getResult().getResultClient().create(result);
            }

        }

        updateData();

    }

}
