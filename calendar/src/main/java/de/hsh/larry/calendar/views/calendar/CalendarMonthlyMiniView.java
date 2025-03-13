package de.hsh.larry.calendar.views.calendar;

import de.hsh.larry.calendar.logic.CalendarManager;
import de.hsh.larry.calendar.logic.CalendarUpdateListener;
import de.hsh.larry.calendar.utils.CalendarFormattingUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The CalendarMonthlyMiniView represents a simplified monthly calendar view.
 * It allows navigation between month, displays the dates and highlights selected
 * and current days.
 *
 * @author Farina
 * */
public class CalendarMonthlyMiniView implements CalendarUpdateListener {

    @FXML
    private Button
            btn01, btn02, btn03, btn04, btn05, btn06, btn07,
            btn08, btn09, btn10, btn11, btn12, btn13, btn14,
            btn15, btn16, btn17, btn18, btn19, btn20, btn21,
            btn22, btn23, btn24, btn25, btn26, btn27, btn28,
            btn29, btn30, btn31, btn32, btn33, btn34, btn35,
            btn36, btn37, btn38, btn39, btn40, btn41, btn42;
    @FXML
    private Label shownMonth;
    @FXML
    private Label shownYear;

    private Button selectedButton;
    private ArrayList<Button> dayButtons;
    private CalendarManager calendarManager;
    private LocalDate date;


    /**
     * Initializes the controller with the specified CalendarManager and sets up the view.
     *
     * @param calendarManager   The CalendarManager instance that manages calendar data.
     */
    public void initialize(CalendarManager calendarManager) {
        this.calendarManager = calendarManager;
        this.date = calendarManager.getToday();
        initializeButtons();
        reload();
    }

    /**
     * Initializes the day buttons and assigns click event handlers to them.
     */
    @FXML
    private void initializeButtons() {
        dayButtons = new ArrayList<>();
        Collections.addAll(dayButtons,
                btn01, btn02, btn03, btn04, btn05, btn06, btn07,
                btn08, btn09, btn10, btn11, btn12, btn13, btn14,
                btn15, btn16, btn17, btn18, btn19, btn20, btn21,
                btn22, btn23, btn24, btn25, btn26, btn27, btn28,
                btn29, btn30, btn31, btn32, btn33, btn34, btn35,
                btn36, btn37, btn38, btn39, btn40, btn41, btn42
        );

        for (Button button : dayButtons) {
            button.setOnAction(this::handleButtonClick);
        }
    }

    /**
     * Reloads the view by updating button texts and labels for the current date.
     */
    public void reload(){
        setButtonText();
        setMonthLabel();
        setYearLabel();
    }

    /**
     * Advances the calendar to the next month and reloads the view.
     *
     * @param event The ActionEvent triggered by the "Next Month" button.
     */
    @FXML
    public void nextMonth(ActionEvent event) {
        this.date = date.plusMonths(1);
        reload();
    }

    /**
     * Moves the calendar to the previous month and reloads the view.
     *
     * @param event The ActionEvent triggered by the "Previous Month" button.
     */
    @FXML
    public void prevMonth(ActionEvent event) {
        this.date = date.minusMonths(1);
        reload();
    }

    /**
     * Handles the event when a day button is clicked. Updates the selected date
     * in the CalendarManager and visually indicates the selected button.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    private void handleButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        LocalDate clickedDate = (LocalDate) clickedButton.getUserData();
        if (clickedDate == null) return;

        calendarManager.setSelectedDate(clickedDate);

        if (selectedButton != null) {
            selectedButton.getStyleClass().remove("button-selected");
        }

        selectedButton = clickedButton;
        selectedButton.getStyleClass().add("button-selected");

    }

    /**
     * Updates the text and metadata of the day buttons to match the currently displayed month.
     * Ensures buttons outside the current month are hidden.
     */
    private void setButtonText() {
        clearButtons();
        ArrayList<LocalDate> daysInMonth = calendarManager.getDaysOfCurrentMonth(date);

        DayOfWeek firstDayOfMonth = getFirstDayOfMonth();
        int startIndex = getStartDayIndex(firstDayOfMonth);

        for (int i = 0; i < dayButtons.size(); i++) {
            Button button = dayButtons.get(i);
            if (i >= startIndex && i < startIndex + daysInMonth.size()) {
                LocalDate currentDay = daysInMonth.get(i - startIndex);
                button.setText(String.valueOf(currentDay.getDayOfMonth()));
                button.setUserData(currentDay);
                button.setVisible(true);

                if (currentDay.equals(calendarManager.getToday())) {
                    button.getStyleClass().add("button-today");
                }
            } else {
                button.setVisible(false);
            }
        }
    }

    /**
     * Retrieves the day of the week for the first day of the currently displayed month.
     *
     * @return  The DayOfWeek of the first day of the month.
     */
    private DayOfWeek getFirstDayOfMonth() {
        return date.withDayOfMonth(1).getDayOfWeek();
    }

    /**
     * Calculates the starting index for placing the first day of the month in the grid.
     * @param firstDayOfWeek        The day of the week for the first day of the month.
     * @return                      The starting index in the grid for the first day of the month.
     */
    private int getStartDayIndex(DayOfWeek firstDayOfWeek) {
        return (firstDayOfWeek.getValue() - 1) % 7;
    }

    /**
     * Clears the text and css-styles of all day buttons.
     */
    @FXML
    private void clearButtons() {
        for (Button button : dayButtons) {
            button.setText("");
            button.getStyleClass().removeAll("button-today", "button-selected");
        }
    }

    /**
     * Updates the month label to display the name of the currently displayed month.
     */
    @FXML
    private void setMonthLabel() {
        shownMonth.setText(CalendarFormattingUtils.capitalizedMonth(date.getMonth().toString()));
    }

    /**
     * Updates the year label to display the currently displayed year.
     */
    @FXML
    private void setYearLabel() {
        shownYear.setText(String.valueOf(date.getYear()));
    }

    /**
     * Updates the displayed date when the date in CalendarManager changes.
     *
     * @param date The new date selected in the CalendarManager.
     */
    @Override
    public void onDateChanged(LocalDate date) {
        this.date = calendarManager.getSelectedDate();
        reload();
    }
}