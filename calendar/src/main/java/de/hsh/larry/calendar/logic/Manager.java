package de.hsh.larry.calendar.logic;

import de.hsh.larry.calendar.models.Profile;
import de.hsh.larry.calendar.views.screens.ScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * The Manager class handles the main logic of managing the application's screens and user interactions.
 * It is responsible for loading, initializing, and switching between various screens
 * and provides utilities to reload or update specific views.
 *
 * @author Felix
 */
public class Manager {

    // To avoid typos, the paths are defined as constants
    private static final String HOME_SCREEN = "/de/hsh/larry/calendar/views/screens/homeScreen.fxml";
    private static final String CALENDAR = "/de/hsh/larry/calendar/views/screens/calendarScreen.fxml";
    private static final String TODOS = "/de/hsh/larry/calendar/views/screens/toDoScreen.fxml";
    private static final String HABITS = "/de/hsh/larry/calendar/views/screens/habitScreen.fxml";

    private final GridPane application;
    private Profile profile;
    private final Image icon;
    private CalendarManager calendarManager;

    private ArrayList<String> fxmlFiles;
    private HashMap<String, Node> screens;
    private HashMap<Node, ScreenController> controllers;

    /**
     * Constructs a new Manager instance with the given application root and user profile.
     *
     * @param application the root GridPane of the application
     * @param profile     the user's profile
     */
    public Manager(GridPane application, Profile profile, Image icon) {
        this.application = application;
        this.profile = profile;
        this.icon = icon;
    }

    /**
     * Initializes the Manager by creating screen instances, loading their controllers, and initializing them.
     */
    public void initialize() {
        calendarManager = new CalendarManager();
        screens = new HashMap<>();
        controllers = new HashMap<>();

        createListOfScreenFxmlFiles();
        loadAllScreensAndGetControllers();
        initializeAllScreenControllers();
    }

    /**
     * Populates the list of FXML file paths for all screens managed by the Manager.
     */
    private void createListOfScreenFxmlFiles() {
        fxmlFiles = new ArrayList<>();

        Collections.addAll(fxmlFiles,
            HOME_SCREEN,
            CALENDAR,
            TODOS,
            HABITS
        );
    }

    /**
     * Loads all screens and retrieves their corresponding controllers.
     */
    private void loadAllScreensAndGetControllers() {
        for (String fxml : fxmlFiles) {
            loadScreenAndGetController(fxml);
        }
    }

    /**
     * Loads a specific screen and retrieves its controller.
     *
     * @param fxml the path to the FXML file for the screen
     */
    private void loadScreenAndGetController(String fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        try {
            Node screen = loader.load();
            ScreenController controller = loader.getController();
            screens.put(fxml, screen);
            controllers.put(screen, controller);
        } catch (IOException e) {
            throw new RuntimeException("Error loading fxml: " + fxml, e);
        }
    }

    /**
     * Initializes all screen controllers.
     */
    private void initializeAllScreenControllers() {
        for (String fxml : fxmlFiles) {
            getController(fxml).initialize(this);
        }
    }

    /**
     * Reloads all views to reflect any updated data.
     */
    public void reloadAllViews() {
        reloadHomeScreen();
        reloadCalendarScreen();
        reloadToDoScreen();
        reloadHabitScreen();
    }

    /**
     * Reloads the home screen.
     */
    public void reloadHomeScreen() {
        getController(HOME_SCREEN).reload();
    }

    /**
     * Reloads the calendar screen.
     */
    public void reloadCalendarScreen() {
        getController(CALENDAR).reload();
    }

    /**
     * Reloads the to-do screen.
     */
    public void reloadToDoScreen() {
        getController(TODOS).reload();
    }

    /**
     * Reloads the habit screen.
     */
    public void reloadHabitScreen() {
        getController(HABITS).reload();
    }

    /**
     * Changes the application view to the home screen.
     */
    public void changeToHomeScreen() {
        reloadHomeScreen();
        changeTo(HOME_SCREEN);
    }
    /**
     * Changes the application view to the calendar screen.
     */
    public void changeToCalendar() {
        reloadCalendarScreen();
        changeTo(CALENDAR);
    }

    /**
     * Changes the application view to the to-dos screen.
     */
    public void changeToToDos() {
        reloadToDoScreen();
        changeTo(TODOS);
    }

    /**
     * Changes the application view to the habits screen.
     */
    public void changeToHabits() {
        reloadHabitScreen();
        changeTo(HABITS);
    }

    /**
     * Switches the application to a specified screen.
     *
     * @param fxml the FXML file path of the screen to display
     */
    private void changeTo(String fxml) {
        removeNodeFromGridPane(application, 0, 0);
        application.add(getScreen(fxml), 0, 0);
    }

    /**
     * Removes a node from a specific position in the grid pane.
     *
     * @param gridPane the grid pane
     * @param column   the column index
     * @param row      the row index
     */
    public void removeNodeFromGridPane(GridPane gridPane, int column, int row) {
        gridPane.getChildren().removeIf(node ->
                GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == column &&
                GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == row
        );
    }

    /**
     * Gets the controller corresponding to a specific FXML file.
     *
     * @param fxml the FXML file path
     * @return the screen controller
     */
    private ScreenController getController(String fxml) {
        return controllers.get(getScreen(fxml));
    }

    /**
     * Gets the screen node corresponding to a specific FXML file.
     *
     * @param fxml the FXML file path
     * @return the screen node
     */
    private Node getScreen(String fxml) {
        return screens.get(fxml);
    }

    // - - - GETTER & SETTER - - - START - - -

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Image getApplicationIcon() {
        return icon;
    }

    public Manager getManager() {
        return this;
    }

    public CalendarManager getCalendarManager() {
        return calendarManager;
    }

    // - - - GETTER & SETTER - - - END - - -

}
