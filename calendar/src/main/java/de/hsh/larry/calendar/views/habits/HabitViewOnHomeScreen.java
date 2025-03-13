package de.hsh.larry.calendar.views.habits;

import de.hsh.larry.calendar.logic.Manager;
import de.hsh.larry.calendar.models.Habit;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.time.LocalDate;
import java.util.TreeMap;

/**
 * The HabitViewOnHomeScreen class provides the methods a Habit needs to be drawn on the HomeScreen.
 * It provides a method for updating the icon and streak style.
 *
 * @author Laura, Felix
 */
public class HabitViewOnHomeScreen extends HabitViewOnScreen {

    @FXML
    private Pane habitIconBackground;
    @FXML
    private Label title;
    @FXML
    private Label streakLabel;
    @FXML
    private ImageView iconImageView;

    /**
     * Initializes a new HabitViewOnHomeScreen.
     *
     * @param habit     The Habit that is going to be viewed.
     * @param manager   The Manager to handle user interactions.
     */
    public void initialize(Habit habit, Manager manager) {
        super.initialize(habit, manager);
        reload();
    }

    /**
     * Reloads the HabitViewOnHomeScreen and updates it with the newest data.
     */
    private void reload() {
        super.reload(habitIconBackground, title, streakLabel, iconImageView);
        updateIconAndStreakStyle();
    }

    /**
     * Styles the appearance of the icon background corresponing to wether the streak was extended or not.
     */
    private void updateIconAndStreakStyle() {
        TreeMap<LocalDate, Boolean> streak = habit.getStreakMap();
        LocalDate today = LocalDate.now();

        habitIconBackground.getStyleClass().clear();
        super.styleTodayCircle(today, habitIconBackground, streak, streakLabel);
    }
}
