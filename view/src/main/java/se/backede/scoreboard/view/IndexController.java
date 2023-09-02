/*
 */
package se.backede.scoreboard.view;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import se.backede.scoreboard.view.resources.dto.Player;
import se.backede.scoreboard.view.resources.PlayerRestClientController;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@Named("indexController")
@ApplicationScoped
public class IndexController implements Serializable {

    private Player selectedPlayer;
    private List<Player> players;
    private List<Player> selectedPlayers;

    @Inject
    private PlayerRestClientController playerClient;

    @PostConstruct
    public void init() {
        playerClient.getAll().ifPresent(x -> {
            players = x;
        });
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedPlayers()) {
            int size = this.selectedPlayers.size();
            return size > 1 ? size + " players selected" : "1 player selected";
        }

        return "Delete";
    }

    public boolean hasSelectedPlayers() {
        return this.selectedPlayers != null && !this.selectedPlayers.isEmpty();
    }

    public void openNew() {
        this.selectedPlayer = new Player();
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
