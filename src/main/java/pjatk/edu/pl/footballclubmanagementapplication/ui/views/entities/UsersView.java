package pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.CoachService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.PlayerService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.UserService;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.MainLayout;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.components.UserForm;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.ADMIN_ROLE;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.PAGE_USERS;
import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.SearchUtils.generateSearchField;

@Secured(ADMIN_ROLE)
@Route(value = PAGE_USERS, layout = MainLayout.class)
public class UsersView extends VerticalLayout {

    private final UserService userService;
    private final CoachService coachService;
    private final PlayerService playerService;
    private final PasswordEncoder passwordEncoder;
    private final Grid<User> userGrid = new Grid<>();
    private final TextField searchField;

    @Autowired
    public UsersView(UserService userService, CoachService coachService, PlayerService playerService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.coachService = coachService;
        this.playerService = playerService;
        this.passwordEncoder = passwordEncoder;

        UserForm userForm = new UserForm(this, userService, coachService, playerService, passwordEncoder);

        userForm.setUser(null);

        searchField = generateSearchField();
        searchField.addValueChangeListener(e -> updateList());

        userGrid.addColumn(User::getEmail).setHeader("Email").setSortable(true);
        userGrid.addColumn(User::getId).setHeader("ID").setSortable(true);
        userGrid.addColumn(User::getRole).setHeader("Role").setSortable(true);


        Button addUserButton = new Button("Add User");
        addUserButton.addClickListener(event -> {
            userGrid.asSingleSelect().clear();
            userForm.setUser(new User());
        });

        userGrid.asSingleSelect().addValueChangeListener(event -> userForm.setUser(userGrid.asSingleSelect().getValue()));
        HorizontalLayout toolbar = new HorizontalLayout(addUserButton);
        add(toolbar, searchField, userGrid, userForm);

        setSizeFull();
        updateList();

    }

    public void updateList() {
        this.userGrid.setItems(userService.getAllUsersWithFilter(searchField.getValue()));
    }
}
