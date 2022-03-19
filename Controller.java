import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.FileNotFoundException;

/**
 * Write a description of JavaFX class Controller here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Controller extends Application
{
    public void start(Stage stage) throws Exception
    {
        stage = new MainViewer();
        stage.show();
    }
}
