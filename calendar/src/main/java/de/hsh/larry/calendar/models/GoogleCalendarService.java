package de.hsh.larry.calendar.models;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import static com.google.api.client.googleapis.auth.oauth2.GoogleCredential.*;

/**
 * Manages the Google Calendar API service.
 * It uses a service account for connecting to its Google Calendar.
 *
 * @author Laura
 */
public class GoogleCalendarService {

    private static GoogleCalendarService instance;
    private final Calendar calendarService;
    private static final String APPLICATION_NAME = "Calendoo";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String path = "src/main/resources/de/hsh/larry/calendar/tokens/";
    private static final String filename = "";
    private static final String SERVICE_ACCOUNT_KEY_PATH = path + filename;

    /**
     * Constructs a new GoogleCalendarService instance for working with the Google Calendar.
     *
     * @throws IOException              If something went wrong with the FileInputStream.
     * @throws GeneralSecurityException If something went wrong with the HttpTransport.
     */
    private GoogleCalendarService() throws IOException, GeneralSecurityException {
        GoogleCredential credentials;
        credentials = fromStream(new FileInputStream(SERVICE_ACCOUNT_KEY_PATH))
                .createScoped(Collections.singleton("https://www.googleapis.com/auth/calendar"));

        this.calendarService = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credentials)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Creates and returns an instance of GoogleCalendarService.
     *
     * @return                  An instance of GoogleCalendarService.
     * @throws RuntimeException If an error occurs while creating the instance.
     */
    public static GoogleCalendarService getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (GoogleCalendarService.class) {
            if (instance == null) {
                try {
                    instance = new GoogleCalendarService();
                } catch (IOException | GeneralSecurityException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return instance;
    }

    // - - - GETTER & SETTER - - - START - - -

    public Calendar getCalendarService() {
        return calendarService;
    }

    // - - - GETTER & SETTER - - - END - - -
}
