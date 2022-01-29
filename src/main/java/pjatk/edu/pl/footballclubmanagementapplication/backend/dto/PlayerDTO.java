package pjatk.edu.pl.footballclubmanagementapplication.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.PlayerTrainingAttendance;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Position;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Role;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Team;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PlayerDTO {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @NotNull
    private Long id;

    @NotEmpty(message = "Email cannot be empty")
    @Size(min = 8, message = "Email must be at least 8 characters long")
    private String email;

    private String passwordHash;
    private String password;

    @NotNull(message = "Role cannot be empty")
    private Role role;

    @NotEmpty(message = "Player name cannot be empty")
    @Size(min = 3, max = 50, message = "Name must be at least 3 characters long")
    private String name;

    @NotEmpty(message = "Surname cannot be empty")
    @Size(min = 3, max = 50, message = "Surname must be at least 3 characters long")
    private String surname;

    @NotNull(message = "Player birth date cannot be empty")
    private LocalDate birthDate;

    @NotEmpty(message = "PESEL cannot be empty")
    @Size(min = 9, message = "PESEL must be at least 9 characters long")
    private String PESEL;

    @NotEmpty(message = "Player phone Number cannot be empty")
    @Size(min = 6, max = 15, message = "Phone number size must be between 6 and 15")
    private String phoneNumber;

    @NotEmpty(message = "Player address cannot be empty")
    @Size(max = 255, message = "Coach address cannot be longer than 255 characters")
    private String address;

    private Integer number;

    @NotNull(message = "Player must have assigned Position")
    private Position position;

    private List<String> teamNames;

    private User user;

    private Set<PlayerTrainingAttendance> playerTrainingAttendances;

    private Set<Team> teams;
}
