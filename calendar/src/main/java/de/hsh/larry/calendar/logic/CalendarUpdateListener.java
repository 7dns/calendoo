package de.hsh.larry.calendar.logic;

import java.time.LocalDate;

/**
 * This interface defines a listener for updates in the calendar.
 * It is used to notify implementing classes when the selected date changes.
 * Classes implementing this interface should override the {@link #onDateChanged(LocalDate)}
 * method to define custom behavior when the date changes.
 *
 * @author Farina
 */
public interface CalendarUpdateListener {
    /**
     * This method is called when the selected date changes.
     *
     * @param date      The new selected date.
     */
    void onDateChanged(LocalDate date);
}