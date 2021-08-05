package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TeamLeagueSeason extends AbstractEntity{

    @ManyToOne
    private Team team;

    @ManyToOne
    private Season season;

    @ManyToOne
    private League league;

}
