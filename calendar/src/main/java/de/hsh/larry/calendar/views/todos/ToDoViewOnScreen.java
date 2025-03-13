package de.hsh.larry.calendar.views.todos;

import de.hsh.larry.calendar.logic.Manager;
import de.hsh.larry.calendar.models.ToDo;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import java.time.LocalDate;

/**
 * The ToDoViewOnScreen class provides the general methods a ToDo needs to be drawn on different screens.
 * It provides Methods for updating the Habits title and the checkbox style.
 *
 * @author Felix
 */
public abstract class ToDoViewOnScreen {

    ToDo toDo;
    Manager manager;
    LocalDate date;

    /**
     * Initializes a new ToDoViewOnScreen.
     *
     * @param toDo      The ToDo that is going to be viewed.
     * @param manager   The Manager to handle user interactions.
     */
    public void initialize(ToDo toDo, Manager manager) {
        this.toDo = toDo;
        this.manager = manager;
        this.date = LocalDate.now();
    }

    /**
     * Reloads the ToDoViewOnScreen and updates it with the newest data.
     */
    abstract void reload();

    /**
     * Updates the title of the ToDoViewOnScreen.
     *
     * @param title The Label the title is going to be set on.
     */
    void updateTitle(Label title) {
        title.setText(toDo.getTitle());
    }

    /**
     * Calls the EntryView to update the checkbox style. Additionally, all elements of the Node are greyed out and
     * any given text is struck through.
     *
     * @param checkbox  The checkbox to update.
     * @param gridPane  The GridPane the checkbox and Label are laying in.
     * @param date      The date to determin this ToDos reoccurrence.
     */
    void updateCheckBoxStyle(Region checkbox, GridPane gridPane, LocalDate date) {
        toDo.getEntryView().updateCheckBoxStyle(checkbox, gridPane, date);
    }

    /**
     * Sets up the checkbox of a ToDo for when it gets clicked.
     *
     * @param checkbox  The checkbox to update.
     * @param gridPane  The GridPane the checkbox and Label are laying in.
     * @param date      The date to determin this ToDos reoccurrence.
     */
    void setUpCheckBoxOnClick(Region checkbox, GridPane gridPane, LocalDate date) {
        checkbox.setOnMouseClicked(event -> toDo.getEntryView().setUpCheckBoxOnClick(checkbox, gridPane, date));
    }

}
