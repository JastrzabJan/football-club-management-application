package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Training extends AbstractEntity {

    @ManyToOne
    @NotNull(message = "Team must be assigned to training")
    private Team team;

    @ManyToOne
    @NotNull(message = "Coach cannot be empty")
    private Coach coach;

    @NotNull(message = "Training Date cannot be empty")
    private LocalDate trainingDate;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Training Type cannot be empty")
    private TrainingType trainingType;

    @OneToMany(mappedBy = "training")
    private Set<PlayerTrainingAttendance> playerTrainingAttendances;
}
