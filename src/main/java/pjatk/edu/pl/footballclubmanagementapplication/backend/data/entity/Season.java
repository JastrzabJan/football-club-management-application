package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.Set;

@Entity
@Data
public class Season extends AbstractEntity {

    private Date startDate;
    private Date endDate;

    @OneToMany(mappedBy = "season")
    private Set<TeamLeagueSeason> teamLeagueSeasons;
}
