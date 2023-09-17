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
import se.backede.scoreboard.view.resources.controller.GameRestClientController;
import se.backede.scoreboard.view.resources.dto.Game;
import se.backede.scoreboard.view.resources.dto.GameType;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@Named("gameController")
@ViewScoped
public class GameController implements Serializable {

    private Game selectedGame;
    private List<Game> games;
    private List<Game> selectedGames;
    private GameType selectedGameType;

    @Inject
    private GameRestClientController gameClient;

    @PostConstruct
    public void init() {
        System.out.println("TESTING");
        gameClient.getAll().ifPresent(x -> {
            games = x;
        });
    }

    public GameType[] getGameTypes() {
        return GameType.values();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedGames()) {
            int size = this.selectedGames.size();
            return size > 1 ? size + " games selected" : "1 game selected";
        }

        return "Delete";
    }

    public boolean hasSelectedGames() {
        return this.selectedGames != null && !this.selectedGames.isEmpty();
    }

    public void openNew() {
        this.selectedGame = new Game();
    }

    private void createGame() {

        Optional<Game> createdGame = gameClient.create(selectedGame);

        if (createdGame.isPresent()) {
            this.games.add(createdGame.get());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Game Added"));
        } else {
            FacesContext.getCurrentInstance().validationFailed();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("failed to add game"));
        }

    }

    private void updateGame() {
        Optional<Game> updatedGame = gameClient.update(selectedGame);

        if (updatedGame.isPresent()) {
            games.remove(selectedGame);
            games.add(updatedGame.get());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Updated Game"));
            PrimeFaces.current().ajax().update("form_game:messages", "form_game:dt-games");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Failed to Update Game"));
            PrimeFaces.current().ajax().update("form_game:messages", "form_game:dt-games");
        }

    }

    public void saveGame() {
        Optional<String> gameId = Optional.ofNullable(selectedGame.getId());

        if (!gameId.isPresent()) {
            createGame();
        } else {
            updateGame();
        }

        PrimeFaces.current().executeScript("PF('manageGameDialog').hide()");
        PrimeFaces.current().ajax().update("form_game:messages", "form_game:dt-games");
    }

    public void deleteGame() {

        Optional<Boolean> deletedGame = gameClient.delete(selectedGame.getId());

        if (deletedGame.isPresent()) {

            this.games.remove(selectedGame);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Game Removed"));
            PrimeFaces.current().ajax().update("form_game:messages", "form_game:dt-games");

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Could not remove Game", selectedGame.getName()));
            PrimeFaces.current().ajax().update("form_game:messages", "form_game:dt-games");
        }
    }

    public void deleteSelectedGames() {

        for (Game selected : selectedGames) {
            Optional<Boolean> deletedGame = gameClient.delete(selected.getId());

            if (deletedGame.isPresent()) {

                if (deletedGame.get()) {
                    this.games.remove(selected);
                    this.selectedGames.remove(selected);
                }

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Game Removed"));
                PrimeFaces.current().ajax().update("form_game:messages", "form_game:dt-games");
                PrimeFaces.current().executeScript("PF('dtGames').clearFilters()");

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Could not remove Game", selected.getName()));
                PrimeFaces.current().ajax().update("form_game:messages", "form_game:dt-games");
            }
        }

    }

}
