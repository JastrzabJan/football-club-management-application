package pjatk.edu.pl.footballclubmanagementapplication.backend.data;

import com.github.javafaker.Faker;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Coach;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.League;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Player;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Position;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Qualifications;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Role;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Season;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Team;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.TeamLeagueSeason;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.CoachRepository;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.LeagueRepository;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.PlayerRepository;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.SeasonRepository;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.TeamLeagueSeasonRepository;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.TeamRepository;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

@SpringComponent
public class DataGenerator {

    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final SeasonRepository seasonRepository;
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;
    private final CoachRepository coachRepository;
    private final TeamLeagueSeasonRepository teamLeagueSeasonRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataGenerator(UserRepository userRepository, PlayerRepository playerRepository, SeasonRepository seasonRepository, LeagueRepository leagueRepository, TeamRepository teamRepository, CoachRepository coachRepository, TeamLeagueSeasonRepository teamLeagueSeasonRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
        this.seasonRepository = seasonRepository;
        this.leagueRepository = leagueRepository;
        this.teamRepository = teamRepository;
        this.coachRepository = coachRepository;
        this.teamLeagueSeasonRepository = teamLeagueSeasonRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void populateTestData() {
        Faker faker = new Faker();
        User adminUser = addUser(userRepository, passwordEncoder, "admin@admin.com", "admin", Role.Admin);
        User managerUser = addUser(userRepository, passwordEncoder, "manager@manager.com", "manager", Role.Manager);
        User coachUser = addUser(userRepository, passwordEncoder, "coach@coach.com", "coach", Role.Coach);
        User playerUser = addUser(userRepository, passwordEncoder, "player@player.com", "player", Role.Player);

        Season season = new Season();
        season.setStartDate(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        season.setEndDate(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        seasonRepository.save(season);
        League league = new League();
        league.setName("Test League");
        leagueRepository.save(league);

        Team team = addTeam(teamRepository, seasonRepository, leagueRepository, teamLeagueSeasonRepository, "Fc Test Club", "1st class", season, league);
        Team team2 = addTeam(teamRepository, seasonRepository, leagueRepository, teamLeagueSeasonRepository, "Fc Test Club 2", "2nd class", season, league);

        Set<Player> testPlayers = new HashSet<>();
        Set<Coach> testCoaches = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            User user = addUser(userRepository, passwordEncoder, faker.bothify("????##@gmail.com"), faker.name().firstName(), Role.Player);
            Player player = addPlayer(playerRepository, faker.name().firstName(),
                    faker.name().lastName(),
                    faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    faker.address().fullAddress(),
                    faker.idNumber().valid(),
                    faker.phoneNumber().phoneNumber(),
                    faker.number().randomDigit(),
                    team);
            player.setUser(user);
            playerRepository.save(player);
            user.setPlayer(player);
            userRepository.save(user);
            testPlayers.add(player);
        }
        for (int i = 0; i < 2; i++) {
            User user = addUser(userRepository, passwordEncoder, faker.bothify("????##@gmail.com"), faker.name().firstName(), Role.Coach);
            Coach coach = addCoach(coachRepository, faker.name().firstName(),
                    faker.name().lastName(),
                    faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    faker.address().fullAddress(),
                    faker.phoneNumber().phoneNumber(),
                    team);
            coach.setUser(user);
            coachRepository.save(coach);
            user.setCoach(coach);
            userRepository.save(user);
            testCoaches.add(coach);
        }
        team.setCoach(testCoaches.stream().findFirst().get());
        team.setPlayers(testPlayers);
        teamRepository.save(team);

//        addTeamLeagueSeason(teamLeagueSeasonRepository, team2, league, season);

    }

    public Player addPlayer(PlayerRepository playerRepository, String name, String surname, LocalDate date, String address, String pesel, String phoneNumber, int number, Team team) {
        return playerRepository.save(createPlayer(name, surname, date, address, pesel, phoneNumber, number, team));
    }

    public Coach addCoach(CoachRepository coachRepository, String name, String surname, LocalDate date, String address, String phoneNumber, Team team) {
        return coachRepository.save(createCoach(name, surname, date, address, phoneNumber, team));
    }

    private Coach createCoach(String name, String surname, LocalDate date, String address, String phoneNumber, Team team) {
        Set<Team> teams = new HashSet<>();
        teams.add(team);
        Coach coach = new Coach();
        coach.setName(name);
        coach.setSurname(surname);
        coach.setBirthDate(date);
        coach.setAddress(address);
        coach.setPhoneNumber(phoneNumber);
        coach.setQualifications(Qualifications.T1);
        coach.setTeams(teams);
        return coach;
    }

    private Player createPlayer(String name, String surname, LocalDate date, String address, String pesel, String phoneNumber, int number, Team team) {
        Set<Team> teams = new HashSet<>();
        teams.add(team);
        Player player = new Player();
        player.setName(name);
        player.setSurname(surname);
        player.setBirthDate(date);
        player.setPESEL(pesel);
        player.setAddress(address);
        player.setPhoneNumber(phoneNumber);
        player.setPosition(Position.STRIKER);
        player.setTeams(teams);
        return player;
    }

    public User addUser(UserRepository userRepository, PasswordEncoder passwordEncoder, String email, String password, Role role) {
        return userRepository.save(createUser(email,
                passwordEncoder.encode(password), role));
    }


    private User createUser(String email, String passwordHash, Role role) {
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        user.setRole(role);
        return user;
    }

    public Team addTeam(TeamRepository teamRepository, SeasonRepository seasonRepository, LeagueRepository leagueRepository, TeamLeagueSeasonRepository teamLeagueSeasonRepository,
                        String teamName, String teamClass, Season season, League league) {
        Team team = new Team();
        team.setName(teamName);
        team.setTeamClass(teamClass);
        teamRepository.save(team);
        TeamLeagueSeason teamLeagueSeason = new TeamLeagueSeason();
        teamLeagueSeason.setTeam(team);
        teamLeagueSeason.setSeason(season);
        teamLeagueSeason.setLeague(league);
        teamLeagueSeasonRepository.save(teamLeagueSeason);
        Set<TeamLeagueSeason> teamLeagueSeasons = new HashSet<>();
        teamLeagueSeasons.add(teamLeagueSeason);
        season.setTeamLeagueSeasons(teamLeagueSeasons);
        team.setTeamLeagueSeasons(teamLeagueSeasons);
        league.setTeamLeagueSeasons(teamLeagueSeasons);
        seasonRepository.save(season);
        leagueRepository.save(league);
        return teamRepository.save(team);
    }

    public TeamLeagueSeason addTeamLeagueSeason(TeamLeagueSeasonRepository teamLeagueSeasonRepository, Team team, League league, Season season) {
        TeamLeagueSeason teamLeagueSeason = new TeamLeagueSeason();
        teamLeagueSeason.setLeague(league);
        teamLeagueSeason.setSeason(season);
        teamLeagueSeason.setTeam(team);
        return teamLeagueSeasonRepository.save(teamLeagueSeason);
    }
}
