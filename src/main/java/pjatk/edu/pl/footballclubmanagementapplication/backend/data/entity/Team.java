package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;


import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Team extends AbstractEntity {


    private String name;
    private String teamClass;

    @ManyToMany(mappedBy = "teams")
    private Set<Coach> coaches = new HashSet<>();

    @OneToMany(mappedBy = "team")
    private Set<TeamLeagueSeason> teamLeagueSeasons;

    @OneToMany(mappedBy = "team")
    private Set<Match> matches;

    @OneToMany(
            mappedBy = "team",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<PlayerTeam> players = new HashSet<>();

    @OneToMany(mappedBy = "team")
    private Set<Training> trainings = new HashSet<>();
}
