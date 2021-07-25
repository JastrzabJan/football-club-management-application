package pjatk.edu.pl.footballclubmanagementapplication.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByEmailIgnoreCase(String username);

//    User findBy(Pageable pageable);

}
