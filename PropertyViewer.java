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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class PropertyViewer extends Stage {

    public PropertyViewer() throws FileNotFoundException {
        makePropertyViewerScene();
    }

    private void makePropertyViewerScene() throws FileNotFoundException {
        // Sets the title of the frame
        setTitle("property Viewer");
        
        //---------------------------------------------------
        // Create the labels needed for the scene
        Label boroughLabel = new Label("Marylebone");
        Label hostLabel = new Label("Kolling");
        Label priceLabel = new Label("£500");
        Label roomsLabel = new Label("One Bedroom En-suite");
        Label minNights = new Label("15  nights min guys");
        Label numReviews = new Label("1200");
        Pane stats = new HBox(boroughLabel,hostLabel, priceLabel, roomsLabel, minNights, numReviews);
        
        Button nextButton = new Button();
        Button previousButton = new Button();

        // Create Border Pane that is contained by VBox
        BorderPane root = new BorderPane(null,boroughLabel,nextButton,stats,previousButton);

        // Create scene for the Vbox
        Scene scene = new Scene(root,600,500);
        setScene(scene);
        show();
    }
}

