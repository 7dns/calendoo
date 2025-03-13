package de.hsh.larry.calendar.views.dialogues;

import javafx.fxml.FXML;

/**
 * The ProfileLoaderOptions class holds the methods for handling the option the user chose of which profile should
 * be used.
 *
 * @author Felix
 */
public class ProfileLoaderOptions {

    private ProfileLoader profileLoader;

    /**
     * Opens the test profile of calendoo and closes the choosing dialogue.
     */
    @FXML
    private void openTestProfile() {
        profileLoader.openTestProfile();
    }

    /**
     * Opens a file chooser for choosing a profile to be loaded.
     */
    @FXML
    private void openFileChooser() {
        profileLoader.openFileChooser();
    }

    /**
     * Switches from the choosing dialogue to the creating dialogue for choosing to create a new profile.
     */
    @FXML
    private void createNewProfile() {
        profileLoader.switchToCreateScene();
    }

    // - - - GETTER & SETTER - - - START - - -

    public void setProfileLoader(ProfileLoader profileLoader) {
        this.profileLoader = profileLoader;
    }

    // - - - GETTER & SETTER - - - END - - -

}
