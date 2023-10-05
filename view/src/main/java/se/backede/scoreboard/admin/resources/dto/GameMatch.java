/*
 */
package se.backede.scoreboard.admin.resources.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameMatch {

    Game game;
    List<Match> matches;

}
