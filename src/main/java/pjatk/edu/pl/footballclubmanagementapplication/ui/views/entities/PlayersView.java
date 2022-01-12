package pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;
import pjatk.edu.pl.footballclubmanagementapplication.backend.dto.PlayerDTO;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.PlayerService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.TeamService;
import pjatk.edu.pl.footballclubmanagementapplication.security.SecurityUtils;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.MainLayout;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.components.PlayerForm;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.ADMIN_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.COACH_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.MANAGER_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.PAGE_PLAYERS;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.SearchUtils.generateSearchField;

@Secured({ADMIN_ROLE, MANAGER_ROLE, COACH_ROLE})
@Route(value = PAGE_PLAYERS, layout = MainLayout.class)
public class PlayersView extends VerticalLayout {

    private final PlayerService playerService;
    private final TeamService teamService;
    private final Grid<PlayerDTO> playerGrid = new Grid<>();
    private final TextField searchField;
    public PlayersView(PlayerService playerService, TeamService teamService) {
        this.playerService = playerService;
        this.teamService = teamService;

        PlayerForm playerForm = new PlayerForm(this, playerService, this.teamService);

        playerForm.setPlayer(null);
        searchField = generateSearchField();
        searchField.addValueChangeListener(e -> updateList());

        if (SecurityUtils.isAccessGranted(UsersView.class)) {
            playerGrid.addColumn(PlayerDTO::getEmail).setHeader("Email").setSortable(true);
            playerGrid.addColumn(PlayerDTO::getPESEL).setHeader("PESEL").setSortable(true);
            playerGrid.addColumn(PlayerDTO::getAddress).setHeader("Address").setSortable(true);
            playerGrid.addColumn(PlayerDTO::getPhoneNumber).setHeader("Phone Number").setSortable(true);
        }
        playerGrid.addColumn(PlayerDTO::getName).setHeader("Name").setSortable(true);
        playerGrid.addColumn(PlayerDTO::getSurname).setHeader("Surname").setSortable(true);
        playerGrid.addColumn(PlayerDTO::getBirthDate).setHeader("Birth Date").setSortable(true);
        playerGrid.addColumn(PlayerDTO::getPosition).setHeader("Position").setSortable(true);
        playerGrid.addColumn(PlayerDTO::getNumber).setHeader("Number").setSortable(true);
        playerGrid.addColumn(PlayerDTO::getTeamNames).setHeader("Teams").setSortable(true);

        playerGrid.asSingleSelect().addValueChangeListener(event -> playerForm.setPlayer(playerGrid.asSingleSelect().getValue()));

        Button addPlayerButton = new Button("Add Player");
        addPlayerButton.addClickListener(event -> {
            playerGrid.asSingleSelect().clear();
            playerForm.setPlayer(new PlayerDTO());
        });
        HorizontalLayout protectedToolbar = new HorizontalLayout(addPlayerButton, searchField);
        HorizontalLayout regularToolbar = new HorizontalLayout(searchField);
        setSizeFull();
        if (SecurityUtils.isAccessGranted(UsersView.class)) {
            add(protectedToolbar, searchField, playerGrid, playerForm);
        } else {
            add(regularToolbar, searchField, playerGrid);
        }
        updateList();
    }

    public void updateList() {
        this.playerGrid.setItems(playerService.getAllPlayersWithFilter(searchField.getValue()));
    }
}
