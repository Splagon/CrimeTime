import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * This creates the price selection pane.
 * On the pane there will be a title, an instruction of what to do, two drop boxes where the user
 * can select their min and max price and a confirm button where the user can confirm their
 * selection and view the map in order to see which properties are available to them and where
 * they are.
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 25/03/22
 */
public class PriceSelectorPane extends MainViewerPane
{
    private static Pane priceSelectorPane;
    
    /**
     * Constructor for objects of class WelcomePane
     * 
     * @param MainViewer mainViewer - used to call methods within the MainViewer
     */
    public PriceSelectorPane(MainViewer mainViewer)
    {
        super(mainViewer);
        titleName = "Price Selector";
        hasMinMaxBox = false;
    }
    
    /**
     * This constructs the pane and its functionality and adds any styling
     */
    public void makePane() {
        //All labels in the window
        Label title = new Label("Price Selection!");
        Label instruction = new Label("Please select a min and max for your price range: ");
        
        //adds the min max box and the confirm button creeated in mainviewer
        HBox minMaxBox = mainViewer.createMinMaxBox();
            minMaxBox.setSpacing(5);
        
        Button confirm = (Button) minMaxBox.getChildren().get(2);
        
        ComboBox<String> minBox = (ComboBox<String>) minMaxBox.getChildren().get(0);
        ComboBox<String> maxBox = (ComboBox<String>) minMaxBox.getChildren().get(1);
        
        //Layout of the window
        BorderPane window = new BorderPane();
            VBox titleAndInstruction = new VBox();
            
            //Adding elements to the window
            window.setCenter(minMaxBox);
            window.setTop(titleAndInstruction);
    
            titleAndInstruction.getChildren().addAll(title, instruction);
            
            //Creating the scene and adding the css styling
            title.getStyleClass().add("windowTitle");
            
            instruction.getStyleClass().add("priceInstruction");
            
            titleAndInstruction.getStyleClass().add("priceTitleAndTitle");
            
            confirm.getStyleClass().add("priceConfirm");
            
            minBox.getStyleClass().add("priceMinMaxBoxes");
            maxBox.getStyleClass().add("priceMinMaxBoxes");
            
            minMaxBox.getStyleClass().add("priceMinMaxBox");
        
        priceSelectorPane = window;
    }
    
    /**
     * returns the pane
     * 
     * @return it will return type Pane
     */
    public Pane getPane() {
        return priceSelectorPane;
    }
}
