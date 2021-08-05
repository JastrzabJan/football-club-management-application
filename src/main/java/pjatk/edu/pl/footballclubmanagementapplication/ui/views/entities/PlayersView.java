package pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;
import pjatk.edu.pl.footballclubmanagementapplication.backend.dto.PlayerDTO;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.PlayerService;
import pjatk.edu.pl.footballclubmanagementapplication.security.SecurityUtils;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.MainLayout;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.components.PlayerForm;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.ADMIN_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.COACH_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.MANAGER_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.PAGE_PLAYERS;

@Secured({ADMIN_ROLE, MANAGER_ROLE, COACH_ROLE})
@Route(value = PAGE_PLAYERS, layout = MainLayout.class)
public class PlayersView extends VerticalLayout {

    private final PlayerService playerService;
    private Grid<PlayerDTO> playerGrid = new Grid<>();


    public PlayersView(PlayerService playerService) {
        this.playerService = playerService;
        PlayerForm playerForm = new PlayerForm(this, playerService);

        playerForm.setPlayer(null);

        if (SecurityUtils.isAccessGranted(UsersView.class)) {
            playerGrid.addColumn(PlayerDTO::getEmail).setHeader("Email");
            playerGrid.addColumn(PlayerDTO::getPESEL).setHeader("PESEL");
        }
        playerGrid.addColumn(PlayerDTO::getName).setHeader("Name");
        playerGrid.addColumn(PlayerDTO::getSurname).setHeader("Surname");
        playerGrid.addColumn(PlayerDTO::getBirthDate).setHeader("Birth Date");
        playerGrid.addColumn(PlayerDTO::getAddress).setHeader("Address");
        playerGrid.addColumn(PlayerDTO::getPhoneNumber).setHeader("Phone Number");
        playerGrid.addColumn(PlayerDTO::getPosition).setHeader("Position");
        playerGrid.addColumn(PlayerDTO::getNumber).setHeader("Number");
        playerGrid.addColumn(PlayerDTO::getTeamNames).setHeader("Teams");

        playerGrid.asSingleSelect().addValueChangeListener(event -> playerForm.setPlayer(playerGrid.asSingleSelect().getValue()));

        Button addPlayerButton = new Button("Add Player");
        addPlayerButton.addClickListener(event -> {
            playerGrid.asSingleSelect().clear();
            playerForm.setPlayer(new PlayerDTO());
        });
        HorizontalLayout toolbar = new HorizontalLayout(addPlayerButton);
        setSizeFull();
        if (SecurityUtils.isAccessGranted(UsersView.class)) {
            add(toolbar, playerGrid, playerForm);
        } else {
            add(playerGrid, playerForm);
        }
        updateList();

    }

    public void updateList() {
        playerGrid.setItems(playerService.getAllPlayersDTO());
    }
}
