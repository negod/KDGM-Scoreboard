package se.backede.scoreboard;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.Set;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@ApplicationPath("rest-service")
public class JaxRsActivator extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(se.backede.scoreboard.player.PlayerService.class);
        resources.add(se.backede.scoreboard.team.TeamService.class);
        resources.add(se.backede.scoreboard.game.GameService.class);
        resources.add(se.backede.scoreboard.match.MatchService.class);
        resources.add(se.backede.scoreboard.competition.CompetitionService.class);
    }

}
