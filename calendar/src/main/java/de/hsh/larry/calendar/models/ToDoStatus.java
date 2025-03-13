package de.hsh.larry.calendar.models;

import java.io.Serializable;

/**
 * Represents the status of a to-do in the calendar.
 * The task status indicates the current progress or state of a to-do.
 * <p>
 * The available statuses are:
 * <ul>
 *     <li>NOT_STARTED: The task has not yet been started.</li>
 *     <li>IN_PROGRESS: The task is currently in progress.</li>
 *     <li>DONE: The task has been completed.</li>
 * </ul>
 *
 * @author Felix
 */
public enum ToDoStatus implements Serializable {

    NOT_STARTED,
    IN_PROGRESS,
    DONE;

    private static final long serialVersionUID = 1;

}
