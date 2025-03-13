package de.hsh.larry.calendar.views.models;

import de.hsh.larry.calendar.logic.Manager;
import de.hsh.larry.calendar.models.Entry;
import de.hsh.larry.calendar.models.Event;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents a visual view for calendar events.
 * Extends {@link EntryView} and provides specific implementations for rendering
 * all-day and timed events, as well as handling event interactions.
 */
public class EventView extends EntryView implements Serializable {

    private static final String CALENDAR_TIMED = "/de/hsh/larry/calendar/views/calendar/eventTimedViewOnCalendarScreen.fxml";
    private static final String CALENDAR_ALL_DAY = "/de/hsh/larry/calendar/views/calendar/eventAllDayViewOnCalendarScreen.fxml";
    private static final String DETAIL = "/de/hsh/larry/calendar/views/detailedViews/eventDetailedView.fxml";

    private final Event event;

    /**
     * Constructs an EventView with the associated event entry.
     *
     * @param entry The entry object representing the calendar event. Must be an instance of event.
     */
    public EventView(Entry entry) {
        super(entry);
        event = (Event) entry;
    }

    /**
     * Draws the event on the calendar screen. Differentiates between all-day
     * and timed events, using respective layouts.
     *
     * @return A pane representing the visual appearance of the event.
     */
    @Override
    public Pane drawCalendarScreenEntry() {
        if (event.isAllDay()) {
            return drawAllDayEvent();
        } else {
            return drawTimedEvent();
        }
    }

    /**
     * Opens the detailed view for this event.
     *
     * @param mouseEvent The mouseevent triggering the detailed view.
     * @param manager    The manager managing application-wide data and logic.
     * @param date       The selected date for which the event details are displayed.
     */
    @Override
    public void openDetailedView(MouseEvent mouseEvent, Manager manager, LocalDate date) {
        openDetailedView(mouseEvent, manager, date, DETAIL);
    }

    /**
     * Draws an all-day event on the CalendarScreen.
     *
     * @return A pane representing the visual appearance of the all-day event.
     */
    private Pane drawAllDayEvent() {
        return drawCalendarScreenEntry(CALENDAR_ALL_DAY, event);
    }

    /**
     * Draws a timed event on the CalendarScreen.
     *
     * @return A pane representing the visual appearance of the timed event.
     */
    private Pane drawTimedEvent() {
        return drawCalendarScreenEntry(CALENDAR_TIMED, event);
    }
}
