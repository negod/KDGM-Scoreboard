/*
 */
package se.backede.scoreboard.admin.resources.dto.converter;

import jakarta.el.ValueExpression;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import se.backede.scoreboard.admin.controller.PlayerController;
import se.backede.scoreboard.admin.resources.dto.Player;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@FacesConverter(value = "playerConverter")
public class PlayerConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String teamId) {
        ValueExpression vex
                = fc.getApplication().getExpressionFactory()
                        .createValueExpression(fc.getELContext(),
                                "#{playerController}", PlayerController.class);

        PlayerController playerController = (PlayerController) vex.getValue(fc.getELContext());
        return playerController.getItemById(teamId);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object player) {
        if (player != null) {
            return ((Player) player).getId();
        }
        return "";
    }

}
