package de.hsh.larry.calendar.views.application;

import de.hsh.larry.calendar.logic.Manager;
import javafx.fxml.FXML;

/**
 * Represents the view of the application, providing methods to navigate between different screens.
 *
 * @author Felix
 */
public class ApplicationView {

    private Manager manager;

    /**
     * Changes the view to the home screen.
     */
    @FXML
    public void changeToHomeScreen () {
        manager.changeToHomeScreen();
    }

    /**
     * Changes the view to the calendar screen.
     */
    @FXML
    public void changeToCalendar () {
        manager.changeToCalendar();
    }

    /**
     * Changes the view to the to-do screen.
     */
    @FXML
    public void changeToToDos () {
        manager.changeToToDos();
    }

    /**
     * Changes the view to the habits screen.
     */
    @FXML
    public void changeToHabits () {
        manager.changeToHabits();
    }

    // - - - GETTER & SETTER - - - START - - -

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    // - - - GETTER & SETTER - - - END - - -

}
