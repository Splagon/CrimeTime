import javafx.stage.Stage;
import javafx.application.Application;

/**
 * Write a description of class TestControllerProperty here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TestControllerProperty extends Application
{
    @Override
    public void start(Stage stage) throws Exception{
        stage = new PropertyViewer("Camden", 10, 1000, null);
        stage.show();
    }
}