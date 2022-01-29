package pjatk.edu.pl.footballclubmanagementapplication.ui.views.components.utils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class ButtonProvider {

    public static Button createDeleteButton(){
        Button saveButton = new Button("Save");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return saveButton;
    }

    public static Button createSaveButton(){
        Button deleteButton = new Button("Delete");
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        return deleteButton;
    }

    public static Notification createValidationErrorNotification(){
        Notification validationErrorNotification = new Notification();
        validationErrorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        validationErrorNotification.setText("Form data validation didn't pass");
        validationErrorNotification.setDuration(3000);
        return validationErrorNotification;
    }
}
