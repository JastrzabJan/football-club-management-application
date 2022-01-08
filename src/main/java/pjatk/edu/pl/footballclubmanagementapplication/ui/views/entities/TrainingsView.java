package pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Training;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.CoachService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.TeamService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.TrainingService;
import pjatk.edu.pl.footballclubmanagementapplication.security.SecurityUtils;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.MainLayout;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.components.TrainingForm;

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

    private Grid<Training> trainingGrid = new Grid<>();

    public TrainingsView(TrainingService trainingService, CoachService coachService, TeamService teamService) {
        this.trainingService = trainingService;
        this.coachService = coachService;
        this.teamService = teamService;
        TrainingForm trainingForm = new TrainingForm(this, trainingService, this.coachService, this.teamService);

        trainingForm.setTraining(null);

        trainingGrid.addColumn(Training::getTrainingDate).setHeader("Training Date");
        trainingGrid.addColumn(Training::getTrainingType).setHeader("Training Type");
        trainingGrid.addColumn(Training::getCoach).setHeader("Training Coach");
        trainingGrid.addColumn(Training::getTeam).setHeader("Training Team");

        trainingGrid.asSingleSelect().addValueChangeListener(event -> trainingForm.setTraining(trainingGrid.asSingleSelect().getValue()));

        Button addTrainingButton = new Button("Add Training");
        addTrainingButton.addClickListener(event -> {
            trainingGrid.asSingleSelect().clear();
            trainingForm.setTraining(new Training());
        });
        HorizontalLayout toolbar = new HorizontalLayout(addTrainingButton);
        setSizeFull();
        if (SecurityUtils.isAccessGranted(CoachesView.class)) {
            add(toolbar, trainingGrid, trainingForm);
        } else {
            add(trainingGrid, trainingForm);
        }
        updateList();

    }

    public void updateList() {
        trainingGrid.setItems(trainingService.getAllTrainings());
    }
}
