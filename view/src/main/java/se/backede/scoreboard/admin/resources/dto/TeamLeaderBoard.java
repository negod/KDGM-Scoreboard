/*
 */
package se.backede.scoreboard.admin.resources.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@Builder
public class TeamLeaderBoard {

    Integer position;
    String teamName;
    Long teamScore;

}
