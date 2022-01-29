package pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.League;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Season;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Team;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.LeagueService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.SeasonService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.TeamLeagueSeasonService;
import pjatk.edu.pl.footballclubmanagementapplication.security.SecurityUtils;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.MainLayout;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.accessMocks.ManagerAccessMock;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.components.LeagueForm;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.*;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.SearchUtils.generateSearchField;

@Secured({ADMIN_ROLE, MANAGER_ROLE, COACH_ROLE, PLAYER_ROLE})
@Route(value = PAGE_LEAGUES, layout = MainLayout.class)
public class LeaguesView extends VerticalLayout {

    @Autowired
    private final LeagueService leagueService;
    @Autowired
    private final TeamLeagueSeasonService teamLeagueSeasonService;
    @Autowired
    private final SeasonService seasonService;

    private final Grid<League> leaguesGrid = new Grid<>();
    private final Grid<Team> leagueGrid = new Grid<>();

    private final TextField searchField;

    public void setLeague(League league) {
        this.league = league;
    }

    private League league;

    public LeaguesView(LeagueService leagueService, TeamLeagueSeasonService teamLeagueSeasonService, SeasonService seasonService) {
        this.leagueService = leagueService;
        this.teamLeagueSeasonService = teamLeagueSeasonService;
        this.seasonService = seasonService;

        LeagueForm leagueForm = new LeagueForm(this, leagueService);
        leagueForm.setLeague(null);

        this.searchField = generateSearchField();
        this.searchField.addValueChangeListener(e -> updateList());

        leagueGrid.addColumn(Team::getName).setHeader("Team Name").setSortable(true);
        leagueGrid.setVisible(false);

        HeaderRow leagueGridHeaderRow = leagueGrid.appendHeaderRow();
        leagueGridHeaderRow.getCells().get(0).setComponent(createFilterHeader("Season", this.seasonService));

        leaguesGrid.addColumn(League::getName).setHeader("League Name").setSortable(true);

        Button returnToLeaguesListButton = new Button("Return to Leagues list");
        returnToLeaguesListButton.addClickListener(event -> {
            leagueGrid.setVisible(false);
            updateLeagueGrid(null, null);
            leaguesGrid.setVisible(true);
            returnToLeaguesListButton.setVisible(false);
            setLeague(null);
        });
        returnToLeaguesListButton.setVisible(false);

        leaguesGrid.addItemClickListener(event -> event.getItem());

        leaguesGrid.addComponentColumn(leagueClick -> {
            Button viewButton = new Button("View");
            viewButton.addClickListener(event -> {
                leaguesGrid.setVisible(false);
                updateLeagueGrid(leagueClick, seasonService.getNewestSeason());
                leagueGrid.setVisible(true);
                returnToLeaguesListButton.setVisible(true);
                setLeague(leagueClick);
            });
            return viewButton;
        });

        Button addLeagueButton = new Button("Add League");
        addLeagueButton.addClickListener(event -> {
            leaguesGrid.asSingleSelect().clear();
            leagueForm.setLeague(new League());
        });

        HorizontalLayout regularToolbar = new HorizontalLayout(returnToLeaguesListButton);
        HorizontalLayout protectedToolbar = new HorizontalLayout(addLeagueButton, returnToLeaguesListButton);

        setSizeFull();

        if (SecurityUtils.isAccessGranted(ManagerAccessMock.class)) {
            leaguesGrid.asSingleSelect().addValueChangeListener(event -> leagueForm.setLeague(leaguesGrid.asSingleSelect().getValue()));
            add(protectedToolbar, searchField, leaguesGrid, leagueGrid, leagueForm);
        } else {
            add(regularToolbar, searchField, leaguesGrid, leagueGrid);
        }
        updateList();
        updateLeagueGrid(league, seasonService.getNewestSeason());
    }

    public void updateList() {
        leaguesGrid.setItems(leagueService.getAllLeaguesWithFilter(searchField.getValue()));
    }

    public void updateLeagueGrid(League league, Season season) {
        leagueGrid.setItems(teamLeagueSeasonService.getTeamsInLeagueInSeason(season, league));
    }

    private Component createFilterHeader(String labelText,
                                         SeasonService seasonService) {
        ComboBox<Season> seasonFilter = new ComboBox("Season");
        seasonFilter.setItems(seasonService.getAllSeasons());
        seasonFilter.setClearButtonVisible(true);
        seasonFilter.setWidthFull();
        seasonFilter.getStyle().set("max-width", "100%");
        seasonFilter.addValueChangeListener(event -> {
            leagueGrid.setItems(teamLeagueSeasonService.getTeamsInLeagueInSeason(event.getValue(), league));
        });
        VerticalLayout layout = new VerticalLayout(seasonFilter);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");

        return layout;
    }

}