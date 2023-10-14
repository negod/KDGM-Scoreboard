/*
 */
package se.backede.scoreboard.admin.resources.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
@NoArgsConstructor
@SuperBuilder
public class Team extends GenericDto {

    @NonNull
    private String name;
    private List<Player> players = new ArrayList<>();

}
