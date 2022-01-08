package pjatk.edu.pl.footballclubmanagementapplication.ui.views.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.League;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Season;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Team;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.LeagueService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.SeasonService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.TeamLeagueSeasonService;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.MainLayout;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.LeaguesView;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.PAGE_LEAGUE;

public class LeagueComponent extends VerticalLayout {

    @Autowired
    private TeamLeagueSeasonService teamLeagueSeasonService;
    @Autowired
    private SeasonService seasonService;
    @Autowired
    private LeagueService leagueService;

    private Grid<Team> teamGrid = new Grid<>(Team.class);

    private League league;

    @Autowired
    public LeagueComponent(LeaguesView leaguesView, LeagueService leagueService, TeamLeagueSeasonService teamLeagueSeasonService, SeasonService seasonService) {
        this.leagueService = leagueService;
        this.teamLeagueSeasonService = teamLeagueSeasonService;
        this.seasonService = seasonService;

        teamGrid.addColumn(Team::getName).setHeader("Team Name").setSortable(true);
        HeaderRow headerRow = teamGrid.appendHeaderRow();

        headerRow.getCells().get(0).setComponent(createFilterHeader("Season", this.seasonService));
    }

    public void setLeague(League passedLeague, Season season) {
        league = passedLeague;
        if (league == null) {
            setVisible(false);
        } else {
            teamGrid.setItems(teamLeagueSeasonService.getTeamsInLeagueInSeason(season, passedLeague));
            setVisible(true);
        }
    }

    private Component createFilterHeader(String labelText,
                                         SeasonService seasonService) {
        Label label = new Label(labelText);
        label.getStyle().set("padding-top", "var(--lumo-space-m)")
                .set("font-size", "var(--lumo-font-size-xs)");
        ComboBox<Season> seasonFilter = new ComboBox("Season");
        seasonFilter.setItems(seasonService.getAllSeasons());
        seasonFilter.setClearButtonVisible(true);
        seasonFilter.setWidthFull();
        seasonFilter.getStyle().set("max-width", "100%");
        seasonFilter.addValueChangeListener(event -> {
            setLeague(league, seasonFilter.getValue());
        });
        VerticalLayout layout = new VerticalLayout(label, seasonFilter);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");

        return layout;
    }

}
