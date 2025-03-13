package de.hsh.larry.calendar.views.calendar;

import de.hsh.larry.calendar.models.Entry;
import de.hsh.larry.calendar.utils.ColorUtils;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The EntryViewOnCalendarScreen class provides the general methods an Entry needs to be drawn on the CalendarScreen.
 * It provides methods for setting a Label, the color and the color of the sidebar.
 *
 * @author Laura, Felix
 */
public abstract class EntryViewOnCalendarScreen {

    /**
     * Sets a selection of parameters of an Entry.
     *
     * @param entry The Entry that is going to be drawn.
     */
    public abstract void setParameters(Entry entry);

    /**
     * Sets the given Label to the given title.
     *
     * @param label The Label to set the title to.
     * @param text The title to set on the Label.
     */
    void setLabelText(Label label, String text) {
        label.setText(text);
    }

    /**
     * Sets the background color of the Entry.
     *
     * @param rectangle The background to be colored.
     * @param color     The color to set the background to.
     */
    void setColor(Rectangle rectangle, Color color) {
        rectangle.setFill(color);
    }

    /**
     * Sets the sideBar color of the Entry.
     *
     * @param sideBar   The sidebar to be colored.
     * @param color     The color to set the sideBar to.
     */
    void setSideBarColor(Region sideBar, Color color) {
        String colorAsHex = ColorUtils.convertFXColorToHexString(color);
        sideBar.setStyle(String.format("calendar-color: %s;", colorAsHex));
    }

}
