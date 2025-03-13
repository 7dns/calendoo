package de.hsh.larry.calendar.views.todos;

import de.hsh.larry.calendar.logic.Manager;
import de.hsh.larry.calendar.models.ToDo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;

/**
 * The ToDoViewOnToDoScreen class provides the methods a ToDo needs to be drawn on the ToDoScreen.
 * It provides Methods for reloading it, updating the time, calendar and calendar color and also for setting up the
 * the checkbox and DetailedView of a ToDo.
 *
 * @author Felix
 */
public class ToDoViewOnToDoScreen extends ToDoViewOnScreen {

    @FXML
    private GridPane gridPane;
    @FXML
    private Region highlight;
    @FXML
    private Region checkbox;
    @FXML
    private Label title;
    @FXML
    private HBox details;
    @FXML
    private HBox detailsTime;
    @FXML
    private Label time;
    @FXML
    private Circle calendarColor;
    @FXML
    private Label calendar;

    /**
     * Initializes a new ToDoViewOnToDoScreen.
     *
     * @param toDo      The ToDo that is going to be viewed.
     * @param manager   The Manager to handle user interactions.
     */
    @Override
    public void initialize(ToDo toDo, Manager manager) {
        super.initialize(toDo, manager);
        setUpCheckBox();
        setUpCheckBoxOnClick(checkbox, gridPane, date);
        setUpShowDetails(toDo, manager);
        reload();
    }

    /**
     * Reloads the ToDoViewOnToDoScreen and updates it with the newest data.
     */
    @FXML
    void reload() {
        updateTitle(title);
        updateTime();
        updateCalendarColor();
        updateCalendar();
    }

    /**
     * Updates the time of the ToDoViewOnToDoScreen.
     */
    private void updateTime() {
        if (!toDo.isAllDay()) {
            time.setText(toDo.getStartTime().toString());
        } else {
            details.getChildren().remove(detailsTime);
        }
    }

    /**
     * Updates the color of the calendar of the ToDoViewOnToDoScreen.
     */
    private void updateCalendarColor() {
        calendarColor.setFill(toDo.getCalendar().getColor());
    }

    /**
     * Updates the calendar of the ToDoViewOnToDoScreen.
     */
    private void updateCalendar() {
        calendar.setText(toDo.getCalendar().getName());
    }

    /**
     * Calls the method of the supclass to update the checkbox style.
     */
    private void setUpCheckBox() {
        updateCheckBoxStyle(checkbox, gridPane, date);
    }

    /**
     * Sets up the DetailedView of a ToDo for when it gets clicked.
     *
     * @param toDo      The ToDo to be displayed.
     * @param manager   The Manager for handling user interactions.
     */
    private void setUpShowDetails(ToDo toDo, Manager manager) {
        highlight.setOnMouseClicked(event -> toDo.getEntryView().openDetailedView(event, manager, date));
    }

}
