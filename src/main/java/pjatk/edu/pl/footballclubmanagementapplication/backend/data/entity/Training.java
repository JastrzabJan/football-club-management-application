package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @NotNull(message = "Training Start cannot be empty")
    private LocalDateTime trainingStart;

    @NotNull(message = "Training End cannot be empty")
    private LocalDateTime trainingEnd;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Training Type cannot be empty")
    private TrainingType trainingType;

    @OneToMany(mappedBy = "training", fetch = FetchType.EAGER)
    private Set<PlayerTrainingAttendance> playerTrainingAttendances;
}
