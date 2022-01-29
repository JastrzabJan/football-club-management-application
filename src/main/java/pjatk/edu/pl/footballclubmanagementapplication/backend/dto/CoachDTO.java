package pjatk.edu.pl.footballclubmanagementapplication.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Qualifications;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Role;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Team;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class CoachDTO {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @NotNull
    private Long id;

    @NotEmpty(message = "Email cannot be empty")
    @Size(min = 2, message = "Email must be at least 8 characters long")
    private String email;

    private String passwordHash;
    private String password;

    @NotNull(message = "Coach role cannot be empty")
    private Role role;

    @NotEmpty(message = "Coach name cannot be empty")
    @Size(min = 3, max = 50, message = "Name must be at least 3 characters long")
    private String name;

    @NotEmpty(message = "Surname cannot be empty")
    @Size(min = 3, max = 50, message = "Surname must be at least 3 characters long")
    private String surname;

    @NotNull(message = "Coach birth date cannot be empty")
    private LocalDate birthDate;

    @NotEmpty(message = "Coach phone Number cannot be empty")
    @Size(min = 6, max = 15, message = "Phone number size must be between 6 and 15")
    private String phoneNumber;

    @NotEmpty(message = "Coach address cannot be empty")
    @Size(max = 255, message = "Coach address cannot be longer than 255 characters")
    private String address;

    private List<String> teamNames;
    private User user;

    private Set<Team> teams;

    @NotNull(message = "Coach qualifications cannot be empty")
    private Qualifications qualifications;
}
