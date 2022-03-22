import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;

/**
 * Write a description of class bookingStage here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class bookingStage extends Stage
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class bookingStage
     */
    public bookingStage()
    {
        makeStage();
    }
    private void makeStage()  {
        setTitle("Bookings Window");
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("rootBooking");
        
        //pane.setCenter(centerPane);
            Label windowTitle = new Label("Your bookings: ");
            pane.setAlignment(windowTitle, Pos.CENTER);
        pane.setTop(windowTitle);
        
        ArrayList<Booking> bookingList = PropertyViewer.getBookingList();
        //for(Booking booking : bookingList) {
            
        //}
        Scene scene = new  Scene(pane, 600, 400);
        scene.getStylesheets().add("stylesheet.css");
        setScene(scene);
        show();
    }
}
