/*
 */
package se.backede.scoreboard.admin.commons;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class GenericDto implements Serializable{

    private String id;

}
