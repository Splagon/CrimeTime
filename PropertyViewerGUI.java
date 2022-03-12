import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;

public class PropertyViewerGUI extends Stage {
    public PropertyViewerGUI() throws Exception {
        makePropertyViewerScene();
    }

    private void makePropertyViewerScene() throws Exception {
        setTitle("Property Viewer !");
        URL url = getClass().getResource("propertyViewer.fxml");
        Pane root = FXMLLoader.load(url);
        
        // Create scene for the Vbox
        Scene scene = new Scene(root);
        setScene(scene);
        show();
    } 

    // ------- Implementations of the buttons -------- //
    
    /**
     * Implements the action when the "next" button is clicked.
     */
    @FXML
    private void nextAction(ActionEvent event) {
        System.out.println("1");
    }
    /**
     * Implements the action when the "previous" button is clicked.
     */
    @FXML
    private void previousAction(ActionEvent event) {
        
    }
    /**
     * Implements the action when the image is clicked.
     * (It may be a good idea to implement a pop up window that displays
     * the description of the property when the mouse hovers over the image.)
     */
    private void imageAction(ActionEvent event){
        
    }
    /**
     * Implements the view on map button's action 
     */
    private void viewOnMapAction(ActionEvent event){
        
    }
}

