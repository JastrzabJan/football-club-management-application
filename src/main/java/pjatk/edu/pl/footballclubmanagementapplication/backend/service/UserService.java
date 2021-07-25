package pjatk.edu.pl.footballclubmanagementapplication.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.UserRepository;

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

    @Override
    public User save(User currentUser, User userEntity) {
        return getRepository().saveAndFlush(userEntity);
    }

    @Override
    public void delete(User currentUser, User entity) {

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
}
