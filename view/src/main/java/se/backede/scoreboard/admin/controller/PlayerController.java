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
import se.backede.scoreboard.admin.resources.dto.Player;
import se.backede.scoreboard.admin.resources.controller.PlayerRestClientController;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@Named("playerController")
@ViewScoped
public class PlayerController extends CrudController<Player> implements Serializable {

    @Inject
    private PlayerRestClientController playerClient;

    @PostConstruct
    @Override
    public void init() {
        super.setupController(playerClient);
    }

    @Override
    public void openNew() {
        setSelectedItem(new Player());
    }

    @Override
    public void onDualListChange() {
    }

    @Override
    public void onDualListTransfer(TransferEvent event) {
    }

}
