/*
 */
package se.backede.scoreboard.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.backede.scoreboard.common.constants.CompetitionConstants;
import se.backede.scoreboard.entity.Competition;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class CompetitionDao {

    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    public CompetitionDao() {
    }

    public Optional<List<Competition>> getAll() {

        try {
            return Optional.ofNullable(em.createNamedQuery(CompetitionConstants.QUERY_GET_ALL_COMPETITIONS, Competition.class).getResultList());
        } catch (Exception e) {
            Logger.getLogger(CompetitionDao.class.getName()).log(Level.SEVERE, "Error when getting all competitions", e);
        }
        return Optional.empty();
    }

    public Optional<Competition> createCompetition(Competition competition) {

        try {
            em.persist(competition);
            return Optional.of(competition);
        } catch (Exception e) {
            Logger.getLogger(CompetitionDao.class.getName()).log(Level.SEVERE, "Error when persisting competition", e);
            return Optional.empty();
        }
    }

    public Optional<Competition> getCompetitionById(String id) {

        try {
            Competition find = em.find(Competition.class, id);
            return Optional.ofNullable(find);
        } catch (Exception e) {
            Logger.getLogger(CompetitionDao.class.getName()).log(Level.SEVERE, "Error when getting competition by ID", e);
            return Optional.empty();
        }
    }

    public Optional<Boolean> deleteCompetition(String id) {

        try {

            Competition competitionToRemove = em.find(Competition.class, id);

            em.remove(competitionToRemove);

            return Optional.of(Boolean.TRUE);
        } catch (Exception e) {
            Logger.getLogger(CompetitionDao.class.getName()).log(Level.SEVERE, "Error when deleting competition", e);
        }

        return Optional.empty();

    }

    public Optional<Competition> updateCompetition(Competition competition) {
        try {
            Competition find = em.find(Competition.class, competition.getId());

            if (competition.getName() != null) {
                find.setName(competition.getName());
            }

            em.merge(find);
            return Optional.ofNullable(find);
        } catch (Exception e) {
            Logger.getLogger(CompetitionDao.class.getName()).log(Level.SEVERE, "Error when updating competition", e);
            return Optional.empty();
        }
    }

}
