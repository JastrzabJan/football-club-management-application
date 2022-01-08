package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Coach extends AbstractEntity {


    //    @OneToOne(cascade = CascadeType.ALL)
//    @JoinTable(name = "coach_user",
//            joinColumns = {@JoinColumn(name = "coach_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
//    )

    @NotBlank(message = "Coach name cannot be blank")
    @Size(max = 255)
    private String name;

    @NotBlank(message = "Coach surname cannot be blank")
    @Size(max = 255)
    private String surname;
    private LocalDate birthDate;

    @NotBlank(message = "Coach Phone Number cannot be blank")
    @Size(min = 6, max = 50, message = "Wrong Phone Number size")
    private String phoneNumber;

    @NotBlank(message = "Coach address cannot be blank")
    @Size(max = 255)
    private String Address;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Qualifications qualifications;

    @OneToMany(mappedBy = "coach")
    private Set<Team> teams = new HashSet<>();

    @OneToMany(mappedBy = "coach")
    private Set<Training> trainings = new HashSet<>();


    public String toString(){
        return name +" " + surname;
    }
}
