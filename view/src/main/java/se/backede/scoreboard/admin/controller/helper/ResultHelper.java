/*
 */
package se.backede.scoreboard.admin.controller.helper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Builder;
import se.backede.scoreboard.admin.resources.dto.Result;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Builder
public class ResultHelper {

    Map<String, List<Result>> results;

    public Optional<Result> getResultByPlayerAndMatch(String playerId, String matchId) {
        return Optional.ofNullable(results.get(matchId))
                .orElse(Collections.emptyList()) // Returnerar en tom lista om matchId inte finns i mappen
                .stream() // Skapar en stream av listan
                .filter(result -> result.getPlayer().getId().equals(playerId)) // Filtrerar listan baserat på playerId
                .findFirst(); // Tar första träffen (om det finns någon)
    }

}
