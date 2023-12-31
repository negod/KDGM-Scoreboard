/*
 */
package se.backede.scoreboard.competitiongame;

import java.io.Serializable;
import jakarta.persistence.EmbeddedId;
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
import se.backede.scoreboard.common.constants.CompetitionGameConstants;
import se.backede.scoreboard.common.constants.GlobalConstants;
import se.backede.scoreboard.competition.CompetitionEntity;
import se.backede.scoreboard.game.GameEntity;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity(name = CompetitionGameConstants.TABLE_NAME)
@Table(name = "competition_game", schema = GlobalConstants.SCHEMA_NAME)
@NamedQueries({
    @NamedQuery(name = "CompetitionGame.findAll", query = "SELECT c FROM CompetitionGame c"),
    @NamedQuery(name = "CompetitionGame.findById", query = "SELECT c FROM CompetitionGame c WHERE c.competitionGamePK.id = :id"),
    @NamedQuery(name = "CompetitionGame.findByCompetitionId", query = "SELECT c FROM CompetitionGame c WHERE c.competitionGamePK.competitionId = :competitionId"),
    @NamedQuery(name = "CompetitionGame.findByGameId", query = "SELECT c FROM CompetitionGame c WHERE c.competitionGamePK.gameId = :gameId"),
    @NamedQuery(name = CompetitionGameConstants.QUERY_MATCH_BY_COMPETITION_AND_GAME, query = "SELECT c FROM CompetitionGame c WHERE c.competitionGamePK.gameId = :gameId AND c.competitionGamePK.competitionId =:competitionId")})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionGameEntity implements Serializable {

    @EmbeddedId
    protected CompetitionGamePKEntity competitionGamePK;

    @JoinColumn(name = "competition_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CompetitionEntity competition;

    @JoinColumn(name = "game_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GameEntity game;

}
