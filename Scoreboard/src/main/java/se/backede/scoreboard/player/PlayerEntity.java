package se.backede.scoreboard.player;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.common.dao.GenericEntity;
import se.backede.scoreboard.common.constants.GlobalConstants;
import se.backede.scoreboard.common.constants.PlayerConstants;
import se.backede.scoreboard.match.MatchEntity;
import se.backede.scoreboard.team.TeamEntity;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity(name = "Player")
@ToString
@Table(schema = GlobalConstants.SCHEMA_NAME, name = PlayerConstants.TABLE_NAME)
@NamedQueries({
    @NamedQuery(name = PlayerConstants.QUERY_GET_ALL_PLAYERS, query = "SELECT p FROM Player p"),})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerEntity extends GenericEntity {

    @NotNull(message = "Name cannot be NULL")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Nick Name cannot be NULL")
    @Column(name = "nick_name")
    private String nickName;

    @ManyToMany(mappedBy = "players")
    private List<TeamEntity> teams = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "player")
    private List<MatchEntity> results;

}
