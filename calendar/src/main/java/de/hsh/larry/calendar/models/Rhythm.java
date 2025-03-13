package de.hsh.larry.calendar.models;

import java.io.Serializable;

/**
 * Represents the repetition pattern of an entry in the calendar.
 * The rhythm determines how frequently an entry recurs over time.
 * <p>
 * The available rhythms are:
 * <ul>
 *     <li>DOES_NOT_REPEAT: The entry does not repeat.</li>
 *     <li>DAILY: The entry repeats every day.</li>
 *     <li>WEEKLY: The entry repeats every week on the same day.</li>
 * </ul>
 *
 * @author Felix
 */
public enum Rhythm implements Serializable {

    DOES_NOT_REPEAT,
    DAILY,
    WEEKLY;

    private static final long serialVersionUID = 1;

    /**
     * Returns a string representation of the rhythm.
     *
     * @return a string representation of the rhythm
     */
    @Override
    public String toString() {
        return name().replaceAll("_", " ").toLowerCase();
    }

}
