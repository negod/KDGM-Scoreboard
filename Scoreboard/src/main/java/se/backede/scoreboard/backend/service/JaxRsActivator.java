/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.backede.scoreboard.backend.service;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
import se.backede.scoreboard.backend.service.player.PlayerService;

/**
 *
 * @author joaki
 */
@ApplicationPath("test")
public class JaxRsActivator extends Application {

    private Set<Object> singletons = new HashSet<Object>();

    public JaxRsActivator() {
        singletons.add(new PlayerService());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

}
