package pjatk.edu.pl.footballclubmanagementapplication.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
