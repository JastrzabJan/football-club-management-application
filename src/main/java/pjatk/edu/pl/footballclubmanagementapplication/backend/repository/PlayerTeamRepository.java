package pjatk.edu.pl.footballclubmanagementapplication.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.PlayerTeam;import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.PlayerTeamId;

public interface PlayerTeamRepository extends JpaRepository<PlayerTeam, Long> {

}
