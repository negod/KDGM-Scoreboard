/*
 */
package se.backede.scoreboard.admin.resources.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@EqualsAndHashCode()
@Builder
public class PlayerResult {

    Player player;
    Long scoreValue;

}
