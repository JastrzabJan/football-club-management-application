package pjatk.edu.pl.footballclubmanagementapplication.ui.views.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Match;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Team;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.MatchService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.TeamService;
import pjatk.edu.pl.footballclubmanagementapplication.security.SecurityUtils;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.CoachesView;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.MatchesView;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.views.components.utils.ButtonProvider.*;

public class MatchForm extends FormLayout {

    private final MatchesView matchesView;
    private final MatchService matchService;
    private final TeamService teamService;

    private final TextField opponentTeam = new TextField("Opponent Team");
    private final TextField place = new TextField("Place");
    private final IntegerField goalsHome = new IntegerField("Goals");
    private final IntegerField goalsAway = new IntegerField("Opponent Goals");
    private final DateTimePicker gameDate = new DateTimePicker("Game Day");
    private final ComboBox<Team> team = new ComboBox<>("Team");

    Notification validationErrorNotification = createValidationErrorNotification();

    private final Button save = createDeleteButton();
    private final Button delete = createSaveButton();

    private final BeanValidationBinder<Match> binder = new BeanValidationBinder<>(Match.class);

    public MatchForm(MatchesView matchesView, MatchService matchService, TeamService teamService) {
        this.matchesView = matchesView;
        this.matchService = matchService;
        this.teamService = teamService;

        team.setItems(teamService.findAll());

        place.setValueChangeMode(ValueChangeMode.EAGER);
        goalsAway.setValueChangeMode(ValueChangeMode.EAGER);
        goalsHome.setValueChangeMode(ValueChangeMode.EAGER);
        opponentTeam.setValueChangeMode(ValueChangeMode.EAGER);

        binder.bindInstanceFields(this);
//        binder.bind(opponentTeam, Match::getOpponentTeam, Match::setOpponentTeam);
//        binder.bind(place, Match::getPlace, Match::setPlace);
//        binder.bind(goalsHome, Match::getGoalsHome, Match::setGoalsHome);
//        binder.bind(goalsAway, Match::getGoalsAway, Match::setGoalsAway);
//        binder.bind(team, Match::getTeam, Match::setTeam);

        HorizontalLayout buttons = new HorizontalLayout(save, delete, validationErrorNotification);

        if (SecurityUtils.isAccessGranted(CoachesView.class)) {
            add(team, opponentTeam, place, goalsHome, goalsAway, gameDate, buttons);
        } else {
            add(team, opponentTeam, place, goalsHome, goalsAway, gameDate);
        }

        save.addClickListener(buttonClickEvent -> save());
        delete.addClickListener(buttonClickEvent -> delete());
    }

    public void setMatch(Match match) {
        binder.setBean(match);

        if (match == null) {
            setVisible(false);
        } else {
            setVisible(true);
            team.focus();
        }
    }

    private void save() {
        if (binder.validate().isOk()) {
            Match match = binder.getBean();
            matchService.save(match);
            matchesView.updateList();
            setMatch(null);
        }else{
            validationErrorNotification.open();
        }
    }

    private void delete() {
        Match match = binder.getBean();
        matchService.delete(match);
        matchesView.updateList();
        setMatch(null);
    }
}
