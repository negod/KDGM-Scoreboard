package se.backede.scoreboard.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import se.backede.scoreboard.common.GenericEntity;
import se.backede.scoreboard.common.constants.GlobalConstants;
import se.backede.scoreboard.common.constants.TeamConstants;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity
@ToString(exclude = "players")
@Table(schema = GlobalConstants.SCHEMA_NAME, name = TeamConstants.TABLE_NAME)
@NamedQueries({
    @NamedQuery(name = TeamConstants.QUERY_GET_ALL_TEAMS, query = "SELECT t FROM Team t"),
    @NamedQuery(name = TeamConstants.QUERY_UPDATE_TEAM, query = "UPDATE Team t set t.name =:name WHERE t.id=:id "),})

@Getter
@Setter
public class Team extends GenericEntity {

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, orphanRemoval = false, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Player> players;

    @NotNull(message = "Name cannot be NULL")
    @Column(name = "name")
    private String name;

}
