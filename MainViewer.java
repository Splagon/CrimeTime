import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.image.*;
import javafx.scene.shape.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.control.ComboBox;
import java.util.ArrayList;

/**
 * Write a description of class MapViewer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MainViewer extends Stage
{
    // instance variables - replace the example below with your o
    Scene welcomeScene;
    Scene mapScene;
    BorderPane root = new BorderPane();
    Image boroughs;
    ImageView imageView = new ImageView();

    /**
     * Constructor for objects of class MapViewer
     */
    public MainViewer() throws Exception
    {        
        boroughs = new Image(new FileInputStream(System.getProperty("user.dir") + "\\boroughs.png"));
        
        ComboBox<String> minBox = new ComboBox<String>();
        minBox.getItems().addAll("No Min", "500", "600", "700", "800", "900");
        
        ComboBox<String> maxBox = new ComboBox<String>();
        maxBox.getItems().addAll("500", "600", "700", "800", "900", "No Max");
        
        HBox minMaxBox = new HBox();
        minMaxBox.getChildren().addAll(minBox, maxBox);
            
        root.setTop(minMaxBox);
        
        //makeWelcomeScene();
        makeMapScene();
        
        setScene(mapScene);
        //setScene(welcomeScene);
    }
    

    private void makeWelcomeScene() {
        setTitle("Welcome");
        
        //All labels in the window
        Label title = new Label("Welcome!");
        Label instructionsTitle = new Label("Instructions: ");
        Label instructionsText = new Label("    - When you are ready click start, this will send you to the next window where you will be able to enter your price range." + "\n" +
                                       "    - Once your price range has been selected you will then be able to view the map and see where the you be able to find " + "\n" +
                                       "      a property");
        
        //Buttons in the window
        Button startButton = new Button("Start"); 
        
        //layout of the whole window
        VBox window = new VBox(); //root of the scene
        VBox instructions = new VBox();
        BorderPane instrcutionsAndStart = new BorderPane();

        //adding elements to the window
        window.getChildren().addAll(title, instrcutionsAndStart); 
        
        instructions.getChildren().addAll(instructionsTitle, instructionsText); 
        
        instrcutionsAndStart.setLeft(instructions);
        instrcutionsAndStart.setCenter(startButton);
        
        //creating the scene
        root.setCenter(window);
        welcomeScene = new Scene(root, 1000, 300);
    }
    
    private void makeMapScene() throws Exception {
        setTitle("Map of London");
        
        mapScene = new Scene(root, 500, 500);
        mapScene.getStylesheets().add("stylesheet.css");
        
        Pane window = new FlowPane();
        
        GridPane stats = new GridPane();
            Label statsLabel = new Label("Stats");
            Label statsLabel1 = new Label("Stat 1");
            Label statsLabel2 = new Label("Stat 2");
            Label statsLabel3 = new Label("Stat 3");
            Label statsLabel4 = new Label("Stat 4");
            
            stats.add(statsLabel, 0, 0);
            stats.add(statsLabel1, 0, 1);
            stats.add(statsLabel2, 0, 2);
            stats.add(statsLabel3, 0, 3);
            stats.add(statsLabel4, 0, 4);
        
        StackPane mapView = new StackPane();
            
            ImageView mapImage = new ImageView(boroughs);
                mapImage.setFitWidth(900);
                mapImage.setPreserveRatio(true);
        
            FlowPane mapButtons = new FlowPane();
                ArrayList[] buttonArray = new ArrayList[33];
                for (int i = 0; i < 33; i++) {
                    Button boroughButton = new Button("Borough");
                    boroughButton.setShape(new Circle(100));
                    boroughButton.getStyleClass().add("boroughButton");
                    mapButtons.getChildren().add(boroughButton);
                }
                
            mapView.getChildren().addAll(mapImage, mapButtons);
            
        GridPane key = new GridPane();
            Label keyLabel = new Label("Key");
            Label keyLabel1 = new Label("Value 1");
            Label keyLabel2 = new Label("Value 2");
            Label keyLabel3 = new Label("Value 3");
            Label keyLabel4 = new Label("Value 4");
            
            key.add(keyLabel, 0, 0);
            key.add(keyLabel1, 0, 1);
            key.add(keyLabel2, 0, 2);
            key.add(keyLabel3, 0, 3);
            key.add(keyLabel4, 0, 4);

        window.getChildren().addAll(stats, mapView, key);
        
        root.setCenter(window);
        
        //Testing to see if a stage can launch another stage
        //PropertyViewer p = new PropertyViewer();
        //p.show();
    }
}
