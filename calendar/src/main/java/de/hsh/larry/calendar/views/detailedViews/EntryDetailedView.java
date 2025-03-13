package de.hsh.larry.calendar.views.detailedViews;

import de.hsh.larry.calendar.logic.Editor;
import de.hsh.larry.calendar.logic.Manager;
import de.hsh.larry.calendar.models.Entry;
import de.hsh.larry.calendar.models.Event;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

/**
 * The abstract EntryDetailedView class provides the general methods every Entry needs in their DetailedViews.
 * It provides Methods for setting it up.
 *
 * @author Laura, Felix
 */
public abstract class EntryDetailedView {

    Manager manager;
    Stage popUp;
    LocalDate date;

    /**
     * Initializes a new EntryDetailedView.
     *
     * @param entry     The Entry to be displayed.
     * @param manager   The Manager to handle user interactions.
     * @param popUp     The Stage the Editor is going to be on.
     * @param date      The date of the Entry to be displayed.
     */
    public void initialize(Entry entry, Manager manager, Stage popUp, LocalDate date) {
        this.manager = manager;
        this.popUp = popUp;
        this.date = date;
    }

    /**
     * Reloads the EntryDetailedView and updates it with the newest data.
     */
    public abstract void reload();

    /**
     * Sets up the buttons for editing or deleting the Entry and for closing the DetailedView
     * for when they get clicked.
     *
     * @param edit      The button to access editing the Entry.
     * @param delete    The button to delete the Entry.
     * @param close     The button to clos the DetailedView.
     * @param entry     The Entry with which one interacts.
     */
    void setUpButtons(Region edit, Region delete, Region close, Entry entry) {
        edit.setOnMouseClicked(event -> {
            Editor editor = new Editor(manager, entry);
            editor.openEditor();
        });

        delete.setOnMouseClicked(event -> {
            entry.getCalendar().removeEntry(entry);
            manager.reloadAllViews();
            popUp.close();
        });

        close.setOnMouseClicked(event -> popUp.close());
    }

    /**
     * Sets the given Label to the given title.
     *
     * @param label The Label to set the title to.
     * @param text The title to set on the Label.
     */
    void updateLabelText(Label label, String text) {
        label.setText(text);
    }

    /**
     * Updates the date and time of the EntryDetailedView after formating it.
     *
     * @param label The Label to set the date on.
     * @param entry The Entry to get the time from.
     * @param date  The date of the Entry to be displayed.
     */
    void updateDateAndTime(Label label, Entry entry, LocalDate date) {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.UK);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String formattedDate = date.format(dateFormatter);

        formatter.format("%s, %s", capitalize(date.getDayOfWeek().toString()), formattedDate);

        if (entry.hasEndTime()) {
            Event event = (Event) entry;
            formatter.format(" · %s - %s", event.getStartTime(), event.getEndTime());
        } else if (!entry.isAllDay()) {
            formatter.format(" · %s", entry.getStartTime());
        }

        formatter.close();

        label.setText(sb.toString());
    }

    /**
     * Updates the description of the EntryDetailedView.
     * If the Entry has no description, the matching row gets deleted.
     *
     * @param label         The Label to set the description on.
     * @param entry         The Entry to get the description from.
     * @param gridPane      The GridPane the Label lays in.
     * @param rowToDelete   The row the Label lays in.
     */
    void updateDescription(Label label, Entry entry, GridPane gridPane, int rowToDelete) {
        if (entry.hasDescription()) {
            updateLabelText(label, entry.getDescription());
        } else {
            removeRow(gridPane, rowToDelete);
        }
    }

    /**
     * Capitalizes the first character of a String and sets the rest to lower case.
     *
     * @param s The String to format.
     * @return  The formatted String.
     */
    String capitalize(String s) {
        return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
    }

    /**
     * Removes a row form a GridPane with all its contents.
     *
     * @param gridPane  The GridPane to remove a row from.
     * @param row       The row that is going to be deleted.
     */
    void removeRow(GridPane gridPane, int row) {
        List<Node> nodesToRemove = new ArrayList<>();

        for (Node child : gridPane.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(child);
            if (rowIndex != null && rowIndex == row) {
                nodesToRemove.add(child);
            }
        }

        gridPane.getChildren().removeAll(nodesToRemove);
    }

}
