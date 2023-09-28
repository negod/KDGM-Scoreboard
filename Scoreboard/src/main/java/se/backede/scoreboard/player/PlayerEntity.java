package se.backede.scoreboard.player;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
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
import se.backede.scoreboard.result.ResultEntity;
import se.backede.scoreboard.team.TeamEntity;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity(name = "Player")
@ToString
@Table(schema = GlobalConstants.SCHEMA_NAME, name = PlayerConstants.TABLE_NAME)
@NamedQueries({
    @NamedQuery(name = PlayerConstants.QUERY_GET_ALL_PLAYERS, query = "SELECT p FROM Player p"),
    @NamedQuery(name = PlayerConstants.QUERY_UPDATE_PLAYER, query = "UPDATE Player p set p.name =:name, p.nickName =:nickName, p.team =:team WHERE p.id=:id "),
    @NamedQuery(name = PlayerConstants.QUERY_DELETE_TEAM, query = "UPDATE Player p set p.team = null WHERE p.id=:id ")
})

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "player")
    private List<ResultEntity> results;

}
