package de.hsh.larry.calendar.utils;
/**
 * A utility class for performing common operations related to calendar formatting.
 * This class provides methods to format month names, including capitalizing
 * the first letter and shortening month names if they exceed a specific length.
 *
 * @author Farina
 */
public class CalendarFormattingUtils {


    /**
     * Capitalizes the first letter of the specified month name and converts the rest to lowercase.
     *
     * @param month     The month name in uppercase.
     * @return          The formatted month name with the first letter capitalized.
     */
    public static String capitalizedMonth(String month) {
        return month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase();
    }

    /**
     * Formats months shortens them if they are longer than characters and then
     * calls capitalizedMonth.
     *
     * @param month     The full name of the month in uppercase.
     * @return          The formatted month.
     */
    public static String formatMonth(String month) {
        if (month.length() > 5) {
            return capitalizedMonth(month.substring(0, 3) + ".");
        } else {
            return capitalizedMonth(month);
        }
    }
}
