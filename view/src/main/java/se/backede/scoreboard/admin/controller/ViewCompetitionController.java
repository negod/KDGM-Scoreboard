/*
 */
package se.backede.scoreboard.admin.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.menu.BaseMenuModel;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.MenuModel;
import se.backede.scoreboard.admin.controller.helper.IndexHelper;
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

    @Inject
    CompetitionController competition;

    @Inject
    TeamController team;

    @Inject
    PlayerController player;

    @Inject
    GameController game;

    @PostConstruct
    public void init() {
        selectedCompetition = competition.getItemById(competitionId);
        prepareSteps();

        gamesIndex = new IndexHelper(selectedCompetition.getGames().size(), 0);
    }

    public void prepareSteps() {
        stepsModel = new BaseMenuModel();
        for (Game competitionGame : selectedCompetition.getGames()) {
            DefaultMenuItem item = DefaultMenuItem.builder()
                    .url("#")
                    .value(competitionGame.getName())
                    .build();
            stepsModel.getElements().add(item);
        }
    }

}
