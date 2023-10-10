/*
 */
package se.backede.scoreboard.team;

import se.backede.scoreboard.competition.CompetitionEntity;
import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.common.constants.GlobalConstants;
import se.backede.scoreboard.common.constants.TeamConstants;
import se.backede.scoreboard.common.dao.GenericEntity;
import se.backede.scoreboard.match.MatchEntity;
import se.backede.scoreboard.player.PlayerEntity;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity(name = TeamConstants.TABLE_NAME)
@Table(name = "team", schema = GlobalConstants.SCHEMA_NAME)
@NamedQueries({
    @NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t"),
    @NamedQuery(name = "Team.findById", query = "SELECT t FROM Team t WHERE t.id = :id"),
    @NamedQuery(name = "Team.findByName", query = "SELECT t FROM Team t WHERE t.name = :name"),
    @NamedQuery(name = "Team.findByUpdated", query = "SELECT t FROM Team t WHERE t.updatedDate = :updated")})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TeamEntity extends GenericEntity implements Serializable {

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "teamList")
    private List<CompetitionEntity> competitionList;

    @ManyToMany(mappedBy = "teamList")
    private List<PlayerEntity> playerList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team1Id")
    private List<MatchEntity> matchList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team2Id")
    private List<MatchEntity> matchList1;

}
