package pjatk.edu.pl.footballclubmanagementapplication.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Season;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.SeasonRepository;

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
    public Season save(User currentUser, Season entity) {
        return this.seasonRepository.save(entity);
    }

    @Override
    public void delete(User currentUser, Season entity) {
        this.seasonRepository.delete(entity);
    }

    @Override
    public Season createNew(User currentUser) {
        return new Season();
    }
}
