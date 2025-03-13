package de.hsh.larry.calendar.utils;

import javafx.scene.paint.Color;

/**
 * The ColorUtils class provides methods to convert a JavaFX color to hexadecimal forms.
 *
 * @author Laura
 */
public class ColorUtils {

    /**
     * Converts a JavaFX color to its hexadecimal form as String (#000000).
     *
     * @param argb  The color to be converted.
     * @return      The hexadecimal form of the color.
     */
    public static String convertFXColorToHexString(Color argb) {
        String s = argb.toString();
        return "#" + s.substring(2, 8);
    }

    /**
     * Converts a JavaFX color to its hexadecimal code as Integer (0x000000).
     *
     * @param color The color to be converted.
     * @return      The hexadecimal code of the color.
     */
    public static int convertFXColorToHexInt(Color color) {
        int red = (int) (color.getRed() * 255);
        int green = (int) (color.getGreen() * 255);
        int blue = (int) (color.getBlue() * 255);
        return Integer.decode(String.format("0x%02X%02X%02X", red, green, blue));
    }

    /**
     * Calculates the absolute difference between two hexadecimal color codes.
     *
     * @param eventColorHexCode The first color to compare.
     * @param otherColorHexCode The second color to compare.
     * @return                  The difference between the colors.
     */
    public static int getDifferenceBetweenHexColors(int eventColorHexCode, int otherColorHexCode) {
        int difference = eventColorHexCode - otherColorHexCode;
        return Math.abs(difference);
    }

}
