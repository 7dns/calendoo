package de.hsh.larry.calendar.logic;

import de.hsh.larry.calendar.models.Calendar;
import de.hsh.larry.calendar.models.Entry;
import de.hsh.larry.calendar.models.Profile;
import de.hsh.larry.calendar.models.Rhythm;
import de.hsh.larry.calendar.views.editorViews.EntryEditorView;
import javafx.scene.paint.Color;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This class provides the general functionality of the EntryEditor with attributes every type of Entry has.
 * It provides methods for creating a new Entry and saving an existing Entry.
 *
 * @author Felix
 */
public abstract class EntryEditor {

    Calendar calendar;
    String title;
    LocalDate date;
    boolean isAllDay;
    LocalTime startTime;
    Rhythm rhythm;
    String description;
    Color color;

    private final Manager manager;
    private final Profile profile;
    EntryEditorView view;

    /**
     * Constructs a new EntryEditor and gets the users profile.
     *
     * @param manager           The Manager to handle user interactions.
     * @param entryEditorView   The View to the EntryEditor.
     */
    public EntryEditor(Manager manager, EntryEditorView entryEditorView) {
        this.manager = manager;
        this.profile = manager.getProfile();
        this.view = entryEditorView;
    }

    /**
     * Creates a new Entry of the corresponding type as implemented in the subclasses.
     */
    public abstract void createNewEntry();

    /**
     * Saves an existing Entry with the new data entered into the EntryEditorView.
     *
     * @param entry The Entry to save the data to.
     */
    public void saveExistingEntry(Entry entry) {
        getInputFromView();

        entry.setCalendar(calendar);
        entry.setTitle(title);
        entry.setStartDate(date);
        entry.setRhythm(rhythm);
        entry.setDescription(description);
        entry.setColor(color);

        if (!isAllDay) {
            entry.setStartTime(startTime);
        } else {
            entry.setStartTime(null);
        }
    }

    /**
     * Gets the data input from the EntryEditorView.
     */
    public void getInputFromView() {
        calendar = view.getEntryCalendar();
        title = view.getEntryTitle();
        date = view.getEntryStartDate();
        isAllDay = view.isEntryAllDay();
        startTime = view.getEntryStartTime();
        rhythm = view.getEntryRhythm();
        description = view.getEntryDescription();
        color = view.getEntryColor();
    }

    // - - - GETTER & SETTER - - - START - - -

    public Profile getProfile() {
        return profile;
    }

    public Manager getManager() {
        return manager;
    }

    // - - - GETTER & SETTER - - - END - - -

}
