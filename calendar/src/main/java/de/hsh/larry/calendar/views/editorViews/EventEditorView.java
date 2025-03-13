package de.hsh.larry.calendar.views.editorViews;

import de.hsh.larry.calendar.logic.Editor;
import de.hsh.larry.calendar.logic.EntryEditor;
import de.hsh.larry.calendar.models.Calendar;
import de.hsh.larry.calendar.models.Event;
import de.hsh.larry.calendar.models.Rhythm;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This class extends the functionality of the EntryEditor to the needs of Events.
 * It provides methods to set up the view and above all
 * overrides the <code>setEntryDetails</code>-method to set Event-specific details.
 * It contains all input elements the user interacts with and provides methods to get and set inputs.
 *
 * @author Felix, Laura
 */
public class EventEditorView extends EntryEditorView {

    @FXML
    private TextField eventTitle;
    @FXML
    private DatePicker eventStartDate;
    @FXML
    private CheckBox eventAllDay;
    @FXML
    private Label eventStartTimeLabel;
    @FXML
    private ComboBox<String> eventStartTime;
    @FXML
    private Label eventEndTimeLabel;
    @FXML
    private ComboBox<String> eventEndTime;
    @FXML
    private ChoiceBox<Rhythm> eventRhythm;
    @FXML
    private TextField eventLocation;
    @FXML
    private TextArea eventDescription;
    @FXML
    private ColorPicker eventColor;
    @FXML
    private ChoiceBox<Calendar> eventCalendar;
    @FXML
    private CheckBox syncGoogle;

    /**
     * Initializes the view with the provided Editor and EntryEditor.
     * Sets up the view to manage user input.
     *
     * @param editor            the Editor instance
     * @param entryEditorView   the specific EntryEditor handling the logic
     */
    @Override
    public void initialize(Editor editor, EntryEditor entryEditorView) {
        super.initialize(editor, entryEditorView);
        setMondayAsFirstDayOfTheWeek(eventStartDate);
        setUpEventAllDay();
        setUpEventStartAndEndTime();
        setUpRhythmChoiceBox(eventRhythm);
        setUpCalendarChoiceBox(eventCalendar);
        setUpColorPicker(eventColor, eventCalendar);
    }

    /**
     * Updates the details displayed in the view. Extends the method of the super class to include
     * an end time, an event location and to be synchronized with the Google Calendar.
     * This method is only called when editing an existing Entry.
     */
    @Override
    public void setEntryDetails() {
        if (!(entry instanceof Event)) {
            return;
        }

        super.setEntryDetails();
        Event event = (Event) entry;
        toggleStartAndEndTime(event.isAllDay());

        if (!event.isAllDay()) {
            eventStartTime.setValue(event.getStartTime().toString());
            eventEndTime.setValue(event.getEndTime().toString());
        }

        eventLocation.setText(event.getLocation());
        syncGoogle.setSelected(event.isGoogleEvent());
    }

    /**
     * Configures the CheckBox to allow inputs for start and end time depending on whether the CheckBox
     * is selected. Sets up a listener to handle changes.
     */
    private void setUpEventAllDay() {
        eventAllDay.setOnAction(event -> toggleStartAndEndTime(eventAllDay.isSelected()));
    }

    /**
     * Enables the inputs for start and end time depending on the given boolean.
     *
     * @param status            input is allowed if true; otherwise not
     */
    private void toggleStartAndEndTime(boolean status) {
        toggleNodeDisabled(eventStartTimeLabel, status);
        toggleNodeDisabled(eventStartTime, status);
        toggleNodeDisabled(eventEndTimeLabel, status);
        toggleNodeDisabled(eventEndTime, status);
    }

    /**
     * Configures the time ComboBoxes with options between 00:00 and 23:30 in 30 minute intervals.
     * Sets the start time to the next half or full hour and the end time to 30 minutes after that.
     */
    private void setUpEventStartAndEndTime() {
        setUpTimeComboBox(eventStartTime);
        setUpTimeComboBox(eventEndTime);

        LocalTime startTime = calculateNextHalfHour(LocalTime.now());
        LocalTime endTime = startTime.plusMinutes(30);

        eventStartTime.getSelectionModel().select(startTime.toString());
        eventEndTime.getSelectionModel().select(endTime.toString());
    }

    // - - - GETTER & SETTER - - - START - - -

    @Override
    public Calendar getEntryCalendar() {
        return eventCalendar.getValue();
    }

    @Override
    public void setEntryCalendar(Calendar calendar) {
        eventCalendar.setValue(calendar);
    }

    @Override
    public String getEntryTitle() {
        return eventTitle.getText();
    }

    @Override
    public void setEntryTitle(String title) {
        eventTitle.setText(title);
    }

    @Override
    public String getEntryDescription() {
        return eventDescription.getText();
    }

    @Override
    public void setEntryDescription(String description) {
        eventDescription.setText(description);
    }

    @Override
    public LocalDate getEntryStartDate() {
        return LocalDate.parse(eventStartDate.getValue().toString());
    }

    @Override
    public void setEntryStartDate(LocalDate date) {
        eventStartDate.setValue(date);
    }

    @Override
    public boolean isEntryAllDay() {
        return eventAllDay.isSelected();
    }

    @Override
    public void setEntryAllDay(boolean allDay) {
        eventAllDay.setSelected(allDay);
    }

    @Override
    public LocalTime getEntryStartTime() {
        return LocalTime.parse(eventStartTime.getValue());
    }

    @Override
    public void setEntryStartTime(LocalTime startTime) {
        eventStartTime.setValue(startTime.toString());
    }

    @Override
    public LocalTime getEntryEndTime() {
        return LocalTime.parse(eventEndTime.getValue());
    }

    public void setEntryEndTime(LocalTime endTime) {
        eventEndTime.setValue(endTime.toString());
    }

    @Override
    public Rhythm getEntryRhythm() {
        return eventRhythm.getValue();
    }

    @Override
    public void setEntryRhythm(Rhythm rhythm) {
        eventRhythm.setValue(rhythm);
    }

    @Override
    public String getEntryLocation() {
        return eventLocation.getText();
    }

    public void setEntryLocation(String location) {
        eventLocation.setText(location);
    }

    @Override
    public Color getEntryColor() {
        return eventColor.getValue();
    }

    @Override
    public void setColorPickerToCalendarColor(Color color) {
        eventColor.setValue(color);
    }

    @Override
    public boolean getAddToGoogle() {
        return syncGoogle.isSelected();
    }

    public void setAddToGoogle(boolean addToGoogle) {
        syncGoogle.setSelected(addToGoogle);
    }

    // - - - GETTER & SETTER - - - END - - -

}
