package de.hsh.larry.calendar.logic;

/**
 * Represents the type of editor in the calendar.
 * It can either be an editor for:
 * <ul>
 *     <li>EVENT: an Event,</li>
 *     <li>ToDo: a ToDo,</li>
 *     <li>HABIT: a Habit.</li>
 * </ul>
 *
 * @author Felix
 */
public enum EditorType {

    EVENT("/de/hsh/larry/calendar/views/editorView/eventEditorView.fxml"),
    TODO("/de/hsh/larry/calendar/views/editorView/toDoEditorView.fxml"),
    HABIT("/de/hsh/larry/calendar/views/editorView/habitEditorView.fxml");

    private final String fxmlPath;

    /**
     * Constructs a EditorType enum constant.
     *
     * @param fxmlPath  The Path to the fxml file.
     */
    EditorType(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    /**
     * Turns an enum constant into a String.
     *
     * @return  The enum constant as String.
     */
    @Override
    public String toString() {
        switch (this) {
            case EVENT:
                return "Event";
            case TODO:
                return "ToDo";
            case HABIT:
                return "Habit";
            default:
                throw new IllegalStateException();
        }
    }

    // - - - GETTER & SETTER - - - START - - -

    public String getFxmlPath() {
        return fxmlPath;
    }

    // - - - GETTER & SETTER - - - END - - -
}

