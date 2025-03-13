package de.hsh.larry.calendar.views.screens;

import de.hsh.larry.calendar.logic.CalendarManager;
import de.hsh.larry.calendar.logic.CalendarUpdateListener;
import de.hsh.larry.calendar.logic.Manager;
import de.hsh.larry.calendar.models.*;
import de.hsh.larry.calendar.utils.CalendarFormattingUtils;
import de.hsh.larry.calendar.views.calendar.CalendarMonthlyMiniView;
import de.hsh.larry.calendar.views.calendar.CalendarWeeklyView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * The CalendarScreen class showcases the main calendar view for calendoo.
 * It provides the functionality to show views (weekly, monthly) and manage calendars and entries.
 *
 * @author Felix, Farina
 */
public class CalendarScreen extends ScreenController implements CalendarUpdateListener {

    private static final String WEEKLY_VIEW = "/de/hsh/larry/calendar/views/calendar/calendarWeeklyView.fxml";
    private static final String MONTHLY_MINI_VIEW = "/de/hsh/larry/calendar/views/calendar/calendarMonthlyMiniView.fxml";

    @FXML
    private GridPane sideBar;
    @FXML
    private Region addNewCalendarButton;
    @FXML
    private VBox vBoxCalendars;
    @FXML
    private VBox vBoxEntryTypes;
    @FXML
    private GridPane rightGridPane;
    @FXML
    private Label month01;
    @FXML
    private Label year01;
    @FXML
    private Label month02;
    @FXML
    private Label year02;
    @FXML
    private Label minus;
    @FXML
    private ComboBox<String> viewChoice;

    private CalendarManager calendarManager;
    private CalendarWeeklyView controllerWeeklyView;
    private CalendarMonthlyMiniView controllerMonthlyMiniView;
    private HashMap<Class<? extends Entry>, CheckBox> entryTypeCheckBoxes;

    /**
     * Initializes the CalendarScreen with a manager, sets up listeners, and loads views.
     *
     * @param manager       The main application manager.
     */
    @Override
    public void initialize (Manager manager) {
        super.initialize(manager);
        this.calendarManager = manager.getCalendarManager();
        calendarManager.addCalendarUpdateListener(this);

        viewChoice = setViewChoice();

        setUpAddCalendarButton(addNewCalendarButton);
        setUpCalendarCheckBoxes();
        setUpEntryTypeCheckBoxes();
        setMonthAndYear();
        loadWeeklyView();
        loadMonthlyMiniView();
    }

    /**
     * Reloads the screen, updating all components to reflect the current state of the calendar.
     */
    @Override
    public void reload() {
        setUpCalendarCheckBoxes();
        controllerWeeklyView.reload();
        controllerMonthlyMiniView.reload();
        setMonthAndYear();
    }

    /**
     * Sets up checkboxes for managing calendars and links them to the CalendarManager.
     */
    private void setUpCalendarCheckBoxes() {
        HashMap<Calendar, CheckBox> calendarCheckBoxes = setCalendars(vBoxCalendars);

        for (Calendar calendar : calendarCheckBoxes.keySet()) {
            CheckBox checkBox = calendarCheckBoxes.get(calendar);
            checkBox.setSelected(true);
            calendarManager.setCalendarCheckBoxStatus(calendar, checkBox.isSelected());
            checkBox.setOnAction(event -> {
                calendarManager.setCalendarCheckBoxStatus(calendar, checkBox.isSelected());
                controllerWeeklyView.reload();
            });
        }
    }

    /**
     * Sets up checkboxes for managing entry types and links them to the CalendarManager.
     */
    private void setUpEntryTypeCheckBoxes() {
        entryTypeCheckBoxes = new HashMap<>();
        setEntryTypes(vBoxEntryTypes);

        for (Class<? extends Entry> entryType : entryTypeCheckBoxes.keySet()) {
            CheckBox checkBox = entryTypeCheckBoxes.get(entryType);
            checkBox.setSelected(true);
            calendarManager.setEntryTypeCheckBoxStatus(entryType, checkBox.isSelected());
            checkBox.setOnAction(event -> {
                calendarManager.setEntryTypeCheckBoxStatus(entryType, checkBox.isSelected());
                controllerWeeklyView.reload();
            });
        }
    }

    /**
     * Populates the VBox with entry types (e.g., Events, ToDos, Habits).
     *
     * @param vBox          The VBox to populate with checkboxes.
     */
    private void setEntryTypes(VBox vBox) {
        vBox.getChildren().clear();

        ArrayList<Class<? extends Entry>> temp = new ArrayList<>();
        temp.add(Event.class);
        temp.add(ToDo.class);
        temp.add(Habit.class);

        for (Class<? extends Entry> entryType : temp) {
            CheckBox checkBox = new CheckBox(entryType.getSimpleName());
            vBox.getChildren().add(checkBox);
            entryTypeCheckBoxes.put(entryType, checkBox);
        }
    }

    /**
     * Updates the labels for the month and year based on the currently selected week.
     */
    private void setMonthAndYear() {
        LocalDate firstDate = calendarManager.getDaysOfCurrentWeek().get(0);
        LocalDate secondDate = calendarManager.getDaysOfCurrentWeek().get(6);

        month01.setText(CalendarFormattingUtils.formatMonth(firstDate.getMonth().toString()));
        month02.setText(CalendarFormattingUtils.formatMonth(secondDate.getMonth().toString()));

        year01.setText(String.valueOf(firstDate.getYear()));
        year02.setText(String.valueOf(secondDate.getYear()));

        updateLabelVisibility(calendarManager.weekWithTwoMonths(), calendarManager.weekWithTwoYears());
    }

    /**
     * Opens a new editor for creating entries.
     */
    @FXML
    public void createNewEntry() {
        openNewEditor();
    }

    /**
     * Advances the calendar to the next week.
     */
    @FXML
    public void changeNext(){
        calendarManager.setSelectedDate(calendarManager.changeNextWeek());
    }

    /**
     * Moves the calendar to the previous week.
     */
    @FXML
    public void changePast(){
        calendarManager.setSelectedDate(calendarManager.changePastWeek());
    }

    /**
     * Resets the calendar to the current week.
     */
    @FXML
    public void changeCurrent(){
        calendarManager.setSelectedDate(calendarManager.getToday());
    }

    /**
     * Sets up the view choice combo box with options for selecting different views.
     *
     * @return          The initialized ComboBox instance.
     */
    @FXML
    private ComboBox<String> setViewChoice() {
        viewChoice.getItems().add("week");
        viewChoice.setDisable(true);
        return viewChoice;
    }

    /**
     * Loads the weekly view into the right pane of the calendar screen.
     */
    private void loadWeeklyView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(WEEKLY_VIEW));
        BorderPane screen;

        try {
            screen = loader.load();
            controllerWeeklyView = loader.getController();
            calendarManager.addCalendarUpdateListener(controllerWeeklyView);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        controllerWeeklyView.initialize(getManager(), getManager().getCalendarManager());
        rightGridPane.add(screen, 0, 1);
    }

    /**
     * Loads the monthly mini view into the sidebar of the calendar screen.
     */
    private void loadMonthlyMiniView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MONTHLY_MINI_VIEW));
        try {
            VBox screen = loader.load();
            sideBar.add(screen, 0, 2);
            controllerMonthlyMiniView = loader.getController();
            controllerMonthlyMiniView.initialize(getManager().getCalendarManager());

            calendarManager.addCalendarUpdateListener(controllerMonthlyMiniView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the visibility of month and year labels based on the calendar state.
     *
     * @param weekWithTwoMonths         Whether the current week spans two months.
     * @param weekWithTwoYears          Whether the current week spans two years.
     */
    private void updateLabelVisibility(boolean weekWithTwoMonths, boolean weekWithTwoYears) {
        HashMap<Label, Boolean> visibilityMap = new HashMap<>();

        visibilityMap.put(month02, weekWithTwoMonths);
        visibilityMap.put(year02, weekWithTwoMonths);
        visibilityMap.put(minus, weekWithTwoMonths);

        visibilityMap.put(year01, !(weekWithTwoMonths && !weekWithTwoYears));

        setVisibilityOfLabelUsingMap(visibilityMap);

        if (weekWithTwoMonths && !weekWithTwoYears) {
            year01.setText(null);
        }
    }

    /**
     * Sets the visibility of labels based on a map of labels to visibility states.
     *
     * @param visibilityMap         A map linking labels to their desired visibility states.
     */
    private void setVisibilityOfLabelUsingMap(HashMap<Label, Boolean> visibilityMap) {
        for (HashMap.Entry<Label, Boolean> mapEntry : visibilityMap.entrySet()) {
            Label label = mapEntry.getKey();
            Boolean isVisible = mapEntry.getValue();
            label.setVisible(isVisible);
        }
    }

    /**
     * Handles updates when the selected date changes.
     *
     * @param date          The newly selected date.
     */
    @Override
    public void onDateChanged(LocalDate date) {
        setMonthAndYear();
    }
}
