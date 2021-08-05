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
public class PlayerTrainingAttendance extends AbstractEntity{

    @ManyToOne
    private Player player;

    @ManyToOne
    private Training training;

    private Boolean AttendanceConfirmed;
    private Boolean AttendanceReal;
    private Date date;
}
