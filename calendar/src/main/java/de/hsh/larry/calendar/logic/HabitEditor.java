package de.hsh.larry.calendar.logic;

import de.hsh.larry.calendar.models.Entry;
import de.hsh.larry.calendar.models.Habit;
import de.hsh.larry.calendar.views.editorViews.HabitEditorView;

/**
 * The HabitEditor class provides methods for creating a new Habit and saving an existing Habit.
 *
 * @author Laura, Felix
 */
public class HabitEditor extends EntryEditor {

    /**
     * Constructs a new HabitEditor.
     *
     * @param manager           The Manager to handle user interactions.
     * @param habitEditorView   The View to the HabitEditor.
     */
    public HabitEditor(Manager manager, HabitEditorView habitEditorView) {
        super(manager, habitEditorView);
    }

    /**
     * Creates a new Entry of the type Habit.
     */
    @Override
    public void createNewEntry() {
        super.getInputFromView();

        Habit newHabit;

        if (isAllDay) {
            newHabit = new Habit(calendar, title, date, rhythm);
        } else {
            newHabit = new Habit(calendar, title, date, startTime, rhythm);
        }

        newHabit.setDescription(description);
        newHabit.setColor(color);

        newHabit.setIcon(view.getHabitIconPath());
    }

    /**
     * Saves an existing Entry of the type Habit with the new data entered into the Editor.
     *
     * @param entry The Entry to save the data to.
     */
    @Override
    public void saveExistingEntry(Entry entry) {
        super.saveExistingEntry(entry);

        Habit habit = (Habit) entry;

        habit.setIcon(view.getHabitIconPath());
    }

}
