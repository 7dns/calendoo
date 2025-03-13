package de.hsh.larry.calendar.views.models;

import de.hsh.larry.calendar.logic.Manager;
import de.hsh.larry.calendar.models.Entry;
import de.hsh.larry.calendar.models.Habit;
import de.hsh.larry.calendar.views.habits.HabitViewOnScreen;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents a visual view for calendar habits.
 * Extends {@link EntryView} and provides specific methods for rendering habits
 * on various screens (calendar, home, and habit-specific screens) and handling interactions.
 * @author Felix, Laura
 */
public class HabitView extends EntryView implements Serializable {

    private static final String CALENDAR = "/de/hsh/larry/calendar/views/calendar/habitViewOnCalendarScreen.fxml";
    private static final String HOME = "/de/hsh/larry/calendar/views/habits/habitViewOnHomeScreen.fxml";
    private static final String HABIT = "/de/hsh/larry/calendar/views/habits/habitViewOnHabitScreen.fxml";
    private static final String DETAIL = "/de/hsh/larry/calendar/views/detailedViews/habitDetailedView.fxml";

    private final Habit habit;

    /**
     * Constructs a HabitView with the associated habit entry.
     *
     * @param entry The entry object representing the habit.
     *              Must be an instance of habit.
     */
    public HabitView(Entry entry) {
        super(entry);
        habit = (Habit) entry;
    }

    /**
     * Draws the habit on the CalendarScreen.
     *
     * @return A pane representing the visual appearance of the habit on the calendar.
     */
    @Override
    public Pane drawCalendarScreenEntry() {
        return drawCalendarScreenEntry(CALENDAR, habit);
    }

    /**
     * Opens the detailed view for this habit.
     *
     * @param mouseEvent The mouseevent triggering the detailed view.
     * @param manager    The manager managing application-wide data and logic.
     * @param date       The selected date for which the habit details are displayed.
     */
    @Override
    public void openDetailedView(MouseEvent mouseEvent, Manager manager, LocalDate date) {
        openDetailedView(mouseEvent, manager, date, DETAIL);
    }

    /**
     * Draws the habit on the habit-specific screen.
     *
     * @param manager   The manager managing application-wide data and logic.
     * @return          A pane representing the habit's appearance on the HabotSreen.
     */
    public Pane drawHabitScreenHabit(Manager manager) {
        return drawScreenHabit(manager, HABIT);
    }

    /**
     * Draws the habit on the home screen.
     *
     * @param manager   The manager managing application-wide data and logic.
     * @return          A pane representing the habit's appearance on the HomeScreen.
     */
    public Pane drawHomeScreenHabit(Manager manager) {
        return drawScreenHabit(manager, HOME);
    }

    /**
     * Sets the habit's icon in the provided {@link ImageView}.
     *
     * @param imageView The imageView where the habit's icon should be displayed.
     */
    public void setIconInImageView(ImageView imageView) {
        String iconPath = habit.getIcon();
        if (iconPath != null) {
            imageView.setImage(new Image(Habit.class.getResource(iconPath).toExternalForm()));
        }
    }

    /**
     * Helper method to load and render the habit on a specified screen.
     *
     * @param manager           The manager managing application-wide data and logic.
     * @param fxml              The FXML path for the specific screen.
     * @return                  A pane representing the habit on the specified screen.
     * @throws RuntimeException If an IO-error occurs while loading the FXML.
     */
    private Pane drawScreenHabit(Manager manager, String fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Pane pane;

        try {
            pane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HabitViewOnScreen controller = loader.getController();
        controller.initialize(habit, manager);

        return pane;
    }
}
