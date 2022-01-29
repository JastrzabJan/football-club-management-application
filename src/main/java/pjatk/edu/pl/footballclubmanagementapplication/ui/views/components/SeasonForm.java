package pjatk.edu.pl.footballclubmanagementapplication.ui.views.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Season;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.SeasonService;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities.SeasonsView;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.views.components.utils.ButtonProvider.*;

public class SeasonForm extends FormLayout {

    private final SeasonsView seasonsView;
    private final SeasonService seasonService;

    private final DatePicker startDate = new DatePicker("Start Date");
    private final DatePicker endDate = new DatePicker("End Date");

    Notification validationErrorNotification = createValidationErrorNotification();

    private final Button save = createDeleteButton();
    private final Button delete = createSaveButton();

    private final BeanValidationBinder<Season> binder = new BeanValidationBinder<>(Season.class);

    public SeasonForm(SeasonsView seasonsView, SeasonService seasonService) {
        this.seasonsView = seasonsView;
        this.seasonService = seasonService;


        binder.addStatusChangeListener(event -> {
            final boolean isValid = !event.hasValidationErrors();
            save.setEnabled(isValid);
        });

        binder.bindInstanceFields(this);

        HorizontalLayout buttons = new HorizontalLayout(save, delete, validationErrorNotification);

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
        if(binder.validate().isOk()) {
            Season season = binder.getBean();
            seasonService.save(season);
            seasonsView.updateList();
            setSeason(null);
        }else{
            validationErrorNotification.open();
        }
    }

    private void delete() {
        Season season = binder.getBean();
        seasonService.delete(season);
        seasonsView.updateList();
        setSeason(null);
    }

}
