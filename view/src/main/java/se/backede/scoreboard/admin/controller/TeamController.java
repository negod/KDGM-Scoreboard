/*
 */
package se.backede.scoreboard.admin.controller;

import se.backede.scoreboard.admin.commons.CrudController;
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
import se.backede.scoreboard.admin.resources.controller.PlayerRestClientController;
import se.backede.scoreboard.admin.resources.controller.TeamRestClientController;
import se.backede.scoreboard.admin.resources.dto.Player;
import se.backede.scoreboard.admin.resources.dto.Team;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@Named("teamController")
@ViewScoped
public class TeamController extends CrudController<Team> implements Serializable {

    private DualListModel<Player> dualList;
    private List<Player> players = new ArrayList<>();

    @Inject
    private TeamRestClientController teamClient;

    @Inject
    private PlayerRestClientController playerClient;

    @PostConstruct
    @Override
    public void init() {
        super.setupController(teamClient);

        playerClient.getAll().ifPresent(fetchedPlayers -> {
            players = fetchedPlayers;
            dualList = new DualListModel<>(players, new ArrayList<>());
        });
    }

    public void onChange() {

        if (getSelectedItem() == null) {
            return;
        }

        List<Player> selectedTeams = getSelectedItem().getPlayers();

        List<Player> availableTeams = players.stream()
                .filter(team -> !selectedTeams.contains(team))
                .collect(Collectors.toList());

        dualList = new DualListModel<>(availableTeams, selectedTeams);

        PrimeFaces.current().ajax().update("form:pickList");

    }

    @Override
    public void openNew() {
        setSelectedItem(new Team());

        playerClient.getAll().ifPresent(fetchedTeams -> {
            players = fetchedTeams;
            dualList = new DualListModel<>(players, new ArrayList<>());
        });
    }

    public void onTransfer(TransferEvent event) {
        if (event.isAdd()) {
            for (Object item : event.getItems()) {
                String id = (String) item;
                Player gameById = getPlayerById(id);
                super.getSelectedItem().getPlayers().add(gameById);
            }
        } else if (event.isRemove()) {
            for (Object item : event.getItems()) {
                String id = (String) item;
                Player gameById = getPlayerById(id);
                super.getSelectedItem().getPlayers().remove(gameById);
            }
        }
    }

    public Player getPlayerById(String teamId) {
        return players.stream()
                .filter(player -> player.getId().equals(teamId))
                .findFirst()
                .orElse(null);
    }

    public Team getTeamById(String teamId) {
        return getAllItems().stream()
                .filter(team -> team.getId().equals(teamId))
                .findFirst()
                .orElse(null);
    }

}
