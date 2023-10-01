/*
 */
package se.backede.scoreboard.admin.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import se.backede.scoreboard.admin.commons.CrudController;
import se.backede.scoreboard.admin.resources.controller.CompetitionRestClientController;
import se.backede.scoreboard.admin.resources.dto.Competition;
import se.backede.scoreboard.admin.resources.dto.Game;
import se.backede.scoreboard.admin.resources.dto.Team;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@Named("competitionController")
@ViewScoped
public class CompetitionController extends CrudController<Competition> implements Serializable {

    @Inject
    CompetitionRestClientController competitionClient;

    @Inject
    GameController game;

    @Inject
    TeamController team;

    @PostConstruct
    @Override
    public void init() {
        super.setupController(competitionClient);
    }

    @Override
    public void openNew() {
        setSelectedItem(new Competition());
    }

    public void onDualListChange() {

        if (getSelectedItem() == null) {
            return;
        }

        List<Game> selectedGames = getSelectedItem().getGames();
        List<Team> selectedTeams = getSelectedItem().getTeams();

        List<Game> availableGames = game.getAllItems().stream()
                .filter(game -> !selectedGames.contains(game))
                .collect(Collectors.toList());

        List<Team> availableTeams = team.getAllItems().stream()
                .filter(team -> !selectedTeams.contains(team))
                .collect(Collectors.toList());

        game.setDualList(new DualListModel<>(availableGames, selectedGames));
        team.setDualList(new DualListModel<>(availableTeams, selectedTeams));
        
        PrimeFaces.current().ajax().update("form:gamePickList", "form:teamPickList");

    }

    public void onDualListTransfer(TransferEvent event) {
    }

    public void onGameTransfer(TransferEvent event) {
        if (event.isAdd()) {
            for (Object item : event.getItems()) {
                super.getSelectedItem().getGames().add((Game) item);
            }
        } else if (event.isRemove()) {
            for (Object item : event.getItems()) {
                super.getSelectedItem().getGames().remove((Game) item);
            }
        }

        super.saveItem();
    }

    public void onTeamTransfer(TransferEvent event) {
        if (event.isAdd()) {
            for (Object item : event.getItems()) {
                super.getSelectedItem().getTeams().add((Team) item);
            }
        } else if (event.isRemove()) {
            for (Object item : event.getItems()) {
                super.getSelectedItem().getTeams().remove((Team) item);
            }
        }

        super.saveItem();
    }

}
