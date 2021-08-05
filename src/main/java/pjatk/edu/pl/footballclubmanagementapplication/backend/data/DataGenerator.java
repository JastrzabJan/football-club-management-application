package pjatk.edu.pl.footballclubmanagementapplication.backend.data;

import com.github.javafaker.Faker;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Player;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Position;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Role;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.PlayerRepository;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneId;

@SpringComponent
public class DataGenerator {

    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataGenerator(UserRepository userRepository, PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void populateTestData() {
        Faker faker = new Faker();
        User adminUser = addUser(userRepository, passwordEncoder, "admin@admin.com", "admin", Role.Admin);
        User managerUser = addUser(userRepository, passwordEncoder, "manager@manager.com", "manager", Role.Manager);
        User coachUser = addUser(userRepository, passwordEncoder, "coach@coach.com", "coach", Role.Coach);
        User playerUser = addUser(userRepository, passwordEncoder, "player@player.com", "player", Role.Player);

        for(int i = 0; i<100; i ++){
            User user = addUser(userRepository, passwordEncoder, faker.bothify("????##@gmail.com"), faker.name().firstName(), Role.Player);
            Player player = addPlayer(playerRepository, faker.name().firstName(),
                    faker.name().lastName(),
                    faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    faker.address().fullAddress(),
                    faker.idNumber().valid(),
                    faker.phoneNumber().phoneNumber(),
                    faker.number().randomDigit());
            player.setUser(user);
            playerRepository.save(player);
            user.setPlayer(player);
            userRepository.save(user);
        }
    }

    public Player addPlayer(PlayerRepository playerRepository,String name, String surname, LocalDate date, String address, String pesel, String phoneNumber, int number){
        return playerRepository.save(createPlayer(name, surname, date, address, pesel, phoneNumber, number));
    }

    private Player createPlayer(String name, String surname, LocalDate date, String address, String pesel, String phoneNumber, int number){
        Player player = new Player();
        player.setName(name);
        player.setSurname(surname);
        player.setBirthDate(date);
        player.setAddress(address);
        player.setPESEL(pesel);
        player.setPhoneNumber(phoneNumber);
        player.setPosition(Position.STRIKER);
        player.setNumber(number);
        return player;
    }

    public User addUser(UserRepository userRepository, PasswordEncoder passwordEncoder, String email, String password, Role role) {
        return userRepository.save(createUser(email,
                passwordEncoder.encode(password), role));
    }


    private User createUser(String email,  String passwordHash, Role role) {
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        user.setRole(role);
        return user;
    }
}
