package de.hsh.larry.calendar.models;

import de.hsh.larry.calendar.views.models.EntryView;
import javafx.scene.paint.Color;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents an abstract calendar entry, such as an event, a to-do or a habit.
 * Each entry is associated with a calendar and contains information such as a title,
 * description, start date, and optional start time. Entries may have recurrences.
 * This class provides methods to manage and display the entry.
 *
 * @author Felix
 */
public abstract class Entry implements Comparable<Entry>, Serializable {

    private static final long serialVersionUID = 1;
    private static final int HEIGHT_HOUR = 60;
    private static final int MIN_MINUTES = 30;
    private static long idCounter = 0;

    private long id;
    private Calendar calendar;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalTime startTime;
    private Rhythm rhythm;
    private String color;

    /**
     * Constructs a new Entry with the specified calendar, title, and start date.
     *
     * @param calendar  the calendar to which this entry belongs
     * @param title     the title of the entry
     * @param startDate the start date of the entry
     */
    public Entry (Calendar calendar, String title, LocalDate startDate) {
        setId();
        calendar.addEntry(this);
        setCalendar(calendar);
        setTitle(title);
        setStartDate(startDate);
        setRhythm(Rhythm.DOES_NOT_REPEAT);
    }

    /**
     * Checks whether the entry is active on the given date.
     *
     * @param date the date to check
     * @return true if the entry is active on the specified date; false otherwise
     */
    public boolean isActiveOnDate(LocalDate date) {
        switch (getRhythm()) {
            case DOES_NOT_REPEAT:
                return date.equals(getStartDate());
            case DAILY:
                return !date.isBefore(getStartDate());
            case WEEKLY:
                return !date.isBefore(getStartDate()) && date.getDayOfWeek() == getStartDate().getDayOfWeek();
            default:
                return false;
        }
    }

    /**
     * Checks whether the entry has a description.
     *
     * @return true if the entry has a non-empty description; false otherwise
     */
    public boolean hasDescription() {
        return description != null && !description.isEmpty();
    }

    /**
     * Checks whether the entry is an all-day event.
     *
     * @return true if the entry has no specific start time; false otherwise
     */
    public boolean isAllDay() {
        return getStartTime() == null;
    }

    /**
     * Checks whether the entry has an end time.
     *
     * @return false by default; can be overridden in subclasses
     */
    public boolean hasEndTime() {
        return false;
    }

    /**
     * Checks whether the entry has a location.
     *
     * @return false by default; can be overridden in subclasses
     */
    public boolean hasLocation() {
        return false;
    }

    /**
     * Compares this entry to another entry for ordering based on start date and time.
     * All-day entries are compared based solely on the date.
     *
     * @param other the entry to compare against
     * @return a negative integer, zero, or a positive integer as this entry is less than,
     * equal to, or greater than the specified entry
     */
    @Override
    public int compareTo(Entry other) {
        int compareDate = getStartDate().compareTo(other.getStartDate());
        if (compareDate != 0) {
            return compareDate;
        }

        if (isAllDay() && !other.isAllDay()) {
            return -1;
        } else if (!isAllDay() && other.isAllDay()) {
            return 1;
        }

        return getStartTime().compareTo(other.getStartTime());
    }

    /**
     * Returns a string representation of the entry, including its title and start date.
     *
     * @return a string representation of the entry
     */
    @Override
    public String toString() {
        return String.format("%s @%s", getTitle(), getStartDate().toString());
    }

    // - - - GETTER & SETTER - - - START - - -

    public long getId() {
        return id;
    }

    private void setId() {
        this.id = ++idCounter;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Rhythm getRhythm() {
        return rhythm;
    }

    public void setRhythm(Rhythm rhythm) {
        this.rhythm = rhythm;
    }

    public Color getColor() {
        if (color != null) {
            return Color.valueOf(color);
        } else {
            return calendar.getColor();
        }
    }

    public void setColor(Color color) {
        this.color = color.toString();
    }

    public int getHeightPerHour() {
        return HEIGHT_HOUR;
    }

    public int getMinMinutes() {
        return MIN_MINUTES;
    }

    public EntryView getEntryView() {
        return getCalendar().getEntryView(this);
    }


    // - - - GETTER & SETTER - - - END - - -
}
