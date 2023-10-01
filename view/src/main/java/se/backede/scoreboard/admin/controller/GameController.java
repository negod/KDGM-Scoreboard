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

    @Override
    public void onDualListChange() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void onDualListTransfer(TransferEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
