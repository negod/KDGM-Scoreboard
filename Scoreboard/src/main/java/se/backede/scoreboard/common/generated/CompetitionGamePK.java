/*
 */
package se.backede.scoreboard.common.generated;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Embeddable
public class CompetitionGamePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @Column(name = "competition_id")
    private String competitionId;
    @Basic(optional = false)
    @Column(name = "game_id")
    private String gameId;
    @Basic(optional = false)
    @Column(name = "game_order")
    private int gameOrder;

    public CompetitionGamePK() {
    }

    public CompetitionGamePK(String id, String competitionId, String gameId, int gameOrder) {
        this.id = id;
        this.competitionId = competitionId;
        this.gameId = gameId;
        this.gameOrder = gameOrder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(String competitionId) {
        this.competitionId = competitionId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getGameOrder() {
        return gameOrder;
    }

    public void setGameOrder(int gameOrder) {
        this.gameOrder = gameOrder;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        hash += (competitionId != null ? competitionId.hashCode() : 0);
        hash += (gameId != null ? gameId.hashCode() : 0);
        hash += (int) gameOrder;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompetitionGamePK)) {
            return false;
        }
        CompetitionGamePK other = (CompetitionGamePK) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        if ((this.competitionId == null && other.competitionId != null) || (this.competitionId != null && !this.competitionId.equals(other.competitionId))) {
            return false;
        }
        if ((this.gameId == null && other.gameId != null) || (this.gameId != null && !this.gameId.equals(other.gameId))) {
            return false;
        }
        if (this.gameOrder != other.gameOrder) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.backede.scoreboard.common.generated.CompetitionGamePK[ id=" + id + ", competitionId=" + competitionId + ", gameId=" + gameId + ", gameOrder=" + gameOrder + " ]";
    }
    
}
