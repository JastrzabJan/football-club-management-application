package pjatk.edu.pl.footballclubmanagementapplication.ui.views.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Coach;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Team;
import pjatk.edu.pl.footballclubmanagementapplication.backend.dto.CoachDTO;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.CoachService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.TeamService;
import pjatk.edu.pl.footballclubmanagementapplication.security.SecurityUtils;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.CoachesView;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.TeamsView;

public class TeamForm extends FormLayout {

    private TeamsView teamsView;
    private final TeamService teamService;
    private final CoachService coachService;

    private TextField name = new TextField("Name");
    private TextField teamClass = new TextField("Team Class");
    private ComboBox<CoachDTO> coach = new ComboBox<>("Coach");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private final Binder<Team> binder = new BeanValidationBinder<>(Team.class);

    public TeamForm(TeamsView teamsView, TeamService teamService, CoachService coachService) {
        this.teamService = teamService;
        this.teamsView = teamsView;
        this.coachService = coachService;

        coach.setItems(coachService.getAllCoachesDTO());
        name.setRequired(true);
        teamClass.setRequired(true);
        coach.setRequired(true);

        binder.bindInstanceFields(this);

        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        binder.addStatusChangeListener(event -> {
            final boolean isValid = !event.hasValidationErrors();
            save.setEnabled(isValid);
        });

        if (SecurityUtils.isAccessGranted(CoachesView.class)) {
            add(name, teamClass, buttons);
        } else {
            name.setReadOnly(true);
            teamClass.setReadOnly(true);
            add(name, teamClass);
        }

        save.addClickListener(buttonClickEvent -> save());
        delete.addClickListener(buttonClickEvent -> delete());
    }

    public void setTeam(Team team) {
        binder.setBean(team);

        if (team == null) {
            setVisible(false);
        } else {
            setVisible(true);
            name.focus();
        }
    }

    private void save() {
        Team team = binder.getBean();
        teamService.save(team);
        teamsView.updateList();
        setTeam(null);
    }

    private void delete() {
        Team team = binder.getBean();
        teamService.delete(team);
        teamsView.updateList();
        setTeam(null);
    }
}
