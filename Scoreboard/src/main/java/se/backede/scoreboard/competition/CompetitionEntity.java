/*
 */
package se.backede.scoreboard.competition;

import se.backede.scoreboard.competitiongame.CompetitionGameEntity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.common.constants.CompetitionConstants;
import se.backede.scoreboard.common.constants.GlobalConstants;
import se.backede.scoreboard.common.dao.GenericEntity;
import se.backede.scoreboard.match.MatchEntity;
import se.backede.scoreboard.team.TeamEntity;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity(name = CompetitionConstants.TABLE_NAME)
@Table(name = "competition", schema = GlobalConstants.SCHEMA_NAME)
@NamedQueries({
    @NamedQuery(name = "Competition.findAll", query = "SELECT c FROM Competition c"),
    @NamedQuery(name = "Competition.findById", query = "SELECT c FROM Competition c WHERE c.id = :id"),
    @NamedQuery(name = "Competition.findByUpdated", query = "SELECT c FROM Competition c WHERE c.updatedDate = :updated"),
    @NamedQuery(name = "Competition.findByName", query = "SELECT c FROM Competition c WHERE c.name = :name"),
    @NamedQuery(name = "Competition.findByCompetitionDate", query = "SELECT c FROM Competition c WHERE c.competitionDate = :competitionDate"),
    @NamedQuery(name = "Competition.findByStarted", query = "SELECT c FROM Competition c WHERE c.started = :started")})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionEntity extends GenericEntity implements Serializable {

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "competition_date")
    @Temporal(TemporalType.DATE)
    private Date competitionDate;

    @Basic(optional = false)
    @Column(name = "started")
    private boolean started;
    @JoinTable(name = "kggn.competition_team", joinColumns = {
        @JoinColumn(name = "competition_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "team_id", referencedColumnName = "id")})
    @ManyToMany
    private List<TeamEntity> teamList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "competition")
    private List<CompetitionGameEntity> competitionGameList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "competitionId")
    private List<MatchEntity> matchList;

}
