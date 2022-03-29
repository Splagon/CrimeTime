import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Used to launch the application.
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 29/03/22
 */
public class Launcher extends Application
{
    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called when the system is ready for the application to begin running.
     *
     * @param stage the primary stage for this application.
     */
    public void start(Stage stage)
    {
        stage = new MainViewer();
        stage.show();
    }
}
