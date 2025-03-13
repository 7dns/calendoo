package de.hsh.larry.calendar.views.detailedViews;

import de.hsh.larry.calendar.logic.Manager;
import de.hsh.larry.calendar.models.Entry;
import de.hsh.larry.calendar.models.Habit;
import de.hsh.larry.calendar.utils.ColorUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import java.time.LocalDate;

/**
 * The HabitDetailedView class provides the methods a Habit needs in their DetailedView.
 * It provides Methods for updating its specific information.
 *
 * @author Laura, Felix
 */
public class HabitDetailedView extends EntryDetailedView {

    @FXML
    private GridPane gridPane;
    @FXML
    private Region edit;
    @FXML
    private Region delete;
    @FXML
    private Region close;
    @FXML
    private Pane habitIcon;
    @FXML
    private Label titleLabel;
    @FXML
    private Label dateAndTimeLabel;
    @FXML
    private Label rhythmLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label calendarLabel;
    @FXML
    private ImageView iconImageView;

    private Habit habit;

    /**
     * Initializes a new HabitDetailedView.
     *
     * @param entry     The Entry to be displayed.
     * @param manager   The Manager to handle user interactions.
     * @param popUp     The Stage the Editor is going to be on.
     * @param date      The date of the Entry to be displayed.
     */
    @Override
    public void initialize(Entry entry, Manager manager, Stage popUp, LocalDate date) {
        super.initialize(entry, manager, popUp, date);
        this.habit = (Habit) entry;
        setUpButtons(edit, delete, close, entry);
    }

    /**
     * Reloads the HabitDetailedView and updates it with the newest data.
     */
    @Override
    public void reload() {
        updateIcon(habit);
        updateLabelText(titleLabel, habit.getTitle());
        updateDateAndTime(dateAndTimeLabel, habit, date);
        updateLabelText(rhythmLabel, habit.getRhythm().toString());
        updateDescription(descriptionLabel, habit, gridPane, 2);
        updateLabelText(calendarLabel, habit.getCalendar().toString());
    }

    /**
     * Updates the icon and its background color of the HabitDetailedView.
     *
     * @param habit The Habit to get the icon from.
     */
    private void updateIcon(Habit habit) {
        habit.getEntryView().setIconInImageView(iconImageView);
        habitIcon.setStyle(String.format("calendar-color: %s;", ColorUtils.convertFXColorToHexString(habit.getColor())));
    }

}
