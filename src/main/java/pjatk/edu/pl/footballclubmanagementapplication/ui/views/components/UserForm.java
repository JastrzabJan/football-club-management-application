package pjatk.edu.pl.footballclubmanagementapplication.ui.views.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import org.apache.http.impl.cookie.RFC6265LaxSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Role;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.CoachService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.PlayerService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.UserService;
import pjatk.edu.pl.footballclubmanagementapplication.security.SecurityUtils;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.UsersView;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.views.components.utils.ButtonProvider.*;

public class UserForm extends FormLayout {

    private final UsersView usersView;
    private final UserService userService;
    private final CoachService coachService;
    private final PlayerService playerService;
    private final PasswordEncoder passwordEncoder;

    private final EmailField email = new EmailField("Email");
    private final PasswordField passwordHash = new PasswordField("Password");
    private final ComboBox<Role> role = new ComboBox<>("Role");

    Notification validationErrorNotification = createValidationErrorNotification();

    private final Button save = createDeleteButton();
    private final Button delete = createSaveButton();

    private final BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);

    @Autowired
    public UserForm(UsersView usersView, UserService userService, CoachService coachService, PlayerService playerService, PasswordEncoder passwordEncoder) {
        this.usersView = usersView;
        this.userService = userService;
        this.coachService = coachService;
        this.playerService = playerService;
        this.passwordEncoder = passwordEncoder;


        email.setErrorMessage("Please enter a valid email address");
        role.setItems(Role.Admin, Role.Manager);
        binder.forField(passwordHash).bind(User::getPasswordHash, User::setPasswordHash);
        binder.forField(email).bind(User::getEmail, User::setEmail);
        binder.forField(role).bind(User::getRole, User::setRole);
        binder.addStatusChangeListener(event -> {
            final boolean isValid = !event.hasValidationErrors();
            save.setEnabled(isValid);
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(buttonClickEvent -> save());
        delete.addClickListener(buttonClickEvent -> delete());
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        HorizontalLayout buttons = new HorizontalLayout(save, delete, validationErrorNotification);

        if (SecurityUtils.isAccessGranted(UsersView.class)) {
            add(email, passwordHash, role, buttons);
        }
    }

    public void setUser(User user) {
        binder.setBean(user);
        passwordHash.setValue("");
        if (user == null) {
            setVisible(false);
        } else {
            setVisible(true);
            email.focus();
        }
    }

    private void save() {
        User user = binder.getBean();
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        userService.save(user);
        usersView.updateList();
        setUser(null);
    }

    private void delete() {
        User user = binder.getBean();
        userService.delete(user);
        usersView.updateList();
        setUser(null);
    }
}
