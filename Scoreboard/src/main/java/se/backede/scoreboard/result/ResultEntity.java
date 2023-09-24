package se.backede.scoreboard.result;

import se.backede.scoreboard.player.PlayerEntity;
import se.backede.scoreboard.game.GameEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.time.Duration;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import se.backede.scoreboard.common.dao.GenericEntity;
import se.backede.scoreboard.common.constants.GlobalConstants;
import se.backede.scoreboard.common.constants.ResultConstants;
import se.backede.scoreboard.converter.DurationConverter;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity(name = "Result")
@ToString
@Table(schema = GlobalConstants.SCHEMA_NAME, name = ResultConstants.TABLE_NAME)
@NamedQueries({
    @NamedQuery(name = ResultConstants.QUERY_GET_ALL_RESULTS, query = "SELECT r FROM Result r"),
    @NamedQuery(name = ResultConstants.QUERY_GET_BY_GAME, query = "SELECT r FROM Result r where r.game = :game")
})
@Getter
@Setter
public class ResultEntity extends GenericEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "player_id", nullable = false, referencedColumnName = "id")
    private PlayerEntity player;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "game_id", nullable = false, referencedColumnName = "id")
    private GameEntity game;

    private Integer score;

    @Convert(converter = DurationConverter.class)
    private Duration time;

}
