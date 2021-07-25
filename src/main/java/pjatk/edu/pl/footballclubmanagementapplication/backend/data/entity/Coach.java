package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Coach extends User {

    @Enumerated(EnumType.STRING)
    private Qualifications qualifications;

    @ManyToMany(mappedBy = "coaches", cascade = {CascadeType.ALL})
    private Set<Team> teams = new HashSet<>();

    @OneToMany(mappedBy = "coach")
    private Set<Training> trainings = new HashSet<>();
}
