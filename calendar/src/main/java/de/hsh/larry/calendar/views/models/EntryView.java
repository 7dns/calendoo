package de.hsh.larry.calendar.views.models;

import de.hsh.larry.calendar.logic.Manager;
import de.hsh.larry.calendar.models.Entry;
import de.hsh.larry.calendar.views.calendar.EntryViewOnCalendarScreen;
import de.hsh.larry.calendar.views.detailedViews.EntryDetailedView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents a visual representation of a calendar entry.
 * This abstract class provides methods for rendering entries
 * on the calendar screen and handling interactions like opening detailed views.
 *
 * @author Felix
 */
public abstract class EntryView implements Serializable {

    private final Entry entry;

    /**
     * Constructs an EntryView with the associated entry.
     *
     * @param entry         The calendar entry associated with this view.
     */
    public EntryView(Entry entry) {
        this.entry = entry;
    }

    /**
     * Draws the calendar entry on the main CalendarScreen.
     *
     * @return A {@link Pane} representing the visual appearance of the entry.
     */
    public abstract Pane drawCalendarScreenEntry();

    /**
     * Opens the detailed view of the entry when interacted with by the user.
     *
     * @param mouseEvent The mouseevent triggering the detailed view.
     * @param manager    The manager managing application-wide data and logic.
     * @param date       The selected date for which the entry details are displayed.
     */
    public abstract void openDetailedView(MouseEvent mouseEvent, Manager manager, LocalDate date);

    Pane drawCalendarScreenEntry(String fxml, Entry entry) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Pane pane;

        try {
            pane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        EntryViewOnCalendarScreen controller = loader.getController();
        controller.setParameters(entry);

        return pane;
    }

    /**
     * Opens the detailed view of the entry with additional configurations.
     *
     * @param mouseEvent The mouseevent triggering the detailed view.
     * @param manager    The manager managing application-wide data and logic.
     * @param date       The selected date for which the entry details are displayed.
     * @param fxml       The path to the FXML file defining the detailed view layout.
     */
    void openDetailedView(MouseEvent mouseEvent, Manager manager, LocalDate date, String fxml) {
        Stage popUp = new Stage(StageStyle.TRANSPARENT);

        AnchorPane detailedView = loadDetailedView(manager, popUp, fxml, date);
        detailedView = setUpDetailedViewClipping(detailedView);

        setUpDetailedViewSceneAndStage(popUp, detailedView, mouseEvent);

        popUp.show();
    }

    /**
     * Configures the scene and stage for the detailed view.
     *
     * @param popUp         The stage for displaying the detailed view.
     * @param detailedView  The pane containing the detailed view layout.
     * @param mouseEvent    The mouseevent triggering the detailed view.
     */
    void setUpDetailedViewSceneAndStage(Stage popUp, Pane detailedView, MouseEvent mouseEvent) {
        Scene scene = new Scene(detailedView);
        scene.setFill(Color.TRANSPARENT);

        popUp.setScene(scene);
        popUp.setX(mouseEvent.getScreenX() - 25);
        popUp.setY(mouseEvent.getScreenY() - 25);

        scene.setOnMouseExited(event -> popUp.close());
    }

    /**
     * Sets up clipping for the detailed view to create rounded corners.
     *
     * @param root  The AnchorPane containing the detailed view layout.
     * @return      The modified AnchorPane with clipping applied.
     */
    AnchorPane setUpDetailedViewClipping(AnchorPane root) {
        Rectangle clip = new Rectangle();
        clip.setArcHeight(25);
        clip.setArcWidth(25);

        root.layoutBoundsProperty().addListener((observable, oldBounds, newBounds) -> {
            clip.setWidth(newBounds.getWidth());
            clip.setHeight(newBounds.getHeight());
        });

        root.setClip(clip);

        return root;
    }

    /**
     * Loads the detailed view layout and initializes its controller.
     *
     * @param manager   The Manager managing application-wide data and logic.
     * @param popUp     The Stage for displaying the detailed view.
     * @param fxml      The path to the FXML file defining the detailed view layout.
     * @param date      The selected date for which the entry details are displayed.
     * @return          An AnchorPane containing the detailed view layout.
     */
    AnchorPane loadDetailedView(Manager manager, Stage popUp, String fxml, LocalDate date) {
        AnchorPane root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        try {
            root = loader.load();
            EntryDetailedView controller = loader.getController();
            controller.initialize(entry, manager, popUp, date);
            controller.reload();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return root;
    }

}
