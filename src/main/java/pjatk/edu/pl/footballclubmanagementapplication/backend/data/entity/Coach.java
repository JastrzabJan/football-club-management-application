package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "Coach name cannot be empty")
    @Size(max = 255, message = "Address Field length cannot exceed 255 characters")
    private String name;

    @NotEmpty(message = "Coach surname cannot be empty")
    @Size(max = 255, message = "Address Field length cannot exceed 255 characters")
    private String surname;
    private LocalDate birthDate;

    @NotEmpty(message = "Coach Phone Number cannot be empty")
    @Size(min = 6, max = 50, message = "Wrong Phone Number size")
    private String phoneNumber;

    @NotEmpty(message = "Coach address cannot be empty")
    @Size(max = 255, message = "Address Field length cannot exceed 255 characters")
    private String Address;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Qualifications qualifications;

    @OneToMany(mappedBy = "coach", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE
            , CascadeType.REFRESH
            , CascadeType.REMOVE})
    private Set<Team> teams;

    @OneToMany(mappedBy = "coach")
    private Set<Training> trainings;


    public String toString() {
        return name + " " + surname;
    }
}
