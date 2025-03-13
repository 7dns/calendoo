package de.hsh.larry.calendar.logic;

import de.hsh.larry.calendar.models.Entry;
import de.hsh.larry.calendar.models.ToDo;
import de.hsh.larry.calendar.views.editorViews.ToDoEditorView;

/**
 * The ToDoEditor class provides methods for creating a new ToDo and saving an existing ToDo.
 *
 * @author Felix
 */
public class ToDoEditor extends EntryEditor {

    /**
     * Constructs a new ToDoEditor.
     *
     * @param manager           The Manager to handle user interactions.
     * @param toDoEditorView   The View to the ToDoEditor.
     */
    public ToDoEditor(Manager manager, ToDoEditorView toDoEditorView) {
        super(manager, toDoEditorView);
    }

    /**
     * Creates a new Entry of the type ToDo.
     */
    @Override
    public void createNewEntry() {
        super.getInputFromView();

        ToDo newToDo;

        if (isAllDay) {
            newToDo = new ToDo(calendar, title, date);
        } else {
            newToDo = new ToDo(calendar, title, date, startTime);
        }

        newToDo.setRhythm(rhythm);
        newToDo.setDescription(description);
        newToDo.setColor(color);
    }

    /**
     * Saves an existing Entry of the type ToDo with the new data entered into the Editor.
     *
     * @param entry The Entry to save the data to.
     */
    @Override
    public void saveExistingEntry(Entry entry) {
        super.saveExistingEntry(entry);
    }

}
