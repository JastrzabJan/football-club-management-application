package pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Player;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Team;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.CoachService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.TeamService;
import pjatk.edu.pl.footballclubmanagementapplication.security.SecurityUtils;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.MainLayout;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.components.TeamForm;

import java.util.stream.Stream;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.ADMIN_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.COACH_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.MANAGER_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.PAGE_TEAMS;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.PLAYER_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.SearchUtils.generateSearchField;

@Secured({ADMIN_ROLE, MANAGER_ROLE, COACH_ROLE, PLAYER_ROLE})
@Route(value = PAGE_TEAMS, layout = MainLayout.class)
public class TeamsView extends VerticalLayout {
    private final TeamService teamService;
    private final CoachService coachService;
    private TreeGrid<Team> teamGrid = new TreeGrid<>();
    private TextField searchField;

    public TeamsView(TeamService teamService, CoachService coachService) {
        this.teamService = teamService;
        this.coachService = coachService;
        TeamForm teamForm = new TeamForm(this, teamService, coachService);

        teamGrid.setDetailsVisibleOnClick(true);
        teamGrid.setItemDetailsRenderer(createTeamDetailsRenderer());

        teamForm.setTeam(null);
        teamGrid.addColumn(Team::getName).setHeader("Name").setSortable(true);
        teamGrid.addColumn(Team::getTeamClass).setHeader("Team Class").setSortable(true);
        teamGrid.addColumn(Team::getCoach).setHeader("Coach").setSortable(true);

        this.searchField = generateSearchField();
        this.searchField.addValueChangeListener(e -> updateList());


        teamGrid.asSingleSelect().addValueChangeListener(event -> teamForm.setTeam(teamGrid.asSingleSelect().getValue()));

        Button addTeamButton = new Button("Add Team");
        addTeamButton.addClickListener(event -> {
            teamGrid.asSingleSelect().clear();
            teamForm.setTeam(new Team());
        });
        HorizontalLayout toolbar = new HorizontalLayout(addTeamButton);
        setSizeFull();
        if (SecurityUtils.isAccessGranted(CoachesView.class)) {
            add(toolbar, searchField, teamGrid, teamForm);
        } else {
            add(searchField, teamGrid);
        }
        updateList();

    }

    private static ComponentRenderer<TeamDetailsLayout, Team> createTeamDetailsRenderer() {
        return new ComponentRenderer<>(
                TeamDetailsLayout::new,
                TeamDetailsLayout::setTeam);
    }


    public void updateList() {
        teamGrid.setItems(teamService.getAllTeamsWithFilter(searchField.getValue()));
    }

    private static class TeamDetailsLayout extends FormLayout {
        private final ListBox<Player> playersList = new ListBox<>();

        public TeamDetailsLayout() {
            Stream.of(playersList).forEach(field -> {
                field.setReadOnly(true);
                add(field);
            });
        }

        public void setTeam(Team team) {
            playersList.setItems(team.getPlayers());
        }
    }
}
