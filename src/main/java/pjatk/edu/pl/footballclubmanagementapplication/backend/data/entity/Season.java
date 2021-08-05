package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Season extends AbstractEntity {

    private Date startDate;
    private Date endDate;

    @OneToMany(mappedBy = "season")
    private Set<TeamLeagueSeason> teamLeagueSeasons;
}
