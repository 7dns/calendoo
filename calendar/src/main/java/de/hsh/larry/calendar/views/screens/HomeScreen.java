package de.hsh.larry.calendar.views.screens;

import de.hsh.larry.calendar.logic.Manager;
import de.hsh.larry.calendar.models.Calendar;
import de.hsh.larry.calendar.models.Habit;
import de.hsh.larry.calendar.models.Profile;
import de.hsh.larry.calendar.models.ToDo;
import de.hsh.larry.calendar.views.calendar.CalendarDailyView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The HomeScreen class includes an overview of all Entries of today's date.
 * To achive that it includes methods for drawing the different types of Entries,
 * getting a greeting for the user and reloading it correctly.
 *
 * @author Laura, Felix
 */
public class HomeScreen extends ScreenController {

    @FXML
    private Label greeting;
    @FXML
    private Label date;
    @FXML
    private HBox hBoxDay;
    @FXML
    private VBox allDay;
    @FXML
    private VBox toDoVBox;
    @FXML
    private FlowPane flowPaneHabits;

    private LocalDate today;
    ArrayList<Calendar> allCalendars;

    /**
     * Initializes the HomeScreen and gets the profile of the user.
     *
     * @param manager   The Manager to handle user intercations.
     */
    @Override
    public void initialize (Manager manager) {
        super.initialize(manager);
        today = LocalDate.now();
        Profile profile = getProfile();
        allCalendars = profile.getCalendars();
    }

    /**
     * Reloads the HomeScreen and updates its contents to their newest state.
     */
    public void reload() {
        clearEntries();
        updateMessages();
        loadAndSetUpTodaysEntries();
        loadAndSetUpToDos();
        loadAndSetUpHabits();
    }

    /**
     * Clears all Entries from the HomeScreen.
     */
    private void clearEntries() {
        hBoxDay.getChildren().clear();
        allDay.getChildren().clear();
        toDoVBox.getChildren().clear();
        flowPaneHabits.getChildren().clear();
    }

    /**
     * Updates the user greeting to match the time and the user Messages to today's day and date.
     */
    private void updateMessages() {
        greeting.setText(getGreeting());
        date.setText("Today is " + getWeekDay() + ", " + getDateAsString() + ".");
    }

    /**
     * Gets the matching greeting to the current Time.
     *
     * @return  The String of the greeting.
     */
    private String getGreeting() {
        String greeting;
        String name = getProfile().getName();
        LocalTime now = LocalTime.now();

        if (now.isBefore(LocalTime.of(12, 0))) {
            greeting = String.format("Good Morning, %s! ☕", name);
        } else if (now.isBefore(LocalTime.of(18, 0))) {
            greeting = String.format("Good Afternoon, %s! 🌞", name);
        } else {
            greeting = String.format("Good Evening, %s! 🌙", name);
        }

        return greeting;
    }

    /**
     * Gets the name of the current day.
     *
     * @return  The String of the days name.
     */
    private String getWeekDay() {
        String dayOfWeek = today.getDayOfWeek().toString();
        return dayOfWeek.substring(0,1).toUpperCase() + dayOfWeek.substring(1).toLowerCase();
    }

    /**
     * Gets today's full date as String.
     *
     * @return  The date as String.
     */
    private String getDateAsString() {
        return today.getDayOfMonth() + "." + today.getMonthValue() + "." + today.getYear();
    }

    /**
     * Loads and sets up the calendar for Entries of today's date.
     */
    private void loadAndSetUpTodaysEntries() {
        hBoxDay.getChildren().add(new CalendarDailyView(today, getManager(), allDay).drawAllEntriesAndGetPane());
    }

    /**
     * Loads and sets up the ToDos of today's date.
     */
    private void loadAndSetUpToDos() {
        ArrayList<ToDo> allToDos = new ArrayList<>();
        for (Calendar calendar : allCalendars) {
            allToDos.addAll(calendar.getAllEntries(ToDo.class));
        }
        Collections.sort(allToDos);
        drawAllToDos(allToDos);
    }

    /**
     * Draws all ToDos of today's date.
     *
     * @param allToDos  All existing ToDos from all Calendars.
     */
    private void drawAllToDos(ArrayList<ToDo> allToDos) {
        LocalDate today = LocalDate.now();

        for (ToDo toDo : allToDos) {
            if (!toDo.isActiveOnDate(today)) {
                continue;
            }

            toDoVBox.getChildren().add(toDo.getEntryView().drawHomeScreenToDo(getManager()));
        }
    }

    /**
     * Loads and sets up the ToDos of today's date.
     */
    private void loadAndSetUpHabits() {
        for (Calendar calendar : allCalendars) {
            drawAllHabits(calendar.getAllEntries(Habit.class));
        }
    }

    /**
     * Draws all Habits of today's date.
     *
     * @param allHabits All existing Habits from all Calendars.
     */
    private void drawAllHabits(ArrayList<Habit> allHabits) {
        for (Habit habit : allHabits) {
            flowPaneHabits.getChildren().add(habit.getEntryView().drawHomeScreenHabit(getManager()));
        }
    }
}


