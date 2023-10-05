package se.backede.scoreboard.match;

import se.backede.scoreboard.game.GameEntity;
import jakarta.persistence.CascadeType;
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
import se.backede.scoreboard.competition.CompetitionEntity;
import se.backede.scoreboard.team.TeamEntity;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity(name = "Result")
@ToString
@Table(schema = GlobalConstants.SCHEMA_NAME, name = ResultConstants.TABLE_NAME)
@NamedQueries({
    @NamedQuery(name = ResultConstants.QUERY_GET_ALL_RESULTS, query = "SELECT r FROM Result r"),
    @NamedQuery(name = ResultConstants.QUERY_GET_BY_COMPETITION, query = "SELECT r FROM Result r where r.competition = :competition")
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MatchEntity extends GenericEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "team_id", nullable = false, referencedColumnName = "id")
    private TeamEntity team1;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "team_id", nullable = false, referencedColumnName = "id")
    private TeamEntity team2;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "game_id", nullable = false, referencedColumnName = "id")
    private GameEntity game;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "competition_id", nullable = false, referencedColumnName = "id")
    private CompetitionEntity competition;

    private Integer order;

}
