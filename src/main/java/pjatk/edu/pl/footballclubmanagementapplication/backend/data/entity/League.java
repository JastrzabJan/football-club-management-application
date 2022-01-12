package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class League extends AbstractEntity{

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @OneToMany(mappedBy = "league")
    private Set<TeamLeagueSeason> teamLeagueSeasons;
 }
