/*
 */
package se.backede.scoreboard.admin.resources.dto.converter;

import jakarta.el.ValueExpression;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import se.backede.scoreboard.admin.controller.CompetitionController;
import se.backede.scoreboard.admin.resources.dto.Team;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@FacesConverter("competitionConverter")
public class CompetitionConverter implements Converter{
     @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String teamId) {
        ValueExpression vex
                = fc.getApplication().getExpressionFactory()
                        .createValueExpression(fc.getELContext(),
                                "#{competitionController}", CompetitionController.class);

        CompetitionController competitionController = (CompetitionController) vex.getValue(fc.getELContext());
        return competitionController.getTeamById(teamId);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object team) {
        if (team != null) {
            return ((Team) team).getId();
        }
        return "";
    }
}
