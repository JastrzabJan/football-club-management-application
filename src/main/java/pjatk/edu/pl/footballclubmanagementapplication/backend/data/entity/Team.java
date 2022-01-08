package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Team extends AbstractEntity {


    private String name;
    private String teamClass;

    @ManyToOne
    private Coach coach;

    @OneToMany(mappedBy = "team")
    private Set<TeamLeagueSeason> teamLeagueSeasons;

    @OneToMany(mappedBy = "team")
    private Set<Match> matches;

    @ManyToMany(
            mappedBy = "teams", fetch = FetchType.EAGER)
    private Set<Player> players = new HashSet<>();

    @OneToMany(mappedBy = "team")
    private Set<Training> trainings = new HashSet<>();


    public String toString() {
        return name;
    }
}
