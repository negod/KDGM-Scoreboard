package se.backede.scoreboard.competitiongame;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import se.backede.scoreboard.common.dao.GenericEntity;
import se.backede.scoreboard.common.constants.GlobalConstants;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.common.constants.CompetitionGameConstants;
import se.backede.scoreboard.competition.CompetitionEntity;
import se.backede.scoreboard.game.GameEntity;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity(name = "CompetitionGame")
@ToString
@Table(schema = GlobalConstants.SCHEMA_NAME, name = CompetitionGameConstants.TABLE_NAME)
@NamedQueries({
    @NamedQuery(name = CompetitionGameConstants.QUERY_GET_ALL_BY_COMPETITION, query = "SELECT cg FROM CompetitionGame cg where cg.competition =:competition"),
    @NamedQuery(name = CompetitionGameConstants.QUERY_GET_ALL_BY_GAME, query = "SELECT cg FROM CompetitionGame cg where cg.game =:game"),})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionGameEntity extends GenericEntity {

//    @ManyToOne()
//    @JoinColumn(name = "game_id", referencedColumnName = "id")
//    private GameEntity game;
//
//    @ManyToOne()
//    @JoinColumn(name = "competition_id", referencedColumnName = "id")
//    private CompetitionEntity competition;
    @Column(name = "game_id")
    private String game;

    @Column(name = "competition_id")
    private String competition;

    @Column(name = "game_order")
    private Integer gameOrder;

}
