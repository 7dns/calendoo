package de.hsh.larry.calendar.views.editorViews;

import de.hsh.larry.calendar.logic.Editor;
import de.hsh.larry.calendar.logic.EntryEditor;
import de.hsh.larry.calendar.models.Calendar;
import de.hsh.larry.calendar.models.Entry;
import de.hsh.larry.calendar.models.Profile;
import de.hsh.larry.calendar.models.Rhythm;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.paint.Color;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

/**
 * This abstract class represents a view for editing calendar entries.
 * Provides methods to initialize, configure, and manipulate entry details.
 *
 * @author Felix
 */
public abstract class EntryEditorView {

    Editor editor;
    Entry entry;
    private Profile profile;

    /**
     * Initializes the view with the provided Editor and EntryEditor.
     *
     * @param editor            the Editor instance
     * @param entryEditorView   the specific EntryEditor handling the logic
     */
    public void initialize(Editor editor, EntryEditor entryEditorView) {
        this.editor = editor;
        this.profile = entryEditorView.getProfile();
    }

    /**
     * Sets the current Entry being edited and updates its details in the view.
     *
     * @param entry             the calendar Entry to be edited
     */
    public void setEntry(Entry entry) {
        this.entry = entry;
        setEntryDetails();
    }

    /**
     * Updates the details displayed in the view.
     * This method is only called when editing an existing Entry.
     * Subclasses override this method to set their specific details.
     */
    void setEntryDetails() {
        setEntryTitle(entry.getTitle());
        setEntryStartDate(entry.getStartDate());
        setEntryAllDay(entry.isAllDay());

        if (!entry.isAllDay()) {
            setEntryStartTime(entry.getStartTime());
        }

        setEntryRhythm(entry.getRhythm());
        setEntryDescription(entry.getDescription());
        setEntryCalendar(entry.getCalendar());
        setColorPickerToCalendarColor(entry.getColor());
    }

    /**
     * Changes the locale of the given DatePicker to display Monday as the first day of the week.
     *
     * @param datePicker        the DatePicker to change the locale of
     */
    void setMondayAsFirstDayOfTheWeek(DatePicker datePicker) {
        datePicker.setOnShowing(e -> Locale.setDefault(Locale.Category.FORMAT, Locale.UK));
        datePicker.setValue(LocalDate.now());
    }

    /**
     * Configures the given ComboBox with options between 00:00 and 23:30 in 30 minute intervals.
     *
     * @param comboBox          the ComboBox to configure
     */
    void setUpTimeComboBox(ComboBox<String> comboBox) {
        LocalTime current = LocalTime.of(0, 0);
        LocalTime endOfDay = LocalTime.of(23, 30);

        while (current.isBefore(endOfDay)) {
            comboBox.getItems().add(current.toString());
            current = current.plusMinutes(30);
        }
    }

    /**
     * For a given times, calculates the next half or full hour.
     *
     * @param currentTime       the LocalTime use as starting point for the calculation
     * @return                  the next half or full hour
     */
    LocalTime calculateNextHalfHour(LocalTime currentTime) {
        int minute = currentTime.getMinute();
        int extraMinutes = (30 - (minute % 30)) % 30;
        return currentTime.plusMinutes(extraMinutes).withSecond(0).withNano(0);
    }

    /**
     * Configures the given ChoiceBox with all Rhythms as options.
     *
     * @param choiceBox         the ChoiceBox to configure
     */
    void setUpRhythmChoiceBox(ChoiceBox<Rhythm> choiceBox) {
        choiceBox.getItems().addAll(Rhythm.values());
        choiceBox.getSelectionModel().select(0);
    }

    /**
     * Configures the given ChoiceBox with all Calendars of the Profile as options.
     *
     * @param choiceBox         the ChoiceBox to configure
     */
    void setUpCalendarChoiceBox(ChoiceBox<Calendar> choiceBox) {
        choiceBox.getItems().addAll(profile.getCalendars());
        choiceBox.getSelectionModel().select(0);
    }

    /**
     * Configures the given ColorPicker to pick the color of the selected Calendar in the given ChoiceBox.
     * Sets up a listener to handle changes.
     * If no Calendars are available, the ColorPicker defaults to black.
     *
     * @param entryColor        the ColorPicker to configure
     * @param entryCalendar     the ChoiceBox to read from
     */
    void setUpColorPicker(ColorPicker entryColor, ChoiceBox<Calendar> entryCalendar) {
        if (entryCalendar.getItems().isEmpty()) {
            entryColor.setValue(Color.BLACK);
            return;
        }

        setColorPickerToCalendarColor(entryColor, entryCalendar);
        entryCalendar.setOnAction(event -> setColorPickerToCalendarColor(entryColor, entryCalendar));
    }

    /**
     * Configures the given ColorPicker to pick the color of the selected Calendar in the given ChoiceBox.
     *
     * @param entryColor        the ColorPicker to configure
     * @param entryCalendar     the ChoiceBox to read from
     */
    private void setColorPickerToCalendarColor(ColorPicker entryColor, ChoiceBox<Calendar> entryCalendar) {
        entryColor.setValue(entryCalendar.getSelectionModel().getSelectedItem().getColor());
    }

    /**
     * Sets a given Node as disabled or not based off the given boolean.
     *
     * @param node              the Node to be toggled
     * @param status            true if this Node should be disabled; false otherwise
     */
    void toggleNodeDisabled(Node node, boolean status) {
        node.setDisable(status);
    }

    // - - - GETTER & SETTER - - - START - - -

    public abstract Calendar getEntryCalendar();

    public abstract void setEntryCalendar(Calendar calendar);

    public abstract String getEntryTitle();

    public abstract void setEntryTitle(String title);

    public abstract String getEntryDescription();

    public abstract void setEntryDescription(String description);

    public abstract LocalDate getEntryStartDate();

    public abstract void setEntryStartDate(LocalDate startDate);

    public abstract boolean isEntryAllDay();

    public abstract void setEntryAllDay(boolean allDay);

    public abstract LocalTime getEntryStartTime();

    public abstract void setEntryStartTime(LocalTime startTime);

    public abstract Color getEntryColor();

    public abstract void setColorPickerToCalendarColor(Color color);

    public abstract Rhythm getEntryRhythm();

    public abstract void setEntryRhythm(Rhythm rhythm);

    public LocalTime getEntryEndTime() {
        return null;
    }

    public String getEntryLocation() {
        return null;
    }

    public boolean getAddToGoogle() {
        return false;
    }

    public String getHabitIconPath() {
        return null;
    }

    // - - - GETTER & SETTER - - - END - - -

}
