/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.backede.kggm.model.score;



import jakarta.persistence.Entity;
import se.backede.kggm.model.player.Team;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author joaki
 */
@Entity
@Getter
@Setter
public class Score {

    private Team team;
    private int score;

}
