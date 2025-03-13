package de.hsh.larry.calendar.utils;

/**
 * The GoogleColors enum represents the Google Calendar colors,
 * each defined by a name, ID, and hexadecimal color code.
 *
 * @author Laura
 */
public enum GoogleColors {

    LAVENDER(1, 0x7986CB),
    SAGE(2, 0x33B679),
    GRAPE(3, 0x8E24AA),
    FLAMINGO(4, 0xE67C73),
    BANANA(5, 0xF6BF26),
    TANGERINE(6, 0xF4511E),
    PEACOCK(7, 0x039BE5),
    GRAPHITE(8, 0x616161),
    BLUEBERRY(9, 0x3F51B5),
    BASIL(10, 0x0B8043),
    TOMATO(11, 0xD50000);

    private final int id;
    private final int hexCode;

    /**
     * Constructs a GoogleColors enum constant.
     *
     * @param id        The identifier of the color in Google.
     * @param hexCode   The hexadecimal color code.
     */
    GoogleColors(int id, int hexCode) {
        this.id = id;
        this.hexCode = hexCode;
    }

    // - - - GETTER & SETTER - - - START - - -

    public int getID() {
        return id;
    }

    public int getHexCode() {
        return hexCode;
    }

    // - - - GETTER & SETTER - - - END - - -
}
