/*
 */
package se.backede.scoreboard.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import se.backede.scoreboard.common.GenericEntity;
import se.backede.scoreboard.common.constants.CompetitionConstants;
import se.backede.scoreboard.common.constants.GlobalConstants;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity
@ToString
@Table(schema = GlobalConstants.SCHEMA_NAME, name = CompetitionConstants.TABLE_NAME)
@NamedQueries({
    @NamedQuery(name = CompetitionConstants.QUERY_GET_ALL_COMPETITIONS, query = "SELECT c FROM Competition c")
})
@Getter
@Setter
public class Competition extends GenericEntity {

    private Date date;
    private String name;
    private Set<Team> teams;
    private Set<Game> games;

}