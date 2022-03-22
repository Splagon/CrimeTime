import javafx.stage.Stage;
import javafx.application.Application;

/**
 * Write a description of class TestControllerProperty here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TestBookingStage extends Application
{
    @Override
    public void start(Stage stage) throws Exception{
        StatisticsData.initialiseHandler();
        stage = new bookingStage();
        stage.show();
    }
}
