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
import se.backede.scoreboard.view.resources.controller.TeamRestClientController;
import se.backede.scoreboard.view.resources.dto.Team;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@Named("teamController")
@ViewScoped
public class TeamController implements Serializable{

    private Team selectedTeam;
    private List<Team> teams;
    private List<Team> selectedTeams;

    @Inject
    private TeamRestClientController teamClient;

    @PostConstruct
    public void init() {
        teamClient.getAll().ifPresent(x -> {
            teams = x;
        });
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedTeams()) {
            int size = this.selectedTeams.size();
            return size > 1 ? size + " teams selected" : "1 team selected";
        }

        return "Delete";
    }

    public boolean hasSelectedTeams() {
        return this.selectedTeams != null && !this.selectedTeams.isEmpty();
    }

    public void openNew() {
        this.selectedTeam = new Team();
    }

    private void createTeam() {

        Optional<Team> createdTeam = teamClient.create(selectedTeam);

        if (createdTeam.isPresent()) {
            this.teams.add(createdTeam.get());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Team Added"));
        } else {
            FacesContext.getCurrentInstance().validationFailed();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("failed to add team"));
        }
        
        

    }

    private void updateTeam() {
        Optional<Team> updatedTeam = teamClient.update(selectedTeam);

        if (updatedTeam.isPresent()) {
            teams.remove(selectedTeam);
            teams.add(updatedTeam.get());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Updated Team"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-teams");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Failed to Update Team"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-teams");
        }

    }

    public void saveTeam() {
        Optional<String> teamId = Optional.ofNullable(selectedTeam.getId());

        if (!teamId.isPresent()) {
            createTeam();
        } else {
            updateTeam();
        }

        PrimeFaces.current().executeScript("PF('manageTeamDialog').hide()");
        PrimeFaces.current().ajax().update("form_team:messages", "form_team:dt-teams");
    }

    public void deleteTeam() {

        Optional<Boolean> deletedTeam = teamClient.delete(selectedTeam.getId());

        if (deletedTeam.isPresent()) {

            this.teams.remove(selectedTeam);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Team Removed"));
            PrimeFaces.current().ajax().update("form_team:messages", "form_team:dt-teams");

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Could not remove Team", selectedTeam.getName()));
            PrimeFaces.current().ajax().update("form_team:messages", "form_team:dt-teams");
        }
    }

    public void deleteSelectedTeams() {

        for (Team selected : selectedTeams) {
            Optional<Boolean> deletedTeam = teamClient.delete(selected.getId());

            if (deletedTeam.isPresent()) {

                if (deletedTeam.get()) {
                    this.teams.remove(selected);
                    this.selectedTeams.remove(selected);
                }

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Team Removed"));
                PrimeFaces.current().ajax().update("form_team:messages", "form_team:dt-teams");
                PrimeFaces.current().executeScript("PF('dtTeams').clearFilters()");

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Could not remove Team", selected.getName()));
                PrimeFaces.current().ajax().update("form_team:messages", "form_team:dt-teams");
            }
        }

    }

}
