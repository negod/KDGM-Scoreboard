/*
 */
package se.backede.scoreboard.view.commons;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@EqualsAndHashCode
public class GenericDto implements Serializable{

    private String id;

}
