package de.hsh.larry.calendar.views.editorViews;

import de.hsh.larry.calendar.logic.*;
import de.hsh.larry.calendar.models.Entry;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.util.HashMap;

/**
 * This class provides a view frame for specific EntryEditorViews to be embedded and functionality
 * to switch between them. This class also handles saving user input via the EntryEditor. This class is
 * instantiated by the Editor and is responsible for managing the EntryEditorViews and their associated logic.
 *
 * @author Felix
 */
public class EditorView {

    @FXML
    private Label editorTitle;
    @FXML
    private GridPane gridPane;
    @FXML
    private ChoiceBox<EditorType> choiceBoxEntryType;

    private Manager manager;
    private Editor editor;
    private Entry entry;

    private HashMap<EditorType, GridPane> editorViews;
    private HashMap<EditorType, EntryEditorView> entryEditorViews;
    private HashMap<EditorType, EntryEditor> entryEditors;

    private EditorType currentSelection;

    /**
     * Initializes the EditorView with the provided Editor and EntryEditor.
     * Use this method to create a new Entry.
     *
     * @param editor            the Editor managing this view
     * @param manager           the Manager
     */
    public void initialize(Editor editor, Manager manager) {
        this.manager = manager;
        this.editor = editor;
        editorViews = new HashMap<>();
        entryEditorViews = new HashMap<>();
        entryEditors = new HashMap<>();
        editorTitle.setText("Add New Entry");
    }

    /**
     * Initializes the EditorView with an existing Entry. Also sets up the Manager and Editor.
     * Use the method to edit an existing Entry.
     *
     * @param editor            the Editor managing this view
     * @param manager           the Manager
     * @param entry             the Entry to be edited
     */
    public void initialize(Editor editor, Manager manager, Entry entry) {
        this.initialize(editor, manager);
        this.entry = entry;
        editorTitle.setText("Edit Entry");
    }

    /**
     * Sets up the EditorView by setting up its components and the default view.
     */
    public void setupEditorView() {
        setUpChoiceBox();
        loadAllEditorViewsAndControllers();
        createEntryEditors();
        initializeEntryEditorViewControllers();
        changeEditor(EditorType.EVENT);
    }

    /**
     * Configures the ChoiceBox with all EditorTypes and sets up a listener to handle changes.
     */
    private void setUpChoiceBox() {
        choiceBoxEntryType.getItems().addAll(EditorType.values());
        choiceBoxEntryType.getSelectionModel().select(EditorType.EVENT);
        choiceBoxEntryType.setOnAction(event ->
            changeEditor(choiceBoxEntryType.getSelectionModel().getSelectedItem())
        );
    }

    /**
     * Loads all EntryEditorViews, both their panes and controllers.
     */
    private void loadAllEditorViewsAndControllers() {
        for (EditorType type : EditorType.values()) {
            loadEditorViewAndController(type);
        }
    }

    /**
     * Loads the pane and controller of a given EditorType and puts them in their respective maps.
     *
     * @param type              the EditorType to be loaded
     */
    private void loadEditorViewAndController(EditorType type) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(type.getFxmlPath()));

        try {
            GridPane view = loader.load();
            EntryEditorView controller = loader.getController();
            editorViews.put(type, view);
            entryEditorViews.put(type, controller);
        } catch (IOException e) {
            throw new RuntimeException("Error loading fxml: " + type.name(), e);
        }
    }

    /**
     * Creates the EntryEditors of all EditorTypes and puts them in their map.
     */
    private void createEntryEditors() {
        entryEditors.put(
            EditorType.EVENT, new EventEditor(manager, (EventEditorView) entryEditorViews.get(EditorType.EVENT))
        );
        entryEditors.put(
            EditorType.TODO, new ToDoEditor(manager, (ToDoEditorView) entryEditorViews.get(EditorType.TODO))
        );
        entryEditors.put(
            EditorType.HABIT, new HabitEditor(manager, (HabitEditorView) entryEditorViews.get(EditorType.HABIT))
        );
    }

    /**
     * Initializes all controllers of the EntryEditorViews with their respective EntryEditors.
     */
    private void initializeEntryEditorViewControllers() {
        for (EditorType type : EditorType.values()) {
            EntryEditorView controller = entryEditorViews.get(type);
            controller.initialize(editor, entryEditors.get(type));
        }
    }

    /**
     * Switches the view to the given type.
     * If an Entry is specified, its details are loaded.
     *
     * @param newSelection      the EditorType to switch to
     */
    public void changeEditor(EditorType newSelection) {
        currentSelection = newSelection;
        choiceBoxEntryType.getSelectionModel().select(newSelection);
        GridPane editorView = editorViews.get(newSelection);
        manager.removeNodeFromGridPane(gridPane, 0, 1);
        gridPane.add(editorView, 0, 1);

        setEntryInEntryEditorView();
    }

    /**
     * If an Entry is specified, it is handed over to the currently active EntryEditorView to display its details.
     */
    private void setEntryInEntryEditorView() {
        if (entry == null) {
            return;
        }

        entryEditorViews.get(currentSelection).setEntry(entry);
    }

    /**
     * Hides the ChoiceBox to prevent the user from switching the EditorType.
     * This is used when editing an exiting Entry.
     */
    public void hideChoiceBoxEntryType() {
        choiceBoxEntryType.setVisible(false);
    }

    /**
     * On click, the Editor is called to either save the existing Entry or create a new one.
     */
    @FXML
    private void saveButton() {
        editor.saveEntry(entryEditors.get(currentSelection));
    }

}
