package se.backede.scoreboard.team;

import se.backede.scoreboard.player.PlayerEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.common.dao.GenericEntity;
import se.backede.scoreboard.common.constants.GlobalConstants;
import se.backede.scoreboard.common.constants.TeamConstants;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity(name = "Team")
@ToString(exclude = "players")
@Table(schema = GlobalConstants.SCHEMA_NAME, name = TeamConstants.TABLE_NAME)
@NamedQueries({
    @NamedQuery(name = TeamConstants.QUERY_GET_ALL_TEAMS, query = "SELECT t FROM Team t"),})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TeamEntity extends GenericEntity {

    @ManyToMany()
    @JoinTable(
            name = "kggn.player_team",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private List<PlayerEntity> players;

    @NotNull(message = "Name cannot be NULL")
    @Column(name = "name")
    private String name;

}
