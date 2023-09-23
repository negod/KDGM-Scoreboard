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
import se.backede.scoreboard.admin.resources.controller.TeamRestClientController;
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

    @Inject
    private TeamRestClientController teamClient;

    @PostConstruct
    @Override
    public void init() {
        super.setupController(teamClient);
    }

    @Override
    public void openNew() {
        setSelectedItem(new Team());
    }

}
