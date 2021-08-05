package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class PlayerTeamId implements Serializable {


    @NotNull
    private Long playerId;
    @NotNull
    private Long teamId;

    // Has to be overwritten for Composite Key
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        PlayerTeamId that = (PlayerTeamId) o;
        return Objects.equals(playerId, that.playerId) &&
                Objects.equals(teamId, that.teamId);
    }

    // Has to be overwritten for Composite Key
    @Override
    public int hashCode() {
        return Objects.hash(playerId, teamId);
    }

}
