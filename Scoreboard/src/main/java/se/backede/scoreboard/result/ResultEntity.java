package se.backede.scoreboard.result;

import se.backede.scoreboard.player.PlayerEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.common.dao.GenericEntity;
import se.backede.scoreboard.common.constants.GlobalConstants;
import se.backede.scoreboard.common.constants.ResultConstants;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity(name = "Result")
@ToString
@Table(schema = GlobalConstants.SCHEMA_NAME, name = ResultConstants.TABLE_NAME)
@NamedQueries({
    @NamedQuery(name = ResultConstants.QUERY_GET_ALL_RESULTS, query = "SELECT r FROM Result r"),
    @NamedQuery(name = ResultConstants.QUERY_GET_BY_COMPETITION, query = "SELECT r FROM Result r where r.matchId = :matchId")
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResultEntity extends GenericEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "player_id", nullable = false, referencedColumnName = "id")
    private PlayerEntity player;

    @Column(name = "match_id", nullable = false)
    private String matchId;

    @Column(name = "score_value")
    private Long scoreValue;

}
