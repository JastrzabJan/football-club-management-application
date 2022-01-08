package pjatk.edu.pl.footballclubmanagementapplication.ui.views.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import org.vaadin.gatanaso.MultiselectComboBox;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Position;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Team;
import pjatk.edu.pl.footballclubmanagementapplication.backend.dto.PlayerDTO;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.PlayerService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.TeamService;
import pjatk.edu.pl.footballclubmanagementapplication.security.SecurityUtils;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.PlayersView;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.UsersView;


public class PlayerForm extends FormLayout {

    private PlayersView playersView;
    private final PlayerService playerService;
    private final TeamService teamService;

    private EmailField email = new EmailField("Email");
    private PasswordField password = new PasswordField("Password");
    private TextField name = new TextField("Name");
    private TextField surname = new TextField("Surname");
    private TextField address = new TextField("Address");
    private TextField pesel = new TextField("PESEL");
    private TextField phoneNumber = new TextField("Phone Number");
    private ComboBox<Position> position = new ComboBox<>("Position");
    private MultiselectComboBox<Team> teams = new MultiselectComboBox<>("Teams");
    private DatePicker birthDate = new DatePicker("Birthdate");
    private IntegerField number = new IntegerField("Number");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Binder<PlayerDTO> binder = new BeanValidationBinder<>(PlayerDTO.class);

    public PlayerForm(PlayersView playersView, PlayerService playerService, TeamService teamService) {
        this.playerService = playerService;
        this.playersView = playersView;
        this.teamService = teamService;

        email.setErrorMessage("Please enter a valid email address");
        position.setItems(Position.values());
        teams.setItems(teamService.findAll());
        binder.forField(password).bind(PlayerDTO::getPassword, PlayerDTO::setPassword);
        binder.bind(password, PlayerDTO::getPassword, PlayerDTO::setPassword);
        binder.bind(pesel, PlayerDTO::getPESEL, PlayerDTO::setPESEL);
        binder.bind(name, PlayerDTO::getName, PlayerDTO::setName);
        binder.bind(surname, PlayerDTO::getSurname, PlayerDTO::setSurname);
        binder.bind(address, PlayerDTO::getAddress, PlayerDTO::setAddress);
        binder.bind(birthDate, PlayerDTO::getBirthDate, PlayerDTO::setBirthDate);
        binder.bind(phoneNumber, PlayerDTO::getPhoneNumber, PlayerDTO::setPhoneNumber);
        binder.bind(position, PlayerDTO::getPosition, PlayerDTO::setPosition);
        binder.bind(teams, PlayerDTO::getTeams, PlayerDTO::setTeams);
        binder.bind(number, PlayerDTO::getNumber, PlayerDTO::setNumber);

        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        if(SecurityUtils.isAccessGranted(UsersView.class)) {
            add(email, password, pesel, name, surname, address, birthDate, phoneNumber, position, number, teams, buttons);
        }else {
            add(position, number);
        }
        binder.bindInstanceFields(this);
        save.addClickListener(buttonClickEvent -> save());
        delete.addClickListener(buttonClickEvent -> delete());
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
        PlayerDTO player = binder.getBean();
        playerService.save(player);
        playersView.updateList();
        setPlayer(null);
    }

    private void delete() {
        PlayerDTO playerDTO = binder.getBean();
        playerService.delete(playerDTO);
        playersView.updateList();
        setPlayer(null);
    }
}
