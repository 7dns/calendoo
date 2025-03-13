package de.hsh.larry.calendar.views.editorViews;

import de.hsh.larry.calendar.logic.Editor;
import de.hsh.larry.calendar.logic.EntryEditor;
import de.hsh.larry.calendar.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class extends the functionality of the EntryEditor to the needs of Habits.
 * It provides methods to set up the view and above all
 * overrides the <code>setEntryDetails</code>-method to set Habit-specific details.
 * It contains all input elements the user interacts with and provides methods to get and set inputs.
 *
 * @author Felix, Laura
 */
public class HabitEditorView extends EntryEditorView {

    @FXML
    private TextField habitTitle;
    @FXML
    private DatePicker habitStartDate;
    @FXML
    private CheckBox habitAllDay;
    @FXML
    private Label habitStartTimeLabel;
    @FXML
    private ComboBox<String> habitStartTime;
    @FXML
    private ChoiceBox<Rhythm> habitRhythm;
    @FXML
    private TextArea habitDescription;
    @FXML
    private ImageView habitIconPreview;
    @FXML
    private Button chooseHabitIcon;
    @FXML
    private ColorPicker habitColor;
    @FXML
    private ChoiceBox<Calendar> habitCalendar;
    @FXML
    private FlowPane iconChooser;
    @FXML
    private ImageView icon01, icon02, icon03, icon04, icon05,
            icon06, icon07, icon08, icon09, icon10, icon11;

    private String habitIconPath;

    /**
     * Initializes the view with the provided Editor and EntryEditor.
     * Sets up the view to manage user input.
     *
     * @param editor            the Editor instance
     * @param entryEditorView   the specific EntryEditor handling the logic
     */
    @Override
    public void initialize(Editor editor, EntryEditor entryEditorView) {
        super.initialize(editor,entryEditorView);
        initializeIconImageViews();
        setMondayAsFirstDayOfTheWeek(habitStartDate);
        setUpHabitAllDay();
        setUpHabitStartTime();
        setUpRhythmChoiceBox(habitRhythm);
        setUpCalendarChoiceBox(habitCalendar);
        setUpColorPicker(habitColor, habitCalendar);
        setUpChooseIcon();
    }

    /**
     * Updates the details displayed in the view. Extends the method of the super class to include an icon.
     * This method is only called when editing an existing Entry.
     */
    @Override
    public void setEntryDetails() {
        if (!(entry instanceof Habit)) {
            return;
        }

        super.setEntryDetails();
        Habit habit = (Habit) entry;
        toggleStartTime(habit.isAllDay());

        habit.getEntryView().setIconInImageView(habitIconPreview);
    }

    /**
     * Configures the available icons of the icon chooser to be previewed when clicked in the icon chooser.
     * When saving, the currently previewed icon is set as the habit's icon.
     */
    private void initializeIconImageViews() {
        ArrayList<ImageView> allIcons = new ArrayList<>();
        Collections.addAll(allIcons,
                icon01, icon02, icon03, icon04, icon05, icon06,
                icon07, icon08, icon09, icon10, icon11);

        for (ImageView icon : allIcons) {
            icon.setOnMouseClicked(this::setHabitIconPathAndPreviewIcon);
        }
    }

    /**
     * Configures the given ChoiceBox with limited Rhythm options as Habits need to be reoccurring.
     *
     * @param choiceBox         the ChoiceBox to configure
     */
    @Override
    void setUpRhythmChoiceBox(ChoiceBox<Rhythm> choiceBox) {
        choiceBox.getItems().addAll(Rhythm.DAILY, Rhythm.WEEKLY);
        choiceBox.getSelectionModel().select(0);
    }

    /**
     * Configures the CheckBox to allow inputs for the start time depending on whether the CheckBox
     * is selected. Sets up a listener to handle changes.
     */
    private void setUpHabitAllDay() {
        habitAllDay.setOnAction(event -> toggleStartTime(habitAllDay.isSelected()));
    }

    /**
     * Enables the inputs for the start time depending on the given boolean.
     *
     * @param status            input is allowed if true; otherwise not
     */
    private void toggleStartTime (boolean status) {
        toggleNodeDisabled(habitStartTimeLabel, status);
        toggleNodeDisabled(habitStartTime, status);
    }

    /**
     * Configures the time ComboBox with options between 00:00 and 23:30 in 30 minute intervals.
     * Sets the start time to the next half or full hour.
     */
    private void setUpHabitStartTime() {
        setUpTimeComboBox(habitStartTime);
        LocalTime startTime = calculateNextHalfHour(LocalTime.now());
        habitStartTime.getSelectionModel().select(startTime.toString());
    }

    /**
     * Configures the icon chooser to toggle its visibility when the respective button is clicked.
     */
    private void setUpChooseIcon() {
        chooseHabitIcon.setOnAction(event -> iconChooser.setVisible(!iconChooser.isVisible()));
    }

    // - - - GETTER & SETTER - - - START - - -

    @Override
    public Calendar getEntryCalendar() {
        return habitCalendar.getValue();
    }

    @Override
    public void setEntryCalendar(Calendar calendar) {
        habitCalendar.setValue(calendar);
    }

    @Override
    public String getEntryTitle() {
        return habitTitle.getText();
    }

    @Override
    public void setEntryTitle(String title) {
        habitTitle.setText(title);
    }

    @Override
    public String getEntryDescription() {
        return habitDescription.getText();
    }

    @Override
    public void setEntryDescription(String description) {
        habitDescription.setText(description);
    }

    @Override
    public LocalDate getEntryStartDate() {
        return LocalDate.parse(habitStartDate.getValue().toString());
    }

    @Override
    public void setEntryStartDate(LocalDate date) {
        habitStartDate.setValue(date);
    }

    @Override
    public boolean isEntryAllDay() {
        return habitAllDay.isSelected();
    }

    @Override
    public void setEntryAllDay(boolean allDay) {
        habitAllDay.setSelected(allDay);
    }

    @Override
    public LocalTime getEntryStartTime() {
        return LocalTime.parse(habitStartTime.getValue());
    }

    @Override
    public void setEntryStartTime(LocalTime time) {
        habitStartTime.setValue(time.toString());
    }

    @Override
    public Rhythm getEntryRhythm() {
        return habitRhythm.getValue();
    }

    @Override
    public void setEntryRhythm(Rhythm rhythm) {
        habitRhythm.setValue(rhythm);
    }

    @Override
    public Color getEntryColor() {
        return habitColor.getValue();
    }

    @Override
    public void setColorPickerToCalendarColor(Color color) {
        habitColor.setValue(color);
    }

    @Override
    public String getHabitIconPath() {
        return habitIconPath;
    }

    @FXML
    private void setHabitIconPathAndPreviewIcon(MouseEvent event) {
        ImageView clickedIcon = (ImageView) event.getSource();
        String habitIconPathFull = clickedIcon.getImage().getUrl();
        habitIconPath = habitIconPathFull.substring(habitIconPathFull.indexOf("/de"));
        habitIconPreview.setImage(new Image(getClass().getResource(habitIconPath).toExternalForm()));
        iconChooser.setVisible(false);
    }

    // - - - GETTER & SETTER - - - END - - -

}
