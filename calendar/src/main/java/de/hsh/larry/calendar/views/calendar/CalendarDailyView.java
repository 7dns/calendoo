package de.hsh.larry.calendar.views.calendar;

import de.hsh.larry.calendar.logic.CalendarManager;
import de.hsh.larry.calendar.logic.Manager;
import de.hsh.larry.calendar.models.Calendar;
import de.hsh.larry.calendar.models.Entry;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The CalendarDailyView class draws a day of the week with all its timed and all day Entries.
 *
 * @author Laura, Felix
 */
public class CalendarDailyView {

    private static final String DAILY_TIMED = "/de/hsh/larry/calendar/views/calendar/calendarDailyTimedView.fxml";

    private final LocalDate date;
    private final Manager manager;
    private final CalendarManager calendarManager;

    private final VBox allDayEntries;
    private Pane timedEntries;
    private CalendarDailyTimedView timedEntriesController;

    /**
     * Constructs a new CalendarDailyView object.
     *
     * @param date      The date of the day to draw its Entries.
     * @param manager   The Manager to handle user interactions.
     * @param allDay    The VBox for drawing all day Entries.
     */
    public CalendarDailyView(LocalDate date, Manager manager, VBox allDay) {
        this.date = date;
        this.manager = manager;
        this.calendarManager = manager.getCalendarManager();
        this.allDayEntries = allDay;
        loadTimedView();
    }

    /**
     * Loads the Pane with the timed Entries of this day drawn onto.
     */
    private void loadTimedView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(DAILY_TIMED));

        try {
            timedEntries = loader.load();
            timedEntriesController = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Draws all Entries on the CalendarDailyView if it belongs to its day.
     */
    private void drawEntriesOfDay() {
        ArrayList<Calendar> selectedCalendars = calendarManager.getSelectedCalendars();
        ArrayList<Class <? extends Entry>> selectedEntryTypes = calendarManager.getSelectedTypes();

        for (Calendar calendar : selectedCalendars) {
            for (Class <? extends Entry> entryType : selectedEntryTypes) {
                ArrayList<? extends Entry> entries = calendar.getAllEntries(entryType);
                for (Entry entry : entries) {
                    if (entry.isActiveOnDate(date)) {
                        drawEntry(entry, allDayEntries);
                    }
                }
            }
        }
    }

    /**
     * Draws an Entry on the CalendarDailyView trough pre-drawing it and setting it to the corresponing place.
     *
     * @param entry     The Entry to be drawn.
     * @param allDay    The VBox for all day Entries.
     */
    private void drawEntry(Entry entry, VBox allDay) {
        Pane entryPane = entry.getEntryView().drawCalendarScreenEntry();

        entryPane.setOnMouseClicked(event -> entry.getEntryView().openDetailedView(event, manager, date));

        if (entry.isAllDay()) {
            drawAllDayEntry(entryPane, allDay);
        } else {
            timedEntriesController.drawTimedEntry(entry, entryPane);
        }
    }

    /**
     * Draws an Entry that ist all day on the CalendarDailyView in the corresponding VBox for it.
     *
     * @param entryPane The Pane the Entry is drawn on.
     * @param allDay    The VBox for all day Entries to set the Pane into.
     */
    private void drawAllDayEntry(Pane entryPane, VBox allDay) {
        allDay.getChildren().add(entryPane);
    }

    /**
     * Draws all Entries on the CalendarDailyView and returns the Pane with the timed Entries drawn onto.
     *
     * @return  The Pane with the timed Entries of the day.
     */
    public Pane drawAllEntriesAndGetPane() {
        drawEntriesOfDay();
        return timedEntries;
    }
}
