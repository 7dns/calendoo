package de.hsh.larry.calendar.views.dialogues;

import de.hsh.larry.calendar.logic.CalendarEditor;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
/**
 * Controller class for the CalendarEditorView.
 * Handles user input and interactions with the CalendarEditor logic.
 *
 * @author Felix
 */
public class CalendarEditorView {

    @FXML
    private Label title;
    @FXML
    private TextField newCalendarName;
    @FXML
    private ColorPicker newCalendarColor;

    private CalendarEditor calendarCreator;

    /**
     * Initializes the CalendarEditorView with a reference to the CalendarEditor logic.
     *
     * @param calendarCreator           The CalendarEditor instance that manages the calendar logic
     */
    public void initialize(CalendarEditor calendarCreator) {
        this.calendarCreator = calendarCreator;
    }

    public void setTitle(String newTitle) {
        title.setText(newTitle);
    }

    public String getNewCalendarName() {
        return newCalendarName.getText();
    }

    public Color getNewCalendarColor() {
        return newCalendarColor.getValue();
    }

    public void setCalendarName(String calendarName) {
        newCalendarName.setText(calendarName);
    }

    public void setCalendarColor(Color calendarColor) {
        newCalendarColor.setValue(calendarColor);
    }

    /**
     * Invokes the CalendarEditor's saveCalendar method to persist changes.
     */
    @FXML
    private void saveNewCalendar () {
        calendarCreator.saveCalendar();
    }

}
