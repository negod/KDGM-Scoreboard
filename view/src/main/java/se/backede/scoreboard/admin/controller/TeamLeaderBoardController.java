/*
 */
package se.backede.scoreboard.admin.controller;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.DependsOn;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import se.backede.scoreboard.admin.controller.helper.LeaderBoardCalculator;
import se.backede.scoreboard.admin.resources.dto.Competition;
import se.backede.scoreboard.admin.resources.dto.Match;
import se.backede.scoreboard.admin.resources.dto.MatchResult;
import se.backede.scoreboard.admin.resources.dto.Result;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@Named("teamLeaderBoardController")
@ViewScoped
@DependsOn(value = {"viewCompetitionController"})
public class TeamLeaderBoardController implements Serializable {

    private Competition selectedCompetition;

    @Inject
    ViewCompetitionController view;

    List<Result> allResults = new ArrayList<>();

    List<Match> matches;

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

}
