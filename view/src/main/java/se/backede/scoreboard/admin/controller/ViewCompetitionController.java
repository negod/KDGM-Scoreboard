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
import se.backede.scoreboard.admin.controller.helper.IndexHelper;
import se.backede.scoreboard.admin.controller.helper.MatchHelper;
import se.backede.scoreboard.admin.resources.dto.Competition;
import se.backede.scoreboard.admin.resources.dto.Game;
import se.backede.scoreboard.admin.resources.dto.Match;
import se.backede.scoreboard.admin.resources.dto.Result;

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

    Map<String, List<Match>> matches = new HashMap<>();
    Map<String, List<Result>> resultList = new HashMap<>();

    @PostConstruct
    public void init() {
        selectedCompetition = competition.getItemById(competitionId);

        Map<Integer, String> indexGameId = new HashMap<>();
        for (Game game1 : selectedCompetition.getGames()) {
            indexGameId.put(game1.getGameOrder(), game1.getId());
        }

        gamesIndex = new IndexHelper(selectedCompetition.getGames().size() - 1, 0, indexGameId);

        if (selectedCompetition.getStarted()) {
            match.getMatchClient().getByCompetitionId(selectedCompetition.getId()).ifPresent(matches -> {
                this.matches = MatchHelper.getMatchesGroupedOnGameId(matches);
            });
        } else {
            matches = MatchHelper.createMatches(selectedCompetition.getGames(), selectedCompetition.getTeams());

            for (Map.Entry<String, List<Match>> entry : matches.entrySet()) {

                //TODO Match Order
                Integer order = 0;
                for (Match matche : entry.getValue()) {
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
        return matches.get(gamesIndex.getActiveIndex());
    }

    public void prepareSteps() {
        stepsModel = new BaseMenuModel();
        for (int i = 0; i < gamesIndex.getMaxIndex() + 1; i++) {
            Game gameById = this.game.getItemById(gamesIndex.getGameByIndex(i));
            DefaultMenuItem item = DefaultMenuItem.builder()
                    .url("#")
                    .value(gameById.getName())
                    .build();
            stepsModel.getElements().add(item);
        }
    }

}
