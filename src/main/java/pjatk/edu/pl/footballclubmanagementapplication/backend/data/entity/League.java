package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class League extends AbstractEntity{

    private String name;

    @OneToMany(mappedBy = "league")
    private Set<TeamLeagueSeason> teamLeagueSeasons;
 }
