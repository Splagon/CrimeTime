

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Write a description of JavaFX class TestControllerProperty here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TestControllerStats extends Application
{
    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called after the init() method has returned and after 
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage)
    {
        // Create a Button or any control item
        StatisticsData.initialiseHandler();
        stage = new StatisticsViewer(100, 200);
        stage.show();
    }
}