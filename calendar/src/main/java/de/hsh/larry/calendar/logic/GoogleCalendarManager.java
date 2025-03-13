package de.hsh.larry.calendar.logic;

import com.google.api.services.calendar.Calendar;
import de.hsh.larry.calendar.models.Event;
import de.hsh.larry.calendar.models.GoogleCalendarService;
import de.hsh.larry.calendar.utils.GoogleCalendarUtils;
import java.io.IOException;

/**
 * The GoogleCalendarManager class provides methods to interact with the Google Calendar API.
 * It manages the interaction with the Google API including:
 * <ul>
 *     <li>retrieving, inserting, updating and deleting Google-Events,</li>
 *     <li>checking the connection with the Google API,</li>
 *     <li>managing the calenderID (the targeted Google Calendar).</li>
 * </ul>
 *
 * @author Laura
 */
public class GoogleCalendarManager {

    private static String calendarID;

    /**
     * Checks if the calendarID has been set.
     *
     * @return  true if the calendarID has been set,
     *          otherwise false.
     */
    private static boolean isCalendarIDSet() {
        return calendarID != null;
    }

    /**
     * Checks if the application is connected to the Google Calendar API.
     *
     * @return  true if the connection is successful,
     *          otherwise false.
     */
    public static boolean isConnected() {
        Calendar calendarService = GoogleCalendarService.getInstance().getCalendarService();
        try {
            calendarService.calendarList().list().setMaxResults(1).execute();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Inserts an Event into the Google Calendar of the calendarID.
     *
     * @param eventToInsert     The Event to be inserted.
     */
    public static void insertEvent(Event eventToInsert) {
        if (!isCalendarIDSet()) {
            return;
        }

        com.google.api.services.calendar.model.Event googleEvent;
        googleEvent = GoogleCalendarUtils.convertToGoogleEvent(eventToInsert);
        Calendar calendarService = GoogleCalendarService.getInstance().getCalendarService();
        try {
            com.google.api.services.calendar.model.Event insertedEvent = calendarService
                    .events()
                    .insert(calendarID, googleEvent)
                    .execute();

            eventToInsert.setGoogleEventId(insertedEvent.getId());
        } catch (IOException ignored) {

        }
    }

    /**
     * Updates an existing Event in the Google Calendar with new data.
     *
     * @param eventId       The ID of the Event to be updated.
     * @param updatedData   The new Event data to update the existing Event.
     * @return              The ID of the updated Event.
     */
    public static String updateEvent(String eventId, com.google.api.services.calendar.model.Event updatedData) {
        if (!isCalendarIDSet()) {
            return null;
        }

        Calendar calendarService = GoogleCalendarService.getInstance().getCalendarService();
        try {
            com.google.api.services.calendar.model.Event existingEvent = calendarService
                    .events()
                    .get(calendarID, eventId)
                    .execute();

            if (updatedData.getSummary() != null) {
                existingEvent.setSummary(updatedData.getSummary());
            }

            if (updatedData.getStart() != null) {
                existingEvent.setStart(updatedData.getStart());
            }

            if (updatedData.getEnd() != null) {
                existingEvent.setEnd(updatedData.getEnd());
            }

            if (updatedData.getDescription() != null) {
                existingEvent.setDescription(updatedData.getDescription());
            }

            if (updatedData.getColorId() != null) {
                existingEvent.setColorId(updatedData.getColorId());
            }

            if (updatedData.getLocation() != null) {
                existingEvent.setLocation(updatedData.getLocation());
            }

            com.google.api.services.calendar.model.Event updatedEvent = calendarService
                    .events()
                    .update(calendarID, eventId, existingEvent)
                    .execute();

            return updatedEvent.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes an Event from the Google Calendar using the ID of the Event.
     *
     * @param eventId   The ID of the Event to be deleted.
     */
    public static void deleteEvent(String eventId) {
        Calendar calendarService = GoogleCalendarService.getInstance().getCalendarService();
        try {
            calendarService.events().delete(calendarID, eventId).execute();
        } catch (Exception ignored) {

        }
    }

    // - - - GETTER & SETTER - - - START - - -

    public static String getCalendarID() {
        return calendarID;
    }

    public static void setCalendarID(String calendarIDToSet) {
        calendarID = calendarIDToSet;
    }

    // - - - GETTER & SETTER - - - END - - -

}
