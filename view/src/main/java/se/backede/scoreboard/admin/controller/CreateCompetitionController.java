/*
 */
package se.backede.scoreboard.admin.controller;

import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.backede.scoreboard.admin.resources.dto.Competition;
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
            Team team = new Team();
            team.setName("Team " + String.valueOf(i + 1));

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

    public void saveCompetition() {

        List<Team> persistedTeams = new ArrayList<>();
        for (Team createdTeam : createdTeams) {
            team.saveItem(createdTeam).ifPresent(item -> {
                persistedTeams.add(item);
            });
        }

        competition.getSelectedItem().setTeams(persistedTeams);
        competition.getSelectedItem().setGames(game.getDualList().getTarget());

        competition.saveSelectedItem();

    }

}
