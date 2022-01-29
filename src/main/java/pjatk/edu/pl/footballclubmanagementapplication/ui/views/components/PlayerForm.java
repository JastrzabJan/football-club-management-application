package pjatk.edu.pl.footballclubmanagementapplication.ui.views.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.vaadin.gatanaso.MultiselectComboBox;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Position;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Team;
import pjatk.edu.pl.footballclubmanagementapplication.backend.dto.PlayerDTO;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.PlayerService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.TeamService;
import pjatk.edu.pl.footballclubmanagementapplication.security.SecurityUtils;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.accessMocks.ManagerAccessMock;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.PlayersView;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.views.components.utils.ButtonProvider.*;


public class PlayerForm extends FormLayout {

    private final PlayersView playersView;
    private final PlayerService playerService;
    private final TeamService teamService;

    private final TextField email = new TextField("Email");
    private final PasswordField password = new PasswordField("Password");
    private final TextField name = new TextField("Name");
    private final TextField surname = new TextField("Surname");
    private final TextField address = new TextField("Address");
    private final TextField pesel = new TextField("PESEL");
    private final TextField phoneNumber = new TextField("Phone Number");
    private final ComboBox<Position> position = new ComboBox<>("Position");
    private final MultiselectComboBox<Team> teams = new MultiselectComboBox<>("Teams");
    private final DatePicker birthDate = new DatePicker("Birthdate");
    private final IntegerField number = new IntegerField("Number");

    Notification validationErrorNotification = createValidationErrorNotification();

    private final Button save = createDeleteButton();
    private final Button delete = createSaveButton();

    private final BeanValidationBinder<PlayerDTO> binder = new BeanValidationBinder<>(PlayerDTO.class);

    public PlayerForm(PlayersView playersView, PlayerService playerService, TeamService teamService) {
        password.setValueChangeMode(ValueChangeMode.EAGER);
        name.setValueChangeMode(ValueChangeMode.EAGER);
        email.setValueChangeMode(ValueChangeMode.EAGER);
        surname.setValueChangeMode(ValueChangeMode.EAGER);
        address.setValueChangeMode(ValueChangeMode.EAGER);
        pesel.setValueChangeMode(ValueChangeMode.EAGER);
        phoneNumber.setValueChangeMode(ValueChangeMode.EAGER);
        number.setValueChangeMode(ValueChangeMode.EAGER);

        this.playerService = playerService;
        this.playersView = playersView;
        this.teamService = teamService;

        email.setErrorMessage("Please enter a valid email address");
        position.setItems(Position.values());
        teams.setItems(teamService.findAll());

        HorizontalLayout buttons = new HorizontalLayout(save, delete, validationErrorNotification);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        save.addClickListener(buttonClickEvent -> save());
        delete.addClickListener(buttonClickEvent -> delete());

        binder.bindInstanceFields(this);

        if (SecurityUtils.isAccessGranted(ManagerAccessMock.class)) {
            add(email, password, pesel, name, surname, address, birthDate, phoneNumber, position, number, teams, buttons);
        } else {
            add(position, number, name, surname, teams, validationErrorNotification);
        }
    }

    public void setPlayer(PlayerDTO player) {
        binder.setBean(player);

        if (player == null) {
            setVisible(false);
        } else {
            setVisible(true);
            name.focus();
        }
    }

    private void save() {
        if (binder.validate().isOk()) {
            PlayerDTO player = binder.getBean();
            playerService.save(player);
            playersView.updateList();
            setPlayer(null);
        } else {
            validationErrorNotification.open();
        }
    }

    private void delete() {
        PlayerDTO playerDTO = binder.getBean();
        playerService.delete(playerDTO);
        playersView.updateList();
        setPlayer(null);
    }
}
