package de.hsh.larry.calendar.views.editorViews;

import de.hsh.larry.calendar.logic.Editor;
import de.hsh.larry.calendar.logic.EntryEditor;
import de.hsh.larry.calendar.models.Calendar;
import de.hsh.larry.calendar.models.Rhythm;
import de.hsh.larry.calendar.models.ToDo;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This class extends the functionality of the EntryEditor to the needs of to-dos.
 * It provides methods to set up the view and above all
 * overrides the <code>setEntryDetails</code>-method to set to-do-specific details.
 * It contains all input elements the user interacts with and provides methods to get and set inputs.
 *
 * @author Felix
 */
public class ToDoEditorView extends EntryEditorView {

    @FXML
    private TextField toDoTitle;
    @FXML
    private DatePicker toDoStartDate;
    @FXML
    private CheckBox toDoAllDay;
    @FXML
    private Label toDoStartTimeLabel;
    @FXML
    private ComboBox<String> toDoStartTime;
    @FXML
    private ChoiceBox<Rhythm> toDoRhythm;
    @FXML
    private TextArea toDoDescription;
    @FXML
    private ColorPicker toDoColor;
    @FXML
    private ChoiceBox<Calendar> toDoCalendar;

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
        setMondayAsFirstDayOfTheWeek(toDoStartDate);
        setUpToDoAllDay();
        setUpToDoStartTime();
        setUpRhythmChoiceBox(toDoRhythm);
        setUpCalendarChoiceBox(toDoCalendar);
        setUpColorPicker(toDoColor, toDoCalendar);
    }

    /**
     * Updates the details displayed in the view.
     * This method is only called when editing an existing Entry.
     */
    @Override
    public void setEntryDetails() {
        if (!(entry instanceof ToDo)) {
            return;
        }

        super.setEntryDetails();
        ToDo toDo = (ToDo) entry;
        toggleStartTime(toDo.isAllDay());
    }

    /**
     * Configures the CheckBox to allow inputs for the start time depending on whether the CheckBox
     * is selected. Sets up a listener to handle changes.
     */
    private void setUpToDoAllDay() {
        toDoAllDay.setOnAction(event -> toggleStartTime(toDoAllDay.isSelected()));
    }

    /**
     * Enables the inputs for the start time depending on the given boolean.
     *
     * @param status            input is allowed if true; otherwise not
     */
    private void toggleStartTime (boolean status) {
        toggleNodeDisabled(toDoStartTimeLabel, status);
        toggleNodeDisabled(toDoStartTime, status);
    }

    /**
     * Configures the time ComboBox with options between 00:00 and 23:30 in 30 minute intervals.
     * Sets the start time to the next half or full hour.
     */
    private void setUpToDoStartTime() {
        setUpTimeComboBox(toDoStartTime);
        LocalTime startTime = calculateNextHalfHour(LocalTime.now());
        toDoStartTime.getSelectionModel().select(startTime.toString());
    }

    // - - - GETTER & SETTER - - - START - - -

    @Override
    public Calendar getEntryCalendar() {
        return toDoCalendar.getValue();
    }

    @Override
    public void setEntryCalendar(Calendar calendar) {
        toDoCalendar.setValue(calendar);
    }

    @Override
    public String getEntryTitle() {
        return toDoTitle.getText();
    }

    @Override
    public void setEntryTitle(String title) {
        toDoTitle.setText(title);
    }

    @Override
    public String getEntryDescription() {
        return toDoDescription.getText();
    }

    @Override
    public void setEntryDescription(String description) {
        toDoDescription.setText(description);
    }

    @Override
    public LocalDate getEntryStartDate() {
        return LocalDate.parse(toDoStartDate.getValue().toString());
    }

    @Override
    public void setEntryStartDate(LocalDate date) {
        toDoStartDate.setValue(date);
    }

    @Override
    public boolean isEntryAllDay() {
        return toDoAllDay.isSelected();
    }

    @Override
    public void setEntryAllDay(boolean allDay) {
        toDoAllDay.setSelected(allDay);
    }

    @Override
    public LocalTime getEntryStartTime() {
        return LocalTime.parse(toDoStartTime.getValue());
    }

    @Override
    public void setEntryStartTime(LocalTime time) {
        toDoStartTime.setValue(time.toString());
    }

    @Override
    public Rhythm getEntryRhythm() {
        return toDoRhythm.getValue();
    }

    @Override
    public void setEntryRhythm(Rhythm rhythm) {
        toDoRhythm.setValue(rhythm);
    }

    @Override
    public Color getEntryColor() {
        return toDoColor.getValue();
    }

    @Override
    public void setColorPickerToCalendarColor(Color color) {
        toDoColor.setValue(color);
    }

    // - - - GETTER & SETTER - - - END - - -

}
