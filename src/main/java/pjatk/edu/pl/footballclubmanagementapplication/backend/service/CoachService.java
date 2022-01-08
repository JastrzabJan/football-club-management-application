package pjatk.edu.pl.footballclubmanagementapplication.backend.service;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Coach;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Role;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;
import pjatk.edu.pl.footballclubmanagementapplication.backend.dto.CoachDTO;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.CoachRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.SearchUtils.matchesTerm;

@Service
public class CoachService implements CrudService<Coach> {

    private final CoachRepository coachRepository;
    private final UserService userService;
    private PasswordEncoder passwordEncoder;

    public CoachService(CoachRepository coachRepository, UserService userService, PasswordEncoder passwordEncoder) {
        this.coachRepository = coachRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JpaRepository<Coach, Long> getRepository() {
        return this.coachRepository;
    }

    @Override
    public Coach save(Coach entity) {
        return getRepository().save(entity);
    }

    @Override
    public void delete(Coach entity) {
        getRepository().delete(entity);
    }

    @Override
    public Coach createNew(User currentUser) {
        return new Coach();
    }

    public List<Coach> findAll() {
        return getRepository().findAll();
    }

    public List<CoachDTO> getAllCoachesDTO() {
        return getRepository().findAll().stream().map(this::convertToCoachDTO).collect(Collectors.toList());
    }

    public List<CoachDTO> getAllCoachesDTOwithFilter(String filterText) {
        return getRepository()
                .findAll()
                .stream()
                .map(this::convertToCoachDTO)
                .filter(e ->
                {
                    if (filterText.isEmpty()) {
                        return true;
                    } else {
                        return matchesTerm(e.getName(), filterText) || matchesTerm(e.getSurname(), filterText);
                    }
                }).collect(Collectors.toList());
    }

    private CoachDTO convertToCoachDTO(Coach coach) {
        CoachDTO coachDTO = new CoachDTO();
        coachDTO.setUser(coach.getUser());
        coachDTO.setQualifications(coach.getQualifications());
        coachDTO.setId(coach.getId());
        coachDTO.setEmail(coach.getUser().getEmail());
        coachDTO.setPasswordHash(coach.getUser().getPasswordHash());
        coachDTO.setRole(coach.getUser().getRole());
        coachDTO.setName(coach.getName());
        coachDTO.setSurname(coach.getSurname());
        coachDTO.setPhoneNumber(coach.getPhoneNumber());
        coachDTO.setAddress(coach.getAddress());
        coachDTO.setBirthDate(coach.getBirthDate());
        coachDTO.setTeamNames(coach.getTeams().stream().map(coachTeam -> coachTeam.getName()).collect(Collectors.toList()));
        coachDTO.setTeams(coach.getTeams());
        return coachDTO;
    }


    public void save(CoachDTO coachDTO) {

        if (coachDTO.getId() != null) {
            Coach coach = coachRepository.findById(coachDTO.getId()).get();
            coach.setName(coachDTO.getName());
            coach.setSurname(coachDTO.getSurname());
            coach.setTeams(coachDTO.getTeams());
            coach.setBirthDate(coachDTO.getBirthDate());
            coach.setAddress(coachDTO.getAddress());
            coach.setPhoneNumber(coachDTO.getPhoneNumber());
            coach.setQualifications(coachDTO.getQualifications());
            coachRepository.save(coach);
            User user = coachDTO.getUser();
            user.setEmail(coachDTO.getEmail());
            if (coachDTO.getPassword().length() > 3) {
                user.setPasswordHash(passwordEncoder.encode(coachDTO.getPassword()));
            } else {
                user.setPasswordHash(coachDTO.getPasswordHash());
            }
            user.setRole(coachDTO.getRole());
            userService.save(user);
        } else {
            User user = new User();
            user.setEmail(coachDTO.getEmail());
            user.setPasswordHash(passwordEncoder.encode(coachDTO.getPassword()));
            user.setRole(Role.Coach);
            userService.save(user);
            Coach coach = new Coach();
            coach.setName(coachDTO.getName());
            coach.setSurname(coachDTO.getSurname());
            coach.setTeams(coachDTO.getTeams());
            coach.setBirthDate(coachDTO.getBirthDate());
            coach.setAddress(coachDTO.getAddress());
            coach.setPhoneNumber(coachDTO.getPhoneNumber());
            coach.setQualifications(coachDTO.getQualifications());
            coach.setUser(user);
            coachRepository.save(coach);
            user.setCoach(coach);
            userService.getRepository().save(user);

        }
    }

    public void delete(CoachDTO coachDTO) {
        Coach coach = Optional.of(coachRepository.findById(coachDTO.getId())).get().orElseThrow(NoSuchElementException::new);
        coachRepository.delete(coach);
    }

}
