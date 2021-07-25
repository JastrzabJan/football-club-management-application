package pjatk.edu.pl.footballclubmanagementapplication.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Player;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.PlayerRepository;

@Service
public class PlayerService implements CrudService<Player>{

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public JpaRepository getRepository() {
        return this.playerRepository;
    }

    @Override
    public Player save(User currentUser, Player entity) {
        return this.playerRepository.save(entity);
    }

    @Override
    public void delete(User currentUser, Player entity) {
        this.playerRepository.delete(entity);
    }

    @Override
    public Player createNew(User currentUser) {
        return new Player();
    }
}
