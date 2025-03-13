package de.hsh.larry.calendar.views.detailedViews;

import de.hsh.larry.calendar.logic.Manager;
import de.hsh.larry.calendar.models.Entry;
import de.hsh.larry.calendar.models.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.time.LocalDate;

/**
 * The EventDetailedView class provides the methods an Event needs in their DetailedView.
 * It provides Methods for updating its specific information.
 *
 * @author Felix
 */
public class EventDetailedView extends EntryDetailedView {

    @FXML
    private GridPane gridPane;
    @FXML
    private Region edit;
    @FXML
    private Region delete;
    @FXML
    private Region close;
    @FXML
    private Rectangle color;
    @FXML
    private Label titleLabel;
    @FXML
    private Label dateAndTimeLabel;
    @FXML
    private Label rhythmLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label calendarLabel;

    private Event event;

    /**
     * Initializes a new EventDetailedView.
     *
     * @param entry     The Entry to be displayed.
     * @param manager   The Manager to handle user interactions.
     * @param popUp     The Stage the Editor is going to be on.
     * @param date      The date of the Entry to be displayed.
     */
    @Override
    public void initialize(Entry entry, Manager manager, Stage popUp, LocalDate date) {
        super.initialize(entry, manager, popUp, date);
        this.event = (Event) entry;
        setUpButtons(edit, delete, close, entry);
    }

    /**
     * Reloads the EventDetailedView and updates it with the newest data.
     */
    @Override
    public void reload() {
        updateColor();
        updateLabelText(titleLabel, event.getTitle());
        updateDateAndTime(dateAndTimeLabel, event, date);
        updateLabelText(rhythmLabel, event.getRhythm().toString());
        updateLocation();
        updateDescription(descriptionLabel, event, gridPane, 3);
        updateLabelText(calendarLabel, event.getCalendar().toString());
    }

    /**
     * Updates the Color of a Rectangle of the EventDetailedView.
     */
    private void updateColor() {
        color.setFill(event.getColor());
    }

    /**
     * Updates the Label of the Location of the EventDetailedView.
     */
    private void updateLocation() {
        if (event.hasLocation()) {
            updateLabelText(locationLabel, event.getLocation());
        } else {
            removeRow(gridPane, 2);
        }
    }

}
