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
import java.net.URL;
import java.io.File;
import java.util.Iterator;
import javafx.scene.layout.GridPane;

import javafx.scene.paint.Color;

/**
 * Write a description of class MapViewer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MainViewer extends Stage
{
    // instance variables - replace the example below with your o
    private Scene welcomeScene;
    private Scene mapScene;
    
    private BorderPane root = new BorderPane();
    
    private String[][] mapPositions;
    
    private DataHandler dataHandler = new DataHandler();
    
    /**
     * Constructor for objects of class MapViewer
     */
    public MainViewer() throws Exception
    {         
        //makeWelcomeScene();
        //setScene(welcomeScene);
        
        makeMapScene();
        setScene(mapScene);
    }
    

    private void makeWelcomeScene() {
        setTitle("Welcome");
        
        //All labels in the window
        Label title = new Label("Welcome!");
        Label instructionsTitle = new Label("Instructions: ");
        Label instructions1 = new Label("- When you are ready click start, this will send you to the next window where you will be able to enter your price range.");
        Label instructions2 = new Label("- Once your price range has been selected you will then be able to view the map and see where the you be able to find a property. ");
        
        //Buttons in the window
        Button startButton = new Button("Start"); 
        
        //layout of the whole window
        VBox window = new VBox(); //root of the scene
        VBox instructions = new VBox();
        BorderPane instrcutionsAndStart = new BorderPane();

        //adding elements to the window
        window.getChildren().addAll(title, instrcutionsAndStart);
        instructions.getChildren().addAll(instructionsTitle, instructions1, instructions2); 
        
        instrcutionsAndStart.setLeft(instructions);
        instrcutionsAndStart.setCenter(startButton);
        
        //creating the scene and adding the CSS
        welcomeScene = new Scene(window, 500, 500);
        welcomeScene.getStylesheets().add("stylesheet.css");
        
        title.getStyleClass().add("welcomeTittle");
        
        instructionsTitle.getStyleClass().add("instructionsTittle"); 
        
        instructions1.getStyleClass().add("instructions"); 
        instructions2.getStyleClass().add("instructions"); 
        
        window.getStyleClass().add("windowVBox");
        
        instrcutionsAndStart.getStyleClass().add("instrcutionsAndStart");
        
        startButton.getStyleClass().add("startButton");
    }
    
    private void makeMapScene() throws Exception {
        mapPositions = dataHandler.getMapPositions();
        
        setTitle("Map of London");
        
        ComboBox<String> minBox = new ComboBox<String>();
        minBox.getItems().addAll("No Min", "500", "600", "700", "800", "900");
        
        ComboBox<String> maxBox = new ComboBox<String>();
        maxBox.getItems().addAll("500", "600", "700", "800", "900", "No Max");
        
        HBox minMaxBox = new HBox();
        minMaxBox.getChildren().addAll(minBox, maxBox);
            
        root.setTop(minMaxBox);
        
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
            
        // AnchorPane mapView = new AnchorPane();
            // mapView.setMinSize(708, 700);
        
            // ArrayList<StackPane> mapRows = new ArrayList<StackPane>();

            // // rows
            // for (int m = 0; m < mapPositions.length; m++) {
                
                // StackPane row = new StackPane();
                
                // FlowPane hexagonRow = new FlowPane();
                    // hexagonRow.setMinWidth(mapView.getMinWidth());
                
                // FlowPane buttonRow = new FlowPane();
                    // buttonRow.setMinWidth(mapView.getMinWidth());
                
                // if (m % 2 == 0) {
                        // Rectangle HRinsetSpace = new Rectangle(47,94);
                            // HRinsetSpace.setFill(Color.TRANSPARENT);
                        // Rectangle BRinsetSpace = new Rectangle(47,94);
                            // BRinsetSpace.setFill(Color.TRANSPARENT);
                            // //BRinsetSpace.setFill(Color.GOLD);
                        // hexagonRow.getChildren().add(HRinsetSpace);
                        // buttonRow.getChildren().add(BRinsetSpace);
                // }
                
                // //columns
                // for (int n = 0; n < mapPositions[m].length; n++) {
                    // if (mapPositions[m][n] != null) {
                        // MapButton boroughButton = new MapButton(mapPositions[m][n]);
                        // boroughButton.setShape(new Circle(94));
                        // boroughButton.getStyleClass().add("boroughButton");
                        // boroughButton.setOnAction(e ->
                            // {
                                // try { openPropertyViewer(boroughButton.getBoroughName()); }
                                // catch (Exception ex) {}
                            // });
                            
                        // buttonRow.setMargin(boroughButton, new Insets(0,3,0,3));
                        // buttonRow.getChildren().add(boroughButton);
                        
                        // ImageView hexagonIV = new ImageView(new Image("/hexagon.png", true));
                            // hexagonIV.setFitWidth(94);
                            // hexagonIV.setFitHeight(94);
                    
                        // hexagonRow.getChildren().add(hexagonIV);
                    // }
                    // else {
                        // Rectangle HRemptySpace = new Rectangle(94,94);
                        // Rectangle BRemptySpace = new Rectangle(94,94);
                            // HRemptySpace.setFill(Color.TRANSPARENT);
                            // BRemptySpace.setFill(Color.TRANSPARENT);
                            // //BRemptySpace.setFill(Color.GOLD);
                        // hexagonRow.getChildren().add(HRemptySpace);
                        // buttonRow.getChildren().add(BRemptySpace);
                    // }
                // }
                
                // if (m % 2 == 1) {
                        // Rectangle HRinsetSpace = new Rectangle(47,94);
                            // HRinsetSpace.setFill(Color.TRANSPARENT);
                        // Rectangle BRinsetSpace = new Rectangle(47,94);
                            // BRinsetSpace.setFill(Color.TRANSPARENT);
                        // hexagonRow.getChildren().add(HRinsetSpace);
                        // buttonRow.getChildren().add(BRinsetSpace);
                // }
                
                // row.getChildren().addAll(hexagonRow, buttonRow);
                // mapRows.add(row);
                
                // AnchorPane.setTopAnchor(row, m*72.0);
                // mapView.getChildren().add(row);
            // }
            
        AnchorPane mapView = new AnchorPane();
            mapView.setMinSize(708, 700);
        
            //ArrayList<StackPane> mapRows = new ArrayList<StackPane>();

            // rows
            for (int m = 0; m < mapPositions.length; m++) {
                
                FlowPane row = new FlowPane();
                StackPane rowSpace;
                
                row.setMinWidth(mapView.getMinWidth());
                
                if (m % 2 == 0) {
                        rowSpace = new StackPane();
                        Rectangle insetSpace = new Rectangle(47,94);
                            insetSpace.setFill(Color.TRANSPARENT);
                            //insetSpace.setFill(Color.GOLD);
                        rowSpace.getChildren().add(insetSpace);
                        row.getChildren().add(rowSpace);
                }
                
                //columns
                for (int n = 0; n < mapPositions[m].length; n++) {
                    rowSpace = new StackPane();
                    if (mapPositions[m][n] != null) {
                        MapButton boroughButton = new MapButton(mapPositions[m][n]);
                        boroughButton.setShape(new Circle(94));
                        boroughButton.getStyleClass().add("boroughButton");
                        boroughButton.setOnAction(e ->
                            {
                                try { openPropertyViewer(boroughButton.getBoroughName()); }
                                catch (Exception ex) {}
                            });
                            
                        //buttonRow.setMargin(boroughButton, new Insets(0,3,0,3));
                        //buttonRow.getChildren().add(boroughButton);
                        
                        ImageView hexagonIV = new ImageView(new Image("/hexagon.png", true));
                            hexagonIV.setFitWidth(94);
                            hexagonIV.setFitHeight(94);
                    
                        rowSpace.getChildren().addAll(hexagonIV, boroughButton);
                    }
                    else {
                        Rectangle emptySpace = new Rectangle(94,94);
                            emptySpace.setFill(Color.TRANSPARENT);
                            //emptySpace.setFill(Color.GOLD);
                        rowSpace.getChildren().add(emptySpace);
                    }
                    row.getChildren().add(rowSpace);
                }
                
                if (m % 2 == 1) {
                    rowSpace = new StackPane();
                    Rectangle insetSpace = new Rectangle(47,94);
                        insetSpace.setFill(Color.TRANSPARENT);
                        //insetSpace.setFill(Color.GOLD);
                    rowSpace.getChildren().add(insetSpace);
                    row.getChildren().add(rowSpace);
                }
                
                //mapRows.add(row);
                
                AnchorPane.setTopAnchor(row, m*72.0);
                mapView.getChildren().add(row);
            }
            
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

    }
    
    private void openPropertyViewer(String boroughName) throws Exception {
        Stage stage = new PropertyViewer(boroughName);
        stage.show();
    }
}
