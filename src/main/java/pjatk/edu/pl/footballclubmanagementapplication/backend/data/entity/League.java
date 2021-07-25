package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Data
public class League extends AbstractEntity{

    private String name;

    @OneToMany(mappedBy = "league")
    private Set<TeamLeagueSeason> teamLeagueSeasons;
 }
