package de.hsh.larry.calendar.logic;

import de.hsh.larry.calendar.models.Entry;
import de.hsh.larry.calendar.models.Event;
import de.hsh.larry.calendar.utils.GoogleCalendarUtils;
import de.hsh.larry.calendar.views.editorViews.EventEditorView;
import java.time.LocalTime;

import static de.hsh.larry.calendar.utils.GoogleCalendarUtils.*;

/**
 * The EventEditor class provides methods for creating a new Event and saving an existing Event.
 * If the user marked the checkbox, the Events are also going to be added or updated in the Google Calendar.
 *
 * @author Laura, Felix
 */
public class EventEditor extends EntryEditor {

    /**
     * Constructs a new EventEditor.
     *
     * @param manager           The Manager to handle user interactions.
     * @param eventEditorView   The View to the EventEditor.
     */
    public EventEditor(Manager manager, EventEditorView eventEditorView) {
        super(manager, eventEditorView);
    }

    /**
     * Creates a new Entry of the type Event.
     */
    @Override
    public void createNewEntry() {
        super.getInputFromView();

        LocalTime endTime = view.getEntryEndTime();
        String location = view.getEntryLocation();

        Event newEvent;

        if (isAllDay) {
            newEvent = new Event(calendar, title, date);
        } else {
            newEvent = new Event(calendar, title, date, startTime, endTime);
        }

        newEvent.setRhythm(rhythm);
        newEvent.setLocation(location);
        newEvent.setDescription(description);
        newEvent.setColor(color);

        if (view.getAddToGoogle()) {
            GoogleCalendarManager.insertEvent(newEvent);
        }

    }

    /**
     * Saves an existing Entry of the type Event with the new data entered into the Editor.
     *
     * @param entry The Entry to save the data to.
     */
    @Override
    public void saveExistingEntry(Entry entry) {
        super.saveExistingEntry(entry);

        Event event = (Event) entry;

        LocalTime endTime = view.getEntryEndTime();
        String location = view.getEntryLocation();

        if (!isAllDay) {
            event.setStartTime(startTime);
            event.setEndTime(endTime);
        } else {
            event.setStartTime(null);
            event.setEndTime(null);
        }

        event.setLocation(location);

        if (event.isGoogleEvent()) {
            if (!view.getAddToGoogle()) {
                GoogleCalendarManager.deleteEvent(event.getGoogleEventId());
                return;
            }
            updateGoogleEvent(event);
        }
    }

    /**
     * Updates the corresponding Google-Event to the given calendoo-Event with the data for the calendoo-Event.
     *
     * @param event The calendoo-Event to get the updated data from.
     */
    private void updateGoogleEvent(Event event) {
        com.google.api.services.calendar.model.Event updatedData = GoogleCalendarUtils.getGoogleEventById(event.getGoogleEventId());

        if (updatedData == null) {
            return;
        }

        updatedData.setSummary(event.getTitle());
        updatedData.setDescription(event.getDescription());
        updatedData.setColorId(String.valueOf(chooseNearestColor(event.getColor())));
        updatedData.setLocation(event.getLocation());

        transferEventDateTimes(event, updatedData);

        String returnId = GoogleCalendarManager.updateEvent(updatedData.getId(), updatedData);
        event.setGoogleEventId(returnId);
    }

}
