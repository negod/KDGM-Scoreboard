package se.backede.scoreboard.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import se.backede.scoreboard.common.GenericEntity;
import se.backede.scoreboard.common.constants.GlobalConstants;
import se.backede.scoreboard.common.constants.PlayerConstants;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity
@EqualsAndHashCode
@ToString
@Table(schema = GlobalConstants.SCHEMA_NAME, name = PlayerConstants.TABLE_NAME)
@NamedQueries({
    @NamedQuery(name = PlayerConstants.QUERY_GET_ALL_PLAYERS, query = "SELECT p FROM Player p")
})

@Getter
@Setter
public class Player extends GenericEntity {

    @NotNull(message = "Name cannot be NULL")
    @Column(name = "name")
    private String name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

}
