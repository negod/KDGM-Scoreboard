/*
 */
package se.backede.scoreboard.competitiongame;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.common.dao.GenericEntity;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Embeddable
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionGamePKEntity extends GenericEntity implements Serializable {

    @Basic(optional = false)
    @Column(name = "competition_id")
    private String competitionId;

    @Basic(optional = false)
    @Column(name = "game_id")
    private String gameId;

    @Basic(optional = false)
    @Column(name = "game_order")
    private Integer gameOrder;

}
