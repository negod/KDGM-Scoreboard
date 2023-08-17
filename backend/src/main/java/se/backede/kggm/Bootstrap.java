/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.backede.kggm;

import fish.payara.micro.BootstrapException;
import fish.payara.micro.PayaraMicro;
import java.io.File;

/**
 *
 * @author joaki
 */
public class Bootstrap {

    public static void main(String[] args) throws BootstrapException {

        System.out.println("----------- BOOTSTRAPPING ---------");

        PayaraMicro.getInstance()
                .setDeploymentDir(new File("/"))
                .setHttpAutoBind(true)
                .bootStrap();

    }

}
