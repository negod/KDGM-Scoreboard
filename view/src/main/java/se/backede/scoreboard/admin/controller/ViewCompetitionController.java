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
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.menu.BaseMenuModel;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.MenuModel;
import se.backede.scoreboard.admin.controller.helper.IndexHelper;
import se.backede.scoreboard.admin.controller.helper.MatchMaker;
import se.backede.scoreboard.admin.resources.dto.Competition;
import se.backede.scoreboard.admin.resources.dto.Game;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@Named("viewCompetitionController")
@ViewScoped
public class ViewCompetitionController implements Serializable {

    @Inject
    @ManagedProperty(value = "#{param.competitionId}")
    String competitionId;

    private Competition selectedCompetition;

    MenuModel stepsModel;
    IndexHelper gamesIndex;
    MatchMaker matchMaker = new MatchMaker();

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

    Map<Integer, Game> gameIndex = new HashMap<>();

    @PostConstruct
    public void init() {
        selectedCompetition = competition.getItemById(competitionId);
        prepareSteps();
        gamesIndex = new IndexHelper(selectedCompetition.getGames().size(), 0);
    }

    public void prepareSteps() {
        stepsModel = new BaseMenuModel();
        int index = 0;
        for (Game competitionGame : selectedCompetition.getGames()) {
            gameIndex.put(index, competitionGame);
            DefaultMenuItem item = DefaultMenuItem.builder()
                    .url("#")
                    .value(competitionGame.getName())
                    .build();
            stepsModel.getElements().add(item);
            index++;
        }
    }

}
