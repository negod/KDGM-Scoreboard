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
import java.util.Random;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import se.backede.scoreboard.admin.resources.controller.TeamRestClientController;
import se.backede.scoreboard.admin.resources.dto.Player;
import se.backede.scoreboard.admin.resources.dto.Team;
import se.backede.scoreboard.admin.resources.dto.TeamName;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@Named("teamController")
@ViewScoped
public class TeamController extends CrudController<Team> implements Serializable {

    @Inject
    private TeamRestClientController teamClient;

    @Inject
    PlayerController player;

    private List<TeamName> teamNames = new ArrayList<>();

    @PostConstruct
    @Override
    public void init() {
        super.setupController(teamClient);

        teamClient.getAllTeamNames().ifPresent(teamNames -> {
            this.teamNames = teamNames;
        });

    }

    public TeamName getRandomTeamName() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(teamNames.size()); // generera ett index mellan 0 och (teamNames.size() - 1)
        return teamNames.get(randomIndex);
    }

    @Override
    public void onDualListChange() {

        if (getSelectedItem() == null) {
            return;
        }

        List<Player> selectedTeams = getSelectedItem().getPlayers();

        List<Player> availableTeams = player.getAllItems().stream()
                .filter(team -> !selectedTeams.contains(team))
                .collect(Collectors.toList());

        player.setDualList(new DualListModel<>(availableTeams, selectedTeams));

        PrimeFaces.current().ajax().update("form:pickList");

    }

    @Override
    public void openNew() {
        setSelectedItem(new Team());
        player.setDualList(new DualListModel<>(player.getAllItems(), new ArrayList<>()));
    }

    @Override
    public void onDualListTransfer(TransferEvent event) {
        if (event.isAdd()) {
            for (Object item : event.getItems()) {
                super.getSelectedItem().getPlayers().add((Player) item);
            }
        } else if (event.isRemove()) {
            for (Object item : event.getItems()) {
                super.getSelectedItem().getPlayers().remove((Player) item);
            }
        }
    }

}
