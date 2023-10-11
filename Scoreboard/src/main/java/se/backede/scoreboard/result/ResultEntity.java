/*
 */
package se.backede.scoreboard.result;

import se.backede.scoreboard.player.PlayerEntity;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.common.constants.GlobalConstants;
import se.backede.scoreboard.common.constants.ResultConstants;
import se.backede.scoreboard.common.dao.GenericEntity;
import se.backede.scoreboard.match.MatchEntity;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity(name = ResultConstants.TABLE_NAME)
@Table(name = "result", schema = GlobalConstants.SCHEMA_NAME)
@NamedQueries({
    @NamedQuery(name = ResultConstants.QUERY_GET_ALL_RESULTS, query = "SELECT r FROM Result r"),
    @NamedQuery(name = ResultConstants.QUERY_GET_BY_MATCH, query = "SELECT r FROM Result r where r.matchId.id = :matchId")
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResultEntity extends GenericEntity implements Serializable {

    @Column(name = "score_value")
    private Long scoreValue;

    @JoinColumn(name = "match_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MatchEntity matchId;

    @JoinColumn(name = "player_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PlayerEntity playerId;

}
