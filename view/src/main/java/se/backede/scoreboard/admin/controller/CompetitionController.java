/*
 */
package se.backede.scoreboard.admin.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import se.backede.scoreboard.admin.commons.CrudController;
import se.backede.scoreboard.admin.resources.controller.CompetitionRestClientController;
import se.backede.scoreboard.admin.resources.controller.GameRestClientController;
import se.backede.scoreboard.admin.resources.controller.TeamRestClientController;
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
    GameRestClientController gameClient;

    @Inject
    TeamRestClientController teamClient;

    private List<Team> teams;
    private List<Game> games;

    private DualListModel<Game> gamesDualList;
    private DualListModel<Team> teamsDualList;

    @PostConstruct
    @Override
    public void init() {
        super.setupController(competitionClient);

        gameClient.getAll().ifPresent(allItems -> {
            games = allItems;
            gamesDualList = new DualListModel<>(games, new ArrayList<>());
        });

        teamClient.getAll().ifPresent(allItems -> {
            teams = allItems;
            teamsDualList = new DualListModel<>(teams, new ArrayList<>());
        });
    }

    @Override
    public void openNew() {
        setSelectedItem(new Competition());
    }

    public void onCompetitionChange() {
        if (getSelectedItem() == null) {
            return;
        }

        List<Game> selectedGames = getSelectedItem().getGames();
        List<Team> selectedTeams = getSelectedItem().getTeams();

        List<Game> availableGames = games.stream()
                .filter(game -> !selectedGames.contains(game))
                .collect(Collectors.toList());

        List<Team> availableTeams = teams.stream()
                .filter(team -> !selectedTeams.contains(team))
                .collect(Collectors.toList());

        gamesDualList = new DualListModel<>(availableGames, selectedGames);
        teamsDualList = new DualListModel<>(availableTeams, selectedTeams);

        PrimeFaces.current().ajax().update("form:gamePickList", "form:teamPickList");

    }

    public void onGameTransfer(TransferEvent event) {

        if (event.isAdd()) {
            for (Object item : event.getItems()) {
                String id = (String) item;
                Game gameById = getGameById(id);
                super.getSelectedItem().getGames().add(gameById);
            }
        } else if (event.isRemove()) {
            for (Object item : event.getItems()) {
                String id = (String) item;
                Game gameById = getGameById(id);
                super.getSelectedItem().getGames().remove(gameById);
            }
        }

        super.saveItem();
        // Logik för att hantera överföring av spel
    }

    public void onTeamTransfer(TransferEvent event) {
        if (event.isAdd()) {
            for (Object item : event.getItems()) {
                String id = (String) item;
                Team gameById = getTeamById(id);
                super.getSelectedItem().getTeams().add(gameById);
            }
        } else if (event.isRemove()) {
            for (Object item : event.getItems()) {
                String id = (String) item;
                Team gameById = getTeamById(id);
                super.getSelectedItem().getTeams().remove(gameById);
            }
        }

        super.saveItem();
        // Logik för att h
    }

    public Team getTeamById(String teamId) {
        for (Team team : teams) {
            if (team.getId().equals(teamId)) {
                return team;
            }
        }
        return null;
    }

    public Game getGameById(String teamId) {
        for (Game game : games) {
            if (game.getId().equals(teamId)) {
                return game;
            }
        }
        return null;
    }

    public Competition getCompetitionById(String competitionId) {
        for (Competition competition : getAllItems()) {
            if (competition.getId().equals(competitionId)) {
                return competition;
            }
        }
        return null;
    }

}
