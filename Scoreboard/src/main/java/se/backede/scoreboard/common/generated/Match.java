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
import javax.persistence.ManyToOne;
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
@Table(name = "match")
@NamedQueries({
    @NamedQuery(name = "Match.findAll", query = "SELECT m FROM Match m"),
    @NamedQuery(name = "Match.findById", query = "SELECT m FROM Match m WHERE m.id = :id"),
    @NamedQuery(name = "Match.findByUpdated", query = "SELECT m FROM Match m WHERE m.updated = :updated"),
    @NamedQuery(name = "Match.findByMatchOrder", query = "SELECT m FROM Match m WHERE m.matchOrder = :matchOrder")})
public class Match implements Serializable {

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
    @Column(name = "match_order")
    private int matchOrder;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "matchId")
    private List<Result> resultList;
    @JoinColumn(name = "competition_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Competition competitionId;
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Game gameId;
    @JoinColumn(name = "team1_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Team team1Id;
    @JoinColumn(name = "team2_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Team team2Id;

    public Match() {
    }

    public Match(String id) {
        this.id = id;
    }

    public Match(String id, Date updated, int matchOrder) {
        this.id = id;
        this.updated = updated;
        this.matchOrder = matchOrder;
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

    public int getMatchOrder() {
        return matchOrder;
    }

    public void setMatchOrder(int matchOrder) {
        this.matchOrder = matchOrder;
    }

    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
    }

    public Competition getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Competition competitionId) {
        this.competitionId = competitionId;
    }

    public Game getGameId() {
        return gameId;
    }

    public void setGameId(Game gameId) {
        this.gameId = gameId;
    }

    public Team getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(Team team1Id) {
        this.team1Id = team1Id;
    }

    public Team getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(Team team2Id) {
        this.team2Id = team2Id;
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
        if (!(object instanceof Match)) {
            return false;
        }
        Match other = (Match) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.backede.scoreboard.common.generated.Match[ id=" + id + " ]";
    }
    
}
