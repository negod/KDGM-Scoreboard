/*
 */
package se.backede.scoreboard.common.constants;

import se.backede.scoreboard.common.CommonConstants;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class PlayerConstants extends CommonConstants {

    public static final String TABLE_NAME = "Player";
    
    public static final String TABLE_COLUMN_NAME = "name";
    public static final String TABLE_COLUMN_NICK_NAME = "nickName";
    public static final String TABLE_COLUMN_TEAM = "team";
    
    public static final String QUERY_GET_ALL_PLAYERS = "GetAllPlayers";
    public static final String QUERY_UPDATE_PLAYER = "updatePlayer";
    public static final String QUERY_DELETE_TEAM = "deleteTeamFromPlayer";

}
