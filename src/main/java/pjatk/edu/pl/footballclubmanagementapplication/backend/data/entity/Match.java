package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Match extends AbstractEntity {

    private String opponentTeam;
    private String place;
    private int goalsHome;
    private int goalsAway;
    private LocalDate gameDay;

    @ManyToOne
    private Team team;

}
