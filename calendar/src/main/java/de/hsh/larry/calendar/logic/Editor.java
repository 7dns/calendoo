package de.hsh.larry.calendar.logic;

import de.hsh.larry.calendar.models.*;
import de.hsh.larry.calendar.views.editorViews.EditorView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * This class handles the logic for the Editor, managing the creation, editing, and saving of Entries.
 * It creates the EditorView which contains the different EntryEditorViews.
 * The logic for the different Entry types is handled by the EntryEditors which are known to the Editor View.
 *
 * @author Felix
 */
public class Editor {

    private static final String EDITOR = "/de/hsh/larry/calendar/views/editorView/editorView.fxml";

    private final Manager manager;
    private Entry entry;

    private Stage editorStage;
    private Parent editorScreen;
    private EditorView editorView;

    /**
     * Constructs an Editor with the Manager.
     * Use this constructor to create a new Entry.
     *
     * @param manager   the Manager
     */
    public Editor(Manager manager) {
        this.manager = manager;
    }

    /**
     * Constructs an editor with the manager and the Entry to be edited.
     * Use this constructor to edit an existing Entry.
     *
     * @param manager   the main application manager
     * @param entry     the Entry to be edited
     */
    public Editor(Manager manager, Entry entry) {
        this(manager);
        this.entry = entry;
    }

    /**
     * Opens the editor stage. Loads the editor view within and initializes it.
     * If an Entry to be edited was given, changes the EditorView to a specific one matching that Entry's class.
     */
    public void openEditor() {
        loadEditor();
        setUpStage();

        if (entry == null) {
            return;
        }

        selectSpecificEditor();
    }

    /**
     * Changes the EditorView content to a specific EntryEditorView based on the given Entry.
     * The ChoiceBox to change the Editor is then hidden to prevent the user from switching it.
     * This method is only called if an Entry to be edited was given.
     */
    private void selectSpecificEditor() {
        if (entry instanceof Habit) {
            selectHabitEditor();
        } else if (entry instanceof ToDo) {
            selectToDoEditor();
        } else if (entry instanceof Event) {
            selectEventEditor();
        }

        editorView.hideChoiceBoxEntryType();
    }

    /**
     * Changes the EditorView to the EventEditorView for editing events.
     */
    public void selectEventEditor() {
        editorView.changeEditor(EditorType.EVENT);
    }

    /**
     * Changes the EditorView to the ToDoEditorView for editing to-dos.
     */
    public void selectToDoEditor() {
        editorView.changeEditor(EditorType.TODO);
    }

    /**
     * Changes the EditorView to the HabitEditorView for editing habits.
     */
    public void selectHabitEditor() {
        editorView.changeEditor(EditorType.HABIT);
    }

    /**
     * Loads the EditorView, initializes it and sets up its view.
     * If an Entry to be edited was given, the EditorView is given that Entry to load its contents.
     */
    private void loadEditor() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EDITOR));

        try {
            editorScreen = loader.load();
            editorView = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException("Error loading fxml as Parent: " + EDITOR, e);
        }

        if (entry != null) {
            editorView.initialize(this, manager, entry);
        } else {
            editorView.initialize(this, manager);
        }

        editorView.setupEditorView();
    }

    /**
     * Sets up the stage, places the EditorView in it and shows the stage.
     */
    private void setUpStage() {
        editorStage = new Stage();
        editorStage.setMinWidth(500);
        editorStage.setMinHeight(600);
        editorStage.setResizable(false);
        editorStage.setTitle("calendoo – Editor");
        editorStage.getIcons().add(manager.getApplicationIcon());
        editorStage.setScene(new Scene(editorScreen));
        editorStage.show();
    }

    /**
     * Closes the editor stage.
     */
    private void closeEditor() {
        if (editorStage == null) {
            return;
        }

        editorStage.close();
    }

    /**
     * Either saves an existing Entry or creates a new one using an EntryEditor.
     * This method is called by the EditorView when the user saves their input.
     * Afterward, the editor is closed and all views are reloaded to reflect the current status.
     *
     * @param entryEditor   the EntryEditor managing the input
     */
    public void saveEntry(EntryEditor entryEditor) {
        if (entry != null) {
            entryEditor.saveExistingEntry(entry);
        } else {
            entryEditor.createNewEntry();
        }

        closeEditor();
        manager.reloadAllViews();
    }

}
