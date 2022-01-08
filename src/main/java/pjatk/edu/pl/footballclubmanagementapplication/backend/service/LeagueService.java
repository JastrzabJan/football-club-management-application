package pjatk.edu.pl.footballclubmanagementapplication.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.League;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.LeagueRepository;

import java.util.List;
import java.util.stream.Collectors;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.SearchUtils.matchesTerm;

@Service
public class LeagueService implements CrudService<League> {

    private final LeagueRepository leagueRepository;

    @Autowired
    public LeagueService(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    @Override
    public JpaRepository<League, Long> getRepository() {
        return this.leagueRepository;
    }

    @Override
    public League save(League entity) {
        return getRepository().save(entity);
    }

    @Override
    public void delete(League entity) {
        getRepository().delete(entity);
    }

    @Override
    public League createNew(User currentUser) {
        return new League();
    }

    public List<League> getAllLeagues() {
        return getRepository().findAll();
    }

    public List<League> getAllLeaguesWithFilter(String filterText) {
        return getRepository().findAll().stream().filter(e ->
        {
            if (filterText.isEmpty()) {
                return true;
            } else {
                return matchesTerm(e.getName(), filterText);
            }
        }).collect(Collectors.toList());
    }

}
