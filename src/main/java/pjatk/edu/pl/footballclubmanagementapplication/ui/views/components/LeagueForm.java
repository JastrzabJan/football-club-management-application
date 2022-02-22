package pjatk.edu.pl.footballclubmanagementapplication.ui.views.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.League;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.LeagueService;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.LeaguesView;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.views.components.utils.ButtonProvider.*;

public class LeagueForm extends FormLayout {

    private final LeaguesView leaguesView;
    private final LeagueService leagueService;

    private final TextField name = new TextField("Name");

    Notification validationErrorNotification = createValidationErrorNotification();

    private final Button save = createDeleteButton();
    private final Button delete = createSaveButton();

    private final Button viewLeagueButton = new Button ("View");
    private final BeanValidationBinder<League> binder = new BeanValidationBinder<>(League.class);

    public LeagueForm(LeaguesView leaguesView, LeagueService leagueService) {

        this.leagueService = leagueService;
        this.leaguesView = leaguesView;

        HorizontalLayout buttons = new HorizontalLayout(save, delete, validationErrorNotification);
        viewLeagueButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        add(name, buttons);

        binder.bindInstanceFields(this);
        save.addClickListener(buttonClickEvent -> save());
        delete.addClickListener(buttonClickEvent -> delete());
    }

    public void setLeague(League league) {
        binder.setBean(league);
        if (league == null) {
            setVisible(false);
        } else {
            setVisible(true);
            name.focus();
        }
    }

    private void save() {
        if(binder.validate().isOk()) {
            League league = binder.getBean();
            leagueService.save(league);
            leaguesView.updateList();
            setLeague(null);
        }else{
            validationErrorNotification.open();
        }
    }

    private void delete() {
        League league = binder.getBean();
        leagueService.delete(league);
        leaguesView.updateList();
        setLeague(null);
    }
}
