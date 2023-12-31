/*
 */
package se.backede.scoreboard.admin.resources.dto.converter;

import jakarta.el.ValueExpression;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import se.backede.scoreboard.admin.controller.TeamController;
import se.backede.scoreboard.admin.resources.dto.Team;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@FacesConverter(value = "teamConverter")
public class TeamConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String teamId) {
        ValueExpression vex
                = fc.getApplication().getExpressionFactory()
                        .createValueExpression(fc.getELContext(),
                                "#{teamController}", TeamController.class);

        TeamController teamController = (TeamController) vex.getValue(fc.getELContext());
        return teamController.getItemById(teamId);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object team) {
        if (team != null) {
            return ((Team) team).getId();
        }
        return "";
    }

}
