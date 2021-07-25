package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Data
public class Match extends AbstractEntity {

    private String opponent;
    private String place;
    private int goalsHome;
    private int goalsAway;
    private Date gameDay;
    private boolean host;

    @ManyToOne
    @JoinColumn(name = "id")
    private Team team;


}
