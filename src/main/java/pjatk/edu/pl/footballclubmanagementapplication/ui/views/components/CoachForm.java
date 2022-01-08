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
import com.vaadin.flow.data.value.ValueChangeMode;
import org.vaadin.gatanaso.MultiselectComboBox;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Qualifications;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Team;
import pjatk.edu.pl.footballclubmanagementapplication.backend.dto.CoachDTO;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.CoachService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.TeamService;
import pjatk.edu.pl.footballclubmanagementapplication.security.SecurityUtils;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.CoachesView;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.UsersView;

public class CoachForm extends FormLayout {

    private CoachesView coachesView;
    private final CoachService coachService;
    private final TeamService teamService;

    private EmailField email = new EmailField("Email");
    private PasswordField password = new PasswordField("Password");
    private TextField name = new TextField("Name");
    private TextField surname = new TextField("Surname");
    private TextField address = new TextField("Address");
    private TextField phoneNumber = new TextField("Phone Number");
    private ComboBox<Qualifications> qualifications = new ComboBox<>("Qualifications");
    private MultiselectComboBox<Team> teams = new MultiselectComboBox<>("Teams");
    private DatePicker birthDate = new DatePicker("Birthdate");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private final Binder<CoachDTO> binder = new BeanValidationBinder<>(CoachDTO.class);

    public CoachForm(CoachesView coachesView, CoachService coachService, TeamService teamService) {

        save.setEnabled(false);
        this.coachService = coachService;
        this.coachesView = coachesView;
        this.teamService = teamService;

        qualifications.setItems(Qualifications.values());
        teams.setItems(teamService.findAll());

        password.setRequired(true);
        name.setRequired(true);
        surname.setRequired(true);
        address.setRequired(true);
        phoneNumber.setRequired(true);
        qualifications.setRequired(true);
        teams.setRequired(true);
        birthDate.setRequired(true);

        binder.bindInstanceFields(this);

        binder.addStatusChangeListener(event -> {
            final boolean isValid = !event.hasValidationErrors();
            save.setEnabled(isValid);
        });

        HorizontalLayout buttons = new HorizontalLayout(save, delete);

        if (SecurityUtils.isAccessGranted(UsersView.class)) {
            add(email, password, name, surname, address, birthDate, phoneNumber, qualifications, teams, buttons);
        } else {
            add(name, surname, birthDate, qualifications);
        }

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        save.addClickListener(buttonClickEvent -> save());
        delete.addClickListener(buttonClickEvent -> delete());
    }

    public void setCoach(CoachDTO coach) {
        binder.setBean(coach);
        if (coach == null) {
            setVisible(false);
        } else {
            setVisible(true);
            name.focus();
        }
    }

    private void save() {
        CoachDTO coach = binder.getBean();
        coachService.save(coach);
        coachesView.updateList();
        setCoach(null);
    }

    private void delete() {
        CoachDTO coachDTO = binder.getBean();
        coachService.delete(coachDTO);
        coachesView.updateList();
        setCoach(null);
    }
}