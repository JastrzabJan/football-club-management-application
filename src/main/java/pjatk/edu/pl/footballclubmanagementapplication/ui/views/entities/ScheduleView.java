package pjatk.edu.pl.footballclubmanagementapplication.ui.views.entities;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.stefan.fullcalendar.*;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Match;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.Training;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.MatchService;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.TrainingService;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.MainLayout;
import pjatk.edu.pl.footballclubmanagementapplication.ui.views.components.EntryDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static pjatk.edu.pl.footballclubmanagementapplication.ui.utils.FrontendConstants.*;


@Secured({ADMIN_ROLE, MANAGER_ROLE, COACH_ROLE, PLAYER_ROLE})
@Route(value = PAGE_SCHEDULE, layout = MainLayout.class)
public class ScheduleView extends VerticalLayout {

    private final TrainingService trainingService;
    private final MatchService matchService;
    private Button buttonDatePicker;
    private FormLayout toolbar;
    private ComboBox<CalendarView> comboBoxView;
    private final FullCalendar calendar;

    public ScheduleView(TrainingService trainingService, MatchService matchService) {
        this.trainingService = trainingService;
        this.matchService = matchService;

        createToolbar();
        add(toolbar);

        HorizontalLayout calendarContainer = new HorizontalLayout();
        calendar = FullCalendarBuilder.create().withAutoBrowserTimezone().build();

        calendarContainer.add(calendar);
        calendarContainer.setFlexGrow(1, calendar);

        calendarContainer.setVisible(true);
        calendar.setVisible(true);
        calendar.setHeightAuto();
        calendar.setWidthFull();

        calendar.addEntryClickedListener(event -> {
            if (event.getEntry().getRenderingMode() != Entry.RenderingMode.BACKGROUND && event.getEntry().getRenderingMode() != Entry.RenderingMode.INVERSE_BACKGROUND)
                new EntryDialog(calendar, event.getEntry()).open();
        });

        calendarContainer.getStyle().set("background-color", "#999999");

        setSizeFull();

        trainingService.getAllTrainings().stream().forEach(training -> {
            addEntryToCalendarForTraining(training);
        });
        matchService.getAllMatches().stream().forEach(match -> {
            addEntryToCalendarForMatch(match);
        });
        this.add(calendarContainer);
    }

    private void addEntryToCalendarForTraining(Training training) {
        Entry entry = new Entry();
        entry.setTitle(training.getTeam().getName() + " Training");
        entry.setStart(training.getTrainingStart());
        entry.setColor("#93c47d");
        entry.setCustomProperty("Coach", training.getCoach());
        entry.setCustomProperty("Training Type", training.getTrainingType());
        calendar.addEntry(entry);
    }

    private void addEntryToCalendarForMatch(Match match) {
        Entry entry = new Entry();
        entry.setTitle(match.getTeam().getName() + " Match vs " + match.getOpponentTeam());
        entry.setStart(match.getGameDate());
        entry.setAllDay(true);
        entry.setCustomProperty("Team", match.getTeam());
        entry.setCustomProperty("Opponent Team", match.getOpponentTeam());
        entry.setCustomProperty("Place", match.getPlace());
        entry.setColor("#8e7cc3");
        calendar.addEntry(entry);
    }

    private void createToolbar() {
        toolbar = new FormLayout();
        toolbar.getElement().getStyle().set("margin-top", "0px");
        toolbar.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1), new FormLayout.ResponsiveStep("25em", 4));

        FormLayout temporalLayout = new FormLayout();
        temporalLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));

        Button buttonToday = new Button("Today", VaadinIcon.HOME.create(), e -> calendar.today());
        buttonToday.setWidthFull();

        HorizontalLayout temporalSelectorLayout = new HorizontalLayout();

        Button buttonPrevious = new Button("", VaadinIcon.ANGLE_LEFT.create(), e -> calendar.previous());
        Button buttonNext = new Button("", VaadinIcon.ANGLE_RIGHT.create(), e -> calendar.next());
        buttonNext.setIconAfterText(true);

        // simulate the date picker light that we can use in polymer
        DatePicker gotoDate = new DatePicker();
        gotoDate.addValueChangeListener(event1 -> calendar.gotoDate(event1.getValue()));
        gotoDate.getElement().getStyle().set("visibility", "hidden");
        gotoDate.getElement().getStyle().set("position", "fixed");
        gotoDate.setWidth("0px");
        gotoDate.setHeight("0px");
        gotoDate.setWeekNumbersVisible(true);
        buttonDatePicker = new Button(VaadinIcon.CALENDAR.create());
        buttonDatePicker.getElement().appendChild(gotoDate.getElement());
        buttonDatePicker.addClickListener(event -> gotoDate.open());
        buttonDatePicker.setWidthFull();

        temporalSelectorLayout.add(buttonPrevious, buttonDatePicker, buttonNext, gotoDate);
        temporalSelectorLayout.setWidthFull();
        temporalSelectorLayout.setSpacing(false);

        temporalLayout.add(buttonToday, temporalSelectorLayout);
        temporalLayout.setWidthFull();

        List<CalendarView> calendarViews = new ArrayList<>(Arrays.asList(CalendarViewImpl.values()));
        calendarViews.sort(Comparator.comparing(CalendarView::getName));

        comboBoxView = new ComboBox<>("", calendarViews);
        comboBoxView.setValue(CalendarViewImpl.DAY_GRID_MONTH);
        comboBoxView.setWidthFull();
        comboBoxView.addValueChangeListener(e -> {
            CalendarView value = e.getValue();
            calendar.changeView(value == null ? CalendarViewImpl.DAY_GRID_MONTH : value);
        });
        comboBoxView.setWidthFull();


        FormLayout commandLayout = new FormLayout();
        commandLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));

        commandLayout.setWidthFull();

        toolbar.add(temporalLayout, comboBoxView, commandLayout);
    }

}
