package de.hsh.larry.calendar.views.habits;

import de.hsh.larry.calendar.logic.Manager;
import de.hsh.larry.calendar.models.Habit;
import de.hsh.larry.calendar.utils.ColorUtils;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import java.time.LocalDate;
import java.util.TreeMap;

/**
 * The HabitViewOnScreen class provides the general methods a Habit needs to be drawn on different screens.
 * It provides Methods for updating the Habits icon, title, streak, circle to be clicked and toggles the streak.
 *
 * @author Laura, Felix
 */
public class HabitViewOnScreen {

    Habit habit;
    LocalDate date;

    /**
     * Initializes a new HabitViewOnScreen.
     *
     * @param habit     The Habit that is going to be viewed.
     * @param manager   The Manager to handle user interactions.
     */
    public void initialize(Habit habit, Manager manager) {
        this.habit = habit;
        this.date = LocalDate.now();
    }

    /**
     * Reloads the HabitViewOnScreen and updates it with the newest data.
     *
     * @param habitIconBackground   The icon of the Habit.
     * @param title                 The title of the Habit.
     * @param streakLabel           The Label of the streak.
     * @param iconImageView         The ImageView to set the icon on.
     */
    void reload(Pane habitIconBackground, Label title, Label streakLabel, ImageView iconImageView) {
        updateHabitIcon(habitIconBackground, iconImageView);
        updateTitle(title);
        updateStreak(streakLabel);
    }

    /**
     * Updates the icon of the HabitViewOnScreen and its background color.
     *
     * @param habitIconBackground   The background of the icon.
     * @param iconImageView         The ImageView to set the icon on.
     */
    void updateHabitIcon(Pane habitIconBackground, ImageView iconImageView) {
        habitIconBackground.setStyle(String.format("calendar-color: %s;", ColorUtils.convertFXColorToHexString(habit.getColor())));
        habit.getEntryView().setIconInImageView(iconImageView);
    }

    /**
     * Updates the title of the HabitViewOnScreen.
     *
     * @param title The title of the Habit.
     */
    void updateTitle(Label title) {
        title.setText(habit.getTitle());
    }

    /**
     * Updates the streak of the HabitViewOnScreen.
     *
     * @param streakLabel   The Label to set the streak on.
     */
    void updateStreak(Label streakLabel) {
        streakLabel.setText(String.valueOf(habit.getStreak()));
    }

    /**
     * Styles the appearance of the circle that represents today's streak.
     *
     * @param today         Today's date.
     * @param circle        The circle to style.
     * @param streak        The streak history of the Habit.
     * @param streakLabel   The Label to set the streak on.
     */
    void styleTodayCircle(LocalDate today, Region circle, TreeMap<LocalDate, Boolean> streak, Label streakLabel) {
        circle.setCursor(Cursor.HAND);
        circle.setOnMouseClicked(event -> toggleStreak(circle, streakLabel));

        String sc = (streak.containsKey(today) && streak.get(today)) ? "streakExtended" : "streakToday";
        circle.getStyleClass().add(sc);
    }

    /**
     * Toggles wether the streak was already extended today or not, matches the circles style to it
     * and updates the streak.
     *
     * @param circle        The circle to be styled.
     * @param streakLabel   The Label to set the streak on.
     */
    private void toggleStreak(Region circle, Label streakLabel) {
        boolean isCurrentlyExtended = circle.getStyleClass().contains("streakExtended");
        LocalDate today = LocalDate.now();

        if (isCurrentlyExtended) {
            habit.setStreak(today, false);
            circle.getStyleClass().remove("streakExtended");
            circle.getStyleClass().add("streakToday");
        } else {
            habit.setStreak(today, true);
            circle.getStyleClass().remove("streakToday");
            circle.getStyleClass().add("streakExtended");
        }

        updateStreak(streakLabel);
    }

}
