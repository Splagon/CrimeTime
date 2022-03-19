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

/**
 * Write a description of class MapViewer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MainViewer extends Stage
{
    // instance variables
    // private Scene welcomeScene;
    // private Scene priceSelectorScene;
    // private Scene mapScene;
    // private Scene statsScene;
    // private Scene favouritesScene;
    
    private Pane welcomePane;
    private Pane priceSelectorPane;
    private Pane mapPane;
    private Pane statsPane;
    private Pane favouritesPane;
    
    private int sceneWidth;
    private int sceneHeight;
    
    private Integer selectedMinPrice;
    private Integer selectedMaxPrice;
    
    private int lowestPrice;
    private int highestPrice;
    
    private Label statusLabel;
    
    private AnchorPane panelSwitcherPane;
    private Button prevPanelButton;
    private Button nextPanelButton;
    
    private String[] sceneOrder = { "welcomePane", "priceSelectorPane", "mapPane", "statsPane", "favouritesPane" };
    //private PaneListing[] paneOrder = { welcomePane, priceSelectorPane, mapPane, statsPane, favouritesPane };

    private int currentSceneIndex;
    
    private BorderPane root = new BorderPane();
    
    private AnchorPane mapView;
    
    private String[][] mapPositions;
    
    //private StatisticsData dataHandler;
    
    private NoOfPropertiesStats noOfPropertiesStats;
    private Scene mainScene;
    
    /**
     * Constructor for objects of class MapViewer
     */
    public MainViewer() throws Exception
    {         
        DataHandler.initialiseHandler();
        
        sceneWidth = 1100;
        sceneHeight = 635;
        
        lowestPrice = StatisticsData.getLowestPrice();
        highestPrice = StatisticsData.getHighestPrice();
        
        makePriceSelectorPane();
        makeStatsPane();
        
        root = new BorderPane();
        makePanelSwitcherPane();
        
        mainScene = new Scene(root, sceneWidth, sceneHeight);
        setResizable(false);
        
        setPane(0);
        
        mainScene.getStylesheets().add("stylesheet.css");
        root.getStyleClass().add("mainRoot");
    }
    
    private void makePanelSwitcherPane() {
        panelSwitcherPane = new AnchorPane();
        
        prevPanelButton = new Button("<");
            prevPanelButton.setOnAction(e -> goToPrevPanel());
        nextPanelButton = new Button(">");
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
        
        setPane(currentSceneIndex);
    }
    
    private void goToNextPanel() {
        currentSceneIndex++;
        
        if (currentSceneIndex >= sceneOrder.length) {
            currentSceneIndex = 0;
        }
        
        setPane(currentSceneIndex);
    }
    
    private void setPane(int currentSceneIndex) {      
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
            case ("favouritesPane") :
                setTitle("Favourites");
                //makeFavouritesPane();
                //paneToChangeTo = paneToChangeTo;
                break;
        }
        
        setButtonsDisabled(currentSceneIndex);
        root.setCenter(paneToChangeTo);
        setScene(mainScene);
    }
    
    private void setButtonsDisabled(int currentSceneIndex) {
        if (selectedMinPrice == null && selectedMaxPrice == null) {    
            if (currentSceneIndex <= 0 || currentSceneIndex >= 4) {
                prevPanelButton.setDisable(false);
                nextPanelButton.setDisable(false);
            }
            else if (currentSceneIndex == 1) {
                prevPanelButton.setDisable(false);
                nextPanelButton.setDisable(true);
            }
            else if (currentSceneIndex == 3) {
                prevPanelButton.setDisable(true);
                nextPanelButton.setDisable(false);
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
        instructions.getChildren().addAll(instructionsTitle, instructions1, instructions2, instructions3); 
        
        instrcutionsAndStart.setLeft(instructions);
        instrcutionsAndStart.setCenter(startButton);
        
        //root.setCenter(window);
        
        //creating the scene and adding the CSS
        welcomePane = window;
        //setResizable(false);
        //welcomePane.getStylesheets().add("stylesheet.css");
        
        window.getStyleClass().add("mainRoot");
        
        title.getStyleClass().add("welcomeTittle");
        
        instructions.getStyleClass().add("instructionsTittle"); 
        
        instructions1.getStyleClass().add("instructions"); 
        instructions2.getStyleClass().add("instructions"); 
        instructions3.getStyleClass().add("instructions");
        
        instrcutionsAndStart.getStyleClass().add("instrcutionsAndStart");
        
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
        
        //window.getStyleClass().add("mainRoot");
        
        title.getStyleClass().add("welcomeTittle");
        
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
        
        // int low = dataHandler.getLowestPrice();
        // int high = dataHandler.getHighestPrice();
        ArrayList<String> options = getPriceSelectionOptions(0, highestPrice);
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
    
    private void changeToMapPane() throws Exception {
        setPane(2);
    }
    
    private void makeMapPane() throws Exception {
        setTitle("Map of London");
            
        BorderPane window = new BorderPane();
            
        //root.setTop(minMaxBox);
        
        // if (mapPane == null) {
            // mapScene = new Scene(root, sceneWidth, sceneHeight);
            // setResizable(false);
        // }
        
        //styling
        //mapScene.getStylesheets().add("stylesheet.css");
        //root.getStyleClass().add("root");
        //Pane window = new FlowPane();
        
        VBox infoPane = new VBox();
            Label titleLabel = new Label("Boroughs of London");
                titleLabel.getStyleClass().add("welcomeTittle");
            VBox stats = createStatsPanel();
            GridPane key = createKey();
            
        infoPane.getChildren().addAll(titleLabel, key, stats);
        infoPane.setPadding(new Insets(20));
        infoPane.setSpacing(30);

        //window.getChildren().addAll(stats, mapView, key);
        //root.setCenter(window);
        
        mapView.setPadding(new Insets(20));
        
        window.setLeft(infoPane);
        window.setCenter(mapView);
        
        mapPane = window;
    }
    
    private void makeHexagonMap() throws Exception {
        mapPositions = StatisticsData.getMapPositions();
        
        noOfPropertiesStats = new NoOfPropertiesStats(selectedMinPrice, selectedMaxPrice);
            
        mapView = new AnchorPane();
            mapView.setMinSize(720, 510);
            // rows
            for (int m = 0; m < mapPositions.length; m++) {
                
                FlowPane row = new FlowPane();
                row.setHgap(1.0);
                StackPane rowSpace;
                
                row.setMinWidth(mapView.getMinWidth());
                
                if (m % 2 == 0) {
                        rowSpace = new StackPane();
                        Rectangle insetSpace = createSpacerRectangle(47);
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
                        
                        ImageView hexagonFilledImage = new ImageView(new Image("/hexagonFilledGreen.png"));
                        ImageView hexagonFilled = setHexagonFilledColour(hexagonFilledImage, boroughButton.getBoroughName(), 93, noOfPropertiesStats);
                    
                        rowSpace.getChildren().addAll(hexagonFilled, hexagonOutline, boroughButton);
                    }
                    else {
                        Rectangle emptySpace = createSpacerRectangle(94);
                        rowSpace.getChildren().add(emptySpace);
                    }
                    row.getChildren().add(rowSpace);
                }
                
                if (m % 2 == 1) {
                    rowSpace = new StackPane();
                    Rectangle insetSpace = createSpacerRectangle(47);
                    rowSpace.getChildren().add(insetSpace);
                    row.getChildren().add(rowSpace);
                }                
                AnchorPane.setTopAnchor(row, m*72.0);
                mapView.getChildren().add(row);
            }
            
            HBox minMaxBox = createMinMaxBox();
                minMaxBox = setInitialMinMaxBoxSelection(minMaxBox);
                AnchorPane.setTopAnchor(minMaxBox, 11.0);
                AnchorPane.setRightAnchor(minMaxBox, 0.0);
                
            mapView.getChildren().add(minMaxBox);
    }
    
    private Rectangle createSpacerRectangle(int widthHeight) {
        Rectangle spacerRectangle = new Rectangle(widthHeight, widthHeight);
        spacerRectangle.setFill(Color.TRANSPARENT);
        return spacerRectangle;
    }
    
    private void openPropertyViewer(String boroughName) throws Exception {
        try {
            PropertyViewer stage = new PropertyViewer(boroughName, selectedMinPrice, selectedMaxPrice, null);
            if(stage.getInternetConnection() == true){
                stage.show();
            } else {
                stage.noConnectionAlert();
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
    
    private ImageView setHexagonFilledColour(ImageView hexagon, int heightWidth, int percentile) throws Exception {
        ColorAdjust shader = new ColorAdjust();
            shader.setBrightness(StatisticsData.getBoroughMapColour(percentile));
            
        hexagon.setFitWidth(heightWidth);
        hexagon.setFitHeight(heightWidth);
            
        hexagon.setEffect(shader);
        
        return hexagon;
    }
    
    private GridPane createKey() throws Exception {
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
        Stage stage = new StatisticsViewer(selectedMinPrice, selectedMaxPrice);
        stage.show();
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
        Label reviewInfo = new Label("default");
        Label availableInfo = new Label("default");
        Label noHomeAndApartmentsInfo = new Label("default");
        Label expensiveInfo = new Label("default");
        Label priceSDInfo = new Label("default");
        Label highAvgReviewInfo = new Label("default:"); 
        XYChart.Series averagePriceData = new XYChart.Series();
    
        // The layout of the window
        VBox window = new VBox();
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
        Label title = new Label("Statistics");
        Label reviewTitle = new Label("Average Reviews Per Property:");
        Label availableTitle = new Label("Total Available Properties:");
        Label noHomeAndApartmentsTitle = new Label("Entire Home and Apartments:");
        Label expensiveTitle = new Label("Most Expensive Borough:");
        Label priceSDTitle = new Label("Standard Deviation of Price (£):");
        Label highAvgReviewTitle = new Label("Borough with the Highest \nAverage Amount of Reviews:");
        
        
        // Adding components 
        window.setAlignment(Pos.CENTER);
        window.getChildren().add(title); 
        window.getChildren().add(statsGrid); 
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
        
        //Set the scene and add CSS
        //Scene scene = new Scene(window, 1200,700);
        
        //scene.getStylesheets().add("stylesheet.css");
        
        statsGrid.setId("statsgrid"); 
        window.getStyleClass().add("statsvbox");
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
        
        title.getStyleClass().add("welcomeTittle"); 
        
        xAxis.setLabel("Borough");
        yAxis.setLabel("Average Price");
        averagePriceData.setName("Average Price per Night per Borough");
        setAveragePricePerBorough();
        barChart.getData().add(averagePriceData);
    
        setText(reviewInfo, StatisticsData.getAverageNoReviews());
        setText(noHomeAndApartmentsInfo, StatisticsData.getNoHomeAndApartments());
        setText(availableInfo, StatisticsData.getAvailableInfo());
        setText(expensiveInfo, StatisticsData.getExpensiveInfo());
        setText(priceSDInfo, StatisticsData.getPriceSDInfo());
        setText(highAvgReviewInfo, StatisticsData.getHighAvgReview());
        
        statsPane = window;
    }
    
    /**
     * Update all of the info labels
     */
    private void setInfo()
    {
        
        
        
        
        
        
        
        // setReviewInfo(); 
        // setNoHomeAndApartmentsInfo();
        // setAvailableInfo();
        // setExpensiveInfo();
        // setPriceSDInfo();
        // setHighAvgReviewInfo();
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
    
    private void setAveragePricePerBorough()
    {
        Map<String, Integer> information = StatisticsData.getAveragePricePerBorough();
        for (Map.Entry<String, Integer> set : information.entrySet())
        {
            // averagePriceData.getData().add(new XYChart.Data(set.getKey(), set.getValue()));
        }
    }
    
    private void makeBookingScene() {
        
    }
}
