package de.hsh.larry.calendar.logic;

import de.hsh.larry.calendar.models.Calendar;
import de.hsh.larry.calendar.views.dialogues.CalendarEditorView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Manages the calendar creation and editing process, providing methods to interact
 * with the CalendarEditorView and save calendar changes
 * @author Felix
 * */
public class CalendarEditor {

    private static final String CREATOR = "/de/hsh/larry/calendar/views/dialogues/calendarEditorView.fxml";

    private final Manager manager;
    private Calendar calendar;

    private String newCalenderName;
    private Color newCalenderColor;

    private Stage creatorStage;
    private Parent creatorScreen;
    private CalendarEditorView creatorView;

    /**
     * Constructor for creating a CalendarEditor instance.
     *
     * @param manager       The manager responsible for managing the application's data
     */
    public CalendarEditor(Manager manager) {
        this.manager = manager;
    }

    /**
     * Constructor for creating a CalendarEditor instance with an existing calendar.
     *
     * @param manager       The manager responsible for managing the application's data
     * @param calendar      The existing calendar to edit
     */
    public CalendarEditor(Manager manager, Calendar calendar) {
        this(manager);
        this.calendar = calendar;
    }

    /**
     * Saves the calendar details, either creating a new one or updating an existing one.
     */
    public void saveCalendar() {
        getInputs();

        if (calendar == null) {
            manager.getProfile().addCalendar(new Calendar(newCalenderName, newCalenderColor));
        } else {
            calendar.setName(newCalenderName);
            calendar.setColor(newCalenderColor);
        }

        manager.reloadAllViews();
        closeEditor();
    }

    /**
     * Opens the calendar editor, either for creating a new calendar or editing an existing one.
     */
    public void openCreator() {
        loadCreator();
        setUpStage();

        if (calendar == null) {
            return;
        }

        loadEditorForExistingCalendar();
    }

    /**
     * Retrieves the user inputs (name and color) from the editor view.
     */
    private void getInputs() {
        newCalenderName = creatorView.getNewCalendarName();
        newCalenderColor = creatorView.getNewCalendarColor();
    }

    /**
     * Loads the CalendarEditorView FXML file and initializes the view.
     */
    private void loadCreator() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(CREATOR));

        try {
            creatorScreen = loader.load();
            creatorView = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException("Error loading fxml as Parent: " + CREATOR, e);
        }

        creatorView.initialize(this);
    }

    /**
     * Sets up the stage for the calendar editor window.
     */
    private void setUpStage() {
        creatorStage = new Stage();
        creatorStage.setTitle("calendoo – Calendar Editor");
        creatorStage.getIcons().add(manager.getApplicationIcon());
        creatorStage.setScene(new Scene(creatorScreen));
        creatorStage.show();
    }

    /**
     * Closes the calendar editor window.
     */
    private void closeEditor() {
        if (creatorStage != null) {
            creatorStage.close();
        }
    }

    /**
     * Initializes the calendar editor for an existing calendar, setting its details in the view.
     */
    private void loadEditorForExistingCalendar() {
        creatorView.setTitle("Edit Calendar");
        creatorView.setCalendarName(calendar.getName());
        creatorView.setCalendarColor(calendar.getColor());
    }

}
