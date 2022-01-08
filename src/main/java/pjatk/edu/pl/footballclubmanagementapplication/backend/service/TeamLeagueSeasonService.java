package pjatk.edu.pl.footballclubmanagementapplication.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.*;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.TeamLeagueSeasonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamLeagueSeasonService implements CrudService<TeamLeagueSeason> {

    private final TeamLeagueSeasonRepository teamLeagueSeasonRepository;

    @Autowired
    public TeamLeagueSeasonService(TeamLeagueSeasonRepository teamLeagueSeasonRepository) {
        this.teamLeagueSeasonRepository = teamLeagueSeasonRepository;
    }

    @Override
    public JpaRepository<TeamLeagueSeason, Long> getRepository() {
        return this.teamLeagueSeasonRepository;
    }

    @Override
    public TeamLeagueSeason save(TeamLeagueSeason entity) {
        return getRepository().save(entity);
    }

    @Override
    public void delete(TeamLeagueSeason entity) {
        getRepository().delete(entity);
    }

    @Override
    public TeamLeagueSeason createNew(User currentUser) {
        return new TeamLeagueSeason();
    }

    public List<TeamLeagueSeason> findAll() {
        return getRepository().findAll();
    }

    public List<Team> getTeamsInLeagueInSeason(Season season, League league){
        List<TeamLeagueSeason> teamLeagueSeasons = findAll().stream().filter(tls -> tls.getSeason().equals(season) && tls.getLeague().equals(league)).collect(Collectors.toList());
        List<Team> teams = teamLeagueSeasons.stream().map(TeamLeagueSeason::getTeam).collect(Collectors.toList());
        return teams;
    }


}
