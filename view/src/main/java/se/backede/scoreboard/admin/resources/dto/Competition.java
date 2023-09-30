/*
 */
package se.backede.scoreboard.admin.resources.dto;

import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import se.backede.scoreboard.admin.commons.GenericDto;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
public class Competition extends GenericDto {

    private Date competitionDate;
    private String name;
    private List<Game> games;
    private List<Team> teams;

}
