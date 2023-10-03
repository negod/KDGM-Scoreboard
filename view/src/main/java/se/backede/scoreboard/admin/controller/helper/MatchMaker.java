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
import se.backede.scoreboard.admin.resources.dto.Team;

public class MatchMaker {

    static class Match {

        Team team1;
        Team team2;

        Match(Team team1, Team team2) {
            this.team1 = team1;
            this.team2 = team2;
        }

        @Override
        public String toString() {
            return team1 + " vs " + team2;
        }
    }

    public List<Match> generateMatches(List<Team> teams) {
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
                    matches.add(new Match(team1, team2));
                }
            }

            // Rotate teams for the next round
            Team lastTeam = rotationList.remove(rotationList.size() - 1);
            rotationList.add(1, lastTeam);
        }
        return matches;
    }

//    public static void main(String[] args) {
//        List<String> teams = List.of("TeamA", "TeamB", "TeamC");
//
//        MatchMaker matchmaker = new MatchMaker();
//        List<Match> matches = matchmaker.generateMatches(teams);
//
//        System.out.println("Match schedule for the day:");
//        matches.forEach(System.out::println);
//    }
}
