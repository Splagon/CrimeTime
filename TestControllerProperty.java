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
        PropertyViewer propertyViewer = new PropertyViewer("Camden", -1, -1, null);
        propertyViewer.show();
        
        propertyViewer.setOnCloseRequest(e -> {
            propertyViewer.closePropertyViewer();
        });
    }
}