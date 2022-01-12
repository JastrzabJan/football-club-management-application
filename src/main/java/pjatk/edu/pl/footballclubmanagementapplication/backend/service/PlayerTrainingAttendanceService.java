package pjatk.edu.pl.footballclubmanagementapplication.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Player;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.PlayerTrainingAttendance;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Training;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.PlayerTrainingAttendanceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerTrainingAttendanceService implements CrudService<PlayerTrainingAttendance> {

    private final PlayerTrainingAttendanceRepository playerTrainingAttendanceRepository;

    @Autowired
    public PlayerTrainingAttendanceService(PlayerTrainingAttendanceRepository playerTrainingAttendanceRepository) {
        this.playerTrainingAttendanceRepository = playerTrainingAttendanceRepository;
    }

    @Override
    public JpaRepository<PlayerTrainingAttendance, Long> getRepository() {
        return this.playerTrainingAttendanceRepository;
    }

    @Override
    public PlayerTrainingAttendance createNew(User currentUser) {
        return null;
    }

    public List<PlayerTrainingAttendance> getAllAttendances() {
        return getRepository().findAll();
    }

    public List<PlayerTrainingAttendance> getAllAttendancesForPlayer(Player player) {
        return getRepository().findAll().stream().filter(pta ->
                pta.getPlayer().equals(player)).collect(Collectors.toList());
    }

    public List<PlayerTrainingAttendance> getAllAttendances(Training training) {
        return getRepository().findAll().stream().filter(pta -> pta.getTraining().equals(training)).collect(Collectors.toList());
    }

    public PlayerTrainingAttendance getAttendanceForPlayerAndTraining(Training training, Player player) {
        return getRepository().findAll().stream().filter(pta ->
                pta.getTraining().equals(training) && pta.getPlayer().equals(player)).findFirst().orElse(null);
    }
}
