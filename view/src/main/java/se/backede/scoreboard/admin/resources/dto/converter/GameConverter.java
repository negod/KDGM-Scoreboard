/*
 */
package se.backede.scoreboard.admin.resources.dto.converter;

import jakarta.el.ValueExpression;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import se.backede.scoreboard.admin.controller.GameController;
import se.backede.scoreboard.admin.resources.dto.Game;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@FacesConverter(value = "gameConverter")
public class GameConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String id) {
        ValueExpression vex
                = fc.getApplication().getExpressionFactory()
                        .createValueExpression(fc.getELContext(),
                                "#{gameController}", GameController.class);

        GameController gameController = (GameController) vex.getValue(fc.getELContext());
        return gameController.getItemById(id);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object item) {
        if (item != null) {
            return ((Game) item).getId();
        }
        return "";
    }

}
