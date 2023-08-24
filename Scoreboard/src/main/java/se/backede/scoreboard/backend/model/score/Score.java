/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.backede.scoreboard.backend.model.score;



import lombok.Getter;
import lombok.Setter;
import se.backede.scoreboard.backend.model.player.Team;

/**
 *
 * @author joaki
 */

@Getter
@Setter
public class Score {

    private int id;
    private int score;
    private Team team;
    

}
