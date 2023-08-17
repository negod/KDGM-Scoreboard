/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.backede.scoreboard.backend.model.player;

import jakarta.persistence.Entity;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author joaki
 */
@Entity
@Getter
@Setter
public class Team {

    private Set<Player> players;
    private String name;

}
