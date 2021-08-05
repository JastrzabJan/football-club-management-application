package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Match extends AbstractEntity {

    private String opponent;
    private String place;
    private int goalsHome;
    private int goalsAway;
    private Date gameDay;
    private boolean host;

    @ManyToOne
    private Team team;


}
