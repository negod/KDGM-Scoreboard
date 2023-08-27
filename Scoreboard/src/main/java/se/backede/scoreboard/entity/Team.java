package se.backede.scoreboard.entity;

import se.backede.scoreboard.entity.Player;
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
