package pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Player;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.PlayerTrainingAttendance;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Training;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.*;
import pjatk.edu.pl.footballclubmanagementapplication.security.SecurityUtils;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.MainLayout;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.accessMocks.CoachAccessMock;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.accessMocks.ManagerAccessMock;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.accessMocks.PlayerAccessMock;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.accessMocks.PlayerAccessOnly;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.components.TrainingForm;

import java.util.stream.Stream;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.ADMIN_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.COACH_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.MANAGER_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.PAGE_TRAININGS;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.PLAYER_ROLE;

@Secured({ADMIN_ROLE, MANAGER_ROLE, COACH_ROLE, PLAYER_ROLE})
@Route(value = PAGE_TRAININGS, layout = MainLayout.class)
public class TrainingsView extends VerticalLayout {

    private final TrainingService trainingService;
    private final CoachService coachService;
    private final TeamService teamService;
    private final PlayerTrainingAttendanceService playerTrainingAttendanceService;
    private final PlayerService playerService;

    private final Grid<Training> trainingGrid = new Grid<>();

    public TrainingsView(TrainingService trainingService, CoachService coachService, TeamService teamService, PlayerTrainingAttendanceService playerTrainingAttendanceService, PlayerService playerService) {
        this.trainingService = trainingService;
        this.coachService = coachService;
        this.teamService = teamService;
        this.playerTrainingAttendanceService = playerTrainingAttendanceService;
        this.playerService = playerService;
        TrainingForm trainingForm = new TrainingForm(this, trainingService, this.coachService, this.teamService);

        trainingForm.setTraining(null);

        trainingGrid.addColumn(Training::getTrainingStart).setHeader("Training Start Date");
        trainingGrid.addColumn(Training::getTrainingType).setHeader("Training Type");
        trainingGrid.addColumn(Training::getCoach).setHeader("Training Coach");
        trainingGrid.addColumn(Training::getTeam).setHeader("Training Team");

        if (SecurityUtils.isAccessGranted(PlayerAccessOnly.class)) {
            trainingGrid.addComponentColumn(training -> {
                Checkbox attendanceCheckbox = new Checkbox("Confirm Attendance");
                Player player = playerService.findByUsernameAndTeam(SecurityUtils.getUsername(), training.getTeam());
                if (player != null) {

                    setVisible(true);

                    boolean value = getCheckboxValue(training, player);

                    attendanceCheckbox.setValue(value);

                    attendanceCheckbox.addValueChangeListener(event -> {
                        if (event.getValue()) {
                            PlayerTrainingAttendance playerTrainingAttendance = new PlayerTrainingAttendance();
                            playerTrainingAttendance.setTraining(training);
                            playerTrainingAttendance.setPlayer(player);
                            playerTrainingAttendanceService.save(playerTrainingAttendance);
                        } else {
                            PlayerTrainingAttendance playerTrainingAttendance = playerTrainingAttendanceService.getAttendanceForPlayerAndTraining(training, player);
                            playerTrainingAttendanceService.delete(playerTrainingAttendance);
                        }
                    });
                }
                return attendanceCheckbox;
            });
        }

        if (SecurityUtils.isAccessGranted(CoachAccessMock.class)) {
            trainingGrid.setDetailsVisibleOnClick(true);
            trainingGrid.setItemDetailsRenderer(createAttendanceDetailsRenderer());
        }
            trainingGrid.asSingleSelect().addValueChangeListener(event -> trainingForm.setTraining(trainingGrid.asSingleSelect().getValue()));

        Button addTrainingButton = new Button("Add Training");
        addTrainingButton.addClickListener(event -> {
            trainingGrid.asSingleSelect().clear();
            trainingForm.setTraining(new Training());
        });
        HorizontalLayout toolbar = new HorizontalLayout(addTrainingButton);
        setSizeFull();
        if (SecurityUtils.isAccessGranted(CoachAccessMock.class)) {
            add(toolbar, trainingGrid, trainingForm);
        } else {
            add(trainingGrid);
        }
        updateList();

    }

    public void updateList() {
        trainingGrid.setItems(trainingService.getAllTrainings());
    }

    public boolean  getCheckboxValue(Training training, Player player) {
        PlayerTrainingAttendance attendance = playerTrainingAttendanceService.getAttendanceForPlayerAndTraining(training, player);
        return attendance != null;
    }
    private static ComponentRenderer<TrainingsView.AttendanceDetailsLayout, Training> createAttendanceDetailsRenderer() {
        return new ComponentRenderer<>(
                AttendanceDetailsLayout::new,
                AttendanceDetailsLayout::setTraining);
    }

    private static class AttendanceDetailsLayout extends FormLayout {
        private final ListBox<PlayerTrainingAttendance> attendanceListBox = new ListBox<>();
        public AttendanceDetailsLayout() {
            Stream.of(attendanceListBox).forEach(field -> {
                field.setReadOnly(true);
                add(field);
            });
        }

        public void setTraining(Training training) {
            attendanceListBox.setItems(training.getPlayerTrainingAttendances());
        }
    }


}
