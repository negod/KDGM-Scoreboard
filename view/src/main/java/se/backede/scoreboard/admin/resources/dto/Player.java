/*
 */
package se.backede.scoreboard.admin.resources.dto;

import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.admin.commons.GenericDto;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@SuperBuilder
public class Player extends GenericDto {

    private String name;
    private String nickName;
    private Date updated;

}
