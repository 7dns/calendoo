package de.hsh.larry.calendar.views.calendar;

import de.hsh.larry.calendar.models.Entry;
import de.hsh.larry.calendar.models.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.time.Duration;

/**
 * The EventTimedViewOnCalendarScreen class provides the methods a timed Event needs to be drawn on the CalendarScreen.
 * It provides a method for setting the parameters of the Habit.
 *
 * @author Felix
 */
public class EventTimedViewOnCalendarScreen extends EntryViewOnCalendarScreen {

    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Region sideBar;

    private Event event;

    /**
     * Sets all parameters of an Entry of type Event, that is timed.
     *
     * @param entry The Entry that is going to be drawn.
     */
    @Override
    public void setParameters(Entry entry) {
        this.event = (Event) entry;
        setLabelText(title, entry.getTitle());
        setLabelText(description, entry.getDescription());
        setColor(rectangle, entry.getColor());
        setSideBarColor(sideBar, entry.getCalendar().getColor());
        setHeight();
    }

    /**
     * Calculates the height the Event should be and sets the background to this height.
     */
    public void setHeight() {
        long diff = (Duration.between(event.getStartTime(), event.getEndTime())).toMinutes();
        double hours = diff / 60.0;
        double height = hours * event.getHeightPerHour();

        if (height < event.getHeightPerHour()) {
            ((VBox) description.getParent()).getChildren().remove(description);
        }

        rectangle.setHeight(height);
    }

}
