/*
 */
package se.backede.scoreboard.common.generated;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity
@Table(name = "competition_game")
@NamedQueries({
    @NamedQuery(name = "CompetitionGame.findAll", query = "SELECT c FROM CompetitionGame c"),
    @NamedQuery(name = "CompetitionGame.findById", query = "SELECT c FROM CompetitionGame c WHERE c.competitionGamePK.id = :id"),
    @NamedQuery(name = "CompetitionGame.findByUpdated", query = "SELECT c FROM CompetitionGame c WHERE c.updated = :updated"),
    @NamedQuery(name = "CompetitionGame.findByCompetitionId", query = "SELECT c FROM CompetitionGame c WHERE c.competitionGamePK.competitionId = :competitionId"),
    @NamedQuery(name = "CompetitionGame.findByGameId", query = "SELECT c FROM CompetitionGame c WHERE c.competitionGamePK.gameId = :gameId"),
    @NamedQuery(name = "CompetitionGame.findByGameOrder", query = "SELECT c FROM CompetitionGame c WHERE c.competitionGamePK.gameOrder = :gameOrder")})
public class CompetitionGame implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CompetitionGamePK competitionGamePK;
    @Basic(optional = false)
    @Column(name = "updated")
    @Temporal(TemporalType.DATE)
    private Date updated;
    @JoinColumn(name = "competition_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Competition competition;
    @JoinColumn(name = "game_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Game game;

    public CompetitionGame() {
    }

    public CompetitionGame(CompetitionGamePK competitionGamePK) {
        this.competitionGamePK = competitionGamePK;
    }

    public CompetitionGame(CompetitionGamePK competitionGamePK, Date updated) {
        this.competitionGamePK = competitionGamePK;
        this.updated = updated;
    }

    public CompetitionGame(String id, String competitionId, String gameId, int gameOrder) {
        this.competitionGamePK = new CompetitionGamePK(id, competitionId, gameId, gameOrder);
    }

    public CompetitionGamePK getCompetitionGamePK() {
        return competitionGamePK;
    }

    public void setCompetitionGamePK(CompetitionGamePK competitionGamePK) {
        this.competitionGamePK = competitionGamePK;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (competitionGamePK != null ? competitionGamePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompetitionGame)) {
            return false;
        }
        CompetitionGame other = (CompetitionGame) object;
        if ((this.competitionGamePK == null && other.competitionGamePK != null) || (this.competitionGamePK != null && !this.competitionGamePK.equals(other.competitionGamePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.backede.scoreboard.common.generated.CompetitionGame[ competitionGamePK=" + competitionGamePK + " ]";
    }
    
}
