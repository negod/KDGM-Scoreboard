/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.backede.scoreboard.backend.model.player.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.backede.scoreboard.backend.common.PlayerConstants;
import se.backede.scoreboard.backend.model.player.Player;
import se.backede.scoreboard.backend.service.player.PlayerService;

/**
 *
 * @author joaki
 */
@ApplicationScoped
@Transactional
public class PlayerDao {

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    public PlayerDao() {
    }

    public Optional<List<Player>> getAll() {

        try {
            return Optional.ofNullable(em.createNamedQuery(PlayerConstants.GET_ALL_PLAYERS, Player.class).getResultList());
        } catch (Exception e) {
            Logger.getLogger(PlayerService.class.getName()).log(Level.SEVERE, "Error when getting all players", e);
        }
        return Optional.empty();
    }

    public Optional createPlayer(Player player) {

        try {
            em.persist(player);
            return Optional.of(player);
        } catch (Exception e) {
            Logger.getLogger(PlayerService.class.getName()).log(Level.SEVERE, "Error when persisting player", e);
            return Optional.empty();
        }
    }

}
