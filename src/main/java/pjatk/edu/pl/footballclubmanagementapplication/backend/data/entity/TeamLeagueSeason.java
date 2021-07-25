package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class TeamLeagueSeason extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "id")
    private Season season;

    @ManyToOne
    @JoinColumn(name = "id")
    private League league;

}
