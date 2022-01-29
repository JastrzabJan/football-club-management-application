package pjatk.edu.pl.footballclubmanagementapplication.ui.views.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Coach;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Team;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Training;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.TrainingType;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.CoachService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.TeamService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.TrainingService;
import pjatk.edu.pl.footballclubmanagementapplication.security.SecurityUtils;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.accessMocks.CoachAccessMock;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.TrainingsView;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.UsersView;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.views.components.utils.ButtonProvider.*;

public class TrainingForm extends FormLayout {

    private final TrainingsView trainingsView;
    private final TrainingService trainingService;

    private final DateTimePicker trainingStart = new DateTimePicker("Training Date");
    private final ComboBox<TrainingType> trainingType = new ComboBox<>("Training Type");
    private final ComboBox<Coach> coach = new ComboBox<>("Coach");
    private final ComboBox<Team> team = new ComboBox<>("Teams");

    Notification validationErrorNotification = createValidationErrorNotification();

    private final Button save = createDeleteButton();
    private final Button delete = createSaveButton();

    private final BeanValidationBinder<Training> binder = new BeanValidationBinder<>(Training.class);

    public TrainingForm(TrainingsView trainingsView, TrainingService trainingService, CoachService coachService, TeamService teamService) {
        this.trainingsView = trainingsView;
        this.trainingService = trainingService;

        trainingType.setItems(TrainingType.values());
        coach.setItems(coachService.findAll());
        team.setItems(teamService.findAll());




        binder.bindInstanceFields(this);

        binder.addStatusChangeListener(event -> {
            final boolean isValid = !event.hasValidationErrors();
            save.setEnabled(isValid);
        });

        HorizontalLayout buttons = new HorizontalLayout(save, delete, validationErrorNotification);

        if (SecurityUtils.isAccessGranted(CoachAccessMock.class)) {
            add(trainingStart, trainingType, coach, team, buttons);
        } else {
            add(trainingStart, trainingType, coach, team);
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
            trainingStart.focus();
        }
    }

    private void save() {
        if(binder.validate().isOk()) {
            Training training = binder.getBean();
            trainingService.save(training);
            trainingsView.updateList();
            setTraining(null);
        }else{
            validationErrorNotification.open();
        }
    }

    private void delete() {
        Training training = binder.getBean();
        trainingService.delete(training);
        trainingsView.updateList();
        setTraining(null);
    }

}
