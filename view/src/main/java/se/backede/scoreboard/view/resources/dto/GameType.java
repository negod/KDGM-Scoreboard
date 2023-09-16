/*
 */
package se.backede.scoreboard.view.resources.dto;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import java.io.Serializable;
import se.backede.scoreboard.view.resources.dto.adapter.GameTypeAdapter;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@JsonbTypeAdapter(GameTypeAdapter.class)
public enum GameType implements Serializable {
    TIME, SCORE, NONE;
}
