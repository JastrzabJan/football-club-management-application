package pjatk.edu.pl.footballclubmanagementapplication.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Team;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.TeamRepository;

import java.util.List;
import java.util.stream.Collectors;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.SearchUtils.matchesTerm;

@Service
public class TeamService implements CrudService<Team> {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public JpaRepository<Team, Long> getRepository() {
        return this.teamRepository;
    }

    @Override
    public Team save(Team entity) {
        return getRepository().save(entity);
    }

    @Override
    public void delete(Team entity) {
        getRepository().delete(entity);
    }

    @Override
    public Team createNew(User currentUser) {
        return new Team();
    }

    public List<Team> findAll() {
        return getRepository().findAll();
    }

    public List<String> findAllTeamNames() {
        return getRepository().findAll().stream().map(team -> team.getName()).collect(Collectors.toList());
    }

    public List<Team> getAllTeamsWithFilter(String filterText) {
        return getRepository().findAll().stream().filter(e ->
        {
            if (filterText.isEmpty()) {
                return true;
            } else {
                return matchesTerm(e.getName(), filterText) || matchesTerm(e.getTeamClass(), filterText);
            }
        }).collect(Collectors.toList());
    }

}
