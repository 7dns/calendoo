package de.hsh.larry.calendar.logic;

import de.hsh.larry.calendar.models.Calendar;
import de.hsh.larry.calendar.models.Entry;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The CalendarManager class provides functionality for managing calendars and entries.
 * It allows the user to select calendars, entry types, and navigate through dates.
 *
 * @author Felix, Farina
 */
public class CalendarManager {

    private final HashMap<Calendar, Boolean> selectedCalendars;
    private final HashMap<Class<? extends Entry>, Boolean> selectedEntryTypes;
    private LocalDate selectedDate;
    private final LocalDate today;
    private final ArrayList<CalendarUpdateListener> listeners = new ArrayList<>();

    /**
     * Constructs a CalendarManager instance, initializing the data structures
     * and setting the default selected date to today.
     */
    public CalendarManager() {
        this.selectedCalendars = new HashMap<>();
        this.selectedEntryTypes = new HashMap<>();
        this.selectedDate = LocalDate.now();
        today = LocalDate.now();
    }

    /**
     * Returns a list of dates for the current week based on the selected date.
     * The week starts from Monday.
     *
     * @return                  A list of LocalDate objects representing the days of the current week
     */
    public ArrayList<LocalDate> getDaysOfCurrentWeek() {
        ArrayList<LocalDate> week = new ArrayList<>();
        LocalDate monday = getFirstDayOfWeek(selectedDate);

        for (int i = 0; i < 7; i++) {
            week.add(monday.plusDays(i));
        }

        return week;
    }

    /**
     * Returns a list of dates for the current month based on the given date.
     *
     * @param date              The LocalDate from which the month will be calculated
     * @return                  A list of LocalDate objects representing the days of the current month
     */
    public ArrayList<LocalDate> getDaysOfCurrentMonth(LocalDate date) {
        ArrayList<LocalDate> dayOfMonth = new ArrayList<>();
        LocalDate firstDayOfMonth = date.withDayOfMonth(1);
        int daysInMonth = firstDayOfMonth.lengthOfMonth();

        for (int i = 0; i < daysInMonth; i++) {
            dayOfMonth.add(firstDayOfMonth.plusDays(i));
        }
        return dayOfMonth;
    }

    /**
     * Sets the status (selected/deselected) of the given calendar.
     *
     * @param calendar          The Calendar to be updated
     * @param status            The selected status (true for selected, false for deselected)
     */
    public void setCalendarCheckBoxStatus(Calendar calendar, Boolean status) {
        selectedCalendars.put(calendar, status);
    }

    /**
     * Finds and returns the first day (Monday) of the week for the given date.
     *
     * @param current           The LocalDate to calculate the start of the week from
     * @return                  The LocalDate representing the Monday of the week
     */
    private LocalDate getFirstDayOfWeek(LocalDate current) {
        while (current.getDayOfWeek() != DayOfWeek.MONDAY) {
            current = current.minusDays(1);
        }

        return current;
    }

    /**
     * Returns a list of selected calendars.
     *
     * @return a list of Calendar objects that are selected
     */
    public ArrayList<Calendar> getSelectedCalendars () {
        ArrayList<Calendar> calendars = new ArrayList<>();

        for (Calendar calendar : selectedCalendars.keySet()) {
            if (selectedCalendars.get(calendar)) {
                calendars.add(calendar);
            }
        }

        return calendars;
    }

    /**
     * Sets the status (selected/deselected) of the given entry type.
     *
     * @param entryType         The Class object representing the entry type
     * @param status            The selected status (true for selected, false for deselected)
     */
    public void setEntryTypeCheckBoxStatus(Class<? extends Entry> entryType, Boolean status) {
        selectedEntryTypes.put(entryType, status);
    }

    /**
     * Returns a list of selected entry types.
     *
     * @return  A list of Class objects representing the selected entry types
     */
    public ArrayList<Class<? extends Entry>> getSelectedTypes() {
        ArrayList<Class<? extends Entry>> types = new ArrayList<>();

        for (Class<? extends Entry> entry : selectedEntryTypes.keySet()) {
            if (selectedEntryTypes.get(entry)) {
                types.add(entry);
            }
        }

        return types;
    }

    /**
     * Changes the selected date to the next week.
     *
     * @return  The LocalDate representing the same day of the next week
     */
    public LocalDate changeNextWeek(){
        return selectedDate.plusWeeks(1);
    }

    /**
     * Changes the selected date to the previous week.
     *
     * @return  The LocalDate representing the same day of the previous week
     */
    public LocalDate changePastWeek(){
        return selectedDate.minusWeeks(1);
    }

    /**
     * Determines whether the current week spans across two different months.
     *
     * @return  true if the current week spans across two months, false otherwise
     */
    public boolean weekWithTwoMonths() {
        ArrayList<LocalDate> week = getDaysOfCurrentWeek();

        int referenceMonth = week.get(0).getMonthValue();
        for (LocalDate date : week) {
            if (date.getMonthValue() != referenceMonth) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether the current week spans across two different years.
     *
     * @return  true if the current week spans across two years, false otherwise
     */
    public boolean weekWithTwoYears() {
        ArrayList<LocalDate> week = getDaysOfCurrentWeek();

        return !(week.get(0).getYear() == week.get(6).getYear());
    }

    /**
     * Adds a listener to be notified when the date changes.
     *
     * @param listener  The listener to be added
     */
    public void addCalendarUpdateListener(CalendarUpdateListener listener) {
        listeners.add(listener);
    }

    /**
     * Notifies all registered listeners that the selected date has changed.
     */
    public void notifyDateChanged() {
        for (CalendarUpdateListener listener : listeners) {
            listener.onDateChanged(selectedDate);
        }
    }

    /**
     * Sets the selected date to a new value and notifies listeners about the change.
     *
     * @param date  The new selected date
     */
    public void setSelectedDate(LocalDate date) {
        this.selectedDate = date;
        notifyDateChanged();
    }

    public LocalDate getSelectedDate() {
        return selectedDate;
    }

    public LocalDate getToday() {
        return today;
    }
}