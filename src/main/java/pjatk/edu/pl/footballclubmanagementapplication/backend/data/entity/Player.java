package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Player extends AbstractEntity {

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank(message = "Player name cannot be blank")
    @Size(max = 255)
    private String name;

    @NotBlank(message = "Player surname cannot be blank")
    @Size(max = 255)
    private String surname;

    @NotNull(message = "Player position cannot be null")
    @Enumerated(EnumType.STRING)
    private Position position;

    private LocalDate birthDate;

    @NotBlank(message = "Player PESEL cannot be null")
    @Size(min = 8, max = 15, message = "Wrong PESEL size")
    private String PESEL;

    @NotBlank(message = "Player phone Number cannot be blank")
    @Size(min = 6, max = 50, message = "Wrong phoneNumber size")
    private String phoneNumber;

    @NotBlank(message = "Player address cannot be blank")
    @Size(max = 255)
    private String Address;

    private Integer Number;

    @ManyToMany(cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "player_teams",
            joinColumns = {@JoinColumn(name = "player_id")},
            inverseJoinColumns = {@JoinColumn(name = "team_id")}
    )
    private Set<Team> teams = new HashSet<>();

    @OneToMany(mappedBy = "player",
            fetch = FetchType.EAGER)
    private Set<PlayerTrainingAttendance> playerTrainingAttendances;

    @Override
    public String toString() {
        return name + " " + surname + " , POSITION: " + position;
    }
}
