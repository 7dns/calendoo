package de.hsh.larry.calendar.views.dialogues;

import de.hsh.larry.calendar.TestProfile;
import de.hsh.larry.calendar.logic.Serializer;
import de.hsh.larry.calendar.models.Calendar;
import de.hsh.larry.calendar.models.Profile;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

/**
 * The ProfileLoader holds methods for either choosing to use calendoo with an existing profile, a test profile or
 * a new profile. For these funtionalities it creates dialogues to interact with the user.
 *
 * @author Felix
 */
public class ProfileLoader {

    private static final String ICON = "/de/hsh/larry/calendar/icons/icon.png";
    private static final String OPTIONS = "/de/hsh/larry/calendar/views/dialogues/profileLoaderOptions.fxml";
    private static final String CREATE = "/de/hsh/larry/calendar/views/dialogues/profileLoaderCreate.fxml";

    private Profile profile;
    private Stage loaderStage;
    private ProfileLoaderOptions profileLoaderOptions;
    private ProfileLoaderCreate profileLoaderCreate;

    /**
     * Gets a profile by creating and opening a dialog for choosing which profile should be used.
     *
     * @return  The chosen profile.
     */
    public Profile getProfile() {
        createAndShowStage();
        return profile;
    }

    /**
     * Creates and opens a dialogue for choosing which profile should be used.
     */
    private void createAndShowStage() {
        loaderStage = new Stage();
        loaderStage.setTitle("calendoo");
        loaderStage.getIcons().add(new Image(getClass().getResourceAsStream(ICON)));
        loaderStage.setScene(createOptionsScene());
        loaderStage.showAndWait();
    }

    /**
     * Creates the options for choosing which profile should be used.
     * A user can choose from the test profile, a saved profile or a new profile.
     *
     * @return  The Scene for choosing which profile should be loaded.
     */
    private Scene createOptionsScene() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(OPTIONS));
        VBox options = null;

        try {
            options = loader.load();
        } catch (IOException ignored) {

        }

        profileLoaderOptions = loader.getController();
        profileLoaderOptions.setProfileLoader(this);

        return new Scene(options);
    }

    /**
     * Creates the dialogue for the option of creating a new profile.
     *
     * @return  The Scene for creating a new profile.
     */
    private Scene createCreateScene() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(CREATE));
        VBox create = null;

        try {
            create = loader.load();
        } catch (IOException ignored) {

        }

        profileLoaderCreate = loader.getController();
        profileLoaderCreate.setProfileLoader(this);

        return new Scene(create);
    }

    /**
     * Opens the test profile of calendoo and closes the choosing dialogue.
     */
    void openTestProfile() {
        profile = TestProfile.createProfile();
        loaderStage.close();
    }

    /**
     * Opens a file chooser for choosing a profile to be loaded.
     * If a profile could be found it gets deserialized and the choosing dialogue closes.
     */
    void openFileChooser() {
        File profileFile = new FileChooser().showOpenDialog(loaderStage);

        if (profileFile != null) {
            profile = new Serializer().deserializeProfile(profileFile);
            loaderStage.close();
        }
    }

    /**
     * Switches from the choosing dialogue to the creating dialogue.
     */
    void switchToCreateScene() {
        loaderStage.setScene(createCreateScene());
    }

    /**
     * Creates a new profile with a standard calendar and closes the choosing dialogue.
     *
     * @param name  The name of the new profile.
     */
    void createNewProfile(String name) {
        profile = new Profile(name);
        profile.addCalendar(new Calendar("Personal", Color.LIGHTGREEN));
        loaderStage.close();
    }


}
