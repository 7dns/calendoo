package de.hsh.larry.calendar.models;

import de.hsh.larry.calendar.views.models.HabitView;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.TreeMap;

/**
 * Represents a specific type of calendar entry: a habit.
 * Habits are recurring entries that users can track daily or weekly.
 * The class provides functionalities to track streaks, associate an icon, and visualize habits in various views.
 *
 * @author Felix, Laura
 */
public class Habit extends Entry implements Serializable {

    private static final long serialVersionUID = 1;

    private String icon;
    private TreeMap<LocalDate, Boolean> streak;

    /**
     * Constructs a habit with the specified calendar, title, start date, and rhythm.
     *
     * @param calendar  the calendar to which this habit belongs
     * @param title     the title of the habit
     * @param startDate the start date of the habit
     * @param rhythm    the recurrence pattern of the habit
     */
    public Habit(Calendar calendar, String title, LocalDate startDate, Rhythm rhythm) {
        super(calendar, title, startDate);
        setRhythm(rhythm);
        createNewStreakMap(startDate);
    }

    /**
     * Constructs a habit with a specific start time in addition to other parameters.
     *
     * @param calendar  the calendar to which this habit belongs
     * @param title     the title of the habit
     * @param startDate the start date of the habit
     * @param startTime the start time of the habit
     * @param rhythm    the recurrence pattern of the habit
     */
    public Habit(Calendar calendar, String title, LocalDate startDate, LocalTime startTime, Rhythm rhythm) {
        this(calendar, title, startDate, rhythm);
        setStartTime(startTime);
    }

    /**
     * Updates the streak map by filling in any missing days up to the current date.
     * Days without entries will be marked as not extended.
     */
    private void updateStreak() {
        LocalDate current = streak.lastKey().plusDays(1);
        LocalDate today = LocalDate.now();

        while (current.isBefore(today)) {
            streak.put(current, false);
            current = current.plusDays(1);
        }
    }

    // - - - GETTER & SETTER - - - START - - -

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * Calculates the current streak of the habit, updating the streak map if necessary.
     *
     * @return the number of consecutive days the habit has been extended
     */
    public int getStreak() {
        updateStreak();

        int counter = 0;

        LocalDate current = streak.firstKey();
        LocalDate today = LocalDate.now();

        while (!current.isAfter(today)) {
            if (streak.containsKey(current) && streak.get(current)) {
                counter++;
            }
            current = current.plusDays(1);
        }

        return counter;
    }

    /**
     * Updates the streak map by marking the specified date as extended or not.
     *
     * @param localDate the date to update in the streak map
     * @param extended  true if the habit was completed on the date; false otherwise
     */
    public void setStreak(LocalDate localDate, boolean extended) {
        streak.put(localDate, extended);
    }

    /**
     * Initializes a new streak map starting from the given date.
     * The initial date is marked as not extended.
     *
     * @param start the start date for the streak map
     */
    private void createNewStreakMap(LocalDate start) {
        streak = new TreeMap<>();
        streak.put(start, false);
    }

    public TreeMap<LocalDate, Boolean> getStreakMap() {
        return new TreeMap<>(streak);
    }

    public HabitView getEntryView() {
        return (HabitView) getCalendar().getEntryView(this);
    }

    // - - - GETTER & SETTER - - - END - - -
}
