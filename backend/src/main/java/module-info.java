/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/module-info.java to edit this template
 */

module se.backede.kggm {
    
    requires lombok;
    requires jakarta.ws.rs;
    requires jakarta.persistence;
    requires org.slf4j;
    requires payara.micro;
    requires org.hibernate.orm.core;
    
    exports se.backede.kggm;
    
}
