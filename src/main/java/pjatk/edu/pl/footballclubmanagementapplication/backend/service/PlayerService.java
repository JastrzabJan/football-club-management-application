package pjatk.edu.pl.footballclubmanagementapplication.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Player;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Role;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;
import pjatk.edu.pl.footballclubmanagementapplication.backend.dto.PlayerDTO;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.PlayerRepository;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.SearchUtils.matchesTerm;

@Service
public class PlayerService implements CrudService<Player> {

    private final PlayerRepository playerRepository;
    private final UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, UserService userService, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JpaRepository<Player, Long> getRepository() {
        return this.playerRepository;
    }

    @Override
    public Player save(Player entity) {
        return getRepository().save(entity);
    }

    @Override
    public void delete(Player entity) {
        getRepository().delete(entity);
    }

    @Override
    public Player createNew(User currentUser) {
        return new Player();
    }

    public List<Player> findAll() {
        return getRepository().findAll();
    }

    public List<PlayerDTO> getAllPlayersDTO() {
        return playerRepository.findAll().stream().map(this::convertToPlayerDTO).collect(Collectors.toList());
    }

    public List<PlayerDTO> getAllPlayersWithFilter(String filterText) {
        return playerRepository
                .findAll()
                .stream()
                .map(this::convertToPlayerDTO)
                .filter(e -> {
                    if (filterText.isEmpty()) {
                        return true;
                    } else {
                        return matchesTerm(e.getName(), filterText) || matchesTerm(e.getSurname(), filterText);
                    }
                }).collect(Collectors.toList());
    }

    private PlayerDTO convertToPlayerDTO(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setUser(player.getUser());
        playerDTO.setId(player.getId());
        playerDTO.setEmail(player.getUser().getEmail());
        playerDTO.setPasswordHash(player.getUser().getPasswordHash());
        playerDTO.setRole(player.getUser().getRole());
        playerDTO.setName(player.getName());
        playerDTO.setSurname(player.getSurname());
        playerDTO.setPESEL(player.getPESEL());
        playerDTO.setPhoneNumber(player.getPhoneNumber());
        playerDTO.setAddress(player.getAddress());
        playerDTO.setPosition(player.getPosition());
        playerDTO.setBirthDate(player.getBirthDate());
        playerDTO.setTeamNames(player.getTeams().stream().map(playerTeam -> playerTeam.getName()).collect(Collectors.toList()));
        playerDTO.setTeams(player.getTeams());
        playerDTO.setPlayerTrainingAttendances(player.getPlayerTrainingAttendances());
        return playerDTO;
    }


    public void save(PlayerDTO playerDTO) {

        if (playerDTO.getId() != null) {
            Player player = playerRepository.findById(playerDTO.getId()).get();
            player.setName(playerDTO.getName());
            player.setSurname(playerDTO.getSurname());
            player.setTeams(playerDTO.getTeams());
            player.setPESEL(playerDTO.getPESEL());
            player.setPlayerTrainingAttendances(playerDTO.getPlayerTrainingAttendances());
            player.setNumber(playerDTO.getNumber());
            player.setBirthDate(playerDTO.getBirthDate());
            player.setAddress(playerDTO.getAddress());
            player.setPhoneNumber(playerDTO.getPhoneNumber());
            playerRepository.save(player);
            User user = playerDTO.getUser();
            user.setEmail(playerDTO.getEmail());
            if (playerDTO.getPassword().length() > 3) {
                user.setPasswordHash(passwordEncoder.encode(playerDTO.getPassword()));
            } else {
                user.setPasswordHash(playerDTO.getPasswordHash());
            }
            user.setRole(playerDTO.getRole());
            userService.save(user);
        } else {
            User user = new User();
            user.setEmail(playerDTO.getEmail());
            user.setPasswordHash(passwordEncoder.encode(playerDTO.getPassword()));
            user.setRole(Role.Player);
            userService.save(user);
            Player player = new Player();
            player.setName(playerDTO.getName());
            player.setSurname(playerDTO.getSurname());
            player.setTeams(playerDTO.getTeams());
            player.setPESEL(playerDTO.getPESEL());
            player.setPlayerTrainingAttendances(playerDTO.getPlayerTrainingAttendances());
            player.setNumber(playerDTO.getNumber());
            player.setPosition(playerDTO.getPosition());
            player.setBirthDate(playerDTO.getBirthDate());
            player.setAddress(playerDTO.getAddress());
            player.setPhoneNumber(playerDTO.getPhoneNumber());
            player.setUser(user);
            playerRepository.save(player);
            user.setPlayer(player);
            userService.getRepository().save(user);

        }
    }

    public void delete(PlayerDTO playerDTO) {
        Player player = Optional.of(playerRepository.findById(playerDTO.getId())).get().orElseThrow(NoSuchElementException::new);
        playerRepository.delete(player);
    }
}
