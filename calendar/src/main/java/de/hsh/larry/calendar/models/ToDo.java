package de.hsh.larry.calendar.models;

import de.hsh.larry.calendar.views.models.ToDoView;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.TreeMap;

/**
 * Represents a specific type of calendar entry: a to-do.
 * To-dos are entries that users can use to track a tasks status with.
 * The class provides functionalities for managing the status of a to-do on different dates.
 *
 * @author Felix
 */
public class ToDo extends Entry implements Serializable {

    private static final long serialVersionUID = 1;

    private TreeMap<LocalDate, ToDoStatus> status;

    /**
     * Constructs a new to-do with the specified calendar, title, and start date.
     *
     * @param calendar  the calendar to which the to-do belongs
     * @param title     the title of the to-do
     * @param startDate the start date of the to-do
     */
    public ToDo(Calendar calendar, String title, LocalDate startDate) {
        super(calendar, title, startDate);
        setStatus(startDate, ToDoStatus.NOT_STARTED);
    }

    /**
     * Constructs a new to-do with the specified calendar, title, start date, and start time.
     *
     * @param calendar  the calendar to which the to-do belongs
     * @param title     the title of the to-do
     * @param startDate the start date of the to-do
     * @param startTime the start time of the to-do
     */
    public ToDo(Calendar calendar, String title, LocalDate startDate, LocalTime startTime) {
        this(calendar, title, startDate);
        setStartTime(startTime);
    }

    // - - - GETTER & SETTER - - - START - - -

    /**
     * Sets the status of the to-do for a specific date.
     *
     * @param localDate the date for which the status is being set
     * @param newStatus the new status of the to-do
     */
    public void setStatus(LocalDate localDate, ToDoStatus newStatus) {
        if (status == null) {
            status = new TreeMap<>();
        }
        this.status.put(localDate, newStatus);
    }

    /**
     * Retrieves the status of the to-do for a specific date.
     * If no status is set for the date, it defaults to <code>NOT_STARTED</code>.
     *
     * @param localDate the date for which the status is being retrieved
     * @return the status of the to-do for the specified date
     */
    public ToDoStatus getStatus(LocalDate localDate) {
        if (!status.containsKey(localDate)) {
            status.put(localDate, ToDoStatus.NOT_STARTED);
        }

        return status.get(localDate);
    }

    public ToDoView getEntryView() {
        return (ToDoView) getCalendar().getEntryView(this);
    }

    // - - - GETTER & SETTER - - - END - - -

}
