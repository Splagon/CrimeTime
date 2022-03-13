import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.util.Random;
import java.util.ArrayList;

/**
 * Write a description of JavaFX class TestControllerProperty here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TestControllerProperty extends Application
{
    @FXML
    private Label hostName;
    @FXML
    private Label neighbourhood;
    @FXML
    private Label minNights;
    @FXML
    private Label numReviews;
    @FXML
    private Label price;
    @FXML
    private Label roomType;
    private DataHandler dataHandler = new DataHandler();
    // List holding all properties of one borough
    private ArrayList<AirbnbListing> boroughProperties = dataHandler.listings;
    // Object to randomize
    private Random rand = new Random();
    private int currentPropertyIndex;
    
    /**
     * Display the characteristics of the first property 
     */
    @FXML
    public void initialize(){
        displayInitialData();
    }
    
    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called after the init() method has returned and after 
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage) throws Exception
    { 
        stage = new PropertyViewerGUI();
        stage.show();
    }
    
    // ------- Implementations of the buttons -------- //
    
    /**
     * Implements the action when the "next" button is clicked.
     */
    @FXML
    private void nextAction(ActionEvent event) {
        currentPropertyIndex += 1;
        if(currentPropertyIndex > boroughProperties.size() - 1){
            currentPropertyIndex = 0;
        }
        displayData(boroughProperties.get(currentPropertyIndex));
    }
    /**
     * Implements the action when the "previous" button is clicked.
     */
    @FXML
    private void previousAction(ActionEvent event) {
        currentPropertyIndex -= 1;
        if(currentPropertyIndex < 0){
            currentPropertyIndex = boroughProperties.size()-1;
        }
        displayData(boroughProperties.get(currentPropertyIndex));
    }
        /**
     * Implements the action when the image is clicked.
     * (It may be a good idea to implement a pop up window that displays
     * the description of the property when the mouse hovers over the image.)
     */
    private void imageAction(ActionEvent event) {
        
    }
    /**
     * Implements the view on map button's action 
     */
    private void viewOnMapAction(ActionEvent event) {
        
    }
    /**
     * Display the characteristics of the first property
     */
    private void displayInitialData(){
        //currentProperty = boroughProperties.get(rand.nextInt(boroughProperties.size()-1));
        currentPropertyIndex = 0;
        displayData(boroughProperties.get(currentPropertyIndex));
    }
    // ------- Support Methods -------- //
    /**
     * Display the characteristics of a property
     */
    private void displayData(AirbnbListing property){
        hostName.setText("Host: " + property.getHost_name());
        neighbourhood.setText(property.getNeighbourhood());
        minNights.setText(property.getMinimumNights() + " minimum night(s)");
        numReviews.setText(property.getNumberOfReviews() + " Review(s)");
        price.setText("Price: £" + property.getPrice());
        roomType.setText(property.getRoom_type());
    }
    // ------- Main method to launch application -------- //
    public static void main(String[] args) {
        launch(args);
    }
}
