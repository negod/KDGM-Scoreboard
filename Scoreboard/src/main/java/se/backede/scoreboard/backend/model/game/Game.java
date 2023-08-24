/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.backede.scoreboard.backend.model.game;

import lombok.Getter;
import lombok.Setter;
/**
 *
 * @author joaki
 */
@Getter
@Setter
public class Game {

    private int id;
    private String name;
    private GameType game;

}
