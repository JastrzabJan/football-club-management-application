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
import org.springframework.security.access.annotation.Secured;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Match;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Team;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.MatchService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.TeamService;
import pjatk.edu.pl.footballclubmanagementapplication.security.SecurityUtils;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.MainLayout;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.components.MatchForm;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.ADMIN_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.COACH_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.MANAGER_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.PAGE_MATCHES;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.PLAYER_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.SearchUtils.generateSearchField;

@Secured({ADMIN_ROLE, MANAGER_ROLE, COACH_ROLE, PLAYER_ROLE})
@Route(value = PAGE_MATCHES, layout = MainLayout.class)
public class MatchesView extends VerticalLayout {

    private final MatchService matchService;
    private final TeamService teamService;
    private final Grid<Match> matchGrid = new Grid<>();
    private final TextField searchField;

    public MatchesView(MatchService matchService, TeamService teamService) {
        this.matchService = matchService;
        this.teamService = teamService;

        this.searchField = generateSearchField();
        searchField.addValueChangeListener(e -> updateList());

        MatchForm matchForm = new MatchForm(this, matchService, teamService);

        matchForm.setMatch(null);

        matchGrid.addColumn(Match::getTeam).setHeader("Team").setSortable(true);
        matchGrid.addColumn(Match::getOpponentTeam).setHeader("Opponent Team").setSortable(true);
        matchGrid.addColumn(Match::getPlace).setHeader("Match place").setSortable(true);
        matchGrid.addColumn(Match::getGoalsHome).setHeader("Goals");
        matchGrid.addColumn(Match::getGoalsAway).setHeader("Opponent's Goals");
        matchGrid.addColumn(Match::getGameDate).setHeader("Game Date").setSortable(true);

        HeaderRow matchGridHeaderRow = matchGrid.appendHeaderRow();
        matchGridHeaderRow.getCells().get(0).setComponent(createFilterHeader());

        matchGrid.asSingleSelect().addValueChangeListener(event -> matchForm.setMatch(matchGrid.asSingleSelect().getValue()));

        Button addLeagueButton = new Button("Add Match");
        addLeagueButton.addClickListener(event -> {
            matchGrid.asSingleSelect().clear();
            matchForm.setMatch(new Match());
        });
        HorizontalLayout toolbar = new HorizontalLayout(addLeagueButton);
        setSizeFull();
        if (SecurityUtils.isAccessGranted(CoachesView.class)) {
            add(toolbar, searchField, matchGrid, matchForm);
        } else {
            add(searchField, matchGrid);
        }
        updateList();
    }


    public void updateList() {
        matchGrid.setItems(matchService.getAllMatchesWithFilter(searchField.getValue()));
    }

    private Component createFilterHeader() {
        ComboBox<Team> seasonFilter = new ComboBox("Team");
        seasonFilter.setItems(teamService.findAll());
        seasonFilter.setClearButtonVisible(true);
        seasonFilter.setWidthFull();
        seasonFilter.getStyle().set("max-width", "100%");
        seasonFilter.addValueChangeListener(event -> {
            matchGrid.setItems(matchService.getMatchesForSelectedTeam(event.getValue()));
        });
        VerticalLayout layout = new VerticalLayout(seasonFilter);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");

        return layout;
    }

}
