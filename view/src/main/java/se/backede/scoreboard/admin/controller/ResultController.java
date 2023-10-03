/*
 */
package se.backede.scoreboard.admin.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.TransferEvent;
import se.backede.scoreboard.admin.commons.CrudController;
import se.backede.scoreboard.admin.resources.controller.ResultRestClientController;
import se.backede.scoreboard.admin.resources.dto.Result;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@Named("resultController")
@ViewScoped
public class ResultController extends CrudController<Result> implements Serializable {

    @Inject
    private ResultRestClientController resultClient;

    @PostConstruct
    @Override
    public void init() {
        super.setupController(resultClient);
    }

    @Override
    public void onDualListTransfer(TransferEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void onDualListChange() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void openNew() {
        setSelectedItem(new Result());
    }

}
