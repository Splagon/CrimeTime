import javafx.application.Application;
import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;


/**
 * Write a description of class MapViewer here.
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 25/03/22
 */
public class MainViewer extends Stage
{
    // all of the panes visible in the welcome scene
    private MainViewerPane welcomePane = new WelcomePane(this);
    private MainViewerPane priceSelectorPane = new PriceSelectorPane(this);
    private MainViewerPane mapPane = new MapPane(this);
    private MainViewerPane statsPane = new StatsPane(this);
    private MainViewerPane bookingsPane = new BookingsPane(this);
    
    private int sceneWidth;
    private int sceneHeight;
    
    // the user-selected min and max price
    private Integer selectedMinPrice;
    private Integer selectedMaxPrice;
    
    // lowest and highest price of all properties
    private int lowestPrice;
    private int highestPrice;
    
    // the pane containing the pane switcher buttons
    private AnchorPane panelSwitcherPane;
    
    // buttons to switch to the next Pane
    private Button prevPanelButton;
    private Button nextPanelButton;
    
    private final String prevButtonPreFix = "<-- ";
    private final String nextButtonPostFix = " -->";
    
    // order of the panes in the main viewer
    private MainViewerPane[] paneOrder = { welcomePane, priceSelectorPane, mapPane, statsPane, bookingsPane };

    // index of the current pane being shown
    private int currentPaneIndex;
    
    // root of the window
    private BorderPane root = new BorderPane();
    
    private Scene mainScene;
    
    // the stage which holds the stats viewer
    private StatisticsViewer statisticsViewer;
    
    /**
     * Constructor for objects of class MapViewer
     */
    public MainViewer()
    {         
        StatisticsData.initialiseHandler();
        
        sceneWidth = 1300;
        sceneHeight = 650;
        
        lowestPrice = StatisticsData.getLowestPrice();
        highestPrice = StatisticsData.getHighestPrice();
        
        root = new BorderPane();
        Animations.spin(root);
        
        //panel switcher buttons
        prevPanelButton = new Button(prevButtonPreFix + "Bookings");
        prevPanelButton.setPrefSize(155, 20);
        nextPanelButton = new Button("Price Selection" + nextButtonPostFix);
        nextPanelButton.setPrefSize(155, 20);
        
        //styling for the buttons
        prevPanelButton.getStyleClass().add("smallWindowButtons");
        nextPanelButton.getStyleClass().add("smallWindowButtons");
        
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
        
        AnchorPane.setTopAnchor(prevPanelButton, 5.0);
        AnchorPane.setLeftAnchor(prevPanelButton, 0.0);
        AnchorPane.setTopAnchor(nextPanelButton, 5.0);
        AnchorPane.setRightAnchor(nextPanelButton, 0.0);
        
        panelSwitcherPane.getChildren().add(prevPanelButton);
        panelSwitcherPane.getChildren().add(nextPanelButton);
        
        root.setBottom(panelSwitcherPane);
    }
    
    private void goToPrevPanel() {
        // currentSceneIndex--;
        
        // if (currentSceneIndex < 0) {
            // currentSceneIndex = paneOrder.length - 1;
        // }
        
        currentPaneIndex = decreaseIndex(currentPaneIndex, Arrays.asList(paneOrder));
        
        updateButtonText();
        
        setPane(currentPaneIndex);
    }
    
    private void goToNextPanel() {
        // currentSceneIndex++;
        
        // if (currentSceneIndex >= paneOrder.length) {
            // currentSceneIndex = 0;
        // }
        
        currentPaneIndex = increaseIndex(currentPaneIndex, Arrays.asList(paneOrder));
        
        updateButtonText();
        
        setPane(currentPaneIndex);
    }
    
    private int decreaseIndex(int index, List list)
    {
        index--;
        
        if (index < 0) {
            index = list.size() - 1;
        }
        
        return index;
    }
    
    private int increaseIndex(int index, List list)
    {
        index++;
        
        if (index >= list.size()) {
            index = 0;
        }
        
        return index;
    }
    
    /**
     * Updates the text of the next and previous pane buttons
     */
    private void updateButtonText() {
        String nameOfPaneToChangeTo = paneOrder[currentPaneIndex].getClass().toString();
        nameOfPaneToChangeTo = nameOfPaneToChangeTo.substring(6);
        
        int prevSceneIndex = decreaseIndex(currentPaneIndex, Arrays.asList(paneOrder));
        int nextSceneIndex = increaseIndex(currentPaneIndex, Arrays.asList(paneOrder));
        
        MainViewerPane prevPane = paneOrder[prevSceneIndex];
        MainViewerPane nextPane = paneOrder[nextSceneIndex];
        
        setPrevNextButtonText(prevPane, nextPane);
    }
    
    /**
     * Appends the name of the next and previous pane to the appropriate button
     */
    private void setPrevNextButtonText(MainViewerPane prevPane, MainViewerPane nextPane)
    {
        prevPanelButton.setText(prevButtonPreFix + prevPane.getTitleName());
        nextPanelButton.setText(nextPane.getTitleName() + nextButtonPostFix);
    }
    
    public void setPane(int newSceneIndex) 
    {
        currentPaneIndex = newSceneIndex;
        
        MainViewerPane paneToChangeTo = paneOrder[currentPaneIndex];
        
        paneToChangeTo.makePane();
        updateButtonText();
        setButtonsDisabled(currentPaneIndex);
 
        Animations.fadeIn(paneToChangeTo.getPane(), 1000);
        
        root.setCenter(paneToChangeTo.getPane());
        
        addTopMinMaxBox(paneToChangeTo);
        
        setTitle(paneToChangeTo.getTitleName());
        
        setScene(mainScene);
    }
    
    public void refreshPane() 
    {
        setPane(currentPaneIndex);
    }
    
    private void addTopMinMaxBox(MainViewerPane paneToChangeTo) {
        if (paneToChangeTo.getHasMinMaxBox()) 
        {
            AnchorPane topPane = new AnchorPane();
            final double spacing = 5.0;
            
            HBox minMaxBox = createMinMaxBox();
                minMaxBox = setInitialMinMaxBoxSelection(minMaxBox);
                AnchorPane.setTopAnchor(minMaxBox, spacing);
                AnchorPane.setBottomAnchor(minMaxBox, spacing);
                AnchorPane.setRightAnchor(minMaxBox, spacing);
                
                Button confirm = (Button) minMaxBox.getChildren().get(2);
                ComboBox<String> minBox = (ComboBox<String>) minMaxBox.getChildren().get(0);
                ComboBox<String> maxBox = (ComboBox<String>) minMaxBox.getChildren().get(1);
        
                //styling the min and max box as well as the confirm button
                confirm.getStyleClass().add("confirmForMap");
                minBox.getStyleClass().add("mapMinMaxBoxes");
                maxBox.getStyleClass().add("mapMinMaxBoxes");
                minMaxBox.getStyleClass().add("mapMinMaxBox");
            
            topPane.getChildren().add(minMaxBox);
            
            root.setTop(topPane);
        }
        else
        {
            AnchorPane emptyPane = new AnchorPane();
            root.setTop(emptyPane);
        }
    }
    
    private void setButtonsDisabled(int currentSceneIndex) 
    {
        int nextSceneIndex = increaseIndex(currentSceneIndex, Arrays.asList(paneOrder));
        int prevSceneIndex = decreaseIndex(currentSceneIndex, Arrays.asList(paneOrder));
        
        if (selectedMinPrice == null && selectedMaxPrice == null) 
        {    
            buttonDisablerForMapPane(prevPanelButton, prevSceneIndex);
            buttonDisablerForMapPane(nextPanelButton, nextSceneIndex);
        }
        else
        {
            prevPanelButton.setDisable(false);
            nextPanelButton.setDisable(false);
        }
    }
    
    /**
     * If the selected min and max price is not chosen, the next and previous 
     * pane buttons are disabled if the next or previous pane are the map
     * respectively.
     */
    private void buttonDisablerForMapPane(Button button, int index) 
    {
        if (paneOrder[index].equals(mapPane)) 
        {
            button.setDisable(true);
        }
        else 
        {
            button.setDisable(false);
        }
    }
    
    /**
     * Creates two combo boxes allowing the user to select the minimum and 
     * price and confirm the selected options using a confirm button.
     * 
     * @return HBox containing the minimum and maximum price combo boxes with a 
     *         confirm button.
     */
    public HBox createMinMaxBox() {
        //adding the options to the price selection box, as well as assigning appropriate values
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
            confirm.setOnAction(e -> confirmButtonAction());
        
        minBox.setOnAction(e -> getSelectionOfUser(minBox, confirm, true));
        maxBox.setOnAction(e -> getSelectionOfUser(maxBox, confirm, false));
        
        minMaxBox.getChildren().addAll(minBox, maxBox, confirm);
        
        minMaxBox = setInitialMinMaxBoxSelection(minMaxBox);
        
        return minMaxBox;
    }
    
    /**
     * Switches the pane to the map pane if the confirm button is pressed and the
     * requirements are met.
     */
    private void confirmButtonAction() 
    {
        MapPane currentMapPane = (MapPane) mapPane;
        
        currentMapPane.makeHexagonMap();
        currentMapPane.makePane();
        
        changeToMapPane();
    }
    
    /**
     * Gets the current selection of the respective combobox and enables/disables
     * the confirm button depending of the user's selection
     * 
     * @param box The combobox to get the value selected.
     * @param confirm The confirm button to enable/disable.
     * @param isMinBox Indicates whether the box is the min price combobox.
     */
    private void getSelectionOfUser(ComboBox<String> box, Button confirm, boolean isMinBox)
    {
        getMinMaxBoxValue(box, isMinBox);
        showStatus(confirm);
    }
    
    /**
     * Gets the user's selection of the referenced box.
     * 
     * @param box The combobox to get the value selected
     * @param isMinBox Indicates whether the box is the min price combobox.
     */
    private void getMinMaxBoxValue(ComboBox<String> box, boolean isMinBox) 
    {
        String selected = box.getValue();
        
        if (selected.equals("No Min")) 
        {
            selectedMinPrice = -1;
        }
        else if (selected.equals("No Max")) 
        {
            selectedMaxPrice = -1;
        }
        else 
        {
            Integer integerHold = Integer.parseInt(selected);
            
            if (isMinBox) 
            {
                selectedMinPrice = integerHold;
            }
            else
            {
                selectedMaxPrice = integerHold;
            }
        }
    }
    
    /**
     * Checks whether the current min and max price selections fall within the 
     * specified paramaters and returns the corresponding status message.
     * 
     * @param confirm The confirm button to change the status of.
     * 
     * @return The current status message of the comparison between the comboboxes.
     */
    public void showStatus(Button confirm) {
        if (selectedMinPrice == null && selectedMaxPrice == null) 
        {
            confirm.setDisable(true);
            // Alert alert = new Alert(AlertType.WARNING);
                // alert.setHeaderText("Both your min and max have not been selected");
                // alert.setContentText("Unfortunately, you will need to select both and a max price");
            // alert.show();
            return;
        }
        else if (selectedMinPrice != null && selectedMaxPrice == null) 
        {
            confirm.setDisable(true);
            return;
        }
        else if (selectedMinPrice == null && selectedMaxPrice != null) 
        {
            confirm.setDisable(true);
            return;
        }
        else  
        {
            if (selectedMinPrice == -1 || selectedMaxPrice == -1)  
            {
                confirm.setDisable(false);
                return;
            }
            else if (selectedMinPrice > selectedMaxPrice) 
            {
                confirm.setDisable(true);
                Alert alert = new Alert(AlertType.WARNING);
                    alert.setHeaderText("Your min price must be less than or equal to your max price!");
                    alert.setContentText("Unfortunately, your min price is not less than your max price!");
                alert.show();
                return;
            }
            confirm.setDisable(false);
            return;
        }
    }
    
    /**
     * Sets the initial value of the minimum and maximum comboboxes to fit the
     * current selected minimum and maxium prices.
     * 
     * @param minMaxBox The HBox containing the minimum and maximum comboboxes.
     * 
     * @return A HBox containing the appropriate min and max price selections.
     */
    public HBox setInitialMinMaxBoxSelection(HBox minMaxBox) 
    {
        ComboBox<String> minBox = (ComboBox<String>) minMaxBox.getChildren().get(0);
        ComboBox<String> maxBox = (ComboBox<String>) minMaxBox.getChildren().get(1);
        
        if (selectedMinPrice != null)
        {
            if (selectedMinPrice >= 0) 
            {
                minBox.getSelectionModel().select(selectedMinPrice.toString());
            }
            else
            {
                minBox.getSelectionModel().select("No Min");
            }
        }
        
        if (selectedMaxPrice != null)
        {
            if (selectedMaxPrice >= 0) 
            {
                maxBox.getSelectionModel().select(selectedMaxPrice.toString());
            }
            else 
            {
                maxBox.getSelectionModel().select("No Max");
            }
        }
        
        minMaxBox.getChildren().set(0, minBox);
        minMaxBox.getChildren().set(1, maxBox);
        
        return minMaxBox;
    }
    
    /**
     * Creates the list of all possible prices which can be selected.
     * 
     * @param low The lowest price of all of the properties.
     * @param high The highest price of all of the properties.
     * 
     * @return An arraylist of all possible prices which can be selected.
     */
    private ArrayList<String> getPriceSelectionOptions(int low, int high) 
    {
        ArrayList<String> options = new ArrayList<String>();
        
        // for (int i = low; i <= 100; i+=10) 
        // {
            // Integer num = i;
            // options.add(num.toString());
        // }
        
        // for (int i = 100; i < 200; i+=25) 
        // {
            // Integer num = i;
            // options.add(num.toString());
        // }
        
        // for (int i = 200; i < 500; i+=50) 
        // {
            // Integer num = i;
            // options.add(num.toString());
        // }
        
        // for (int i = 500; i < 1000; i+=100) 
        // {
            // Integer num = i;
            // options.add(num.toString());
        // }
        
        // for (int i = 1000; i <= high; i+=1000) 
        // {
            // Integer num = i;
            // options.add(num.toString());
        // }
        
        for (int i = low; i <= high;) 
        {
            Integer num = i;
            options.add(num.toString());
            
            if (i <= 100)
            {
                i += 10;
            }
            else if (i < 200)
            {
                i += 25;
            }
            else if (i < 500)
            {
                i += 50;
            }
            else if (i < 1000)
            {
                i += 100;
            }
            else
            {
                i += 1000;
            }
        }
        
        return options;
    }
    
    public void changeToPriceSelectorPane() 
    {
        setPane(1);
    }
    
    public void changeToMapPane() 
    {
        setPane(2);
    }
    
    public Integer getSelectedMinPrice() 
    {
        return selectedMinPrice;
    }
    
    public Integer getSelectedMaxPrice() 
    {
        return selectedMaxPrice;
    }
    
    public Scene getMainScene() 
    {
        return getScene();
    }
    
    public StatisticsViewer getStatisticsViewer() 
    {
        return statisticsViewer;
    }
    
    /**
     * 
     */
    public static void setStagePosititon(Stage openingStage, Stage closingStage) {
        double currentStagePositionX = closingStage.getX();
        double currentStagePositionY = closingStage.getY();
        openingStage.setX(currentStagePositionX);
        openingStage.setY(currentStagePositionY);
    }
}
