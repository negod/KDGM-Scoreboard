/*
 */
package se.backede.scoreboard.admin.mockdata;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import se.backede.scoreboard.admin.resources.dto.Competition;
import se.backede.scoreboard.admin.resources.dto.Game;
import se.backede.scoreboard.admin.resources.dto.GameType;
import se.backede.scoreboard.admin.resources.dto.Match;
import se.backede.scoreboard.admin.resources.dto.Player;
import se.backede.scoreboard.admin.resources.dto.PlayerResult;
import se.backede.scoreboard.admin.resources.dto.Team;
import se.backede.scoreboard.admin.resources.dto.TeamResult;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class ModelMock {
    
    public static TeamResult getTeamResult(int players) {

        //Team players
        List<Player> playerListTeam1 = PlayerMock.getPlayerList(players);

        //Team 
        Team team1 = TeamMock.getTeam(playerListTeam1);

        //Player results for the team
        List<PlayerResult> playerResultsTeam1 = new ArrayList<>();
        for (Player player : playerListTeam1) {
            PlayerResult playerResult = PlayerResultMock.getPlayerResult(player);
            playerResultsTeam1.add(playerResult);
        }
        
        return TeamResultMock.getTeamResult(playerResultsTeam1, team1);
        
    }

    /**
     * 2 teams with x players in each team 1 Game with GameType NONE 1
     * Competition with all players and all teams
     *
     * @param players amount of players i eacn team
     * @return
     */
    public static Match getMatch(int players, Integer order) {
        
        List<Player> playerListTeam1 = PlayerMock.getPlayerList(players);
        List<Player> playerListTeam2 = PlayerMock.getPlayerList(players);
        
        List<Team> teams = new ArrayList<>();
        Team team1 = TeamMock.getTeam(playerListTeam1);
        Team team2 = TeamMock.getTeam(playerListTeam2);
        teams.add(team1);
        teams.add(team2);
        
        List<Game> games = new ArrayList<>();
        Game game = GameMock.getGame(GameType.NONE);
        games.add(game);
        
        Competition competition = CompetitionMock.getCompetition(games, teams);
        
        return Match.builder()
                .id(UUID.randomUUID().toString())
                .competition(competition)
                .team1(team1)
                .team2(team2)
                .game(game)
                .order(order)
                .build();
        
    }
    
    
    
}
