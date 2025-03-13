package de.hsh.larry.calendar.views.calendar;

import de.hsh.larry.calendar.models.Entry;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

/**
 * The CalendarDailyTimedView class draws a day of the week with its timed Entries on a Pane.
 *
 * @author Felix
 */
public class CalendarDailyTimedView {

    @FXML
    private Pane root;

    /**
     * Draws an Entry that ist timed on the CalendarDailyTimedView to the place of its start and end time.
     *
     * @param entry     The entry to draw and get its times from.
     * @param entryPane The Pane the Entry is drawn on.
     */
    public void drawTimedEntry(Entry entry, Pane entryPane) {
        int hour = entry.getStartTime().getHour();
        int minute = entry.getStartTime().getMinute();
        root.getChildren().add(entryPane);
        entryPane.setTranslateY((hour + minute / 60.0) * entry.getHeightPerHour());
    }

}
