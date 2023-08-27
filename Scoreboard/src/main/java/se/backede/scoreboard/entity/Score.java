package se.backede.scoreboard.entity;



import lombok.Getter;
import lombok.Setter;
import se.backede.scoreboard.entity.Team;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
public class Score {

    private int id;
    private int score;
    private Team team;
    

}
