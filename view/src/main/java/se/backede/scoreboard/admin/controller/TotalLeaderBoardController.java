/*
 */
package se.backede.scoreboard.admin.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import se.backede.scoreboard.admin.controller.helper.LeaderBoardCalculator;
import se.backede.scoreboard.admin.resources.dto.Competition;
import se.backede.scoreboard.admin.resources.dto.PlayerLeaderBoard;
import se.backede.scoreboard.admin.resources.dto.TeamLeaderBoard;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@Named("totalLeaderBoardController")
@ViewScoped
public class TotalLeaderBoardController implements Serializable {

    @Inject
    TeamLeaderBoardController teamLeaderBoardController;

    private Competition selectedCompetition;

    List<TeamLeaderBoard> teamResults = new ArrayList<>();
    List<PlayerLeaderBoard> playerResults = new ArrayList<>();

    @PostConstruct
    public void init() {
        this.selectedCompetition = teamLeaderBoardController.getSelectedCompetition();

        updateData();
    }

    public void updateData() {

        teamLeaderBoardController.updateData();

        Map<String, List<TeamLeaderBoard>> teamResults = teamLeaderBoardController.getTeamResults();
        Map<String, List<PlayerLeaderBoard>> playerResults = teamLeaderBoardController.getPlayerResults();

        this.teamResults = LeaderBoardCalculator.calculateTotalTeamLeaderBoard(teamResults, teamLeaderBoardController.getGameMap());
        this.playerResults = LeaderBoardCalculator.calculateTotalPlayerLeaderBoard(playerResults, teamLeaderBoardController.getGameMap());
    }

}
