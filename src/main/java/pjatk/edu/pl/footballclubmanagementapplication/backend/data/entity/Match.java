package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Match extends AbstractEntity {

    @NotEmpty(message = "Opponent Team cannot be empty")
    private String opponentTeam;

    @NotEmpty(message = "Place cannot be empty")
    private String place;

    private int goalsHome;
    private int goalsAway;

    @NotNull(message = "Game Date cannot be empty")
    private LocalDateTime gameDate;

    @ManyToOne
    private Team team;

}
