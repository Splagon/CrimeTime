import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Write a description of JavaFX class TestControllerProperty here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ControllerOLD extends Application
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
        stage = new MainViewerOLD();
        stage.show();
    }
}