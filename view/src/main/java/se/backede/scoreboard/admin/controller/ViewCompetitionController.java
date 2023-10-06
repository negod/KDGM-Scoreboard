/*
 */
package se.backede.scoreboard.admin.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.menu.BaseMenuModel;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.MenuModel;
import se.backede.scoreboard.admin.resources.dto.GameMatch;
import se.backede.scoreboard.admin.controller.helper.IndexHelper;
import se.backede.scoreboard.admin.controller.helper.MatchMaker;
import se.backede.scoreboard.admin.resources.dto.Competition;
import se.backede.scoreboard.admin.resources.dto.Game;
import se.backede.scoreboard.admin.resources.dto.Match;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
@Getter
@Setter
@Named("viewCompetitionController")
@ViewScoped
public class ViewCompetitionController implements Serializable {

    @Inject
    @ManagedProperty(value = "#{param.competitionId}")
    String competitionId;

    private Competition selectedCompetition;

    MenuModel stepsModel;
    IndexHelper gamesIndex;

    @Inject
    CompetitionController competition;

    @Inject
    TeamController team;

    @Inject
    PlayerController player;

    @Inject
    GameController game;

    @Inject
    ResultController result;

    @Inject
    MatchController match;

    Map<Integer, GameMatch> matches = new HashMap<>();

    @PostConstruct
    public void init() {
        selectedCompetition = competition.getItemById(competitionId);
        gamesIndex = new IndexHelper(selectedCompetition.getGames().size() - 1, 0);

        if (selectedCompetition.getStarted()) {
            matches = getMatches(selectedCompetition);
        } else {
            matches = createMatches(selectedCompetition.getGames());
        }

        prepareSteps();
    }

    public Map<Integer, GameMatch> getMatches(Competition competition) {
        Map<Integer, GameMatch> matches = new HashMap<>();
        match.getMatchClient().getByCompetitionId(selectedCompetition.getId()).ifPresent(m -> {
            Map<Game, List<Match>> matchesMap
                    = m.stream().collect(Collectors.groupingBy(Match::getGame));

            List<GameMatch> gameMatches = matchesMap.entrySet().stream()
                    .map(entry -> GameMatch.builder()
                            .game(entry.getKey())
                            .matches(entry.getValue())
                            .build())
                    .collect(Collectors.toList());
            //Fixa nedan
            for (int i = 0; i < gameMatches.size(); i++) {
                matches.put(i, gameMatches.get(i));
            }
        });
        return matches;
    }

    public List<Match> getSelectedGameMatches() {

        GameMatch match = matches.get(gamesIndex.getActiveIndex());
        return match.getMatches();

    }

    public Map<Integer, GameMatch> createMatches(List<Game> games) {

        Map<Integer, GameMatch> matches = new HashMap<>();

        int index = 0;
        for (Game game : games) {
            List<Match> generateMatches = MatchMaker.generateMatches(selectedCompetition.getTeams());
            GameMatch build = GameMatch.builder().game(game).matches(generateMatches).build();
            matches.put(index, build);
            index++;
        }
        return matches;

    }

    public void prepareSteps() {
        stepsModel = new BaseMenuModel();
        for (Integer index : matches.keySet()) {
            DefaultMenuItem item = DefaultMenuItem.builder()
                    .url("#")
                    .value(matches.get(index).getGame().getName())
                    .build();
            stepsModel.getElements().add(item);
        }
    }

}
