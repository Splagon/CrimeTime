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
public class MainViewerNEW extends Stage
{
    // instance variables
    private MainViewerPane welcomePane = new WelcomePane(this);
    private MainViewerPane priceSelectorPane = new PriceSelectorPane(this);
    private MainViewerPane mapPane = new MapPane(this);
    private MainViewerPane statsPane = new StatsPane(this);
    private MainViewerPane bookingsPane = new BookingsPane(this);
    
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
    private final String prevButtonPreFix = "<-- ";
    private final String nextButtonPostFix = " -->";
    
    private MainViewerPane[] paneOrder = { welcomePane, priceSelectorPane, mapPane, statsPane, bookingsPane };

    private int currentSceneIndex;
    
    private BorderPane root = new BorderPane();
    
    private AnchorPane mapView;
    
    private String[][] mapPositions;
    
    private Scene mainScene;
    
    private StatisticsViewer statisticsViewer;
    
    // ArrayList<VBox> statsOrder = new ArrayList<>();
    // int currentStat = 0;
    // VBox statsWindow = new VBox();
    
    /**
     * Constructor for objects of class MapViewer
     */
    public MainViewerNEW()
    {         
        DataHandler.initialiseHandler();
        
        sceneWidth = 1100;
        sceneHeight = 635;
        
        lowestPrice = StatisticsData.getLowestPrice();
        highestPrice = StatisticsData.getHighestPrice();
        
        root = new BorderPane();
        
        //panel switcher buttons
        prevPanelButton = new Button(prevButtonPreFix + "Bookings");
        prevPanelButton.setPrefSize(150, 20);
        nextPanelButton = new Button("Price Selection" + nextButtonPostFix);
        nextPanelButton.setPrefSize(150, 20);
        //styling for the buttons
        prevPanelButton.getStyleClass().add("smallWindowButtons");
        nextPanelButton.getStyleClass().add("smallWindowButtons");
        
        //panel switcher
        makePanelSwitcherPane();
        
        mainScene = new Scene(root, sceneWidth, sceneHeight);
        
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
            currentSceneIndex = paneOrder.length - 1;
        }
        
        updateButtonText();
        
        setPane(currentSceneIndex);
    }
    
    private void goToNextPanel() {
        currentSceneIndex++;
        
        if (currentSceneIndex >= paneOrder.length) {
            currentSceneIndex = 0;
        }
        
        updateButtonText();
        
        setPane(currentSceneIndex);
    }
    
    private void updateButtonText() {
        String nameOfPaneToChangeTo = paneOrder[currentSceneIndex].getClass().toString();
        nameOfPaneToChangeTo = nameOfPaneToChangeTo.substring(6);
        
        switch (nameOfPaneToChangeTo) {
            case ("WelcomePane") :
                prevPanelButton.setText(prevButtonPreFix + "Bookings");
                nextPanelButton.setText("Price Selection" + nextButtonPostFix);
                break;
            case ("PriceSelectorPane") :
                prevPanelButton.setText(prevButtonPreFix + "Welcome");
                nextPanelButton.setText("Map" + nextButtonPostFix);
                break;
            case ("MapPane") :
                prevPanelButton.setText(prevButtonPreFix + "Price Selection");
                nextPanelButton.setText("General Statistics" + nextButtonPostFix);
                break;
            case ("StatsPane") :
                prevPanelButton.setText(prevButtonPreFix + "Map");
                nextPanelButton.setText("Bookings" + nextButtonPostFix);
                break;
            case ("BookingsPane") :
                prevPanelButton.setText(prevButtonPreFix + "General Statistics");
                nextPanelButton.setText("Welcome" + nextButtonPostFix);
                break;
        }
    }
    
    public void setPane(int newSceneIndex) {
        currentSceneIndex = newSceneIndex;
        
        MainViewerPane paneToChangeTo = paneOrder[currentSceneIndex];
        paneToChangeTo.makePane();
        
        setButtonsDisabled(currentSceneIndex);
        root.setCenter(paneToChangeTo.getPane());
        setTitle(paneToChangeTo.getTitleName());
        setScene(mainScene);
    }
        
    // // switch (nameOfPaneToChangeTo) {
        // // case ("welcomePane") :
            // // makeWelcomePane();
            // // paneToChangeTo = welcomePane;
            // // break;
        // // case ("priceSelectorPane") :
            // // setTitle("Price Selection Screen");
            // // paneToChangeTo = priceSelectorPane;
            // // break;
        // // case ("mapPane") :
            // // try {
                // // makeMapPane();
                // // paneToChangeTo = mapPane;
            // // }
            // // catch (Exception ex) {};
            // // break;
        // // case ("statsPane") :
            // // setTitle("Information");
            // // paneToChangeTo = statsPane;
            // // break;
        // // case ("bookingsPane") :
            // // setTitle("Bookings");
            // // makeBookingsPane();
            // // paneToChangeTo = bookingsPane;
            // // break;
    // // }
    
    private void setButtonsDisabled(int currentSceneIndex) {
        int nextSceneIndex = currentSceneIndex + 1;
        int prevSceneIndex = currentSceneIndex - 1;
        
        if (nextSceneIndex >= paneOrder.length) {
            nextSceneIndex = 0;
        }
        if (prevSceneIndex < 0) {
            prevSceneIndex = paneOrder.length - 1;
        }
        
        if (selectedMinPrice == null && selectedMaxPrice == null) {    
            if (paneOrder[nextSceneIndex].equals(mapPane)) {
                nextPanelButton.setDisable(true);
            }
            else {
                nextPanelButton.setDisable(false);
            }
            if (paneOrder[prevSceneIndex].equals(mapPane)) {
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
    
    // private void setButtonsDisabled(boolean isPrevButtonsDisabled, boolean isNextButtonsDisabled) {
        // prevPanelButton.setDisable(isPrevButtonsDisabled);
        // nextPanelButton.setDisable(isNextButtonsDisabled);
    // }
    
    public void changeToPriceSelector() {
        setPane(1);
    }
    
    public HBox createMinMaxBox() {
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
                                    MapPane currentMapPane = (MapPane) mapPane;
                                    try {
                                        currentMapPane.makeHexagonMap();
                                        currentMapPane.makePane();
                                    }
                                    catch (Exception ex) {}
                                    
                                    try { changeToMapPane(); }
                                    catch (Exception ex) {}
                                    
                                    //currentMapPane.updateStats();
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
    
    public String showStatus(Button confirm) {
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
    
    public HBox setInitialMinMaxBoxSelection(HBox minMaxBox) {
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
    
    public void changeToMapPane() {
        setPane(2);
    }
    
    public Integer getSelectedMinPrice() {
        return selectedMinPrice;
    }
    
    public Integer getSelectedMaxPrice() {
        return selectedMaxPrice;
    }
    
    public Scene getMainScene() {
        return getScene();
    }
    
    public StatisticsViewer getStatisticsViewer() {
        return statisticsViewer;
    }
}
