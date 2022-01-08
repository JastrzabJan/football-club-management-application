package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Season extends AbstractEntity {

    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;

    @OneToMany(mappedBy = "season")
    private Set<TeamLeagueSeason> teamLeagueSeasons;


    public String getName() {
        return startDate.getYear() + "/" + endDate.getYear();
    }

    @Override
    public String toString() {
        return startDate.getYear() + "/" + endDate.getYear();
    }
}
