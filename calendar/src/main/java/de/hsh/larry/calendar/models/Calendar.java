package de.hsh.larry.calendar.models;

import de.hsh.larry.calendar.logic.GoogleCalendarManager;
import de.hsh.larry.calendar.views.models.EntryView;
import de.hsh.larry.calendar.views.models.EventView;
import de.hsh.larry.calendar.views.models.HabitView;
import de.hsh.larry.calendar.views.models.ToDoView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.paint.Color;

/**
 * Represents a calendar that contains multiple entries, such as events, todos or habits.
 * Each calendar has a unique ID, name, color, and a list of associated entries.
 * This class provides methods to manage entries and retrieve them.
 *
 * @author Laura, Felix
 */
public class Calendar implements Serializable {

    private static final long serialVersionUID = 1;
    private static long idCounter = 0;

    private long id;
    private String name;
    private String color;
    private HashMap <Entry, EntryView> entriesAndEntryViews;

    /**
     * Constructs a new Calendar object with the specified name and color.
     *
     * @param name  the name of the calendar
     * @param color the color representing the calendar
     */
    public Calendar (String name, Color color) {
        setId();
        this.name = name;
        setColor(color);
        entriesAndEntryViews = new HashMap <>();
    }

    /**
     * Adds an entry to the calendar.
     *
     * @param entry the entry to add
     */
    public void addEntry(Entry entry) {
        if (entriesAndEntryViews.containsKey(entry) && entriesAndEntryViews.get(entry) != null) {
            return;
        }

        if (entry.getClass().equals(Event.class)) {
            Event event = (Event) entry;
            entriesAndEntryViews.put(event, new EventView(event));
        } else if (entry.getClass().equals(ToDo.class)) {
            ToDo toDo = (ToDo) entry;
            entriesAndEntryViews.put(toDo, new ToDoView(toDo));
        } else if (entry.getClass().equals(Habit.class)) {
            Habit habit = (Habit) entry;
            entriesAndEntryViews.put(habit, new HabitView(habit));
        }
    }

    /**
     * Removes an entry from the calendar and deletes it from Google Calendar if applicable.
     *
     * @param entry the entry to remove
     */
    public void removeEntry(Entry entry) {
        if (!entriesAndEntryViews.containsKey(entry)) {
            return;
        }

        entriesAndEntryViews.remove(entry);
        removeEntryFromGoogle(entry);
    }

    /**
     * Removes all entries from the calendar.
     */
    public void removeAllEntries() {
        entriesAndEntryViews.clear();
    }

    public EntryView getEntryView(Entry entry) {
        if (!entriesAndEntryViews.containsKey(entry)) {
            return null;
        }

        return entriesAndEntryViews.get(entry);
    }

    /**
     * Retrieves all entries in the calendar that are of the specified type.
     *
     * @param type the class type of the entries
     * @return a list of entries of the specified type
     */
    public <T extends Entry> ArrayList<T> getAllEntries(Class<T> type) {
        ArrayList<T> returnList = new ArrayList<>();

        for (Entry entry : getEntries()) {
            if (type.isInstance(entry)) {
                returnList.add(type.cast(entry));
            }
        }

        return returnList;
    }

    /**
     * Removes an entry from Google Calendar if it is an event synchronized with Google.
     *
     * @param entry the entry to remove from Google Calendar
     */
    private void removeEntryFromGoogle(Entry entry) {
        if (entry instanceof Event) {
            Event eventToRemove = (Event) entry;
            if (eventToRemove.isGoogleEvent()) {
                GoogleCalendarManager.deleteEvent(eventToRemove.getGoogleEventId());
            }
        }
    }

    /**
     * Returns a string representation of the calendar, which is its name.
     *
     * @return the name of the calendar
     */
    @Override
    public String toString() {
        return name;
    }

    // - - - GETTER & SETTER - - - START - - -

    public long getId() {
        return id;
    }

    private void setId() {
        this.id = ++idCounter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return Color.valueOf(color);
    }

    public void setColor(Color color) {
        this.color = color.toString();
    }

    private ArrayList<Entry> getEntries() {
        if (entriesAndEntryViews == null) {
            entriesAndEntryViews = new HashMap <>();
        }

        return new ArrayList<>(entriesAndEntryViews.keySet());
    }

    private void setEntries(ArrayList<Entry> entries) {
        for (Entry entry : entries) {
            addEntry(entry);
        }
    }

    // - - - GETTER & SETTER - - - END - - -
}
