package pjatk.edu.pl.footballclubmanagementapplication.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Team;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.TeamRepository;

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
    public Team save(User currentUser, Team entity) {
        return teamRepository.save(entity);
    }

    @Override
    public void delete(User currentUser, Team entity) {
        teamRepository.delete(entity);
    }

    @Override
    public Team createNew(User currentUser) {
        return new Team();
    }


}
