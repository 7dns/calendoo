package de.hsh.larry.calendar.views.screens;

import de.hsh.larry.calendar.logic.Manager;
import de.hsh.larry.calendar.models.Calendar;
import de.hsh.larry.calendar.models.ToDo;
import de.hsh.larry.calendar.views.todos.ContainerToDoScreen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * The ToDoScreen class includes an overview of all ToDos of today's date.
 * To achive that it includes methods for drawing the ToDos, open an Editor for creating ToDos and setting
 * up the Selection of the Calendars. Also, Sections are made for a better visualization.
 *
 * @author Felix
 */
public class ToDoScreen extends ScreenController {

    private static final String CONTAINER = "/de/hsh/larry/calendar/views/todos/containerToDoScreen.fxml";

    /**
     * Represents the sections the ToDos can get sorted into.
     */
    private enum Section {
        ALL_DAY("All-Day"),
        MORNING("Morning"),
        AFTERNOON("Afternoon"),
        EVENING("Evening");

        private final String name;

        Section(String name) {
            this.name = name;
        }

        private String getName() {
            return name;
        }
    }

    @FXML
    private Region addNewCalendarButton;
    @FXML
    private VBox vBoxCalendars;
    @FXML
    private VBox content;

    @FXML
    private Label labelNoToDos;

    private HashMap<Section, VBox> containers;
    private HashMap<Section, ContainerToDoScreen> containerControllers;
    private HashMap<Calendar, CheckBox> calendarCheckBoxes;

    /**
     * Initializes the ToDoScreen and loads the containers the ToDos can get sorted into.
     *
     * @param manager   The Manager to handle user intercations.
     */
    @Override
    public void initialize(Manager manager) {
        super.initialize(manager);
        containers = new HashMap<>();
        containerControllers = new HashMap<>();
        setUpAddCalendarButton(addNewCalendarButton);
        loadContainersAndControllers();
        setUpCalendarCheckBoxes();
    }

    /**
     * Reloads the ToDoScreen and updates its contents to their newest state.
     */
    @Override
    public void reload() {
        setUpCalendarCheckBoxes();
        reloadContent();
    }

    /**
     * Reloads and updates the list of ToDos and the contents of the ToDos to their newest state.
     * If no ToDo exists, the user is shown a message about that.
     */
    private void reloadContent() {
        addAllSectionsToContent();
        clearToDosOfAllSections();
        drawAllToDos();
        updateVisibleContainers();
        labelNoToDos.setVisible(content.getChildren().isEmpty());
    }

    /**
     * Loads the sections and their controllers the ToDos can get sortet into.
     * Creates a Map with the section and their associated VBox
     * and a Map with the section and their corresponding controller.
     */
    private void loadContainersAndControllers() {
        for (Section section : Section.values()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(CONTAINER));

            VBox vBox;
            ContainerToDoScreen controller;

            try {
                vBox = loader.load();
                controller = loader.getController();
                controller.setTitle(section.getName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            containers.put(section, vBox);
            containerControllers.put(section, controller);
        }
    }

    /**
     * Sets up checkboxes for every calendar of the users profile.
     * A checkbox determines wether the ToDos of the calendar are shown or not.
     * If a checkbox is clicked, the list of ToDos and the contents of the ToDos gets reloaded.
     */
    private void setUpCalendarCheckBoxes() {
        calendarCheckBoxes = setCalendars(vBoxCalendars);

        for (Calendar calendar : calendarCheckBoxes.keySet()) {
            CheckBox checkBox = calendarCheckBoxes.get(calendar);
            checkBox.setSelected(true);
            checkBox.setOnAction(event -> reloadContent());
        }
    }

    /**
     * Clears all content off the ToDoScreen and adds all the Sections ToDos are sorted into onto it.
     */
    private void addAllSectionsToContent() {
        content.getChildren().clear();
        for (Section section : Section.values()) {
            content.getChildren().add(containers.get(section));
        }
    }

    /**
     * Clears all ToDos off the Sections.
     */
    private void clearToDosOfAllSections() {
        for (Section section : Section.values()) {
            containerControllers.get(section).clear();
        }
    }

    /**
     * Determines the section a ToDo will be drawn into. The section is determined by their time.
     *
     * @param toDo  The ToDo to determine a section to.
     * @return      The matching section to the ToDo.
     */
    private Section determineSection(ToDo toDo) {
        if (toDo.isAllDay()) {
            return Section.ALL_DAY;
        }

        LocalTime startTime = toDo.getStartTime();
        if (startTime.isBefore(LocalTime.of(12, 0))) {
            return Section.MORNING;
        } else if (startTime.isBefore(LocalTime.of(18, 0))) {
            return Section.AFTERNOON;
        } else {
            return Section.EVENING;
        }
    }

    /**
     * Updates all currently visible sections by deleting the sections that have no Todos.
     */
    private void updateVisibleContainers() {
        for (Section section : Section.values()) {
            ContainerToDoScreen containerController = this.containerControllers.get(section);
            if (containerController.isEmpty()) {
                VBox container = containers.get(section);
                content.getChildren().remove(container);
            }
        }
    }

    /**
     * Gets all Todos from the selected calendars, sorts them by their time and draws the ones that are set to
     * today's date onto the ToDoScreen.
     * All day ToDos are shown first and then all other Todos sortet by their time into the Sections.
     */
    private void drawAllToDos() {
        LocalDate today = LocalDate.now();
        ArrayList<Calendar> selectedCalendars = getSelectedCalendars(calendarCheckBoxes);
        ArrayList<ToDo> toDos = new ArrayList<>();

        for (Calendar calendar : selectedCalendars) {
            toDos.addAll(calendar.getAllEntries(ToDo.class));
        }

        Collections.sort(toDos);

        for (ToDo toDo : toDos) {
            drawToDoInSection(today, toDo);
        }
    }

    /**
     * Draws a ToDo into into a section determined by their time, if its a ToDo of today's date.
     *
     * @param today Todays date.
     * @param toDo  The ToDo to draw into a section.
     */
    private void drawToDoInSection(LocalDate today, ToDo toDo) {
        if (!toDo.isActiveOnDate(today)) {
            return;
        }

        Section section = determineSection(toDo);
        if (section != null) {
            Pane toDoEntry = toDo.getEntryView().drawToDoScreenToDo(getManager());
            containerControllers.get(section).addToDo(toDoEntry);
        }
    }

    /**
     * Opens a new Editor for creating a new Entry of the type ToDo.
     */
    @FXML
    private void openEditorToCreateNewEntry() {
        openNewEditor().selectToDoEditor();
    }

}
