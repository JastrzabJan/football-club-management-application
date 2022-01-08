package pjatk.edu.pl.footballclubmanagementapplication.backend.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Match;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.MatchRepository;

import java.util.List;

@Service
public class MatchService implements CrudService<Match> {

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public JpaRepository<Match, Long> getRepository() {
        return this.matchRepository;
    }

    @Override
    public Match save(Match entity) {
        return getRepository().save(entity);
    }

    @Override
    public void delete(Match entity) {
        getRepository().delete(entity);
    }

    @Override
    public Match createNew(User currentUser) {
        return new Match();
    }

    public List<Match> getAllMatches() {
        return getRepository().findAll();
    }
}
