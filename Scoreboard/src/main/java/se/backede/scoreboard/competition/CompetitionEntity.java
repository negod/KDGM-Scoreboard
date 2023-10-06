/*
 */
package se.backede.scoreboard.competition;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import se.backede.scoreboard.team.TeamEntity;
import se.backede.scoreboard.game.GameEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.common.dao.GenericEntity;
import se.backede.scoreboard.common.constants.CompetitionConstants;
import se.backede.scoreboard.common.constants.GlobalConstants;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity(name = "Competition")
@ToString
@Table(schema = GlobalConstants.SCHEMA_NAME, name = CompetitionConstants.TABLE_NAME)
@NamedQueries({
    @NamedQuery(name = CompetitionConstants.QUERY_GET_ALL_COMPETITIONS, query = "SELECT c FROM Competition c")
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionEntity extends GenericEntity {

    @Column(name = "competition_date")
    private Date competitionDate;
    private String name;

    @ManyToMany()
    @JoinTable(name = "kggn.competition_game",
            joinColumns = @JoinColumn(name = "competition_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    private Set<GameEntity> games;

    @ManyToMany()
    @JoinTable(name = "kggn.competition_team",
            joinColumns = @JoinColumn(name = "competition_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    private Set<TeamEntity> teams;

    private Boolean started;

}
