package se.backede.scoreboard.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import se.backede.scoreboard.common.GenericEntity;
import se.backede.scoreboard.common.PlayerConstants;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity
@EqualsAndHashCode
@ToString
@Table(schema = "kggn", name = "player")
@NamedQueries({
    @NamedQuery(name = PlayerConstants.QUERY_GET_ALL_PLAYERS, query = "SELECT p FROM Player p")
})

@Getter
@Setter
public class Player extends GenericEntity {

    @NotNull(message = "Name cannot be NULL")
    @Column(name = "name")
    private String name;

}
