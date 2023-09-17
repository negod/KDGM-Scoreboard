/*
 */
package se.backede.scoreboard.view;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
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
public class GameController extends CrudController<Game> implements Serializable {

    private GameType selectedGameType;

    @Inject
    private GameRestClientController gameClient;

    @PostConstruct
    @Override
    public void init() {
        super.setupController(gameClient);
    }

    public GameType[] getGameTypes() {
        return GameType.values();
    }

    @Override
    public void openNew() {
        setSelectedItem(new Game());
    }

}
