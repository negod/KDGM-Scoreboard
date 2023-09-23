/*
 */
package se.backede.scoreboard.admin.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import se.backede.scoreboard.admin.commons.CrudController;
import se.backede.scoreboard.admin.resources.controller.CompetitionrestClientController;
import se.backede.scoreboard.admin.resources.dto.Competition;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@Named("competitionController")
@ViewScoped
public class CompetitionController extends CrudController<Competition> {

    @Inject
    private CompetitionrestClientController competitionClient;

    @PostConstruct
    @Override
    public void init() {
        super.setupController(competitionClient);
    }

    @Override
    public void openNew() {
        setSelectedItem(new Competition());
    }

}
