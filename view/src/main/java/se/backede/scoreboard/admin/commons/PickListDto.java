/*
 */
package se.backede.scoreboard.admin.commons;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 * @param <T>
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class PickListDto<T extends GenericDto> {

    T mainTitem;
    List<T> subItems;

}
