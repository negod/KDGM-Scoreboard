/*
 */
package se.backede.scoreboard.admin.controller;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@Named("createCompetitionController")
@ViewScoped
public class CreateCompetitionController implements Serializable {

    @Inject
    CompetitionController competition;

    @Inject
    GameController game;

    @Inject
    PlayerController player;

    @Inject
    TeamController team;

    private int activeIndex = 0;

    public void nextStep() {
        if (activeIndex < 4) {
            activeIndex++;
        }
    }

    public void prevStep() {
        if (activeIndex > 0) {
            activeIndex--;
        }
    }

    public void onCompetitionChange() {
        competition.onDualListChange();
    }

    public String getSelectedPlayers() {
//        int size = team.getPlayer().getDualList().getTarget().size();
//        return String.valueOf(size);

        return "5";
    }

}
