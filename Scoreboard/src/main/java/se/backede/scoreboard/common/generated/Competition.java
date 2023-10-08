/*
 */
package se.backede.scoreboard.common.generated;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity
@Table(name = "competition")
@NamedQueries({
    @NamedQuery(name = "Competition.findAll", query = "SELECT c FROM Competition c"),
    @NamedQuery(name = "Competition.findById", query = "SELECT c FROM Competition c WHERE c.id = :id"),
    @NamedQuery(name = "Competition.findByUpdated", query = "SELECT c FROM Competition c WHERE c.updated = :updated"),
    @NamedQuery(name = "Competition.findByName", query = "SELECT c FROM Competition c WHERE c.name = :name"),
    @NamedQuery(name = "Competition.findByCompetitionDate", query = "SELECT c FROM Competition c WHERE c.competitionDate = :competitionDate"),
    @NamedQuery(name = "Competition.findByStarted", query = "SELECT c FROM Competition c WHERE c.started = :started")})
public class Competition implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @Column(name = "updated")
    @Temporal(TemporalType.DATE)
    private Date updated;
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
    @JoinTable(name = "competition_team", joinColumns = {
        @JoinColumn(name = "competition_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "team_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Team> teamList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "competition")
    private List<CompetitionGame> competitionGameList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "competitionId")
    private List<Match> matchList;

    public Competition() {
    }

    public Competition(String id) {
        this.id = id;
    }

    public Competition(String id, Date updated, String name, Date competitionDate, boolean started) {
        this.id = id;
        this.updated = updated;
        this.name = name;
        this.competitionDate = competitionDate;
        this.started = started;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCompetitionDate() {
        return competitionDate;
    }

    public void setCompetitionDate(Date competitionDate) {
        this.competitionDate = competitionDate;
    }

    public boolean getStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    public List<CompetitionGame> getCompetitionGameList() {
        return competitionGameList;
    }

    public void setCompetitionGameList(List<CompetitionGame> competitionGameList) {
        this.competitionGameList = competitionGameList;
    }

    public List<Match> getMatchList() {
        return matchList;
    }

    public void setMatchList(List<Match> matchList) {
        this.matchList = matchList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Competition)) {
            return false;
        }
        Competition other = (Competition) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.backede.scoreboard.common.generated.Competition[ id=" + id + " ]";
    }
    
}
