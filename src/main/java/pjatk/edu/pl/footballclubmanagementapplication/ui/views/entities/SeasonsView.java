package pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Season;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.SeasonService;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.MainLayout;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.components.SeasonForm;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.ADMIN_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.MANAGER_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.PAGE_SEASONS;

@Secured({ADMIN_ROLE, MANAGER_ROLE})
@Route(value = PAGE_SEASONS, layout = MainLayout.class)
public class SeasonsView extends VerticalLayout {

    private final SeasonService seasonService;
    private Grid<Season> seasonGrid = new Grid<>();

    public SeasonsView(SeasonService seasonService) {
        this.seasonService = seasonService;
        SeasonForm seasonForm = new SeasonForm(this, seasonService);
        seasonForm.setSeason(null);

        seasonGrid.addColumn(Season::getName).setHeader("Name");
        seasonGrid.addColumn(Season::getStartDate).setHeader("Start Date");
        seasonGrid.addColumn(Season::getEndDate).setHeader("End Date");

        seasonGrid.asSingleSelect().addValueChangeListener(event -> seasonForm.setSeason(seasonGrid.asSingleSelect().getValue()));

        Button addSeasonButton = new Button("Add Season");
        addSeasonButton.addClickListener(event -> {
            seasonGrid.asSingleSelect().clear();
            seasonForm.setSeason(new Season());
        });
        HorizontalLayout toolbar = new HorizontalLayout(addSeasonButton);
        setSizeFull();
        add(toolbar, seasonGrid, seasonForm);
        updateList();

    }

    public void updateList() {
        seasonGrid.setItems(seasonService.getAllSeasons());
    }
}
