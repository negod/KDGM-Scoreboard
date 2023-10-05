package se.backede.scoreboard.game;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import se.backede.scoreboard.common.dao.GenericEntity;
import se.backede.scoreboard.common.constants.GameConstants;
import se.backede.scoreboard.common.constants.GlobalConstants;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.competition.CompetitionEntity;
import se.backede.scoreboard.match.MatchEntity;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity(name = "Game")
@ToString
@Table(schema = GlobalConstants.SCHEMA_NAME, name = GameConstants.TABLE_NAME)
@NamedQueries({
    @NamedQuery(name = GameConstants.QUERY_GET_ALL_GAMES, query = "SELECT g FROM Game g"),
    @NamedQuery(name = GameConstants.QUERY_UPDATE_GAME, query = "UPDATE Game g set g.name =:name, g.gametype =:gametype WHERE g.id=:id "),})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GameEntity extends GenericEntity {

    @NotNull(message = "Name cannot be null")
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "GameType cannot be null")
    private GameType gametype;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "game")
    private List<MatchEntity> results;

    @ManyToMany(mappedBy = "games")
    private Set<CompetitionEntity> competitions;

}
