/*
 */
package se.backede.scoreboard.team;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import se.backede.scoreboard.common.constants.GlobalConstants;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@Entity
@Table(name = "teamnames", schema = GlobalConstants.SCHEMA_NAME)
public class TeamNameEntity {

    @Id
    String name;

}
