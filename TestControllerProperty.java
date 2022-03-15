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
        stage = new PropertyViewer("camden", -1, -1);
        stage.show();
    }
}