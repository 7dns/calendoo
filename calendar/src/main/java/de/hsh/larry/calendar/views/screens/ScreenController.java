package de.hsh.larry.calendar.views.screens;

import de.hsh.larry.calendar.logic.CalendarEditor;
import de.hsh.larry.calendar.logic.Editor;
import de.hsh.larry.calendar.logic.Manager;
import de.hsh.larry.calendar.models.Calendar;
import de.hsh.larry.calendar.models.Profile;
import de.hsh.larry.calendar.utils.ColorUtils;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is extended by all screens and provides functionality by providing methods subclasses use.
 * It includes methods for interacting with different calenders and open editors.
 *
 * @author Laura, Felix
 */
public abstract class ScreenController {

    private Manager manager;
    private Profile profile;

    /**
     * Initializes a new ScreenController.
     *
     * @param manager   The Manager to handle user interactions.
     */
    public void initialize(Manager manager) {
        this.manager = manager;
        this.profile = manager.getProfile();
    }

    /**
     * Reloads the ScreenController and updates its contents to their newest state.
     */
    public abstract void reload ();

    /**
     * Gets all calendars of the profile and lists them in the View.
     * Every calendar gets a checkbox to select it as visible.
     * Also, it sets up a context menu for the calendars.
     *
     * @param vBox  The VBox the calendars are listed in.
     * @return      The Map of checkboxes associated with the calendars.
     */
    HashMap<Calendar, CheckBox> setCalendars(VBox vBox) {
        HashMap<Calendar, CheckBox> calendarCheckBoxes = new HashMap<>();
        vBox.getChildren().clear();

        for (Calendar calendar : profile.getCalendars()) {
            CheckBox checkBox = new CheckBox(calendar.getName());
            checkBox.setStyle(String.format("box-color: %s;", ColorUtils.convertFXColorToHexString(calendar.getColor())));
            checkBox.setContextMenu(setUpCalendarContextMenu(calendar));
            vBox.getChildren().add(checkBox);
            calendarCheckBoxes.put(calendar, checkBox);
        }

        return calendarCheckBoxes;
    }

    /**
     * Gets a List of all the calendars that are selected through their associated checkbox.
     *
     * @param calendarCheckBoxes    The Map of checkboxes associated with the calendars.
     * @return                      The ArrayList of all selected Calendars.
     */
    ArrayList<Calendar> getSelectedCalendars (HashMap<Calendar, CheckBox> calendarCheckBoxes) {
        ArrayList<Calendar> selectedCalendars = new ArrayList<>();

        for (Calendar calendar : calendarCheckBoxes.keySet()) {
            if (calendarCheckBoxes.get(calendar).isSelected()) {
                selectedCalendars.add(calendar);
            }
        }

        return selectedCalendars;
    }

    /**
     * Sets up the add calendar button for when it gets clicked.
     *
     * @param region    The add calendar button.
     */
    void setUpAddCalendarButton(Region region) {
        region.setOnMouseClicked(event -> openNewCalendarEditor());
    }

    /**
     * Makes a new Editor and opens it.
     *
     * @return  The new Editor.
     */
    Editor openNewEditor() {
        Editor editor = new Editor(manager);
        editor.openEditor();
        return editor;
    }

    /**
     * Makes a new CalendarEditor and opens it.
     */
    private void openNewCalendarEditor() {
        openNewCalendarEditor(null);
    }

    /**
     * Makes a new CalendarEditor and opens it with the information from the given calendar.
     *
     * @param calendar  The calendar to get information from.
     */
    private void openNewCalendarEditor(Calendar calendar) {
        CalendarEditor calendarCreator = new CalendarEditor(manager, calendar);
        calendarCreator.openCreator();
    }

    /**
     * Sets up the context menu for the calendars with the functions of either renaming or deleting the calendar that
     * was clicken on.
     *
     * @param calendar  The calendar to execute the funtionality to.
     * @return          The context menu that was set up.
     */
    private ContextMenu setUpCalendarContextMenu(Calendar calendar) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem rename = new MenuItem("Rename");
        MenuItem delete = new MenuItem("Delete");

        rename.setOnAction((event) -> {
            openNewCalendarEditor(calendar);
            manager.reloadAllViews();
            contextMenu.hide();
        });

        delete.setOnAction((event) -> {
            profile.removeCalendar(calendar);
            manager.reloadAllViews();
            contextMenu.hide();
        });

        contextMenu.getItems().addAll(rename, delete);

        return contextMenu;
    }

    // - - - GETTER & SETTER - - - START - - -

    Manager getManager() {
        return manager;
    }

    Profile getProfile() {
        return profile;
    }

    // - - - GETTER & SETTER - - - END - - -

}
