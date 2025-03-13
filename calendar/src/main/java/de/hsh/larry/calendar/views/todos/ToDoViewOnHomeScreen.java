package de.hsh.larry.calendar.views.todos;

import de.hsh.larry.calendar.logic.Manager;
import de.hsh.larry.calendar.models.ToDo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 * The ToDoViewOnHomeScreen class provides the methods a ToDo needs to be drawn on the HomeScreen.
 * It provides Methods for reloading it and setting up the checkbox.
 *
 * @author Felix
 */
public class ToDoViewOnHomeScreen extends ToDoViewOnScreen {

    @FXML
    private GridPane gridPane;
    @FXML
    private Region checkbox;
    @FXML
    private Label title;

    /**
     * Initializes a new ToDoViewOnHomeScreen.
     *
     * @param toDo      The ToDo that is going to be viewed.
     * @param manager   The Manager to handle user interactions.
     */
    @Override
    public void initialize(ToDo toDo, Manager manager) {
        super.initialize(toDo, manager);
        setUpCheckBox();
        setUpCheckBoxOnClick(checkbox, gridPane, date);
        reload();
    }

    /**
     * Reloads the ToDoViewOnHomeScreen and updates it with the newest data.
     */
    @Override
    void reload() {
        updateTitle(title);
    }

    /**
     * Calls the method of the supclass to update the checkbox style.
     */
    private void setUpCheckBox() {
        updateCheckBoxStyle(checkbox, gridPane, date);
    }

}
