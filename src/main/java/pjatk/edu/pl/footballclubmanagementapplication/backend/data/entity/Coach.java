package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Qualifications qualifications;

    @ManyToMany(cascade = {CascadeType.ALL})
    private Set<Team> teams = new HashSet<>();

    @OneToMany(mappedBy = "coach")
    private Set<Training> trainings = new HashSet<>();
}
