package de.hsh.larry.calendar.utils;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import de.hsh.larry.calendar.logic.GoogleCalendarManager;
import de.hsh.larry.calendar.models.GoogleCalendarService;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.EnumSet;

/**
 * The GoogleCalendarUtils class provides methods to interact with a Google-Event, including:
 * <ul>
 *     <li>converting a Calendoo-Event to a Google-Event,</li>
 *     <li>getting a Google-Event from the Google Calendar,</li>
 *     <li>transfering the most matching color of a Event.</li>
 * </ul>
 *
 * @author Laura
 */
public class GoogleCalendarUtils {

    /**
     * Converts a Calendoo-Event to a Google Calendar Event.
     *
     * @param event     The Calendoo-Event to be converted.
     * @return          The corresponding Google Calendar Event.
     */
    public static Event convertToGoogleEvent(de.hsh.larry.calendar.models.Event event) {
        Event returnEvent = new Event();

        returnEvent.setSummary(event.getTitle());
        returnEvent.setDescription(event.getDescription());
        returnEvent.setLocation(event.getLocation());
        returnEvent.setColorId(String.valueOf(chooseNearestColor(event.getColor())));

        transferEventDateTimes(event, returnEvent);

        return returnEvent;
    }

    /**
     * Checks if a Calendoo-Event that is going to interact with the Google Calendar is an all day Event.
     * Basend on the result it sets the start and end EventDateTime of the Google-Event to the right format.
     *
     * @param event         The Calendoo-Event to interact with the Google Calendar.
     * @param returnEvent   The Google-Event to set the EventDateTimes to.
     */
    public static void transferEventDateTimes(de.hsh.larry.calendar.models.Event event, Event returnEvent) {
        EventDateTime start;
        EventDateTime end;
        if (event.isAllDay()) {
            start = new EventDateTime().setDate(convertToGoogleDateTimeAllDay(event.getStartDate()));
            end = new EventDateTime().setDate(convertToGoogleDateTimeAllDay(event.getStartDate()));
        } else {
            start = new EventDateTime().setDateTime(convertToGoogleDateTime(event.getStartDateTime()));
            end = new EventDateTime().setDateTime(convertToGoogleDateTime(event.getEndDateTime()));
        }

        returnEvent.setStart(start);
        returnEvent.setEnd(end);
    }

    /**
     * Converts a LocalDateTime to a Google Calendar DateTime.
     *
     * @param localDateTime     The LocalDateTime to be converted.
     * @return                  A Google Calendar DateTime representing the date and time.
     */
    public static DateTime convertToGoogleDateTime(LocalDateTime localDateTime) {
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return new DateTime(date);
    }

    /**
     * Converts a LocalDate to a Google Calendar DateTime for allDay Events.
     *
     * @param localDate         The LocalDateTime to be converted.
     * @return                  A Google Calendar DateTime representing the date without time.
     */
    public static DateTime convertToGoogleDateTimeAllDay(LocalDate localDate) {
        String date = localDate.toString();
        return new DateTime(date);
    }

    /**
     * Tries to get a Google-Event based on its eventID.
     *
     * @param eventId   The eventID to a Google-Event.
     * @return          The corresponding Google-Event.
     */
    public static Event getGoogleEventById(String eventId) {
        Calendar calendarService = GoogleCalendarService.getInstance().getCalendarService();

        try {
            return calendarService.events().get(GoogleCalendarManager.getCalendarID(), eventId).execute();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Chooses the nearest matching color of the Google Calendar based on the given color.
     *
     * @param eventColor    The color to find a matching color to.
     * @return              The nearest matching Google color to the given color.
     */
    public static int chooseNearestColor(Color eventColor) {
        EnumSet<GoogleColors> colors = EnumSet.allOf(GoogleColors.class);

        int lowestDifference = -1;
        int colorOfLowestDifference = 0;
        for(GoogleColors co : colors) {
            int difference = ColorUtils.getDifferenceBetweenHexColors(ColorUtils.convertFXColorToHexInt(eventColor), co.getHexCode());
            if (difference < lowestDifference || lowestDifference == -1) {
                lowestDifference = difference;
                colorOfLowestDifference = co.getID();
            }
        }

        return colorOfLowestDifference;
    }

}
