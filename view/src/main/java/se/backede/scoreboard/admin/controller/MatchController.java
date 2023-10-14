/*
 */
package se.backede.scoreboard.admin.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.TransferEvent;
import se.backede.scoreboard.admin.commons.CrudController;
import se.backede.scoreboard.admin.resources.controller.MatchRestClientController;
import se.backede.scoreboard.admin.resources.dto.Match;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@Named("matchController")
@ViewScoped
public class MatchController extends CrudController<Match> implements Serializable {

    @Inject
    private MatchRestClientController matchClient;

    @PostConstruct
    @Override
    public void init() {
        super.setupController(matchClient);
    }

    @Override
    public void openNew() {
        setSelectedItem(new Match());
    }

    @Override
    public void onDualListChange() {
    }

    @Override
    public void onDualListTransfer(TransferEvent event) {
    }

}
