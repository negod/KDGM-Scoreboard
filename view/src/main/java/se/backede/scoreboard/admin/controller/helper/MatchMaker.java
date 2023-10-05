/*
 */
package se.backede.scoreboard.admin.controller.helper;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import se.backede.scoreboard.admin.resources.dto.Match;
import se.backede.scoreboard.admin.resources.dto.Team;

public class MatchMaker {

    public static List<Match> generateMatches(List<Team> teams) {
        List<Match> matches = new ArrayList<>();
        List<Team> rotationList = new LinkedList<>(teams);

        int totalRounds = teams.size() - 1;
        if (teams.size() % 2 != 0) {
            rotationList.add(new Team("BYE"));  // Adding a placeholder for an idle team.
            totalRounds++;
        }

        for (int i = 0; i < totalRounds; i++) {
            for (int j = 0; j < rotationList.size() / 2; j++) {
                Team team1 = rotationList.get(j);
                Team team2 = rotationList.get(rotationList.size() - 1 - j);

                // Don't add matches with the placeholder "BYE".
                if (!team1.equals("BYE") && !team2.getName().equals("BYE")) {
                    matches.add(Match.builder().team1(team1).team2(team2).build());
                }
            }

            // Rotate teams for the next round
            Team lastTeam = rotationList.remove(rotationList.size() - 1);
            rotationList.add(1, lastTeam);
        }
        return matches;
    }

}
