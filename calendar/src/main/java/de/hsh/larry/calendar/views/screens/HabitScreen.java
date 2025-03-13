package de.hsh.larry.calendar.views.screens;

import de.hsh.larry.calendar.logic.Manager;
import de.hsh.larry.calendar.models.Calendar;
import de.hsh.larry.calendar.models.Habit;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import java.util.HashMap;

/**
 * The HabitScreen class includes an overview of all Habits of today's date.
 * To achive that it includes methods for drawing the Habits, open an Editor for creating Habits and setting
 * up the Selection of the Calendars.
 *
 * @author Felix
 */
public class HabitScreen extends ScreenController {

    @FXML
    private Region addNewCalendarButton;
    @FXML
    private VBox vBoxCalendars;
    @FXML
    private VBox vBoxHabits;
    @FXML
    private Label labelNoHabits;

    private HashMap<Calendar, CheckBox> calendarCheckBoxes;

    /**
     * Initializes the HabitScreen.
     *
     * @param manager   The Manager to handle user intercations.
     */
    @Override
    public void initialize (Manager manager) {
        super.initialize(manager);
        setUpAddCalendarButton(addNewCalendarButton);
        setUpCalendarCheckBoxes();
    }

    /**
     * Reloads the HabitScreen and updates its contents to their newest state.
     */
    public void reload() {
        setUpCalendarCheckBoxes();
        reloadContent();
    }

    /**
     * Reloads and updates the list of Habits and the contents of the Habits to their newest state.
     * If no Habit exists, the user is shown a message about that.
     */
    private void reloadContent() {
        drawAllHabits();
        labelNoHabits.setVisible(vBoxHabits.getChildren().isEmpty());
    }

    /**
     * Sets up checkboxes for every calendar of the users profile.
     * A checkbox determines wether the Habits of the calendar are shown or not.
     * If a checkbox is clicked, the list of Habits and the contents of the Habits gets reloaded.
     */
    private void setUpCalendarCheckBoxes() {
        calendarCheckBoxes = setCalendars(vBoxCalendars);

        for (Calendar calendar : calendarCheckBoxes.keySet()) {
            CheckBox checkBox = calendarCheckBoxes.get(calendar);
            checkBox.setSelected(true);
            checkBox.setOnAction(event -> reloadContent());
        }
    }

    /**
     * Draws all Habits of the selected calendars onto the HabitScreen.
     */
    private void drawAllHabits() {
        vBoxHabits.getChildren().clear();

        for (Calendar calendar : getSelectedCalendars(calendarCheckBoxes)) {
            for (Habit habit : calendar.getAllEntries(Habit.class)) {
                Pane habitEntry = habit.getEntryView().drawHabitScreenHabit(getManager());
                vBoxHabits.getChildren().add(habitEntry);
            }
        }
    }

    /**
     * Opens a new Editor for creating a new Entry of the type Habit.
     */
    @FXML
    private void openEditorToCreateNewEntry() {
        openNewEditor().selectHabitEditor();
    }

}
