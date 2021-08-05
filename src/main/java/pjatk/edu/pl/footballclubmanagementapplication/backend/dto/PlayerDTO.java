package pjatk.edu.pl.footballclubmanagementapplication.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.PlayerTeam;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.PlayerTrainingAttendance;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Position;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Role;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Team;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PlayerDTO {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Long id;
    private String email;
    private String passwordHash;
    private String password;
    private Role role;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String PESEL;
    private String phoneNumber;
    private String address;
    private Integer number;
    private Position position;
    private List<String> teamNames;
    private Set<PlayerTeam> playerTeams;
    private User user;
    private Set<PlayerTrainingAttendance> playerTrainingAttendances;
    private Set<Team> teams;
}
