package de.hsh.larry.calendar.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Represents a specific type of calendar entry: an event.
 * Events may include additional details such as a location, end date, end time,
 * and a reference to a Google Calendar event ID.
 * The class provides methods for managing event-specific properties
 * and for displaying the event in a calendar view.
 *
 * @author Felix
 */
public class Event extends Entry implements Serializable {

    private static final long serialVersionUID = 1;

    private String location;
    private LocalTime endTime;
    private String googleEventId;

    /**
     * Constructs an all-day Event with a calendar, title, and start date.
     *
     * @param calendar  the calendar to which this event belongs
     * @param title     the title of the event
     * @param startDate the start date of the event
     */
    public Event(Calendar calendar, String title, LocalDate startDate) {
        super(calendar, title, startDate);
    }

    /**
     * Constructs an Event with a specific start time.
     * The event defaults to a duration of 30 minutes.
     *
     * @param calendar  the calendar to which this event belongs
     * @param title     the title of the event
     * @param startDate the start date of the event
     * @param startTime the start time of the event
     */
    public Event(Calendar calendar, String title, LocalDate startDate, LocalTime startTime) {
        this(calendar, title, startDate, startTime, startTime.plusMinutes(30));
    }

    /**
     * Constructs an Event with a specific start time and end time.
     *
     * @param calendar  the calendar to which this event belongs
     * @param title     the title of the event
     * @param startDate the start date of the event
     * @param startTime the start time of the event
     * @param endTime   the end time of the event
     */
    public Event(Calendar calendar, String title, LocalDate startDate, LocalTime startTime, LocalTime endTime) {
        this(calendar, title, startDate);
        setStartTime(startTime);
        setEndTime(startTime, endTime);
    }

    /**
     * Checks whether this event has a location.
     *
     * @return true if the event has a location; false otherwise
     */
    @Override
    public boolean hasLocation() {
        return getLocation() != null && !getLocation().isEmpty();
    }

    /**
     * Checks whether this event is an all-day event.
     * All-day events have no specific start or end time.
     *
     * @return true if the event is all-day; false otherwise
     */
    @Override
    public boolean isAllDay() {
        return getStartTime() == null && getEndTime() == null;
    }

    /**
     * Checks whether this event has an end time.
     *
     * @return true if the event has an end time; false otherwise
     */
    @Override
    public boolean hasEndTime() {
        return getEndTime() != null;
    }

    /**
     * Checks whether this event is synchronized with Google Calendar.
     *
     * @return true if the event has a Google Calendar event ID; false otherwise
     */
    public boolean isGoogleEvent() {
        return googleEventId != null;
    }

    /**
     * Checks whether this event is equal to or longer than the required minimum duration.
     *
     * @return true if the event is at least 30 minutes long; false otherwise
     */
    private boolean checkIfEventHasMinimumDuration(LocalTime startTime, LocalTime endTime) {
        return endTime.minusMinutes(getMinMinutes()).isAfter(startTime);
    }

    // - - - GETTER & SETTER - - - START - - -

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime startTime, LocalTime endTime) {
        if (checkIfEventHasMinimumDuration(startTime, endTime)) {
            setEndTime(endTime);
        } else {
            setEndTime(startTime.plusMinutes(getMinMinutes()));
        }
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getStartDateTime() {
        if (getStartDate() == null || getStartTime() == null) {
            return null;
        }

        return LocalDateTime.of(getStartDate(), getStartTime());
    }

    public LocalDateTime getEndDateTime() {
        if (getStartDate() == null || getEndTime() == null) {
            return null;
        }

        return LocalDateTime.of(getStartDate(), getEndTime());
    }

    public String getGoogleEventId() {
        return googleEventId;
    }

    public void setGoogleEventId(String googleEventId) {
        this.googleEventId = googleEventId;
    }

    // - - - GETTER & SETTER - - - END - - -
}
