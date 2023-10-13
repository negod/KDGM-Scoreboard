/*
 */
package se.backede.scoreboard.admin.controller;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import se.backede.scoreboard.admin.controller.helper.ToggleHelper;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.ReorderEvent;
import se.backede.scoreboard.admin.resources.dto.Game;
import se.backede.scoreboard.admin.resources.dto.Player;
import se.backede.scoreboard.admin.resources.dto.Team;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@Named("createCompetitionController")
@ViewScoped
public class CreateCompetitionController implements Serializable {

    @Inject
    CompetitionController competition;

    @Inject
    GameController game;

    @Inject
    PlayerController player;

    @Inject
    TeamController team;

    ToggleHelper teamToggle = new ToggleHelper();

    List<Team> createdTeams = new ArrayList<>();

    private int playersInEachTeam;

    private int activeIndex = 0;

    public void nextStep() {
        if (activeIndex < 4) {
            activeIndex++;
        }
        if (activeIndex == 4) {
            createTeams();
        }
    }

    public void prevStep() {
        if (activeIndex > 0) {
            activeIndex--;
        }
    }

    public void createTeams() {

        createdTeams = new ArrayList<>();

        List<Player> shuffledPlayers = player.getDualList().getTarget();
        Collections.shuffle(shuffledPlayers); // detta kommer att blanda listan

        int numberOfTeams = shuffledPlayers.size() / playersInEachTeam;

        for (int i = 0; i < numberOfTeams; i++) {
            // skapa ett nytt team
            Team team = new Team("Team " + String.valueOf(i + 1));

            // plocka ut spelare för detta team baserat på index i den blandade listan
            List<Player> playersForThisTeam = shuffledPlayers.subList(i * playersInEachTeam, (i + 1) * playersInEachTeam);
            team.setPlayers(new ArrayList<>(playersForThisTeam));

            // lägg till teamet till listan med skapade teams
            createdTeams.add(team);
        }

        // Om det finns några överskjutande spelare, lägg till dem i redan skapade teams
        int remainingPlayersStartIndex = numberOfTeams * playersInEachTeam;
        for (int i = remainingPlayersStartIndex, j = 0; i < shuffledPlayers.size(); i++, j++) {
            Team team = createdTeams.get(j % numberOfTeams); // cykla genom teams
            team.getPlayers().add(shuffledPlayers.get(i)); // Lägg till spelaren till teamet
        }

    }

    public void onCompetitionChange() {
        competition.onDualListChange();
    }

    public void switchPlayer(AjaxBehaviorEvent event) {
        System.out.println("Player");
    }

    public void switchTeam(AjaxBehaviorEvent event) {
        System.out.println("Team");
    }

    public int getMaxPlayers() {
        return player.getDualList().getTarget().size();
    }

    public void onRowReorder(ReorderEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Row Moved", "From: " + event.getFromIndex() + ", To:" + event.getToIndex());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void saveCompetition() {

        // Fix GameOrder
        List<Team> persistedTeams = new ArrayList<>();
        for (Team createdTeam : createdTeams) {
            team.saveItem(createdTeam).ifPresent(item -> {
                persistedTeams.add(item);
            });
        }
        createdTeams = persistedTeams;

        competition.getSelectedItem().setTeams(persistedTeams);

        competition.saveSelectedItem();
    }

}
