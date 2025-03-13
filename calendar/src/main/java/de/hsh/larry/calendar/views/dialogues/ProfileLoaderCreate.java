package de.hsh.larry.calendar.views.dialogues;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * The ProfileLoaderCreate class holds the methods for handling the option that a new profile should be created.
 *
 * @author Felix
 */
public class ProfileLoaderCreate {

    @FXML
    private TextField textFieldName;

    private ProfileLoader profileLoader;

    /**
     * Creates a new profile with the name the user enters into the textfield.
     */
    @FXML
    private void createNewProfile() {
        profileLoader.createNewProfile(textFieldName.getText());
    }

    // - - - GETTER & SETTER - - - START - - -

    public void setProfileLoader(ProfileLoader profileLoader) {
        this.profileLoader = profileLoader;
    }

    // - - - GETTER & SETTER - - - END - - -

}
