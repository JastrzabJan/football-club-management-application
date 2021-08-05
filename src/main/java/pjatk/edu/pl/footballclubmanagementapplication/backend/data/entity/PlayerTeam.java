package pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PlayerTeam {

    @EmbeddedId
    private PlayerTeamId playerTeamId;

    @ManyToOne
    @MapsId("playerId")
    private Player player;

    @ManyToOne
    @MapsId("teamId")
    private Team team;

    private Date contractFrom;
    private Date contractTo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        PlayerTeam that = (PlayerTeam) o;
        return Objects.equals(player, that.player) &&
                Objects.equals(team, that.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, team);
    }

    @Override
    public String toString(){
        return team.getName();
    }
}
