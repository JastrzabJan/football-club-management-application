package pjatk.edu.pl.footballclubmanagementapplication.backend.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Player;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.PlayerTeam;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Team;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.PlayerTeamRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerTeamService {

    private PlayerTeamRepository playerTeamRepository;

    public PlayerTeamService(PlayerTeamRepository playerTeamRepository) {
        this.playerTeamRepository = playerTeamRepository;
    }

    public JpaRepository<PlayerTeam, Long> getRepository() {
        return this.playerTeamRepository;
    }

    public PlayerTeam createNew(User currentUser) {
        return new PlayerTeam();
    }

    public List<PlayerTeam> findByTeam(Team team) {
        return this.playerTeamRepository.findAll().stream().filter(playerTeam -> playerTeam.getTeam() == team).collect(Collectors.toList());
    }

    public List<PlayerTeam> findByPlayer(Player player) {
        return this.playerTeamRepository.findAll().stream().filter(playerTeam -> playerTeam.getPlayer() == player).collect(Collectors.toList());
    }
}
