package pjatk.edu.pl.footballclubmanagementapplication.ui.views.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.League;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.LeagueService;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.LeaguesView;

public class LeagueForm extends FormLayout {

    private LeaguesView leaguesView;
    private final LeagueService leagueService;

    private TextField name = new TextField("Name");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button viewLeagueButton = new Button ("View");
    private final Binder<League> binder = new BeanValidationBinder<>(League.class);

    public LeagueForm(LeaguesView leaguesView, LeagueService leagueService) {

        this.leagueService = leagueService;
        this.leaguesView = leaguesView;
        HorizontalLayout buttons = new HorizontalLayout(save, delete, viewLeagueButton);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        viewLeagueButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

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
        League league = binder.getBean();
        leagueService.save(league);
        leaguesView.updateList();
        setLeague(null);
    }

    private void delete() {
        League league = binder.getBean();
        leagueService.delete(league);
        leaguesView.updateList();
        setLeague(null);
    }
}
