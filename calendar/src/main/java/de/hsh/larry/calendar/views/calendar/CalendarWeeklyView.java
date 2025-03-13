package de.hsh.larry.calendar.views.calendar;

import de.hsh.larry.calendar.logic.CalendarManager;
import de.hsh.larry.calendar.logic.CalendarUpdateListener;
import de.hsh.larry.calendar.logic.Manager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

/**
 * The CalendarWeeklyView represents a week with its timed and all day Entries on the CalendarWeeklyView.
 * It contains methods for drawing the days into the WeeklyView and changing the look of the days and dates.
 *
 * @author Laura, Farina, Felix
 */
public class CalendarWeeklyView implements CalendarUpdateListener {

    @FXML
    private HBox hBoxDays;

    @FXML
    private Label mondayDate;
    @FXML
    private Label tuesdayDate;
    @FXML
    private Label wednesdayDate;
    @FXML
    private Label thursdayDate;
    @FXML
    private Label fridayDate;
    @FXML
    private Label saturdayDate;
    @FXML
    private Label sundayDate;

    @FXML
    private VBox allDayMonday;
    @FXML
    private VBox allDayTuesday;
    @FXML
    private VBox allDayWednesday;
    @FXML
    private VBox allDayThursday;
    @FXML
    private VBox allDayFriday;
    @FXML
    private VBox allDaySaturday;
    @FXML
    private VBox allDaySunday;

    private Manager manager;
    private CalendarManager calendarManager;

    private ArrayList<DayOfWeek> daysOfWeek;
    private HashMap<DayOfWeek, LocalDate> daysOfWeekDates;
    private HashMap<DayOfWeek, Label> daysOfWeekLabels;
    private HashMap<DayOfWeek, VBox> daysOfWeekVBoxes;

    private LocalDate today;

    /**
     * Initilizes a new CalendarWeeklyView object with its corresponding days.
     *
     * @param manager           The Manager to handle user interactions.
     * @param calendarManager   The CalendarManager to find the current week.
     */
    public void initialize(Manager manager, CalendarManager calendarManager) {
        this.manager = manager;
        this.calendarManager = calendarManager;

        initializeDaysOfWeek();
        today =  LocalDate.now();

        createMapOfDateLabels();
        createMapOfAllDayVBoxes();

        reload();
    }

    /**
     * Initializes a List of the names of the days.
     */
    private void initializeDaysOfWeek() {
        daysOfWeek = new ArrayList<>();
        Collections.addAll(daysOfWeek, DayOfWeek.values());
    }

    /**
     * Reloads the CalendarWeeklyView and updates its contents to their newest state.
     */
    public void reload() {
        loadCurrentWeek();
        createMapOfDates();
        setLabelsOfCurrentWeek();
        drawEntriesOfCurrentWeek();
    }

    /**
     * Reloads the CalendarWeeklyView if the date or week was changed.
     *
     * @param date  The new selected date.
     */
    @Override
    public void onDateChanged(LocalDate date) {
        reload();
    }

    /**
     * Loads the dates of the days from the current week.
     */
    private void loadCurrentWeek() {
        calendarManager.getDaysOfCurrentWeek();
    }

    /**
     * Creates a map of the dates that are in the current week.
     */
    private void createMapOfDates() {
        ArrayList<LocalDate> temp = calendarManager.getDaysOfCurrentWeek();
        daysOfWeekDates = createMapWithDaysOfWeekAsKey(temp);
    }

    /**
     * Creates a map of the Labels the dates are going to be written on.
     */
    private void createMapOfDateLabels() {
        ArrayList<Label> temp = new ArrayList<>();
        Collections.addAll(temp, mondayDate, tuesdayDate, wednesdayDate, thursdayDate, fridayDate, saturdayDate, sundayDate);
        daysOfWeekLabels = createMapWithDaysOfWeekAsKey(temp);
    }

    /**
     * Creates a map of the VBoxes the all day Entries are going to be written in.
     */
    private void createMapOfAllDayVBoxes() {
        ArrayList<VBox> temp = new ArrayList<>();
        Collections.addAll(temp, allDayMonday, allDayTuesday, allDayWednesday, allDayThursday, allDayFriday, allDaySaturday, allDaySunday);
        daysOfWeekVBoxes = createMapWithDaysOfWeekAsKey(temp);
    }

    /**
     * Creates a map where the keys are the names of the days from a week and the values are from a given List.
     *
     * @param temp  The List of elements to put as values.
     * @return      The HashMap with the days names as keys and elements of the List as values.
     */
    private <T> HashMap<DayOfWeek, T> createMapWithDaysOfWeekAsKey(ArrayList<T> temp) {
        HashMap<DayOfWeek, T> map = new HashMap<>();

        for (int i = 0; i < daysOfWeek.size() && i < temp.size(); i++) {
            map.put(daysOfWeek.get(i), temp.get(i));
        }

        return map;
    }

    /**
     * Sets the dates of the currents week to their corresponding Labels.
     */
    private void setLabelsOfCurrentWeek() {
        for (DayOfWeek dayOfWeek : daysOfWeek) {
            LocalDate date = daysOfWeekDates.get(dayOfWeek);
            Label label = daysOfWeekLabels.get(dayOfWeek);
            label.setText(String.valueOf(date.getDayOfMonth()));

            markTodaysDate(label, date);
        }
    }

    /**
     * Removes the style of the date-Label and marks today's date.
     *
     * @param label The Label to style.
     * @param date  The date to check if it's today's date.
     */
    private void markTodaysDate(Label label, LocalDate date) {
        label.getStyleClass().remove("button-today");
        if (date.equals(today)) {
            label.getStyleClass().add("button-today");
        }
    }

    /**
     * Clears already drawn Entries off the WeeklyView and draws all Entries of the current week into the WeeklyView,
     * through drawing the Entries of each day on the DailyView and then setting it into the WeeklyView.
     */
    private void drawEntriesOfCurrentWeek() {
        clearTimedEntries();
        clearAllDayEntries();

        for (DayOfWeek dayOfWeek : daysOfWeek) {
            LocalDate date = daysOfWeekDates.get(dayOfWeek);
            VBox allDayBox = daysOfWeekVBoxes.get(dayOfWeek);
            Pane screen = new CalendarDailyView(date, manager, allDayBox).drawAllEntriesAndGetPane();
            hBoxDays.getChildren().add(screen);
        }
    }

    /**
     * Clears all timed Entries off the WeeklyView.
     */
    private void clearTimedEntries() {
        hBoxDays.getChildren().clear();
    }

    /**
     * Clears all all-day Entries off the WeeklyView.
     */
    private void clearAllDayEntries() {
        for (VBox vBox : daysOfWeekVBoxes.values()) {
            vBox.getChildren().clear();
        }
    }
}