package de.hsh.larry.calendar.views.calendar;

import de.hsh.larry.calendar.models.Entry;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

/**
 * The HabitViewOnCalendarScreen class provides the methods a Habit needs to be drawn on the CalendarScreen.
 * It provides a method for setting the parameters of the Habit.
 *
 * @author Felix
 */
public class HabitViewOnCalendarScreen extends EntryViewOnCalendarScreen {

    @FXML
    private Label title;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Region sideBar;

    /**
     * Sets all parameters of an Entry of type Habit.
     *
     * @param entry The Entry that is going to be drawn.
     */
    @Override
    public void setParameters(Entry entry) {
        setLabelText(title, entry.getTitle());
        setColor(rectangle, entry.getColor());
        setSideBarColor(sideBar, entry.getCalendar().getColor());
    }

}
