package de.hsh.larry.calendar.views.detailedViews;

import de.hsh.larry.calendar.logic.Manager;
import de.hsh.larry.calendar.models.Entry;
import de.hsh.larry.calendar.models.ToDo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import java.time.LocalDate;

/**
 * The ToDoDetailedView class provides the methods a ToDo needs in their DetailedView.
 * It provides Methods for updating its specific information.
 *
 * @author Felix
 */
public class ToDoDetailedView extends EntryDetailedView {

    @FXML
    private GridPane gridPane;
    @FXML
    private Region edit;
    @FXML
    private Region delete;
    @FXML
    private Region close;
    @FXML
    private Region checkbox;
    @FXML
    private Label titleLabel;
    @FXML
    private Label dateAndTimeLabel;
    @FXML
    private Label rhythmLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label calendarLabel;

    private ToDo toDo;

    /**
     * Initializes a new ToDoDetailedView.
     *
     * @param entry     The Entry to be displayed.
     * @param manager   The Manager to handle user interactions.
     * @param popUp     The Stage the Editor is going to be on.
     * @param date      The date of the Entry to be displayed.
     */
    @Override
    public void initialize(Entry entry, Manager manager, Stage popUp, LocalDate date) {
        super.initialize(entry, manager, popUp, date);
        this.toDo = (ToDo) entry;
        setUpButtons(edit, delete, close, entry);
        updateCheckBoxStyle();
        setUpCheckBoxOnClick();
    }

    /**
     * Reloads the ToDoDetailedView and updates it with the newest data.
     */
    @Override
    public void reload() {
        updateLabelText(titleLabel, toDo.getTitle());
        updateDateAndTime(dateAndTimeLabel, toDo, date);
        updateLabelText(rhythmLabel, toDo.getRhythm().toString());
        updateDescription(descriptionLabel, toDo, gridPane, 2);
        updateLabelText(calendarLabel, toDo.getCalendar().toString());
    }

    /**
     * Calls the EntryView to update the checkbox style. Additionally, all elements of the Node are greyed out and
     * any given text is struck through.
     */
    private void updateCheckBoxStyle() {
        toDo.getEntryView().updateCheckBoxStyle(checkbox, titleLabel, date);
    }

    /**
     * Sets up the checkbox of a ToDo for when it gets clicked.
     */
    private void setUpCheckBoxOnClick() {
        checkbox.setOnMouseClicked(event -> {
            toDo.getEntryView().setUpCheckBoxOnClick(checkbox, titleLabel, date);
            manager.reloadAllViews();
        });
    }

}
