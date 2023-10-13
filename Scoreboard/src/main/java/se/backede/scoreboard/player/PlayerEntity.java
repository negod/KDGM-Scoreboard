/*
 */
package se.backede.scoreboard.player;

import se.backede.scoreboard.team.TeamEntity;
import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import se.backede.scoreboard.common.constants.GlobalConstants;
import se.backede.scoreboard.common.constants.PlayerConstants;
import se.backede.scoreboard.common.dao.GenericEntity;
import se.backede.scoreboard.result.ResultEntity;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Entity(name = PlayerConstants.TABLE_NAME)
@Table(name = "player", schema = GlobalConstants.SCHEMA_NAME)
@NamedQueries({
    @NamedQuery(name = "Player.findAll", query = "SELECT p FROM Player p"),
    @NamedQuery(name = "Player.findById", query = "SELECT p FROM Player p WHERE p.id = :id"),
    @NamedQuery(name = "Player.findByUpdated", query = "SELECT p FROM Player p WHERE p.updatedDate = :updated"),
    @NamedQuery(name = "Player.findByName", query = "SELECT p FROM Player p WHERE p.name = :name"),
    @NamedQuery(name = "Player.findByNickName", query = "SELECT p FROM Player p WHERE p.nickName = :nickName")})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerEntity extends GenericEntity implements Serializable {

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "nick_name")
    private String nickName;

//    @JoinTable(name = "kggn.player_team", joinColumns = {
//        @JoinColumn(name = "player_id", referencedColumnName = "id")}, inverseJoinColumns = {
//        @JoinColumn(name = "team_id", referencedColumnName = "id")})
    @ManyToMany(mappedBy = "playerList")
    private List<TeamEntity> teamList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playerId")
    private List<ResultEntity> resultList;

}
