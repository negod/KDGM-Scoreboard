package se.backede.scoreboard.entity;

import jakarta.json.bind.annotation.JsonbNillable;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import se.backede.scoreboard.common.GenericEntity;
import se.backede.scoreboard.common.constants.GameConstants;
import se.backede.scoreboard.common.constants.GlobalConstants;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity
@ToString
@Table(schema = GlobalConstants.SCHEMA_NAME, name = GameConstants.TABLE_NAME)
@NamedQueries({
    @NamedQuery(name = GameConstants.QUERY_GET_ALL_GAMES, query = "SELECT g FROM Game g")
})
@Getter
@Setter
public class Game extends GenericEntity {

    @NotNull(message = "Name cannot be null")
    @JsonbNillable(false)
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "GameType cannot be null")
    @JsonbNillable(false)
    private GameType gametype;

}
