package se.backede.scoreboard.backend.model.score;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
public class Scoreboard {

    private int id;
    private Set<Score> scores;

}
