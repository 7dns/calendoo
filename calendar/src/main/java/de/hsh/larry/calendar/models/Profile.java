package de.hsh.larry.calendar.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a user profile containing a collection of calendars.
 * Each profile has a unique ID, a name, and an associated list of calendars.
 * This class supports managing multiple calendars and their entries
 * and provides methods to add and remove calendars.
 *
 * @author Felix
 */
public class Profile implements Serializable {

    private static final long serialVersionUID = 1;
    private static long idCounter = 0;

    private long id;
    private String name;
    private ArrayList<Calendar> calendars;

    /**
     * Constructs a new Profile with the specified name.
     *
     * @param name the name of the profile
     */
    public Profile(String name) {
        setId();
        setName(name);
        setCalendars(new ArrayList<>());
    }

    /**
     * Adds a calendar to the profile.
     *
     * @param calendar the calendar to be added
     */
    public void addCalendar(Calendar calendar) {
        calendars.add(calendar);
    }

    /**
     * Removes a calendar from the profile.
     * This also removes all entries associated with the calendar before removing it.
     *
     * @param calendar the calendar to be removed
     */
    public void removeCalendar(Calendar calendar) {
        calendar.removeAllEntries();
        calendars.remove(calendar);
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

    public ArrayList<Calendar> getCalendars() {
        return calendars;
    }

    public void setCalendars(ArrayList<Calendar> calendars) {
        this.calendars = calendars;
    }

    // - - - GETTER & SETTER - - - END - - -
}
