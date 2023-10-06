package se.backede.scoreboard.match;

import se.backede.scoreboard.game.GameEntity;
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
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.common.dao.GenericEntity;
import se.backede.scoreboard.common.constants.GlobalConstants;
import se.backede.scoreboard.common.constants.MatchConstants;
import se.backede.scoreboard.competition.CompetitionEntity;
import se.backede.scoreboard.team.TeamEntity;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity(name = "Match")
@ToString
@Table(schema = GlobalConstants.SCHEMA_NAME, name = MatchConstants.TABLE_NAME)
@NamedQueries({
    @NamedQuery(name = MatchConstants.QUERY_GET_BY_COMPETITION, query = "SELECT m FROM Match m where m.competition = :competition")
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MatchEntity extends GenericEntity {

    @ManyToOne()
    @JoinColumn(name = "team1_id", nullable = false, referencedColumnName = "id")
    private TeamEntity team1;

    @ManyToOne()
    @JoinColumn(name = "team2_id", nullable = false, referencedColumnName = "id")
    private TeamEntity team2;

    @ManyToOne()
    @JoinColumn(name = "game_id", nullable = false, referencedColumnName = "id")
    private GameEntity game;

    @ManyToOne()
    @JoinColumn(name = "competition_id", nullable = false, referencedColumnName = "id")
    private CompetitionEntity competition;

}
