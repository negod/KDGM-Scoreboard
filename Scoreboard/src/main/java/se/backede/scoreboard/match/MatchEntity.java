/*
 */
package se.backede.scoreboard.match;

import se.backede.scoreboard.result.ResultEntity;
import se.backede.scoreboard.team.TeamEntity;
import se.backede.scoreboard.game.GameEntity;
import se.backede.scoreboard.competition.CompetitionEntity;
import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
import se.backede.scoreboard.common.constants.MatchConstants;
import se.backede.scoreboard.common.dao.GenericEntity;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity(name = MatchConstants.TABLE_NAME)
@Table(name = "match", schema = GlobalConstants.SCHEMA_NAME)
@NamedQueries({
    @NamedQuery(name = "Match.findAll", query = "SELECT m FROM Match m"),
    @NamedQuery(name = "Match.findById", query = "SELECT m FROM Match m WHERE m.id = :id"),
    @NamedQuery(name = "Match.findByUpdated", query = "SELECT m FROM Match m WHERE m.updatedDate = :updated"),
    @NamedQuery(name = "Match.findByMatchOrder", query = "SELECT m FROM Match m WHERE m.matchOrder = :matchOrder")})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MatchEntity extends GenericEntity implements Serializable {

    @Basic(optional = false)
    @Column(name = "match_order")
    private int matchOrder;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "matchId")
    private List<ResultEntity> resultList;

    @JoinColumn(name = "competition_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CompetitionEntity competitionId;

    @JoinColumn(name = "game_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GameEntity gameId;

    @JoinColumn(name = "team1_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TeamEntity team1Id;

    @JoinColumn(name = "team2_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TeamEntity team2Id;

}
