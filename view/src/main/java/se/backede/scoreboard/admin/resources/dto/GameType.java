/*
 */
package se.backede.scoreboard.admin.resources.dto;

import java.io.Serializable;
import lombok.ToString;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@ToString
public enum GameType implements Serializable {
    TIME, SCORE, NONE;
}
