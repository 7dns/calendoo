package de.hsh.larry.calendar.views.todos;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * The ContainerToDoScreen class provides methods for creating sections on the ToDoScreen and adding ToDos to them.
 * A ContainerToDoScreen is one section.
 *
 * @author Felix
 */
public class ContainerToDoScreen {

    @FXML
    private Label title;
    @FXML
    private VBox toDos;

    /**
     * Checks if this section is empty by checking if it has any ToDos in their VBox.
     *
     * @return  The boolean if the section ist empty.
     */
    public boolean isEmpty() {
        return toDos.getChildren().isEmpty();
    }

    /**
     * Adds a ToDo to this section by adding it to their VBox.
     *
     * @param toDo  The ToDo to be added.
     */
    public void addToDo(Pane toDo) {
        toDos.getChildren().add(toDo);
    }

    /**
     * Clears all ToDos from this section.
     */
    public void clear() {
        toDos.getChildren().clear();
    }

    // - - - GETTER & SETTER - - - START - - -

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public String getTitle() {
        return title.getText();
    }

    // - - - GETTER & SETTER - - - END - - -

}
