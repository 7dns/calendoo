package de.hsh.larry.calendar.logic;

import de.hsh.larry.calendar.models.Profile;
import java.io.*;

/**
 * This class is responsible for serializing and deserializing.
 *
 * @author Felix
 */
public class Serializer {

    /**
     * Serializes a save file.
     *
     * @param saveFile              the file location of the save file to be serialized
     * @param profileSavePath       the save file to serialize
     */
    public void serializeSaveFile(File saveFile, File profileSavePath) {
        try {
            FileOutputStream fos = new FileOutputStream(saveFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(profileSavePath);
            oos.close();
        } catch (IOException ignored) {

        }
    }

    /**
     * Deserializes a save file.
     *
     * @param openFile              the file location of the save file to be deserialized
     * @return                      the deserialized save file
     */
    public File deserializeSaveFile(File openFile) {
        File deserializedSaveFile = null;

        try {
            FileInputStream fis = new FileInputStream(openFile);
            ObjectInputStream ois = new ObjectInputStream(fis);

            deserializedSaveFile = (File) ois.readObject();
            ois.close();
        } catch (Exception ignored) {

        }

        return deserializedSaveFile;
    }

    /**
     * Serializes a Profile.
     *
     * @param saveFile              the file location of the Profile to be serialized
     * @param profileToSerialize    the Profile to serialize
     */
    public void serializeProfile(File saveFile, Profile profileToSerialize) {
        try {
            FileOutputStream fos = new FileOutputStream(saveFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(profileToSerialize);
            oos.close();
        } catch (IOException ignored) {

        }
    }

    /**
     * Deserializes a Profile.
     *
     * @param openFile              the file location of the Profile to be deserialized
     * @return                      the deserialized Profile
     */
    public Profile deserializeProfile(File openFile) {
        Profile deserializedProfile = null;

        try {
            FileInputStream fis = new FileInputStream(openFile);
            ObjectInputStream ois = new ObjectInputStream(fis);

            deserializedProfile = (Profile) ois.readObject();
            ois.close();
        } catch (Exception ignored) {

        }

        return deserializedProfile;
    }

}
