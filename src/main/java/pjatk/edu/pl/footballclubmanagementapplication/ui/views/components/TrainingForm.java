package pjatk.edu.pl.footballclubmanagementapplication.ui.views.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Coach;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Team;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Training;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.TrainingType;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.CoachService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.TeamService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.TrainingService;
import pjatk.edu.pl.footballclubmanagementapplication.security.SecurityUtils;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.TrainingsView;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.UsersView;

public class TrainingForm extends FormLayout {

    private TrainingsView trainingsView;
    private final TrainingService trainingService;
    private final CoachService coachService;
    private final TeamService teamService;

    private DatePicker trainingDate = new DatePicker("Training Date");
    private ComboBox<TrainingType> trainingType = new ComboBox<>("Training Type");
    private ComboBox<Coach> coach = new ComboBox<>("Coach");
    private ComboBox<Team> team = new ComboBox<>("Teams");


    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private final Binder<Training> binder = new BeanValidationBinder<>(Training.class);

    public TrainingForm(TrainingsView trainingsView, TrainingService trainingService, CoachService coachService, TeamService teamService) {
        this.trainingsView = trainingsView;
        this.trainingService = trainingService;
        this.coachService = coachService;
        this.teamService = teamService;

        trainingType.setItems(TrainingType.values());
        coach.setItems(coachService.findAll());
        team.setItems(teamService.findAll());

        coach.setRequired(true);
        team.setRequired(true);
        trainingType.setRequired(true);
        trainingDate.setRequired(true);

        binder.bindInstanceFields(this);
        binder.addStatusChangeListener(event -> {
            final boolean isValid = !event.hasValidationErrors();
            save.setEnabled(isValid);
        });

        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        if (SecurityUtils.isAccessGranted(UsersView.class)) {
            add(trainingDate, trainingType, coach, team, buttons);
        } else {
            add(trainingDate, trainingType, coach, team);
        }

        save.addClickListener(buttonClickEvent -> save());
        delete.addClickListener(buttonClickEvent -> delete());
    }

    public void setTraining(Training training) {
        binder.setBean(training);
        if (training == null) {
            setVisible(false);
        } else {
            setVisible(true);
            trainingDate.focus();
        }
    }

    private void save() {
        Training training = binder.getBean();
        trainingService.save(training);
        trainingsView.updateList();
        setTraining(null);
    }

    private void delete() {
        Training training = binder.getBean();
        trainingService.delete(training);
        trainingsView.updateList();
        setTraining(null);
    }

}
