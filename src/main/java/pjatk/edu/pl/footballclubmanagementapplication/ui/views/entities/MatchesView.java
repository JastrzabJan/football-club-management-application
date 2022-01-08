package pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.League;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Match;
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

@Secured({ADMIN_ROLE, MANAGER_ROLE, COACH_ROLE, PLAYER_ROLE})
@Route(value = PAGE_MATCHES, layout = MainLayout.class)
public class MatchesView extends VerticalLayout {

    private final MatchService matchService;
    private final TeamService teamService;
    private Grid<Match> matchGrid = new Grid<>();

    public MatchesView(MatchService matchService, TeamService teamService) {
        this.matchService = matchService;
        this.teamService = teamService;
        MatchForm matchForm = new MatchForm(this, matchService, teamService);

        matchForm.setMatch(null);

        matchGrid.addColumn(Match::getTeam).setHeader("Team");
        matchGrid.addColumn(Match::getOpponentTeam).setHeader("Opponent Team");
        matchGrid.addColumn(Match::getPlace).setHeader("Match place");
        matchGrid.addColumn(Match::getGoalsHome).setHeader("Goals");
        matchGrid.addColumn(Match::getGoalsAway).setHeader("Opponent's Goals");
        matchGrid.addColumn(Match::getGameDay).setHeader("Game Date");

        matchGrid.asSingleSelect().addValueChangeListener(event -> matchForm.setMatch(matchGrid.asSingleSelect().getValue()));

        Button addLeagueButton = new Button("Add Match");
        addLeagueButton.addClickListener(event -> {
            matchGrid.asSingleSelect().clear();
            matchForm.setMatch(new Match());
        });
        HorizontalLayout toolbar = new HorizontalLayout(addLeagueButton);
        setSizeFull();
        if(SecurityUtils.isAccessGranted(CoachesView.class)){
            add(toolbar, matchGrid, matchForm);
        }else{
            add(matchGrid);
        }
        updateList();
    }


    public void updateList() {
        matchGrid.setItems(matchService.getAllMatches());

    }
}
