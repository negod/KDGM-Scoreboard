/*
 */
package se.backede.scoreboard.admin.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.menu.BaseMenuModel;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.MenuModel;
import se.backede.scoreboard.admin.controller.helper.IndexHelper;
import se.backede.scoreboard.admin.controller.helper.MatchHelper;
import se.backede.scoreboard.admin.resources.dto.Competition;
import se.backede.scoreboard.admin.resources.dto.Game;
import se.backede.scoreboard.admin.resources.dto.Match;
import se.backede.scoreboard.admin.resources.dto.Player;
import se.backede.scoreboard.admin.resources.dto.Result;

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
    CompetitionController competitionController;

    @Inject
    TeamController team;

    @Inject
    PlayerController player;

    @Inject
    GameController gameController;

    @Inject
    ResultController resultController;

    @Inject
    MatchController match;

    Player selectedPlayer;

    Map<String, List<Match>> matchList = new HashMap<>();
    Map<String, List<Result>> resultList = new HashMap<>();

    public IndexHelper createGameIndexesAndBindGameId(List<Game> games) {

        Map<Integer, String> indexedGameIds = new HashMap<>();
        for (Game game : games) {
            indexedGameIds.put(game.getGameOrder(), game.getId());
        }

        return new IndexHelper(selectedCompetition.getGames().size() - 1, 0, indexedGameIds);

    }

    public Map<String, List<Result>> getResultsForMatchesOrderedByMatchId(Map<String, List<Match>> matches) {

        Map<String, List<Result>> results = new HashMap<>();
        for (Map.Entry<String, List<Match>> entry : matches.entrySet()) {
            List<Match> matchList = entry.getValue();

            for (Match match : matchList) {
                resultController.getResultClient().getByMatch(match.getId()).ifPresent(x -> {
                    results.put(match.getId(), x);
                });
            }
        }
        return results;
    }

    @PostConstruct
    public void init() {

        selectedCompetition = competitionController.getItemById(competitionId);

        gamesIndex = createGameIndexesAndBindGameId(selectedCompetition.getGames());

        if (selectedCompetition.getStarted()) {
            match.getMatchClient().getByCompetitionId(selectedCompetition.getId()).ifPresent(matches -> {
                this.matchList = MatchHelper.getMatchesGroupedOnGameId(matches);
            });
            resultList = getResultsForMatchesOrderedByMatchId(matchList);
            MatchHelper.enrichMatchesWithPlayerRestults(matchList, resultList);
        } else {

            matchList = MatchHelper.createMatches(selectedCompetition.getGames(), selectedCompetition.getTeams());

            List<Match> persistedMatches = new ArrayList<>();
            for (Map.Entry<String, List<Match>> entry : matchList.entrySet()) {

                //TODO Match Order
                Integer order = 0;
                for (Match matche : entry.getValue()) {
                    matche.setCompetition(selectedCompetition);
                    matche.setOrder(order);
                    Optional<Match> persistedMatch = match.getMatchClient().create(matche);
                    order++;
                }

            }

            this.matchList = MatchHelper.getMatchesGroupedOnGameId(persistedMatches);
            selectedCompetition.setStarted(Boolean.TRUE);
            competitionController.getCompetitionClient().update(selectedCompetition);
        }

        prepareSteps();
    }

    public List<Match> getSelectedGameMatches() {
        return matchList.get(gamesIndex.getActiveIndex());
    }

    public void prepareSteps() {
        stepsModel = new BaseMenuModel();
        for (int i = 0; i < gamesIndex.getMaxIndex() + 1; i++) {
            Game gameById = this.gameController.getItemById(gamesIndex.getGameByIndex(i));
            DefaultMenuItem item = DefaultMenuItem.builder()
                    .url("#")
                    .value(gameById.getName())
                    .build();
            stepsModel.getElements().add(item);
        }
    }

}
