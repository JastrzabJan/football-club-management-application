package pjatk.edu.pl.footballclubmanagementapplication.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;
import pjatk.edu.pl.footballclubmanagementapplication.backend.dto.PlayerDTO;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.SearchUtils.matchesTerm;


@Service
public class UserService implements CrudService<User>{

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public JpaRepository<User, Long> getRepository() {
        return userRepository;
    }

    public User save(User userEntity) {
        return getRepository().save(userEntity);
    }

    @Override
    public void delete(User entity) {
        getRepository().delete(entity);
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public User load(long id) {
        return null;
    }

    @Override
    public User createNew(User currentUser) {
        return null;
    }

    public List<User> getAllUsersWithFilter(String filterText) {
        return userRepository
                .findAll()
                .stream()
                .filter(e -> {
                    if (filterText.isEmpty()) {
                        return true;
                    } else {
                        return matchesTerm(e.getEmail(), filterText);
                    }
                }).collect(Collectors.toList());
    }
}
