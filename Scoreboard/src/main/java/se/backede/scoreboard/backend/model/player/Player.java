/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.backede.scoreboard.backend.model.player;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import se.backede.scoreboard.backend.common.GenericEntity;
import se.backede.scoreboard.backend.common.PlayerConstants;

/**
 *
 * @author joaki
 */
@Entity
@EqualsAndHashCode
@ToString
@Table(schema = "kggn", name = "player")
@NamedQuery(name = PlayerConstants.GET_ALL_PLAYERS, query = "SELECT p FROM Player p")
@Getter
@Setter
public class Player extends GenericEntity {

    @NotNull(message = "Name cannot be NULL")
    @Column(name = "name")
    private String name;

}
