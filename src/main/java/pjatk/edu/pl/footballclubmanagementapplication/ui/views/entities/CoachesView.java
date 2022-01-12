package pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;
import pjatk.edu.pl.footballclubmanagementapplication.backend.dto.CoachDTO;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.CoachService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.TeamService;
import pjatk.edu.pl.footballclubmanagementapplication.security.SecurityUtils;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.MainLayout;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.components.CoachForm;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.ADMIN_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.COACH_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.MANAGER_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.PAGE_COACHES;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.PLAYER_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.SearchUtils.generateSearchField;

@Secured({ADMIN_ROLE, MANAGER_ROLE, COACH_ROLE})
@Route(value = PAGE_COACHES, layout = MainLayout.class)
public class CoachesView extends VerticalLayout {

    private final CoachService coachService;
    private final TeamService teamService;
    private final Grid<CoachDTO> coachGrid = new Grid<>();
    private final TextField searchField;

    public CoachesView(CoachService coachService, TeamService teamService) {
        this.coachService = coachService;
        this.teamService = teamService;

        CoachForm coachForm = new CoachForm(this, coachService, this.teamService);
        coachForm.setCoach(null);

        this.searchField = generateSearchField();
        this.searchField.addValueChangeListener(e -> updateList());

        if (SecurityUtils.isAccessGranted(UsersView.class)) {
            coachGrid.addColumn(CoachDTO::getEmail).setHeader("Email").setSortable(true);
            coachGrid.addColumn(CoachDTO::getAddress).setHeader("Address").setSortable(true);
            coachGrid.addColumn(CoachDTO::getQualifications).setHeader("Qualifications").setSortable(true);
        }
        coachGrid.addColumn(CoachDTO::getName).setHeader("Name").setSortable(true);
        coachGrid.addColumn(CoachDTO::getSurname).setHeader("Surname").setSortable(true);
        coachGrid.addColumn(CoachDTO::getBirthDate).setHeader("Birth Date").setSortable(true);
        coachGrid.addColumn(CoachDTO::getPhoneNumber).setHeader("Phone Number").setSortable(true);
        coachGrid.addColumn(CoachDTO::getTeamNames).setHeader("Teams").setSortable(true);

        coachGrid.asSingleSelect().addValueChangeListener(event -> coachForm.setCoach(coachGrid.asSingleSelect().getValue()));


        Button addCoachButton = new Button("Add Coach");
        addCoachButton.addClickListener(event -> {
            coachGrid.asSingleSelect().clear();
            coachForm.setCoach(new CoachDTO());
        });
        HorizontalLayout toolbar = new HorizontalLayout(addCoachButton);
        setSizeFull();

        if (SecurityUtils.isAccessGranted(UsersView.class)) {
            add(toolbar, searchField, coachGrid, coachForm);
        } else {
            add(searchField, coachGrid);
        }
        updateList();
    }

    public void updateList() {
        this.coachGrid.setItems(coachService.getAllCoachesDTOwithFilter(searchField.getValue()));
    }
}
