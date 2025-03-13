package de.hsh.larry.calendar.views.habits;

import de.hsh.larry.calendar.logic.Manager;
import de.hsh.larry.calendar.models.Habit;
import de.hsh.larry.calendar.utils.ColorUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import java.time.LocalDate;
import java.util.TreeMap;

/**
 * The HabitViewOnHabitScreen class provides the methods a Habit needs to be drawn on the HabitScreen.
 * It provides methods for updating the rhythm and the style of the streak visualization and also for setting up the
 * DetailedView of a Habit.
 *
 * @author Laura, Felix
 */
public class HabitViewOnHabitScreen extends HabitViewOnScreen {

    @FXML
    private Pane habitIconBackground;
    @FXML
    private Region highlight;
    @FXML
    private Label title;
    @FXML
    private Label streakLabel;
    @FXML
    private Label rhythmLabel;
    @FXML
    private HBox streakVisualization;
    @FXML
    private ImageView iconImageView;

    /**
     * Initializes a new HabitViewOnHabitScreen.
     *
     * @param habit     The Habit that is going to be viewed.
     * @param manager   The Manager to handle user interactions.
     */
    @Override
    public void initialize(Habit habit, Manager manager) {
        super.initialize(habit, manager);
        setUpShowDetails(habit, manager);
        reload();
    }

    /**
     * Reloads the HabitViewOnHabitScreen and updates it with the newest data.
     */
    private void reload() {
        super.reload(habitIconBackground, title, streakLabel, iconImageView);
        updateRhythm();
        updateStreakVisualization();
    }

    /**
     * Updates the rythm of the HabitViewOnHabitScreen.
     */
    private void updateRhythm() {
        rhythmLabel.setText(String.valueOf(habit.getRhythm()));
    }

    /**
     * Updates the streak visualization of by setting the circles to wether the streak on the days
     * was extended or not.
     */
    private void updateStreakVisualization() {
        if (streakVisualization != null) {
            streakVisualization.getChildren().clear();
        }

        TreeMap<LocalDate, Boolean> streak = habit.getStreakMap();
        LocalDate twoWeeksAgo = LocalDate.now().minusDays(13);
        LocalDate current = streak.containsKey(twoWeeksAgo) ? twoWeeksAgo : streak.firstKey();
        LocalDate today = LocalDate.now();

        while (!current.isAfter(today)) {
            Region circle = generateNewCircle();
            styleCircle(current, today, circle, streak);
            streakVisualization.getChildren().add(circle);
            current = current.plusDays(1);
        }
    }

    /**
     * Styles the appearance of the circle that represents today's streak.
     *
     * @param current   The day that is compared to today's date.
     * @param today     Todays date.
     * @param circle    The circle to style.
     * @param streak    The streak history of the Habit.
     */
    private void styleCircle(LocalDate current, LocalDate today, Region circle, TreeMap<LocalDate, Boolean> streak) {
        if (current.isEqual(today)) {
            styleTodayCircle(today, circle, streak, streakLabel);
        } else {
            String sc = (streak.containsKey(current) && streak.get(current)) ? "streakExtended" : "streakNotExtended";
            circle.getStyleClass().add(sc);
        }
    }

    /**
     * Generates a new circle just with the color of the Habit.
     *
     * @return  The Region representing the circle.
     */
    private Region generateNewCircle() {
        Region circle = new Region();
        circle.setPrefSize(20, 20);
        circle.setMaxSize(20, 20);
        circle.setMinSize(20, 20);

        circle.setStyle(String.format("calendar-color: %s;", ColorUtils.convertFXColorToHexString(habit.getColor())));

        return circle;
    }

    /**
     * Sets up the DetailedView of a habit for when it gets clicked.
     *
     * @param habit     The Habit to be displayed.
     * @param manager   The Manager for handling user interactions.
     */
    private void setUpShowDetails(Habit habit, Manager manager) {
        highlight.setOnMouseClicked(event -> habit.getEntryView().openDetailedView(event, manager, date));
    }
}
