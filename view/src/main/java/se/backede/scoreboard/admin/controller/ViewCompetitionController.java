/*
 */
package se.backede.scoreboard.admin.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.menu.BaseMenuModel;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.MenuModel;
import se.backede.scoreboard.admin.resources.dto.GameMatch;
import se.backede.scoreboard.admin.controller.helper.IndexHelper;
import se.backede.scoreboard.admin.controller.helper.MatchHelper;
import se.backede.scoreboard.admin.resources.dto.Competition;
import se.backede.scoreboard.admin.resources.dto.Match;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@Named("viewCompetitionController")
@ViewScoped
//@DependsOn(value = {"competitionController, playerController, gameController, resultController, matchController"})
public class ViewCompetitionController implements Serializable {

    @Inject
    @ManagedProperty(value = "#{param.competitionId}")
    String competitionId;

    private Competition selectedCompetition;

    MenuModel stepsModel;
    IndexHelper gamesIndex;

    @Inject
    CompetitionController competition;

    @Inject
    TeamController team;

    @Inject
    PlayerController player;

    @Inject
    GameController game;

    @Inject
    ResultController result;

    @Inject
    MatchController match;

    Map<Integer, GameMatch> matches = new HashMap<>();

    @PostConstruct
    public void init() {
        selectedCompetition = competition.getItemById(competitionId);
        gamesIndex = new IndexHelper(selectedCompetition.getGames().size() - 1, 0);

        if (selectedCompetition.getStarted()) {
            match.getMatchClient().getByCompetitionId(selectedCompetition.getId()).ifPresent(matches -> {
                this.matches = MatchHelper.getIndexedMatches(matches);

            });
        } else {
            matches = MatchHelper.createMatches(selectedCompetition.getGames(), selectedCompetition.getTeams());

            for (Map.Entry<Integer, GameMatch> entry : matches.entrySet()) {

                //TODO Match Order
                Integer order = 1;
                for (Match matche : entry.getValue().getMatches()) {
                    matche.setCompetition(selectedCompetition);
                    matche.setOrder(order);
                    match.getMatchClient().create(matche);
                    order++;
                }

            }

            selectedCompetition.setStarted(Boolean.TRUE);
            competition.getCompetitionClient().update(selectedCompetition);
        }

        prepareSteps();
    }

    public List<Match> getSelectedGameMatches() {

        GameMatch match = matches.get(gamesIndex.getActiveIndex());
        return match.getMatches();

    }

    public void prepareSteps() {
        stepsModel = new BaseMenuModel();
        for (Integer index : matches.keySet()) {
            DefaultMenuItem item = DefaultMenuItem.builder()
                    .url("#")
                    .value(matches.get(index).getGame().getName())
                    .build();
            stepsModel.getElements().add(item);
        }
    }

}
