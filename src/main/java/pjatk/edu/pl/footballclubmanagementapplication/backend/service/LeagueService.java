package pjatk.edu.pl.footballclubmanagementapplication.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.League;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.LeagueRepository;

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
}
