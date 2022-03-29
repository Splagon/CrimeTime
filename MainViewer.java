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
 * The MainViewer is one of three major windows of the application. The MainViewer
 * contains the main panels involved in the project and is first window to be shown
 * to the user. Consequently, it is also used to access the other windows.
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 28/03/22
 */
public class MainViewer extends Stage
{
    // all of the panes visible in the main viewer window
    private MainViewerPane welcomePane = new WelcomePane(this);
    private MainViewerPane priceSelectorPane = new PriceSelectorPane(this);
    private MainViewerPane mapPane = new MapPane(this);
    private MainViewerPane statsPane = new StatsPane(this);
    private MainViewerPane bookingsPane = new BookingsPane(this);
    
    // set width and height of the window
    private static final int SCENE_WIDTH = 1250;
    private static final int SCENE_HEIGHT = 770;
    
    // the user-selected min and max price
    private Integer selectedMinPrice;
    private Integer selectedMaxPrice;
    
    // lowest and highest price of all properties
    private int lowestPrice;
    private int highestPrice;
    
    // the pane containing the pane switcher buttons
    private AnchorPane paneSwitcherPane;
    
    // buttons to switch to the next Pane
    private Button prevPaneButton;
    private Button nextPaneButton;
    private final String prevButtonPreFix = "<-- ";
    private final String nextButtonPostFix = " -->";
    
    // order of the panes in the main viewer
    private MainViewerPane[] paneOrder = { welcomePane, priceSelectorPane, mapPane, statsPane, bookingsPane };

    // index of the current pane being shown
    private int currentPaneIndex;
    
    // root of the window
    private BorderPane root = new BorderPane();
    
    private Scene mainScene;
    
    /**
     * Initialises and displays the MainViewer
     */
    public MainViewer()
    {   
        StatisticsData.initialiseHandler();
        
        lowestPrice = StatisticsData.getLowestPrice();
        highestPrice = StatisticsData.getHighestPrice();
        
        root = new BorderPane();
        
        makePaneSwitcherPane();
        
        root.setMinSize(SCENE_WIDTH, SCENE_HEIGHT);

        mainScene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        
        this.setMinWidth(SCENE_WIDTH);
        this.setMinHeight(SCENE_HEIGHT);
        
        //sets the intital pane to the welcome Pane
        setPane(0);
        
        mainScene.getStylesheets().add("stylesheet.css");
        root.getStyleClass().add("mainRoot");
    }

    /**
     * Initialises the buttons at the bottom corners of the window which are used
     * to navigate between the different screens.
     */
    private void makePaneSwitcherPane() 
    {
        paneSwitcherPane = new AnchorPane();
        
        //pane switcher buttons with intial labels and their intial state
        prevPaneButton = new Button(prevButtonPreFix + bookingsPane.getTitleName());
        prevPaneButton.setPrefSize(155, 20);
        nextPaneButton = new Button(priceSelectorPane.getTitleName() + nextButtonPostFix);
        nextPaneButton.setPrefSize(155, 20);
        
        //styling for the buttons
        prevPaneButton.getStyleClass().add("smallWindowButtons");
        nextPaneButton.getStyleClass().add("smallWindowButtons");
        
        //switches panel once the button is clicked
        prevPaneButton.setOnAction(e -> goToPrevPane());
        nextPaneButton.setOnAction(e -> goToNextPane());
        
        AnchorPane.setTopAnchor(prevPaneButton, 5.0);
        AnchorPane.setLeftAnchor(prevPaneButton, 0.0);
        AnchorPane.setTopAnchor(nextPaneButton, 5.0);
        AnchorPane.setRightAnchor(nextPaneButton, 0.0);
        
        paneSwitcherPane.getChildren().add(prevPaneButton);
        paneSwitcherPane.getChildren().add(nextPaneButton);
        
        root.setBottom(paneSwitcherPane);
    }
    
    /**
     * Used by the previous panel switcher button to switch to the next panel.
     */
    private void goToPrevPane() 
    {   
        //decreases the current pane index
        currentPaneIndex = decreaseIndex(currentPaneIndex, Arrays.asList(paneOrder));
        
        updateButtonText();
        
        setPane(currentPaneIndex);
    }
    
    /**
     * Used by the previous panel switcher button to switch to the next panel.
     */
    private void goToNextPane() 
    {  
        //increases the current pane index
        currentPaneIndex = increaseIndex(currentPaneIndex, Arrays.asList(paneOrder));
        
        updateButtonText();
        
        setPane(currentPaneIndex);
    }
    
    /**
     * Decreases the index and applies appropriate wrap around according to the
     * list passed in.
     * 
     * @param index The current index of the list.
     * @param list The list that the index represents.
     * 
     * @return The new decremented index number with appropriate wrap around applied.
     */
    private int decreaseIndex(int index, List list)
    {
        index--;
        
        //allows for wrap around
        if (index < 0) 
        {
            index = list.size() - 1;
        }
        
        return index;
    }
    
    /**
     * Increases the index and applies appropriate wrap around according to the
     * list passed in.
     * 
     * @param index The current index of the list.
     * @param list The list that the index represents.
     * 
     * @return The new incremented index number with appropriate wrap around applied.
     */
    private int increaseIndex(int index, List list)
    {
        index++;
        
        //allows for wrap around
        if (index >= list.size()) 
        {
            index = 0;
        }
        
        return index;
    }
    
    /**
     * Updates the text of the next and previous pane buttons
     */
    private void updateButtonText() 
    {
        String nameOfPaneToChangeTo = paneOrder[currentPaneIndex].getClass().toString();
        nameOfPaneToChangeTo = nameOfPaneToChangeTo.substring(6); //removes "Class " from the begging of the String created
        
        int prevSceneIndex = decreaseIndex(currentPaneIndex, Arrays.asList(paneOrder));
        int nextSceneIndex = increaseIndex(currentPaneIndex, Arrays.asList(paneOrder));
        
        MainViewerPane prevPane = paneOrder[prevSceneIndex];
        MainViewerPane nextPane = paneOrder[nextSceneIndex];
        
        //sets the text of the next and previous buttons according to the next pane
        prevPaneButton.setText(prevButtonPreFix + prevPane.getTitleName());
        nextPaneButton.setText(nextPane.getTitleName() + nextButtonPostFix);
    }
    
    /**
     * Sets the pane according to the index passed in.
     * 
     * @param newSceneIndex The index of the pane to set. 
     */
    public void setPane(int newSceneIndex) 
    {
        currentPaneIndex = newSceneIndex;
        
        MainViewerPane paneToChangeTo = paneOrder[currentPaneIndex];
        
        paneToChangeTo.makePane();
        updateButtonText();
        setButtonsDisabled();
 
        Animations.fadeIn(paneToChangeTo.getPane(), 1000);
        
        root.setCenter(paneToChangeTo.getPane());
        
        addTopMinMaxBox(paneToChangeTo);
        
        setTitle(paneToChangeTo.getTitleName());
        
        setScene(mainScene);
    }
    
    /**
     * Refreshes the pane by rebuilding the scene.
     */
    public void refreshPane() 
    {
        setPane(currentPaneIndex);
    }
    
    /**
     * Adds a minimum and maximum price selector combo box to the top right of
     * the window if the pane requires it to.
     * 
     * @param paneToChangeTo The pane that will be displayed
     */
    private void addTopMinMaxBox(MainViewerPane paneToChangeTo) 
    {
        //checks whether the pane should have a min max combobox
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
        //adds an empty pane if there is no combobox.
        else
        {
            AnchorPane emptyPane = new AnchorPane();
            root.setTop(emptyPane);
        }
    }
    
    /**
     * Checks whether the panel switcher buttons should be disabled.
     */
    private void setButtonsDisabled() 
    {
        int nextPaneIndex = increaseIndex(currentPaneIndex, Arrays.asList(paneOrder));
        int prevPaneIndex = decreaseIndex(currentPaneIndex, Arrays.asList(paneOrder));
        
        if (selectedMinPrice == null && selectedMaxPrice == null) 
        {    
            buttonDisablerForMapPane(prevPaneButton, prevPaneIndex);
            buttonDisablerForMapPane(nextPaneButton, nextPaneIndex);
            if (currentPaneIndex == 0)
            {
                //checks to see if the start button has been preesed and sets the state of pane switcher buttons accordingly
                WelcomePane pane = (WelcomePane) paneOrder[currentPaneIndex]; //needs to be cast in order to use the method of a WelcomePane
                prevPaneButton.setDisable(!pane.hasStartBeenPressed());
                nextPaneButton.setDisable(!pane.hasStartBeenPressed());
            }
        }
        else
        {
            if (currentPaneIndex == 0)
            {
                //checks to see if the start button has been preesed and sets the state of pane switcher buttons accordingly
                WelcomePane pane = (WelcomePane) paneOrder[currentPaneIndex]; //needs to be cast in order to use the method of a WelcomePane
                prevPaneButton.setDisable(!pane.hasStartBeenPressed());
                nextPaneButton.setDisable(!pane.hasStartBeenPressed());
            }
            else
            {
                prevPaneButton.setDisable(false);
                nextPaneButton.setDisable(false);
            }
        }
    }
    
    /**
     * If the selected min and max price is not chosen, the next and previous 
     * pane buttons are disabled if the next or previous pane are the map
     * respectively.
     * 
     * @param button The button to disable.
     * @param index The current index of paneOrder.
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
    public HBox createMinMaxBox() 
    {
        HBox minMaxBox = new HBox();
        
        ComboBox<String> minBox = new ComboBox<String>();
        ComboBox<String> maxBox = new ComboBox<String>();
        minBox.setValue("Min Price:");
        maxBox.setValue("Max Price:");
        
        //adds the options to the price selection box
        ArrayList<String> options = getPriceSelectionOptions(lowestPrice, highestPrice);
        
        minBox.getItems().add("No Min");
        minBox.getItems().addAll(options);
        
        maxBox.getItems().addAll(options);
        maxBox.getItems().add("No Max");
        
        Button confirm = new Button("Confirm");
            confirm.setDisable(true);
            confirm.setOnAction(e -> confirmButtonAction());
        
        //Sets the minimum and maximum price respectively when they have been selected
        minBox.setOnAction(e -> getSelectionOfUser(minBox, confirm, true));
        maxBox.setOnAction(e -> getSelectionOfUser(maxBox, confirm, false));
        
        minMaxBox.getChildren().addAll(minBox, maxBox, confirm);
        
        //Initially sets to the user's selection if one has been made.
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
                    alert.setContentText("Unfortunately, your min price is not less than your max price! In order to use the confirm button and use view properties you must have a valid min and max price.");
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
    
    /**
     * once called will display the price selection pane
     */
    public void changeToPriceSelectorPane() 
    {
        //changes the boolean within WelcomePane to show that start has now been pressed
        WelcomePane pane = (WelcomePane) paneOrder[currentPaneIndex]; //needs to be cast in order to use the method of a WelcomePane
        pane.startPressed();
        setPane(1);
    }
    
    /**
     * once called will display the map pane
     */
    public void changeToMapPane() 
    {
        setPane(2);
    }
    
    /**
     * will return the current selected value of the min combo box
     * @return if nothing is currently selected it will then return null
     */
    public Integer getSelectedMinPrice() 
    {
        return selectedMinPrice;
    }
    
    /**
     * will return the current selected value of the max combo box
     * @return if nothing is currently selected it will then return null
     */
    public Integer getSelectedMaxPrice() 
    {
        return selectedMaxPrice;
    }
    
    /**
     * will return the current scene
     */
    public Scene getMainScene() 
    {
        return getScene();
    }
    
    /**
     * Used to set the position of the new stage to be open to be at the same
     * position as the parent stage is/was at.
     * 
     * @param openingStage The stage to open.
     * @param parentStage The stage which is opening the new stage.
     * 
     * @return The new stage with the appropriate positioning.
     */
    public static Stage setStagePosititon(Stage openingStage, Stage parentStage) 
    {
        double currentStagePositionX = parentStage.getX();
        double currentStagePositionY = parentStage.getY();
        
        openingStage.setX(currentStagePositionX);
        openingStage.setY(currentStagePositionY);
        
        return openingStage;
    }
}
