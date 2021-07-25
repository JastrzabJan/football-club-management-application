package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Player extends User {

    @Enumerated(EnumType.STRING)
    private Position position;

    private Date birthDate;
    private Long PESEL;
    private String phoneNumber;
    private String Address;
    private int Number;

    @OneToMany(
            mappedBy = "player",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<PlayerTeam> playerTeams = new HashSet<>();

    @OneToMany(mappedBy = "player")
    private Set<PlayerTrainingAttendance> playerTrainingAttendances;

}
