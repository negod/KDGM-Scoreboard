/*
 */
package se.backede.scoreboard.admin.resources.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import se.backede.scoreboard.admin.commons.GenericDto;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
public class Player extends GenericDto {

    private String name;
    private String nickName;
    private Date updated;
    private Team team;

}