package pjatk.edu.pl.footballclubmanagementapplication.ui.views.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Season;
import pjatk.edu.pl.footballclubmanagementapplication.backend.dto.PlayerDTO;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.SeasonService;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.SeasonsView;

public class SeasonForm extends FormLayout {

    private SeasonsView seasonsView;
    private final SeasonService seasonService;

    private DatePicker startDate = new DatePicker("Start Date");
    private DatePicker endDate = new DatePicker("End Date");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Binder<Season> binder = new BeanValidationBinder<>(Season.class);

    public SeasonForm(SeasonsView seasonsView, SeasonService seasonService) {
        this.seasonsView = seasonsView;
        this.seasonService = seasonService;

        binder.bindInstanceFields(this);

        binder.addStatusChangeListener(event -> {
            final boolean isValid = !event.hasValidationErrors();
            save.setEnabled(isValid);
        });

        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(startDate, endDate, buttons);
        save.addClickListener(buttonClickEvent -> save());
        delete.addClickListener(buttonClickEvent -> delete());
    }

    public void setSeason(Season season) {
        binder.setBean(season);

        if (season == null) {
            setVisible(false);
        } else {
            setVisible(true);
            startDate.focus();
        }
    }

    private void save() {
        Season season = binder.getBean();
        seasonService.save(season);
        seasonsView.updateList();
        setSeason(null);
    }

    private void delete() {
        Season season = binder.getBean();
        seasonService.delete(season);
        seasonsView.updateList();
        setSeason(null);
    }

}
