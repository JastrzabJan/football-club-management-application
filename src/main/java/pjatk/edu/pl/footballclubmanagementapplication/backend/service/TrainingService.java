package pjatk.edu.pl.footballclubmanagementapplication.backend.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Player;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Training;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.TrainingRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingService implements CrudService<Training> {

    private final TrainingRepository trainingRepository;

    public TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public JpaRepository<Training, Long> getRepository() {
        return this.trainingRepository;
    }

    @Override
    public Training save(Training entity) {
        return getRepository().save(entity);
    }

    @Override
    public void delete(Training entity) {
        getRepository().delete(entity);
    }

    @Override
    public Training createNew(User currentUser) {
        return new Training();
    }

    public List<Training> getAllTrainings() {
        return getRepository().findAll();
    }

    public List<Training> getAllTrainingForPlayer(Player player){
        return getAllTrainings().stream().filter(training -> player.getTeams().contains(training.getTeam())).collect(Collectors.toList());
    }
}