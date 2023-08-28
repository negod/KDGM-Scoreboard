package se.backede.scoreboard.entity;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTypeSerializer;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.time.Duration;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import se.backede.scoreboard.common.GenericEntity;
import se.backede.scoreboard.common.constants.GlobalConstants;
import se.backede.scoreboard.common.constants.ResultConstants;
import se.backede.scoreboard.converter.DurationConverter;
import se.backede.scoreboard.serializer.DurationSerializer;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity
@EqualsAndHashCode
@ToString
@Table(schema = GlobalConstants.SCHEMA_NAME, name = ResultConstants.TABLE_NAME)
@NamedQueries({
    @NamedQuery(name = ResultConstants.QUERY_GET_ALL_RESULTS, query = "SELECT r FROM Result r")
})

@Getter
@Setter
public class Result extends GenericEntity {

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    private Integer score;

    @JsonbProperty("time")
    @Convert(converter = DurationConverter.class)
    @JsonbTypeSerializer(DurationSerializer.class)
    private Duration time;

}
