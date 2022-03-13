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
        setResizable(false);
        setScene(scene);
        //show();
    }
}

