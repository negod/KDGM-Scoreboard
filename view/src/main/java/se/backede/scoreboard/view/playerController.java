/*
 */
package se.backede.scoreboard.view;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import se.backede.scoreboard.view.resources.dto.Player;
import se.backede.scoreboard.view.resources.controller.PlayerRestClientController;
import se.backede.scoreboard.view.resources.controller.TeamRestClientController;
import se.backede.scoreboard.view.resources.dto.Team;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@Named("playerController")
@ViewScoped
public class PlayerController implements Serializable {

    private Player selectedPlayer;
    private Team selectedTeam;

    private List<Player> players;
    private List<Player> selectedPlayers;

    private List<Team> teams;

    @Inject
    private PlayerRestClientController playerClient;

    @Inject
    private TeamRestClientController teamClient;

    @PostConstruct
    public void init() {

        playerClient.getAll().ifPresent(fetchedPlayers -> {
            players = fetchedPlayers;
        });

        teamClient.getAll().ifPresent(fetchedTeams -> {
            teams = fetchedTeams;
        });

    }

    public String getDeleteButtonMessage() {
        if (hasSelectedPlayers()) {
            int size = this.selectedPlayers.size();
            return size > 1 ? size + " players selected" : "1 player selected";
        }

        return "Delete";

    }

    public Team getTeamById(String teamId) {
        for (Team team : teams) {
            if (team.getId().equals(teamId)) {
                return team;
            }
        }
        return null;
    }

    public boolean hasSelectedPlayers() {
        return this.selectedPlayers != null && !this.selectedPlayers.isEmpty();
    }

    public void openNew() {
        Player player = new Player();
        this.selectedPlayer = player;
    }

    private void createPlayer() {

        Optional<Player> createdPlayer = playerClient.create(selectedPlayer);

        if (createdPlayer.isPresent()) {
            this.players.add(createdPlayer.get());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Player Added"));
        } else {
            FacesContext.getCurrentInstance().validationFailed();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("failed to add player"));
        }

    }

    private void updatePlayer() {
        Optional<Player> updatedPlayer = playerClient.update(selectedPlayer);

        if (updatedPlayer.isPresent()) {
            players.remove(selectedPlayer);
            players.add(updatedPlayer.get());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Updated Player"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-players");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Failed to Update Player"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-players");
        }

    }

    public void savePlayer() {
        Optional<String> playerId = Optional.ofNullable(selectedPlayer.getId());

        if (!playerId.isPresent()) {
            createPlayer();
        } else {
            updatePlayer();
        }

        PrimeFaces.current().executeScript("PF('managePlayerDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-players");
    }

    public void deletePlayer() {

        Optional<Boolean> deletedPlayer = playerClient.delete(selectedPlayer.getId());

        if (deletedPlayer.isPresent()) {

            this.players.remove(selectedPlayer);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Player Removed"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-players");

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Could not remove Player", selectedPlayer.getName()));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-players");
        }
    }

    public void deleteSelectedPlayers() {

        for (Player selected : selectedPlayers) {
            Optional<Boolean> deletedPlayer = playerClient.delete(selected.getId());

            if (deletedPlayer.isPresent()) {

                if (deletedPlayer.get()) {
                    this.players.remove(selected);
                    this.selectedPlayers.remove(selected);
                }

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Player Removed"));
                PrimeFaces.current().ajax().update("form:messages", "form:dt-players");
                PrimeFaces.current().executeScript("PF('dtPlayers').clearFilters()");

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Could not remove Player", selected.getName()));
                PrimeFaces.current().ajax().update("form:messages", "form:dt-players");
            }
        }

    }
}
