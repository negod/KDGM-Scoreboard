package se.backede.scoreboard.entity;

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
import se.backede.scoreboard.common.GenericEntity;
import se.backede.scoreboard.common.constants.GlobalConstants;
import se.backede.scoreboard.common.constants.ResultConstants;
import se.backede.scoreboard.converter.DurationConverter;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity
@ToString
@Table(schema = GlobalConstants.SCHEMA_NAME, name = ResultConstants.TABLE_NAME)
@NamedQueries({
    @NamedQuery(name = ResultConstants.QUERY_GET_ALL_RESULTS, query = "SELECT r FROM Result r"),
    @NamedQuery(name = ResultConstants.QUERY_GET_BY_GAME, query = "SELECT r FROM Result r where r.game = :game")
})
@Getter
@Setter
public class Result extends GenericEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    private Integer score;

    @Convert(converter = DurationConverter.class)
    private Duration time;

}
