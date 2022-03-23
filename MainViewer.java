import javafx.application.Application;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
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
import javafx.scene.Node;
import java.util.HashMap;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.geometry.*;
import java.util.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import java.awt.Desktop;
import java.net.URI;

/**
 * Write a description of class MapViewer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MainViewer extends Stage
{
    // instance variables
    
    private Pane welcomePane;
    private Pane priceSelectorPane;
    private Pane mapPane;
    private Pane statsPane;
    private Pane bookingsPane;
    
    private int sceneWidth;
    private int sceneHeight;
    
    private Integer selectedMinPrice;
    private Integer selectedMaxPrice;
    
    private int lowestPrice;
    private int highestPrice;
    
    private Label statusLabel;
    
    //the buttons at the bottom of screen
    private AnchorPane panelSwitcherPane;
    private Button prevPanelButton;
    private Button nextPanelButton;
    
    private String[] sceneOrder = { "welcomePane", "priceSelectorPane", "mapPane", "statsPane", "bookingsPane" };
    //private PaneListing[] paneOrder = { welcomePane, priceSelectorPane, mapPane, statsPane, favouritesPane };

    private int currentSceneIndex;
    
    private BorderPane root = new BorderPane();
    
    private AnchorPane mapView;
    
    private String[][] mapPositions;
    
    //private StatisticsData dataHandler;
    
    private NoOfPropertiesStats noOfPropertiesStats;
    
    private Scene mainScene;
    
    private StatisticsViewer statisticsViewer;
    
    ArrayList<VBox> statsOrder = new ArrayList<>();
    
    int currentStat = 0;
    
    VBox statsWindow = new VBox();
    
    /**
     * Constructor for objects of class MapViewer
     */
    public MainViewer()
    {         
        DataHandler.initialiseHandler();
        
        sceneWidth = 1100;
        sceneHeight = 635;
        
        lowestPrice = StatisticsData.getLowestPrice();
        highestPrice = StatisticsData.getHighestPrice();
        
        makePriceSelectorPane();
        makeStatsPane();
        
        root = new BorderPane();
        
        //panel switcher buttons
        prevPanelButton = new Button("<-- Bookings");
        prevPanelButton.setPrefSize(135, 20);
        nextPanelButton = new Button("Price Selection -->");
        nextPanelButton.setPrefSize(135, 20);
        //styling for the buttons
        prevPanelButton.getStyleClass().add("smallWindowButtons");
        nextPanelButton.getStyleClass().add("smallWindowButtons");
        
        //panel switcher
        makePanelSwitcherPane();
        
        mainScene = new Scene(root, sceneWidth, sceneHeight);
        // widthProperty().addListener(new ChangeListener<Number>() {
            // @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                // if (currentSceneIndex == 2){
                    // try {
                        // makeHexagonMap();
                        // setPane(2);
                    // }
                    // catch (Exception e) {}
                // }
            // }
        // });

        //setResizable(false);
        
        setPane(0);
        
        mainScene.getStylesheets().add("stylesheet.css");
        root.getStyleClass().add("mainRoot");
    }
    
    private void makePanelSwitcherPane() {
        panelSwitcherPane = new AnchorPane();
        
        //switches panel once the button is clicked
        prevPanelButton.setOnAction(e -> goToPrevPanel());
        nextPanelButton.setOnAction(e -> goToNextPanel());
        
        AnchorPane.setLeftAnchor(prevPanelButton, 5.0);
        AnchorPane.setRightAnchor(nextPanelButton, 5.0);
        
        panelSwitcherPane.getChildren().add(prevPanelButton);
        panelSwitcherPane.getChildren().add(nextPanelButton);
        
        root.setBottom(panelSwitcherPane);
    }
    
    private void goToPrevPanel() {
        currentSceneIndex--;
        
        if (currentSceneIndex < 0) {
            currentSceneIndex = sceneOrder.length - 1;
        }
        
        updatePrevButtonText();
        updateNextButtonText();
        
        setPane(currentSceneIndex);
    }
    
    private void goToNextPanel() {
        currentSceneIndex++;
        
        if (currentSceneIndex >= sceneOrder.length) {
            currentSceneIndex = 0;
        }
        
        updatePrevButtonText();
        updateNextButtonText();
        
        setPane(currentSceneIndex);
    }
    
    private void updatePrevButtonText() {
        String nameOfPaneToChangeTo = sceneOrder[currentSceneIndex];
        
        switch (nameOfPaneToChangeTo) {
            case ("welcomePane") :
                prevPanelButton.setText("<-- Bookings");
                break;
            case ("priceSelectorPane") :
                prevPanelButton.setText("<-- Welcome");
                break;
            case ("mapPane") :
                prevPanelButton.setText("<-- Price Selection");
                break;
            case ("statsPane") :
                prevPanelButton.setText("<-- Map");
                break;
            case ("bookingsPane") :
                prevPanelButton.setText("<-- General Statistics");
                break;
        }
    }
    
    private void updateNextButtonText() {
        String nameOfPaneToChangeTo = sceneOrder[currentSceneIndex];
        
        switch (nameOfPaneToChangeTo) {
            case ("welcomePane") :
                nextPanelButton.setText("Price Selection -->");
                break;
            case ("priceSelectorPane") :
                nextPanelButton.setText("Map -->");
                break;
            case ("mapPane") :
                nextPanelButton.setText("General Statistics -->");
                break;
            case ("statsPane") :
                nextPanelButton.setText("Bookings -->");
                break;
            case ("bookingsPane") :
                nextPanelButton.setText("Welcome -->");
                break;
        }
    }
    
    public void setPane(int currentSceneIndex) {      
        this.currentSceneIndex = currentSceneIndex;
        String nameOfPaneToChangeTo = sceneOrder[currentSceneIndex];
        Pane paneToChangeTo = new Pane();
        
        switch (nameOfPaneToChangeTo) {
            case ("welcomePane") :
                makeWelcomePane();
                paneToChangeTo = welcomePane;
                break;
            case ("priceSelectorPane") :
                setTitle("Price Selection Screen");
                paneToChangeTo = priceSelectorPane;
                break;
            case ("mapPane") :
                try {
                    makeMapPane();
                    paneToChangeTo = mapPane;
                }
                catch (Exception ex) {};
                break;
            case ("statsPane") :
                setTitle("Information");
                paneToChangeTo = statsPane;
                break;
            case ("bookingsPane") :
                setTitle("Bookings");
                makeBookingsPane();
                paneToChangeTo = bookingsPane;
                break;
        }
        
        setButtonsDisabled(currentSceneIndex);
        root.setCenter(paneToChangeTo);
        setScene(mainScene);
    }
    
    private void setButtonsDisabled(int currentSceneIndex) {
        int nextSceneIndex = currentSceneIndex + 1;
        int prevSceneIndex = currentSceneIndex - 1;
        
        if (nextSceneIndex >= sceneOrder.length) {
            nextSceneIndex = 0;
        }
        if (prevSceneIndex < 0) {
            prevSceneIndex = sceneOrder.length - 1;
        }
        
        if (selectedMinPrice == null && selectedMaxPrice == null) {    
            if (sceneOrder[nextSceneIndex] == "mapPane") {
                nextPanelButton.setDisable(true);
            }
            else {
                nextPanelButton.setDisable(false);
            }
            if (sceneOrder[prevSceneIndex] == "mapPane") {
                prevPanelButton.setDisable(true);
            }
            else {
                prevPanelButton.setDisable(false);
            }
        }
        else {
            prevPanelButton.setDisable(false);
            nextPanelButton.setDisable(false);
        }
    }
    
    private void setButtonsDisabled(boolean isPrevButtonsDisabled, boolean isNextButtonsDisabled) {
        prevPanelButton.setDisable(isPrevButtonsDisabled);
        nextPanelButton.setDisable(isNextButtonsDisabled);
    }

    private void makeWelcomePane() {
        setTitle("Welcome");
        
        //All labels in the window
        Label title = new Label("Welcome!");
        Label instructionsTitle = new Label("Instructions: ");
        Label instructions1 = new Label("- When you are ready click start, this will send you to the next window where you will be able to enter your price range.");
        Label instructions2 = new Label("- Once your price range has been selected you will then be able to view the map and see where the you be able to find a property. ");
        Label instructions3 = new Label("- In order to view the map you will need to click the confirm button once both your min and max price have been slected.");
        
        //Buttons in the window
        Button startButton = new Button("Start"); 
        startButton.setOnAction(e -> changeToPriceSelector());
        
        //layout of the whole window
        VBox window = new VBox(); //root of the scene
        VBox instructions = new VBox();
        BorderPane instrcutionsAndStart = new BorderPane();

        //adding elements to the window
        window.getChildren().addAll(title, instrcutionsAndStart);
        window.setAlignment(Pos.CENTER);
        instructions.getChildren().addAll(instructionsTitle, instructions1, instructions2, instructions3); 
        
        instrcutionsAndStart.setLeft(instructions);
        instrcutionsAndStart.setCenter(startButton);
        
        //creating the scene and adding the CSS
        welcomePane = window;
        
        //window.getStyleClass().add("welcomeVBox");
        
        title.getStyleClass().add("windowTitle");
        
        instructions.getStyleClass().add("instructionsTitle"); 
        
        instructions1.getStyleClass().add("instructions"); 
        instructions2.getStyleClass().add("instructions"); 
        instructions3.getStyleClass().add("instructions");
        
        instrcutionsAndStart.getStyleClass().add("instructionsAndStart");
        
        startButton.getStyleClass().add("startButton");
    }
    
    private void changeToPriceSelector() {
        setPane(1);
    }
    
    private void makePriceSelectorPane() {
        setTitle("Price Selection Screen");
        
        //All labels in the window
        Label title = new Label("Price Selection!");
        Label instruction = new Label("Please select a min and max for your price range: ");
        
        HBox minMaxBox = createMinMaxBox();
        minMaxBox.setSpacing(5);
        
        Button confirm = (Button) minMaxBox.getChildren().get(2);
        
        ComboBox<String> minBox = (ComboBox<String>) minMaxBox.getChildren().get(0);
        ComboBox<String> maxBox = (ComboBox<String>) minMaxBox.getChildren().get(1);
        
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
        priceSelectorPane = window;
        
        title.getStyleClass().add("windowTitle");
        
        instruction.getStyleClass().add("priceInstruction");
        
        titleAndInstruction.getStyleClass().add("priceTitleAndTitle");
        
        confirm.getStyleClass().add("priceConfirm");
        
        minBox.getStyleClass().add("priceMinMaxBoxes");
        maxBox.getStyleClass().add("priceMinMaxBoxes");
        
        minMaxBox.getStyleClass().add("priceMinMaxBox");
        
        statusLabel.getStyleClass().add("priceStatusLabel");
    }
    
    private HBox createMinMaxBox() {
        //adding the options to the price selection box, as well as assigning appropriate values to the instance variables
        HBox minMaxBox = new HBox();
        ComboBox<String> minBox = new ComboBox<String>();
        ComboBox<String> maxBox = new ComboBox<String>();
        minBox.setValue("Min Price:");
        maxBox.setValue("Max Price:");
        
        ArrayList<String> options = getPriceSelectionOptions(lowestPrice, highestPrice);
        minBox.getItems().add("No Min");
        minBox.getItems().addAll(options);
        maxBox.getItems().addAll(options);
        maxBox.getItems().add("No Max");
        
        Button confirm = new Button("Confirm");
        confirm.setDisable(true);
        confirm.setOnAction(e -> 
                                 {
                                    try { makeHexagonMap(); }
                                    catch (Exception ex) {}
                                    
                                    try { changeToMapPane(); }
                                    catch (Exception ex) {}
                                    
                                    updateStats();
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
     * creating the list of all possible prices which can be selected
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
            if (selectedMinPrice == -1 || selectedMaxPrice == -1)  {
                confirm.setDisable(false);
                return "Both your min and max price have been selected";
            }
            else if (selectedMinPrice > selectedMaxPrice) {
                confirm.setDisable(true);
                return "Your min price is not less than your max price!";
            }
            confirm.setDisable(false);
            return "Both your min and max price have been selected";
        }
    }
    
    private void changeToMapPane() {
        setPane(2);
    }
    
    private void makeMapPane() {
        setTitle("Map of London");
            
        BorderPane window = new BorderPane();
        
        VBox infoPane = new VBox();
            Label titleLabel = new Label("Boroughs of London");
                titleLabel.getStyleClass().add("windowTitle");
            HBox minMaxBox = createMinMaxBox();
                minMaxBox = setInitialMinMaxBoxSelection(minMaxBox);
            VBox stats = createStatsPanel();
            GridPane key = createKey();
            
        Button confirm = (Button) minMaxBox.getChildren().get(2);
        
        ComboBox<String> minBox = (ComboBox<String>) minMaxBox.getChildren().get(0);
        ComboBox<String> maxBox = (ComboBox<String>) minMaxBox.getChildren().get(1);
        
        //styling the min and max box as well as the confirm button for the map panel
        confirm.getStyleClass().add("confirmForMap");
        
        minBox.getStyleClass().add("mapMinMaxBoxes");
        maxBox.getStyleClass().add("mapMinMaxBoxes");
        
        minMaxBox.getStyleClass().add("mapMinMaxBox");
            
        infoPane.getChildren().addAll(titleLabel, minMaxBox, key, stats);
        infoPane.setPadding(new Insets(10, 20, 10, 10));
        infoPane.setSpacing(15);
        
        mapView.setPadding(new Insets(20));
        
        window.setLeft(infoPane);
        window.setCenter(mapView);
        
        mapPane = window;
    }
    
    private void makeHexagonMap()  {
        mapPositions = StatisticsData.getMapPositions();
        
        noOfPropertiesStats = new NoOfPropertiesStats(selectedMinPrice, selectedMaxPrice);
        
        double sceneWidth = getScene().getWidth();
        double sceneHeight = getScene().getHeight();
        
        final double WIDTH_TO_HEIGHT_RATIO = 725.0 / 510.0;
        final double HEIGHT_TO_WIDTH_RATIO = 1 / WIDTH_TO_HEIGHT_RATIO;
        
        double newWidth = sceneWidth * 0.655;
        double newHeight = sceneHeight * 0.804;
        
        double newWidthToHeightRatio = newWidth/newHeight;
        
        final double gapSize = 5.0;

        // pane is too narrow
        if (newWidthToHeightRatio < WIDTH_TO_HEIGHT_RATIO) {
            newHeight = newWidth * HEIGHT_TO_WIDTH_RATIO;
        }
        // pane is too wide
        else if (newWidthToHeightRatio > WIDTH_TO_HEIGHT_RATIO) {
            newWidth = newHeight * WIDTH_TO_HEIGHT_RATIO;
        }
        
        //newHeight = 510;
        //newWidth = 720;
            
        mapView = new AnchorPane();
            mapView.setPrefSize(newWidth, newHeight);
            mapView.setMinSize(670, 310);
            
            setMinWidth(1050);
            setMinHeight(620);
            
            //double hexagonWidth = 94.0;
            double hexagonWidth = (int) (newWidth / 7.5);
            // rows
            for (int m = 0; m < mapPositions.length; m++) {
                
                FlowPane row = new FlowPane();
                    row.setHgap(gapSize);
                    row.setMinWidth(Double.MAX_VALUE);
                    
                StackPane rowSpace;
                
                if (m % 2 == 0) {
                        createInsetRectangle(hexagonWidth, row, gapSize);
                }
                
                //columns
                for (int n = 0; n < mapPositions[m].length; n++) {
                    rowSpace = new StackPane();
                    if (mapPositions[m][n] != null) {
                        MapButton boroughButton = new MapButton(mapPositions[m][n]);
                        boroughButton.setShape(new Circle(hexagonWidth));
                        boroughButton.setMinSize(hexagonWidth*0.97, hexagonWidth*0.85);
                        boroughButton.setFont(new Font(boroughButton.getFont().getName(), 0.21 * hexagonWidth));
                        //boroughButton.setStyle("-fx-font-size: " + String.valueOf(20.0/94.0 * hexagonWidth) + ";");
                        boroughButton.getStyleClass().add("boroughButton");
                        boroughButton.setOnAction(e -> openPropertyViewer(boroughButton.getBoroughName()));
                        
                        ImageView hexagonOutline = new ImageView(new Image("/hexagonOutline.png", true));
                            hexagonOutline.setFitWidth(hexagonWidth);
                            hexagonOutline.setFitHeight(hexagonWidth);
                        
                        ImageView hexagonFilledImage = new ImageView(new Image("/hexagonFilledGreen.png"));
                        ImageView hexagonFilled = setHexagonFilledColour(hexagonFilledImage, boroughButton.getBoroughName(), (int) hexagonWidth, noOfPropertiesStats);
                        
                        // Rectangle bgTest = new Rectangle(hexagonWidth,hexagonWidth);
                            // bgTest.setFill(Color.FUCHSIA);
                        
                        rowSpace.getChildren().addAll(hexagonFilled, hexagonOutline, boroughButton);                        
                    }
                    else {
                        Rectangle emptySpace = createSpacerRectangle((int) hexagonWidth);
                        rowSpace.getChildren().add(emptySpace);
                    }
                    row.getChildren().add(rowSpace);
                }
                
                if (m % 2 == 1) {
                    createInsetRectangle(hexagonWidth, row, gapSize);
                }
                AnchorPane.setTopAnchor(row, m * (newHeight/mapPositions.length + gapSize));
                mapView.getChildren().add(row);
            }
            
            // HBox minMaxBox = createMinMaxBox();
                // minMaxBox = setInitialMinMaxBoxSelection(minMaxBox);
                // AnchorPane.setTopAnchor(minMaxBox, 11.0);
                // AnchorPane.setRightAnchor(minMaxBox, 0.0);
                
            // mapView.getChildren().add(minMaxBox);
    }
    
    private void createInsetRectangle(double hexagonWidth, FlowPane row, double gapSize) {
        StackPane rowSpace = new StackPane();
        Rectangle insetSpace = createSpacerRectangle((int) ((hexagonWidth - gapSize) / 2.0));
        rowSpace.getChildren().add(insetSpace);
        row.getChildren().add(rowSpace);
    }
    
    private Rectangle createSpacerRectangle(int widthHeight) {
        Rectangle spacerRectangle = new Rectangle(widthHeight, widthHeight);
        spacerRectangle.setFill(Color.TRANSPARENT);
        return spacerRectangle;
    }
    
    private void openPropertyViewer(String boroughName) {
        try {
            PropertyViewer propertyViewer = new PropertyViewer(boroughName, selectedMinPrice, selectedMaxPrice, null);
            if(propertyViewer.getInternetConnection() == true){
                propertyViewer.show();
            } else {
                propertyViewer.noConnectionAlert();
            }  
        }
        catch (Exception e) {
            Alert alert = new Alert(AlertType.WARNING);
                alert.setHeaderText("No Available Properties in " + boroughName);
                alert.setContentText("Unfortunately, there are no available properties in this\nborough within your price range. Welcome to the London\nhousing market...");
            alert.show();
        };
    }
    
    private ImageView setHexagonFilledColour(ImageView hexagon, String boroughName, int heightWidth, NoOfPropertiesStats noOfPropertiesStats) {
        ColorAdjust shader = new ColorAdjust();
            shader.setBrightness(StatisticsData.getBoroughMapColour(boroughName, selectedMinPrice, selectedMaxPrice, noOfPropertiesStats));
            
        hexagon.setFitWidth(heightWidth);
        hexagon.setFitHeight(heightWidth);
            
        hexagon.setEffect(shader);
        
        return hexagon;
    }
    
    private ImageView setHexagonFilledColour(ImageView hexagon, int heightWidth, int percentile) {
        ColorAdjust shader = new ColorAdjust();
            shader.setBrightness(StatisticsData.getBoroughMapColour(percentile));
            
        hexagon.setFitWidth(heightWidth);
        hexagon.setFitHeight(heightWidth);
            
        hexagon.setEffect(shader);
        
        return hexagon;
    }
    
    private GridPane createKey() {
        GridPane key = new GridPane();
        key.getStyleClass().add("infoGrid");
        
        int hexagonKeyHeightWidth = 20;
        
        Label keyTitleLabel = new Label("Key");
        Label keyDescriptionLabel = new Label("(Sorted by number of\nProperties in Borough)");
            keyDescriptionLabel.getStyleClass().add("labelSmall");
        Label keyLabelEmpty = new Label("No Properties in Borough");
        Label keyLabelPercentile25 = new Label("Below Lower Quartile");
        Label keyLabelPercentile50 = new Label("Between Lower Quartile\n and Median");
        Label keyLabelPercentile75 = new Label("Between Median\n and Lower Quartile");
        Label keyLabelPercentile100 = new Label("Above Upper Quartile");
        

        for (int i = 0; i < 5; i++) {
            StackPane completeHexagon = new StackPane();
            
            ImageView hexagonFilledImage = new ImageView(new Image("/hexagonFilledGreen.png"));
            ImageView hexagonOutline = new ImageView(new Image("/hexagonOutlineThick.png"));
            
            ImageView hexagonFilled = setHexagonFilledColour(hexagonFilledImage, hexagonKeyHeightWidth, i*20);
            
            hexagonOutline.setFitHeight(hexagonKeyHeightWidth);
            hexagonOutline.setFitWidth(hexagonKeyHeightWidth);
            
            completeHexagon.getChildren().addAll(hexagonFilled, hexagonOutline);
            
            key.add(completeHexagon, 0, i+1);
        }
        
        key.add(keyTitleLabel, 0, 0);
        key.add(keyDescriptionLabel, 1, 0);
            
        key.add(keyLabelEmpty, 1, 1);
        key.add(keyLabelPercentile25, 1, 2);
        key.add(keyLabelPercentile50, 1, 3);
        key.add(keyLabelPercentile75, 1, 4);
        key.add(keyLabelPercentile100, 1, 5);
        
        key = alignItemsInGridPane(key);
        
        return key;
    }
    
    private VBox createStatsPanel() {
        //setTitle("Information");
        
    
        VBox statsBox = new VBox();
        statsBox.setSpacing(20);
        
        GridPane statsPanel = new GridPane();
        statsPanel.getStyleClass().add("infoGrid");
        
        Label statsTitleLabel = new Label("Statistics");
        Label statsDescriptionLabel = new Label("(number of\nproperties in borough)");
            statsDescriptionLabel.getStyleClass().add("labelSmall");
        Label statsLabel1 = new Label("Minimum");
        Label statsLabel2 = new Label("Lower Quartile");
        Label statsLabel3 = new Label("Median");
        Label statsLabel4 = new Label("Upper Quartile");
        Label statsLabel5 = new Label("Maximum");
        
        statsPanel.add(statsTitleLabel, 0, 0);
        statsPanel.add(statsDescriptionLabel, 1, 0);
        
        statsPanel.add(new Label(String.valueOf(noOfPropertiesStats.getMinNoOfPropertiesInBorough())), 1, 1);
        statsPanel.add(new Label(String.valueOf(noOfPropertiesStats.getFirstQuartile())), 1, 2);
        statsPanel.add(new Label(String.valueOf(noOfPropertiesStats.getMedian())), 1, 3);
        statsPanel.add(new Label(String.valueOf(noOfPropertiesStats.getThirdQuartile())), 1, 4);
        statsPanel.add(new Label(String.valueOf(noOfPropertiesStats.getMaxNoOfPropertiesInBorough())), 1, 5);
        
        statsPanel = alignItemsInGridPane(statsPanel);
        
        statsPanel.add(statsLabel1, 0, 1);
        statsPanel.add(statsLabel2, 0, 2);
        statsPanel.add(statsLabel3, 0, 3);
        statsPanel.add(statsLabel4, 0, 4);
        statsPanel.add(statsLabel5, 0, 5);
        
        Button moreStatsButton = new Button("Show more stats!");
        moreStatsButton.setOnAction(e -> showMoreStats());
        
        statsBox.getChildren().addAll(statsPanel, moreStatsButton);
            
        return statsBox;
    }
    
    private void showMoreStats() {
        if (statisticsViewer == null) {
            statisticsViewer = new StatisticsViewer(selectedMinPrice, selectedMaxPrice);
            statisticsViewer.show();
        }
        updateStats();
    }
    
    private void updateStats() {
        if (statisticsViewer == null) {
            return;
        }
        
        if (statisticsViewer.getCurrentMinPrice() != selectedMinPrice || statisticsViewer.getCurrentMaxPrice() != selectedMaxPrice) {
            statisticsViewer.update(selectedMinPrice, selectedMaxPrice);
        }
    }
    
    private GridPane alignItemsInGridPane(GridPane grid) {
        for (Node node : grid.getChildren()) {
            grid.setHalignment(node, HPos.CENTER);
            grid.setValignment(node, VPos.CENTER);
            node.maxWidth(Double.MAX_VALUE);
            node.maxHeight(Double.MAX_VALUE);
        }
        return grid;
    }
    
    private void makeStatsPane() {
        if(statsOrder.size() > 0)
        {
            statsOrder.clear();
        }
        currentStat = 0; 
        
        Label reviewInfo = new Label("default");
        Label availableInfo = new Label("default");
        Label noHomeAndApartmentsInfo = new Label("default");
        Label expensiveInfo = new Label("default");
        Label priceSDInfo = new Label("default");
        Label highAvgReviewInfo = new Label("default:"); 
        XYChart.Series averagePriceData = new XYChart.Series();
    
        // The layout of the window
        GridPane statsGrid = new GridPane(); 
        VBox reviews = new VBox();
        VBox available = new VBox(); 
        VBox noHomeAndApartments = new VBox();
        VBox expensive = new VBox(); 
        VBox priceSD = new VBox(); 
        VBox averagePrice = new VBox(); 
        VBox highAvgReview = new VBox();
        
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart barChart = new BarChart(xAxis, yAxis);
          
        
        // The "title" labels in the window
        Label title = new Label("General Statistics");
        Label reviewTitle = new Label("Average Reviews Per Property:");
        Label availableTitle = new Label("Total Available Properties:");
        Label noHomeAndApartmentsTitle = new Label("Entire Home and Apartments:");
        Label expensiveTitle = new Label("Most Expensive Borough:");
        Label priceSDTitle = new Label("Standard Deviation of Price (£):");
        Label highAvgReviewTitle = new Label("Borough with the Highest \nAverage Amount of Reviews:");
        
        VBox statsGridVBox = new VBox();
        statsGridVBox.getChildren().add(statsGrid);
        
        VBox AvgPriceBarCharVBox = new VBox();
        AvgPriceBarCharVBox.getChildren().add(createAvgPriceBarChart());
        
        VBox AvgReviewsBarCharVBox = new VBox();
        AvgReviewsBarCharVBox.getChildren().add(createAvgReviewsBarChart());
        
        statsOrder.add(statsGridVBox);
        statsOrder.add(AvgPriceBarCharVBox);
        statsOrder.add(AvgReviewsBarCharVBox);
        // Adding components 
        statsWindow.setAlignment(Pos.CENTER);
        statsWindow.getChildren().add(title); 
        statsWindow.getChildren().add(getStatsIndex(0)); 
        //window.getChildren().add(barChart);
        title.setAlignment(Pos.CENTER);
        statsGrid.setAlignment(Pos.CENTER); 
        
        statsGrid.add(reviews, 0, 0);
        statsGrid.add(available, 0, 1);
        statsGrid.add(noHomeAndApartments, 1, 0);
        statsGrid.add(expensive, 1, 1);
        statsGrid.add(priceSD, 0, 2); 
        statsGrid.add(highAvgReview, 1 , 2); 
        
        reviews.setAlignment(Pos.CENTER);
        reviews.getChildren().add(reviewTitle); 
        reviews.getChildren().add(reviewInfo);
        
        available.setAlignment(Pos.CENTER);
        available.getChildren().add(availableTitle); 
        available.getChildren().add(availableInfo);
        
        noHomeAndApartments.setAlignment(Pos.CENTER);
        noHomeAndApartments.getChildren().add(noHomeAndApartmentsTitle); 
        noHomeAndApartments.getChildren().add(noHomeAndApartmentsInfo);
        
        expensive.setAlignment(Pos.CENTER);
        expensive.getChildren().add(expensiveTitle); 
        expensive.getChildren().add(expensiveInfo);
        
        priceSD.setAlignment(Pos.CENTER);
        priceSD.getChildren().add(priceSDTitle); 
        priceSD.getChildren().add(priceSDInfo);
        
        highAvgReview.setAlignment(Pos.CENTER);
        highAvgReview.getChildren().add(highAvgReviewTitle); 
        highAvgReview.getChildren().add(highAvgReviewInfo);
        
        //add CSS
        
        statsGrid.setId("statsgrid"); 
        statsWindow.getStyleClass().add("statsWindowAndButtons");
        reviews.getStyleClass().add("statsvbox"); 
        available.getStyleClass().add("statsvbox");
        noHomeAndApartments.getStyleClass().add("statsvbox");
        expensive.getStyleClass().add("statsvbox");
        priceSD.getStyleClass().add("statsvbox");
        highAvgReview.getStyleClass().add("statsvbox");
        
        
        reviewInfo.getStyleClass().add("statslabels"); 
        availableInfo.getStyleClass().add("statslabels"); 
        noHomeAndApartmentsInfo.getStyleClass().add("statslabels"); 
        expensiveInfo.getStyleClass().add("statslabels"); 
        priceSDInfo.getStyleClass().add("statslabels"); 
        highAvgReviewInfo.getStyleClass().add("statslabels");
        
        title.getStyleClass().add("windowTitle"); 
        
    
        setText(reviewInfo, StatisticsData.getAverageNoReviews(false));
        setText(noHomeAndApartmentsInfo, StatisticsData.getNoHomeAndApartments(false));
        setText(availableInfo, StatisticsData.getAvailableInfo(false));
        setText(expensiveInfo, StatisticsData.getExpensiveInfo());
        setText(priceSDInfo, StatisticsData.getPriceSDInfo(false));
        setText(highAvgReviewInfo, StatisticsData.getHighAvgReview());
        
        Button leftStatsButton = new Button();
        leftStatsButton.setText("<");
        leftStatsButton.setOnAction(this::leftStatButtonAction);
        leftStatsButton.setMinSize(10, 100);
        leftStatsButton.setAlignment(Pos.CENTER);
        
        //Create the right button
        Button rightStatsButton = new Button();
        rightStatsButton.setText(">");
        rightStatsButton.setOnAction(this::rightStatButtonAction);
        rightStatsButton.setMinSize(10, 100);
        rightStatsButton.setAlignment(Pos.CENTER);
        
        VBox rightButtonVBox = new VBox();
        rightButtonVBox.getChildren().add(rightStatsButton);
        rightButtonVBox.getStyleClass().add("statsWindowAndButtons");
        rightButtonVBox.setAlignment(Pos.CENTER);
        
        VBox leftButtonVBox = new VBox();
        leftButtonVBox.getChildren().add(leftStatsButton);
        leftButtonVBox.getStyleClass().add("statsWindowAndButtons");
        leftButtonVBox.setAlignment(Pos.CENTER);
        
        
        BorderPane statsBorder = new BorderPane(); 
        statsBorder.setCenter(statsWindow);
        statsBorder.setLeft(leftButtonVBox);
        statsBorder.setRight(rightButtonVBox);
        
        
        statsPane = statsBorder;
    }
    
    private BarChart createAvgPriceBarChart()
    {
        XYChart.Series averagePriceData = new XYChart.Series();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart barChart = new BarChart(xAxis, yAxis);
        xAxis.setLabel("Borough");
        yAxis.setLabel("Average Price");
        averagePriceData = getAveragePricePerBoroughData();
        averagePriceData.setName("Average Price per Night per Borough");
        barChart.getData().add(averagePriceData);
        
        return barChart;
    }
    
    private BarChart createAvgReviewsBarChart()
    {
        XYChart.Series averageReviewsData = new XYChart.Series();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart barChart = new BarChart(xAxis, yAxis);
        xAxis.setLabel("Borough");
        yAxis.setLabel("Average Review Count");
        averageReviewsData = getAverageReviewsPerBoroughData();
        averageReviewsData.setName("Average Number of Reviews per Borough");
        barChart.getData().add(averageReviewsData);
        
        return barChart;
    }
    
    private VBox getStatsIndex(int x)
    {
        if(statsOrder.size() > 0 && x >= 0 && x < statsOrder.size())
        {
            return statsOrder.get(x);
        }
        VBox box = new VBox();
        return box;
    }
    
    /**
     * Action for the left button which will go the element at index current - 1 in both
     * title list and stat list. If it reaches the start of both of the lists, set the labels to
     * the last element in the lists. 
     */
    public void leftStatButtonAction(ActionEvent event)
    {
        if(currentStat - 1 < 0)
        {
            swapWindowContent(statsOrder.size() - 1);
        }
        else
        {
            swapWindowContent(currentStat - 1);
        }
    }
    
    /**
     * Action for the right button which will go the element at index current + 1 in both
     * title list and stat list. If it reaches the end of both of the lists, set the labels to
     * the first element in the lists.
     */
    public void rightStatButtonAction(ActionEvent event)
    {
        if(currentStat + 1 >= statsOrder.size())
        {
            swapWindowContent(0);
        }
        else
        {
            swapWindowContent(currentStat + 1);
        }
    }
    
    private void swapWindowContent(int change)
    {
        statsWindow.getChildren().remove(statsOrder.get(currentStat));
        currentStat = change; 
        statsWindow.getChildren().add(statsOrder.get(currentStat));
    }
    
    
    private void setText(Label label, double dataToFormat) {
        String x = String.valueOf(String.format("%.2f", dataToFormat) + " (2 d.p)"); 
        label.setText(x);
    }
    
    private void setText(Label label, int dataToFormat) {
        label.setText(String.valueOf(dataToFormat));
    }
    
    private void setText(Label label, String dataToFormat) {
        label.setText(dataToFormat);
    }
    
    private XYChart.Series getAveragePricePerBoroughData()
    {
        XYChart.Series data = new XYChart.Series();
        Map<String, Integer> information = StatisticsData.getAveragePricePerBorough();
        for (Map.Entry<String, Integer> set : information.entrySet())
        {
            data.getData().add(new XYChart.Data(set.getKey(), set.getValue()));
        }
        return data; 
    }
    
    private XYChart.Series getAverageReviewsPerBoroughData()
    {
        XYChart.Series data = new XYChart.Series();
        Map<String, Integer> information = StatisticsData.getAverageReviewsPerBorough();
        for (Map.Entry<String, Integer> set : information.entrySet())
        {
            data.getData().add(new XYChart.Data(set.getKey(), set.getValue()));
        }
        return data; 
    }
    
    private void makeBookingsPane() {
        //creating the pane for the bookings and adding any styling
        BorderPane pane = new BorderPane();
        
        VBox contentPane = new VBox();
                
                HBox hbox = new HBox();
                    Label windowTitle = new Label("Your bookings: ");
                    windowTitle.getStyleClass().add("windowTitle");
                hbox.getChildren().add(windowTitle);
                hbox.setAlignment(Pos.CENTER);
                
                ScrollPane scrollPane = new ScrollPane();
                    VBox bookingsPanel = new VBox();
                        ArrayList<Booking> bookingList = DataHandler.getBookingList();
                        if (! bookingList.isEmpty()) {
                            for(Booking booking : bookingList) {
                                BorderPane bookingListing = createBookingListing(booking);
                                    bookingListing.getStyleClass().add("bookingListing");
                                    bookingsPanel.setMargin(bookingListing, new Insets(10));
                                    
                                bookingsPanel.getChildren().add(bookingListing);
                            }
                        }
                        else {
                            Label noBookingsLabel = new Label("There are no bookings currently...");
                            bookingsPanel.getChildren().add(noBookingsLabel);
                        }       
                scrollPane.setContent(bookingsPanel);
        
        contentPane.getChildren().add(hbox);
        contentPane.getChildren().add(scrollPane);
        contentPane.setSpacing(20);

        pane.setCenter(contentPane);
        
        bookingsPane = pane;
    }
    
    private BorderPane createBookingListing(Booking booking) {
        AirbnbListing property = booking.getProperty();
        
        Label propertyName = new Label("Property: " + property.getName());  
        Label hostName = new Label("Host name: " + property.getHost_name());
        Label dates = new Label("Between: " + booking.getCheckInDate().toString()  +  " and " + booking.getCheckOutDate().toString());
        Label durationLabel = new Label("Duration:  " + booking.getDuration() + " night(s)");
        Label priceLabel = new Label("Price:  £" + booking.getGrandTotal());
        
        Button editButton = new Button("Edit Booking");
            editButton.setPrefSize(110, 20);
            editButton.setOnAction(e -> editBooking());
        Button contactButton = new Button("Contact Host");
            contactButton.setPrefSize(110, 20);
            contactButton.setOnAction(e -> contactAction(booking));
        Button cancelButton = new Button("Cancel Booking");
            cancelButton.setPrefSize(110, 20);
            cancelButton.setOnAction(e -> cancelBookingAction(booking));
        
        BorderPane bookingListing = new BorderPane();
            VBox centerPane = new VBox(propertyName, hostName, dates, durationLabel, priceLabel);
                centerPane.setSpacing(5);
            VBox rightPane = new VBox(editButton, contactButton, cancelButton);
                rightPane.setSpacing(20);
                rightPane.setAlignment(Pos.CENTER);
        bookingListing.setCenter(centerPane);
        bookingListing.setRight(rightPane);
        bookingListing.setPadding(new Insets(10));
        bookingListing.setMargin(centerPane, new Insets(0,110,0,0));
        
        return bookingListing;
    }
    
    private void editBooking()  {

    
    }
    
    private void contactAction(Booking booking)  {
        Desktop desktop;
        if (Desktop.isDesktopSupported() 
            && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
            try {
                URI mailto = new URI("mailto:" + booking.getProperty().getMailHost_name(false) + "@kcl.ac.uk?subject=About%20the%20booking%20between%20" + booking.getCheckInDate().toString() + "%20and%20" + booking.getCheckOutDate().toString() + "&body=Hello%20" + booking.getProperty().getMailHost_name(true) + ",%0A%0DI%20need%20to%20edit%20the%20booking%20between%20" + booking.getCheckInDate().toString() + "%20and%20" + booking.getCheckOutDate().toString() + "%20in%20the%20property%20called%20'" + booking.getProperty().getMailName() + "'%20(Property%20iD%20:" + booking.getProperty().getId() + ")%0A%0D");
                desktop.mail(mailto);
            }
            catch (Exception e) {}
        } else {
          // TODO fallback to some Runtime.exec(..) voodoo?
          throw new RuntimeException("desktop doesn't support mailto; mail is dead anyway ;)");
        }
    }
    
    private void cancelBookingAction(Booking booking)  {
        DataHandler.removeToBookingList(booking);
        setPane(4);
    }
}
