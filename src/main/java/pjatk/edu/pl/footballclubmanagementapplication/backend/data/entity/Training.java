package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.Set;

@Entity
@Data
public class Training extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "id")
    private Coach coach;

    private Date trainingDate;

    @Enumerated(EnumType.STRING)
    private TrainingType trainingType;

    @OneToMany(mappedBy = "training")
    private Set<PlayerTrainingAttendance> playerTrainingAttendances;
}
