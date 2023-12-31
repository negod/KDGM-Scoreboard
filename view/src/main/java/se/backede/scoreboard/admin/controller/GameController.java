/*
 */
package se.backede.scoreboard.admin.controller;

import se.backede.scoreboard.admin.commons.CrudController;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.TransferEvent;
import se.backede.scoreboard.admin.resources.controller.GameRestClientController;
import se.backede.scoreboard.admin.resources.dto.Game;
import se.backede.scoreboard.admin.resources.dto.GameType;
import se.backede.scoreboard.admin.resources.dto.ScoreCalculation;

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
    private GameType selectedscoreCalculation;

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

    public ScoreCalculation[] getCalculationTypes() {
        return ScoreCalculation.values();
    }

    @Override
    public void openNew() {
        setSelectedItem(new Game());
    }

    @Override
    public void onDualListChange() {
    }

    @Override
    public void onDualListTransfer(TransferEvent event) {
    }

}
