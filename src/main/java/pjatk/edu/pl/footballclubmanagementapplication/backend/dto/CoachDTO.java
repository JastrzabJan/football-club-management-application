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

    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Email must be at least 8 characters long")
    private String email;

    private String passwordHash;

    @NotNull
    private String password;

    @NotNull(message = "Coach role cannot be empty")
    private Role role;

    @NotBlank(message = "Coach name cannot be blank")
    @Size(max = 255)
    private String name;

    @NotEmpty
    @Size(min = 3, message = "Surname must be at least 3 characters long")
    private String surname;

    @NotNull(message = "Coach birth date cannot be empty")
    private LocalDate birthDate;

    @NotBlank(message = "Player phone Number cannot be blank")
    @Size(min = 6, max = 50, message = "Wrong phoneNumber size")
    private String phoneNumber;

    @NotBlank(message = "Coach address cannot be blank")
    @Size(max = 255)
    private String address;

    private List<String> teamNames;
    private User user;
    private Set<Team> teams;

    @NotNull(message = "Coach qualifications cannot be empty")
    private Qualifications qualifications;
}
