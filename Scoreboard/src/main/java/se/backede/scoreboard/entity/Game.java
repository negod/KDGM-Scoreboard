package se.backede.scoreboard.entity;

import lombok.Getter;
import lombok.Setter;
/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
public class Game {

    private int id;
    private String name;
    private GameType game;

}