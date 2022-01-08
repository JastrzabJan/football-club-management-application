package pjatk.edu.pl.footballclubmanagementapplication.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Season;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.SeasonRepository;

import java.util.List;

@Service
public class SeasonService implements CrudService<Season> {

    private final SeasonRepository seasonRepository;

    @Autowired
    public SeasonService(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    @Override
    public JpaRepository<Season, Long> getRepository() {
        return this.seasonRepository;
    }

    @Override
    public Season save(Season entity) {
        return getRepository().save(entity);
    }

    @Override
    public void delete(Season entity) {
        getRepository().delete(entity);
    }

    @Override
    public Season createNew(User currentUser) {
        return new Season();
    }

    public List<Season> getAllSeasons() {
        return getRepository().findAll();
    }

    public Season getNewestSeason(){
        return getRepository().findAll().stream().findFirst().get();
    }
}
