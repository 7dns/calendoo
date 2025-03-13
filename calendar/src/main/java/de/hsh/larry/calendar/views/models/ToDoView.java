package de.hsh.larry.calendar.views.models;

import de.hsh.larry.calendar.logic.Manager;
import de.hsh.larry.calendar.models.Entry;
import de.hsh.larry.calendar.models.ToDo;
import de.hsh.larry.calendar.views.todos.ToDoViewOnScreen;
import de.hsh.larry.calendar.models.ToDoStatus;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents a visual view for ToDo entries in the calendar.
 * Extends {@link EntryView} and provides specific methods for rendering ToDo items
 * on various screens and managing their interactions.
 * @author Felix, Laura
 */
public class ToDoView extends EntryView implements Serializable {

    private static final String CALENDAR = "/de/hsh/larry/calendar/views/calendar/toDoViewOnCalendarScreen.fxml";
    private static final String HOME = "/de/hsh/larry/calendar/views/todos/toDoViewOnHomeScreen.fxml";
    private static final String TODO = "/de/hsh/larry/calendar/views/todos/toDoViewOnToDoScreen.fxml.fxml";
    private static final String DETAIL = "/de/hsh/larry/calendar/views/detailedViews/toDoDetailedView.fxml";

    private final ToDo toDo;

    /**
     * Constructs a ToDoView with the associated ToDo entry.
     *
     * @param entry The entry object representing the To-Do item.
     *              Must be an instance of toDO.
     */
    public ToDoView(Entry entry) {
        super(entry);
        toDo = (ToDo) entry;
    }

    /**
     * Draws the ToDo item on the CalendarScreen.
     *
     * @return A pane representing the visual appearance of the To-Do item on the calendar.
     */
    @Override
    public Pane drawCalendarScreenEntry() {
        return drawCalendarScreenEntry(CALENDAR, toDo);
    }

    /**
     * Opens the detailed view for this ToDo item.
     *
     * @param mouseEvent The mouseevent triggering the detailed view.
     * @param manager    The manager managing application-wide data and logic.
     * @param date       The selected date for which the ToDo details are displayed.
     */
    @Override
    public void openDetailedView(MouseEvent mouseEvent, Manager manager, LocalDate date) {
        openDetailedView(mouseEvent, manager, date, DETAIL);
    }

    /**
     * Draws the ToDo item on the ToDo-specific screen.
     *
     * @param manager   The manager managing application-wide data and logic.
     * @return          A pane representing the ToDo item's appearance on the To-Do screen.
     */
    public Pane drawToDoScreenToDo(Manager manager) {
        return drawScreenToDo(manager, TODO);
    }

    /**
     * Draws the ToDo item on the home screen.
     *
     * @param manager   The manager managing application-wide data and logic.
     * @return          A gridPane representing the ToDo item's appearance on the home screen.
     */
    public GridPane drawHomeScreenToDo(Manager manager) {
        return drawScreenToDo(manager, HOME);
    }

    /**
     * Updates the styles of a checkbox and its parent node to reflect the ToDo item's status.
     *
     * @param checkbox The region representing the checkbox.
     * @param parent   The parent node associated with the checkbox.
     * @param date     The date for which the status is being displayed.
     */
    public void updateCheckBoxStyle(Region checkbox, Node parent, LocalDate date) {
        checkbox.getStyleClass().clear();
        checkbox.getStyleClass().add("checkbox");
        parent.getStyleClass().clear();

        switch (toDo.getStatus(date)) {
            case NOT_STARTED:
                parent.getStyleClass().add("todo_not_done");
                checkbox.getStyleClass().add("checkbox_not-started");
                break;
            case IN_PROGRESS:
                parent.getStyleClass().add("todo_not_done");
                checkbox.getStyleClass().add("checkbox_in-progress");
                break;
            case DONE:
                parent.getStyleClass().add("todo_done");
                checkbox.getStyleClass().add("checkbox_done");
                break;
        }
    }

    /**
     * Handles click events on a checkbox, cycling the ToDo item's status
     * between NOT_STARTED, IN_PROGRESS, and DONE, and updating the styles accordingly.
     *
     * @param checkbox The region representing the checkbox.
     * @param parent   The parent node associated with the checkbox.
     * @param date     The date for which the status is being modified.
     */
    public void setUpCheckBoxOnClick(Region checkbox, Node parent, LocalDate date) {
        switch (toDo.getStatus(date)) {
            case NOT_STARTED:
                toDo.setStatus(date, ToDoStatus.IN_PROGRESS);
                break;
            case IN_PROGRESS:
                toDo.setStatus(date, ToDoStatus.DONE);
                break;
            case DONE:
                toDo.setStatus(date, ToDoStatus.NOT_STARTED);
                break;
        }

        updateCheckBoxStyle(checkbox, parent, date);
    }

    /**
     * Helper method to load and render the ToDo item on a specified screen.
     *
     * @param manager           The manager managing application-wide data and logic.
     * @param fxml              The FXML path for the specific screen.
     * @return                  A gridPane representing the ToDo on the specified screen.
     * @throws RuntimeException If an IO-error occurs while loading the FXML.
     */
    private GridPane drawScreenToDo(Manager manager, String fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        GridPane gridPane;

        try {
            gridPane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ToDoViewOnScreen controller = loader.getController();
        controller.initialize(toDo, manager);

        return gridPane;
    }
}