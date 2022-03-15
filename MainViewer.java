import javafx.application.Application;
import javafx.application.*;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.control.ComboBox;
import java.util.ArrayList;
import java.net.URL;
import java.io.File;
import java.util.Iterator;
import javafx.scene.layout.GridPane;
import javafx.scene.effect.ColorAdjust;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;

/**
 * Write a description of class MapViewer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MainViewer extends Stage
{
    // instance variables
    private Scene welcomeScene;
    private Scene mapScene;
    private Scene priceSelectorScene;
    
    private Integer selectedMinPrice;
    private Integer selectedMaxPrice;
    
    private int lowestPrice;
    private int highestPrice;
    
    private Label statusLabel;
    
    private BorderPane root = new BorderPane();
    
    private AnchorPane mapView;
    
    private String[][] mapPositions;
    
    private StatisticsData dataHandler;
    
    /**
     * Constructor for objects of class MapViewer
     */
    public MainViewer() throws Exception
    {   
        dataHandler = new StatisticsData();
        
        makeWelcomeScene();
        setScene(welcomeScene);
        lowestPrice = dataHandler.getLowestPrice();
        highestPrice = dataHandler.getHighestPrice();
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
        startButton.setOnAction(this::changeToPriceSelector);
        
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
    
    private void changeToPriceSelector(ActionEvent event) {
        makePriceSelectorScene();
        setScene(priceSelectorScene);
    }
    
    private void makePriceSelectorScene() {
        setTitle("Price Selection Window");
        
        //All labels in the window
        Label title = new Label("Price Selection!");
        Label instruction = new Label("Please select a min and max for your price range: ");
        
        HBox minMaxBox = createMinMaxBox();
        
        Button confirm = (Button) minMaxBox.getChildren().get(2);
        statusLabel = new Label(showStatus(confirm));
        
        //All buttons in the window
        
        //Layout of the window
        BorderPane window = new BorderPane(); //root of the window
        VBox titleAndInstruction = new VBox();
        
        
        //Adding elements to the window
        window.setCenter(minMaxBox);
        window.setTop(titleAndInstruction);
        window.setBottom(statusLabel); 

        titleAndInstruction.getChildren().addAll(title, instruction);
        
        //Creating the scene and adding the css styling
        priceSelectorScene = new Scene(window, 500, 500);
    }
    
    private HBox createMinMaxBox() {
        //adding the options to the price selection box, as well as assigning appropriate values to the instance variables
        HBox minMaxBox = new HBox();
        ComboBox<String>minBox = new ComboBox<String>();
        ComboBox<String> maxBox = new ComboBox<String>();
        
        // int low = dataHandler.getLowestPrice();
        // int high = dataHandler.getHighestPrice();
        ArrayList<String> options = getPriceSelectionOptions(lowestPrice, highestPrice);
        minBox.getItems().add("No Min");
        minBox.getItems().addAll(options);
        maxBox.getItems().addAll(options);
        maxBox.getItems().add("No Max");
        
        Button confirm = new Button("Confirm");
        confirm.setDisable(true);
        // if ("Both your min and max price have been selected".equals(showStatus())) {
            // confirm.setDisable(false);
        // }
        //confirm.disableProperty().bind(!("Both your min and max price have been selected".equals(statusLabel)));
        confirm.setOnAction(e -> 
                            {
                                try { makeHexagonMap(); }
                                catch (Exception ex) {}
                                
                                try { changeToMapScene(); }
                                catch (Exception ex) {}
                            });
        
        minBox.setOnAction(e -> {
            String selected = minBox.getValue();
            if ("No Min".equals(selected)) {
                selectedMinPrice = -1;
            }
            else {
                selectedMinPrice = Integer.parseInt(selected);
            }
            statusLabel.setText(showStatus(confirm));
        });
        maxBox.setOnAction(e -> {
            String selected = maxBox.getValue();
            if ("No Max".equals(selected)) {
                selectedMaxPrice = -1;
            }
            else {
                selectedMaxPrice = Integer.parseInt(selected);
            }
            statusLabel.setText(showStatus(confirm));
        });
        
        minMaxBox.getChildren().addAll(minBox, maxBox, confirm);
        
        return minMaxBox;
    }
    
    private HBox setInitialMinMaxBoxSelection(HBox minMaxBox) {
        ComboBox<String> minBox = (ComboBox<String>) minMaxBox.getChildren().get(0);
        ComboBox<String> maxBox = (ComboBox<String>) minMaxBox.getChildren().get(1);
        
        if (selectedMinPrice != null){
            if (selectedMinPrice >= 0) {
                minBox.getSelectionModel().select(selectedMinPrice.toString());
            }
            else {
                minBox.getSelectionModel().select("No Min");
            }
        }
        if (selectedMaxPrice != null){
            if (selectedMaxPrice >= 0) {
                maxBox.getSelectionModel().select(selectedMaxPrice.toString());
            }
            else {
                maxBox.getSelectionModel().select("No Max");
            }
        }
        
        minMaxBox.getChildren().set(0, minBox);
        minMaxBox.getChildren().set(1, maxBox);
        
        return minMaxBox;
    }
    
    /**
     * creating the list of all possibel prices which can be selected
     */
    private ArrayList<String> getPriceSelectionOptions(int low, int high) {
        ArrayList options = new ArrayList <> ();
        
        for (int i = low; i <= 100; i+=10) {
            Integer num = i;
            options.add(num.toString());
        }
        for (int i = 100; i < 200; i+=25) {
            Integer num = i;
            options.add(num.toString());
        }
        for (int i = 200; i < 500; i+=50) {
            Integer num = i;
            options.add(num.toString());
        }
        for (int i = 500; i < 1000; i+=100) {
            Integer num = i;
            options.add(num.toString());
        }
        for (int i = 1000; i <= high; i+=1000) {
            Integer num = i;
            options.add(num.toString());
        }
        return options;
    }
    
    private String showStatus(Button confirm) {

        if (selectedMinPrice == null && selectedMaxPrice == null) {
            confirm.setDisable(true);
            return "Currently nothing has been selected!";
        }
        else if (selectedMinPrice != null && selectedMaxPrice == null) {
            confirm.setDisable(true);
            return "Currently only your min price has been selected!";
        }
        else if (selectedMinPrice == null && selectedMaxPrice != null) {
            confirm.setDisable(true);
            return "Currently only your max price has been selected!";
        }
        else  {
            if (selectedMinPrice < lowestPrice || selectedMaxPrice > highestPrice)  {
                confirm.setDisable(false);
                return "Both your min and max price have been selected";
            }
            else if (selectedMinPrice >= selectedMaxPrice) {
                confirm.setDisable(true);
                return "Your min price is not less than your max price!";
            }
            confirm.setDisable(false);
            return "Both your min and max price have been selected";
        }
    }
    
    private void changeToMapScene() throws Exception {
        // if (selectedMinPrice != null && selectedMaxPrice != null && ("No Min".equals(selectedMinPrice.toString()) || "No Max".equals(selectedMaxPrice.toString()))) {
            // makeMapScene();
            // setScene(mapScene);
        // }
        makeMapScene();
        setScene(mapScene);
    }
    
    private void makeMapScene() throws Exception {
        setTitle("Map of London");
        
        HBox minMaxBox = createMinMaxBox();
            minMaxBox = setInitialMinMaxBoxSelection(minMaxBox);
            
        root.setTop(minMaxBox);
        
        if (mapScene == null) {
            mapScene = new Scene(root, 500, 500);
        }
        
        //if (mapScene.getStylesheets().isEmpty()) {
            mapScene.getStylesheets().add("stylesheet.css");
        //}
        
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
    
    private void makeHexagonMap() throws Exception {
        mapPositions = dataHandler.getMapPositions();
        
        NoOfPropertiesStats noOfPropertiesStats = new NoOfPropertiesStats(dataHandler, selectedMinPrice, selectedMaxPrice);
        
        //ArrayList<BoroughListing> sortedNumberOfPropertiesAtPrice = dataHandler.getSortedNumberOfPropertiesInBoroughs(selectedMinPrice, selectedMaxPrice);
        
        mapView = new AnchorPane();
            mapView.setMinSize(720, 700);
            // rows
            for (int m = 0; m < mapPositions.length; m++) {
                
                FlowPane row = new FlowPane();
                row.setHgap(1.0);
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
                        
                        ImageView hexagonOutline = new ImageView(new Image("/hexagonOutline.png", true));
                            hexagonOutline.setFitWidth(94);
                            hexagonOutline.setFitHeight(94);
                        
                        //Image hexagonFilledImage = new Image("/hexagonFilled.png");
                        //ImageView hexagonFilled = new ImageView(setHexagonFilledColour(hexagonFilledImage, boroughButton.getBoroughName(), sortedNumberOfPropertiesAtPrice));
                        ImageView hexagonFilledImage = new ImageView(new Image("/hexagonFilledGreen.png"));
                        ImageView hexagonFilled = setHexagonFilledColour(hexagonFilledImage, boroughButton.getBoroughName(), noOfPropertiesStats);
                            hexagonFilled.setFitWidth(93);
                            hexagonFilled.setFitHeight(93);
                    
                        rowSpace.getChildren().addAll(hexagonFilled, hexagonOutline, boroughButton);
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
    }
    
    private void openPropertyViewer(String boroughName) throws Exception {
        try {
            Stage stage = new PropertyViewer(boroughName, selectedMinPrice, selectedMaxPrice, null);
            stage.show();
        }
        catch (Exception e) {
            Alert alert = new Alert(AlertType.WARNING);
                alert.setHeaderText("No Available Properties in " + boroughName);
                alert.setContentText("Unfortunately, there are no available properties in this\nborough within your price range. Welcome to the London\nhousing market...");
            alert.show();
        };
    }
    
    // private Image setHexagonFilledColour(Image hexagon, String boroughName, ArrayList<BoroughListing> sortedNumberOfPropertiesInBorough) {
        // int height = (int) hexagon.getHeight();
        // int width = (int) hexagon.getWidth();
        
        // WritableImage renderedHexagon = new WritableImage(hexagon.getPixelReader(), width, height);
        // final PixelReader pixelReader = renderedHexagon.getPixelReader();
        // final PixelWriter pixelWriter = renderedHexagon.getPixelWriter();

        // Color boroughColour = dataHandler.getBoroughMapColour(boroughName, selectedMinPrice, selectedMaxPrice, sortedNumberOfPropertiesInBorough);
        
        // for(int y = 0; y < height; y++) {
            // for(int x = 0; x < width; x++) { 
                // if (! pixelReader.getColor(x, y).equals(Color.rgb(0, 0, 0, 0.0))) {
                    // //pixelWriter.setColor(x, y, Color.rgb(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256)));
                    // pixelWriter.setColor(x,y,boroughColour);
                // }            
            // }
        // }
        
        // return (Image) renderedHexagon;
    // }
    
    private ImageView setHexagonFilledColour(ImageView hexagon, String boroughName, NoOfPropertiesStats noOfPropertiesStats) {
        ColorAdjust shader = new ColorAdjust();
            shader.setBrightness(dataHandler.getBoroughMapColour(boroughName, selectedMinPrice, selectedMaxPrice, noOfPropertiesStats));
            
        hexagon.setEffect(shader);
        
        return hexagon;
    }
}
