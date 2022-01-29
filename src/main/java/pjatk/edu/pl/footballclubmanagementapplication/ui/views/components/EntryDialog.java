package pjatk.edu.pl.footballclubmanagementapplication.ui.views.components;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.Data;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.Timezone;

import java.time.LocalDateTime;
import java.util.Map;

public class EntryDialog extends Dialog {

    private final DialogEntry dialogEntry;
    private final DateTimePicker fieldStart;
    private final DateTimePicker fieldEnd;
    private final Binder<DialogEntry> binder;

    public EntryDialog(FullCalendar fullCalendar, Entry entry) {

        binder = new Binder<>(DialogEntry.class);

        HorizontalLayout customPropertiesLayout = new HorizontalLayout();


        this.dialogEntry = DialogEntry.of(entry, fullCalendar.getTimezone());
        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);
        TextField fieldTitle = new TextField("Title");
        fieldTitle.setReadOnly(true);
        TextArea fieldDescription = new TextArea("Description");
        fieldDescription.setReadOnly(true);
        fieldStart = new DateTimePicker("Start");
        fieldStart.setReadOnly(true);
        fieldEnd = new DateTimePicker("End");
        fieldEnd.setReadOnly(true);
        Checkbox fieldAllDay = new Checkbox("All day event");
        fieldAllDay.setReadOnly(true);

        dialogEntry.getCustomProperties().entrySet().stream().forEach(property -> {
            TextField textField = new TextField(property.getKey());
            textField.setReadOnly(true);
            textField.setValue(property.getValue().toString());
            customPropertiesLayout.add(textField);
        });
        binder.forField(fieldTitle).bind(DialogEntry::getTitle, DialogEntry::setTitle);
        binder.forField(fieldDescription).bind(DialogEntry::getDescription, DialogEntry::setDescription);
        binder.forField(fieldStart).bind(DialogEntry::getStart, DialogEntry::setStart);
        binder.forField(fieldEnd).bind(DialogEntry::getEnd, DialogEntry::setEnd);
        binder.forField(fieldAllDay).bind(DialogEntry::getAllDay, DialogEntry::setAllDay);

        binder.setBean(dialogEntry);

        VerticalLayout entryLayout = new VerticalLayout(fieldTitle, fieldDescription,
                new HorizontalLayout(fieldAllDay,
                        fieldStart, fieldEnd), customPropertiesLayout);

        entryLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);
        entryLayout.setSizeFull();

        entryLayout.getElement().getStyle().set("overflow-y", "auto");

        add(entryLayout);
    }

    @Data
    private static class DialogEntry {
        private String id;
        private String title;
        private String color;
        private String description;
        private LocalDateTime start;
        private LocalDateTime end;
        private Boolean allDay;
        private Timezone timezone;

        private Map<String, Object> customProperties;

        public static DialogEntry of(Entry entry, Timezone timezone) {
            DialogEntry dialogEntry = new DialogEntry();

            dialogEntry.setCustomProperties(entry.getCustomProperties());
            dialogEntry.setTimezone(timezone);

            dialogEntry.setTitle(entry.getTitle());
            dialogEntry.setColor(entry.getColor());
            dialogEntry.setDescription(entry.getDescription());
            dialogEntry.setAllDay(entry.isAllDay());


            dialogEntry.setStart(entry.getStart(timezone));
            dialogEntry.setEnd(entry.getEnd(timezone));


            return dialogEntry;
        }
    }
}
