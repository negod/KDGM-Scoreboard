/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.backede.scoreboard.backend.model.player;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import se.backede.scoreboard.backend.common.PlayerConstants;

/**
 *
 * @author joaki
 */
@Getter
@Setter
@ToString
@Entity(name = "Player")
@NamedQuery(name = PlayerConstants.GET_ALL_PLAYERS, query = "SELECT p FROM Player p")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    //private Team team;

}
