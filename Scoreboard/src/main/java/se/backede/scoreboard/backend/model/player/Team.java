package se.backede.scoreboard.backend.model.player;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */

@Getter
@Setter
public class Team {

    private int id;
    private Set<Player> players;
    private String name;

}
