/*
 */
package se.backede.scoreboard.admin.resources.dto;

import java.util.List;
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
@SuperBuilder
@RequiredArgsConstructor
public class MatchResult extends GenericDto {

    Match match;
    List<TeamResult> teamResults;

}
