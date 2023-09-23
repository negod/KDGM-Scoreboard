/*
 */
package se.backede.scoreboard.admin.controller;

import se.backede.scoreboard.admin.commons.CrudController;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import se.backede.scoreboard.admin.resources.dto.Player;
import se.backede.scoreboard.admin.resources.controller.PlayerRestClientController;
import se.backede.scoreboard.admin.resources.controller.TeamRestClientController;
import se.backede.scoreboard.admin.resources.dto.Team;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@Named("playerController")
@ViewScoped
public class PlayerController extends CrudController<Player> implements Serializable {
    
    private Team selectedTeam;
    private List<Team> teams;
    
    @Inject
    private PlayerRestClientController playerClient;
    
    @Inject
    private TeamRestClientController teamClient;
    
    @PostConstruct
    @Override
    public void init() {
        super.setupController(playerClient);
        
        teamClient.getAll().ifPresent(fetchedTeams -> {
            teams = fetchedTeams;
        });
        
    }
    
    public Team getTeamById(String teamId) {
        for (Team team : teams) {
            if (team.getId().equals(teamId)) {
                return team;
            }
        }
        return null;
    }
    
    @Override
    public void openNew() {
        setSelectedItem(new Player());
    }
    
}
