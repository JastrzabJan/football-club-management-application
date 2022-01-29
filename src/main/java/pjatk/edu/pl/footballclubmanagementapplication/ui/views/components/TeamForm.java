package pjatk.edu.pl.footballclubmanagementapplication.ui.views.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
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

import static pjatk.edu.pl.footballclubmanagementapplication.ui.views.components.utils.ButtonProvider.*;

public class TeamForm extends FormLayout {

    private final TeamsView teamsView;
    private final TeamService teamService;
    private final CoachService coachService;

    private final TextField name = new TextField("Name");
    private final TextField teamClass = new TextField("Team Class");
    private final ComboBox<Coach> coach = new ComboBox<>("Coach");

    Notification validationErrorNotification = createValidationErrorNotification();

    private final Button save = createDeleteButton();
    private final Button delete = createSaveButton();

    private final BeanValidationBinder<Team> binder = new BeanValidationBinder<>(Team.class);

    public TeamForm(TeamsView teamsView, TeamService teamService, CoachService coachService) {
        this.teamService = teamService;
        this.teamsView = teamsView;
        this.coachService = coachService;

        coach.setItems(coachService.getRepository().findAll());
        name.setRequired(true);
        teamClass.setRequired(true);
        coach.setRequired(true);
        binder.bindInstanceFields(this);

        HorizontalLayout buttons = new HorizontalLayout(save, delete, validationErrorNotification);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        binder.addStatusChangeListener(event -> {
            final boolean isValid = !event.hasValidationErrors();
            save.setEnabled(isValid);
        });

        if (SecurityUtils.isAccessGranted(CoachesView.class)) {
            add(name, teamClass, coach, buttons);
        } else {
            name.setReadOnly(true);
            teamClass.setReadOnly(true);
            coach.setReadOnly(true);
            add(name, teamClass, coach);
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
        if(binder.validate().isOk()) {
            Team team = binder.getBean();
            teamService.save(team);
            teamsView.updateList();
            setTeam(null);
        }else{
            validationErrorNotification.open();
        }
    }

    private void delete() {
        Team team = binder.getBean();
        teamService.delete(team);
        teamsView.updateList();
        setTeam(null);
    }
}
