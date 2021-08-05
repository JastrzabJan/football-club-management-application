package pjatk.edu.pl.footballclubmanagementapplication.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Coach;

public interface CoachRepository extends JpaRepository<Coach, Long> {
}
