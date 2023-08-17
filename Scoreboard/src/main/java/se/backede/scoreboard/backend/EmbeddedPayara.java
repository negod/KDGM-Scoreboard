package se.backede.scoreboard.backend;

import lombok.extern.slf4j.Slf4j;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author joaki
 */
@Slf4j
public class EmbeddedPayara {

//    public static void main(String[] args) throws BootstrapException {
//
//        PayaraMicro instance = PayaraMicro.getInstance();
//        PayaraMicroRuntime runtime = instance.bootStrap();
//
//        Map<InstanceDescriptor, Future<? extends ClusterCommandResult>> results = runtime.run("deploy", "Scoreboard-1.0-SNAPSHOT.war");
//        for (InstanceDescriptor descriptor : results.keySet()) {
//            ClusterCommandResult commandResult = (ClusterCommandResult) results.get(descriptor);
//            if (commandResult.getExitStatus() == ClusterCommandResult.ExitStatus.SUCCESS) {
//                log.info("Application was successfully deployed on cluster instance " + descriptor.getInstanceName());
//            }
//        }
//
//    }
}
