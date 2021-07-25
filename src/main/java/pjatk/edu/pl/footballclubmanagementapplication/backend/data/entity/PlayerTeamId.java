package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class PlayerTeamId implements Serializable {

    private Long playerId;
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
