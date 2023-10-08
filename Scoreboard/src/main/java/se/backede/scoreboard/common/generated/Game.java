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
@Table(name = "game")
@NamedQueries({
    @NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g"),
    @NamedQuery(name = "Game.findById", query = "SELECT g FROM Game g WHERE g.id = :id"),
    @NamedQuery(name = "Game.findByUpdated", query = "SELECT g FROM Game g WHERE g.updated = :updated"),
    @NamedQuery(name = "Game.findByName", query = "SELECT g FROM Game g WHERE g.name = :name"),
    @NamedQuery(name = "Game.findByGametype", query = "SELECT g FROM Game g WHERE g.gametype = :gametype")})
public class Game implements Serializable {

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
    @Column(name = "gametype")
    private String gametype;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<CompetitionGame> competitionGameList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gameId")
    private List<Match> matchList;

    public Game() {
    }

    public Game(String id) {
        this.id = id;
    }

    public Game(String id, Date updated, String name, String gametype) {
        this.id = id;
        this.updated = updated;
        this.name = name;
        this.gametype = gametype;
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

    public String getGametype() {
        return gametype;
    }

    public void setGametype(String gametype) {
        this.gametype = gametype;
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
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.backede.scoreboard.common.generated.Game[ id=" + id + " ]";
    }
    
}
