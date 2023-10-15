/*
 */
package se.backede.scoreboard.game;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import se.backede.scoreboard.competitiongame.CompetitionGameEntity;
import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.common.constants.GameConstants;
import se.backede.scoreboard.common.constants.GlobalConstants;
import se.backede.scoreboard.common.dao.GenericEntity;
import se.backede.scoreboard.match.MatchEntity;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity(name = GameConstants.TABLE_NAME)
@Table(name = "game", schema = GlobalConstants.SCHEMA_NAME)
@NamedQueries({
    @NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g"),
    @NamedQuery(name = "Game.findById", query = "SELECT g FROM Game g WHERE g.id = :id"),
    @NamedQuery(name = "Game.findByUpdated", query = "SELECT g FROM Game g WHERE g.updatedDate = :updated"),
    @NamedQuery(name = "Game.findByName", query = "SELECT g FROM Game g WHERE g.name = :name"),
    @NamedQuery(name = "Game.findByGametype", query = "SELECT g FROM Game g WHERE g.gametype = :gametype")})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GameEntity extends GenericEntity implements Serializable {

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "rules")
    private String rules;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "GameType cannot be null")
    private ScoreCalculation calculationtype;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "GameType cannot be null")
    private GameType gametype;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<CompetitionGameEntity> competitionGameList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gameId")
    private List<MatchEntity> matchList;

}
