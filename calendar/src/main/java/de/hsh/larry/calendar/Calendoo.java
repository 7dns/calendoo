package de.hsh.larry.calendar;

import de.hsh.larry.calendar.logic.GoogleCalendarManager;
import de.hsh.larry.calendar.logic.Manager;
import de.hsh.larry.calendar.logic.Serializer;
import de.hsh.larry.calendar.models.Profile;
import de.hsh.larry.calendar.views.application.ApplicationView;
import de.hsh.larry.calendar.views.dialogues.ProfileLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

/**
 * The main class for the calendoo application.
 * This class handles the initialization and setup of the application, including
 * loading profiles, managing serialization, and integrating with the Google Calendar API.
 *
 * @author Laura, Farina, Felix (Hanover University of Applied Sciences and Arts, winter term 2024/2025)
 */
public class Calendoo extends Application {

    private static final File SAVE_PATH = new File(System.getProperty("user.home") + "/Documents/calendoo");
    private static final String APPLICATION_PATH = "views/application/application.fxml";
    private static final String ICON_PATH = "/de/hsh/larry/calendar/icons/icon.png";

    private final Serializer serializer;
    private Profile profile;
    private File profileSavePath;
    private Image icon;

    /**
     * Default constructor for calendoo, initializing the serializer.
     */
    public Calendoo() {
        this.serializer = new Serializer();
    }

    /**
     * The main method to launch the application.
     * Initializes the Google Calendar API and starts the application.
     *
     * @param args              command-line arguments
     */
    public static void main(String[] args) {
        initializeGoogleCalendar();
        launch();
    }

    /**
     * Checks the connection of the Google Calendar API and sets the calendar ID.
     */
    private static void initializeGoogleCalendar() {
        GoogleCalendarManager.setCalendarID("14eff1aca7b128915c554ebfd87aaf9e98e52d92c421a9e3b52b1d120dcb8450@group.calendar.google.com");
    }


    /**
     * Starts the application, initializing the main UI and loading or creating a user profile.
     *
     * @param primaryStage      the primary stage for the application
     */
    @Override
    public void start(Stage primaryStage) {
        icon = new Image(getClass().getResourceAsStream(ICON_PATH));

        FXMLLoader loader = new FXMLLoader(getClass().getResource(APPLICATION_PATH));
        GridPane application = null;
        try {
            application = loader.load();
        } catch (IOException ignored) {

        }
        ApplicationView applicationViewController = loader.getController();

        profile = loadProfile();
        if (profile == null) {
            profile = new ProfileLoader().getProfile();
        }

        Manager manager = new Manager(application, profile, icon);
        manager.initialize();
        applicationViewController.setManager(manager);
        manager.changeToHomeScreen();

        setUpStage(primaryStage, application);
        primaryStage.show();
    }

    /**
     * Configures and displays the primary stage for the application.
     *
     * @param primaryStage      the primary stage
     * @param applicationRoot   the root layout for the scene
     */
    private void setUpStage(Stage primaryStage, GridPane applicationRoot) {
        Scene scene = new Scene(applicationRoot);

        primaryStage.setMinWidth(1350);
        primaryStage.setMinHeight(800);
        primaryStage.setResizable(false);
        primaryStage.setTitle("calendoo");
        primaryStage.getIcons().add(icon);
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            saveProfile(primaryStage);
        });

        primaryStage.show();
    }


    /**
     * Loads the user's profile from the serialized save file.
     *
     * @return                  the loaded profile, or null if loading fails
     */
    private Profile loadProfile() {
        try {
            profileSavePath = serializer.deserializeSaveFile(SAVE_PATH);
            return serializer.deserializeProfile(profileSavePath);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Saves the user's profile to a file and closes the application.
     *
     * @param primaryStage      the primary stage of the application
     */
    private void saveProfile(Stage primaryStage) {
        try {
            File saveLocation = getSaveLocation(primaryStage);
            if (saveLocation != null) {
                serializer.serializeSaveFile(SAVE_PATH, saveLocation);
                serializer.serializeProfile(saveLocation, profile);
                primaryStage.close();
            }
        } catch (Exception ignored) {

        }
    }

    /**
     * Determines the save location for the user's profile.
     *
     * @param primaryStage      the primary stage of the application
     * @return                  the file selected by the user, or the existing save path if set
     */
    private File getSaveLocation(Stage primaryStage) {
        return profileSavePath == null ? new FileChooser().showSaveDialog(primaryStage) : profileSavePath;
    }

}