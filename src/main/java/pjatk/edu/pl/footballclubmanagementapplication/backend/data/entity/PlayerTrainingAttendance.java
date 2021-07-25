package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Data
public class PlayerTrainingAttendance extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "id")
    private Training training;

    private Boolean AttendanceConfirmed;
    private Boolean AttendanceReal;
    private Date date;
}
