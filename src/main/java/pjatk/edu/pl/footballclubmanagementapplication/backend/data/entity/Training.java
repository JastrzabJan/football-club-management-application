package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Training extends AbstractEntity{

    @ManyToOne
    private Team team;

    @ManyToOne
    private Coach coach;

    private Date trainingDate;

    @Enumerated(EnumType.STRING)
    private TrainingType trainingType;

    @OneToMany(mappedBy = "training")
    private Set<PlayerTrainingAttendance> playerTrainingAttendances;
}
