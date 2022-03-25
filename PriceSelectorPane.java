import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * Write a description of class PriceSelectorPane here.
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 25/03/22
 */
public class PriceSelectorPane extends MainViewerPane
{
    private static Pane priceSelectorPane;
    
    public PriceSelectorPane(MainViewer mainViewer)
    {
        super(mainViewer);
        titleName = "Price Selector";
        hasMinMaxBox = false;
    }
    
    public void makePane() {
        //All labels in the window
        Label title = new Label("Price Selection!");
        Label instruction = new Label("Please select a min and max for your price range: ");
        
        //min max price selction boxes and confirm button
        HBox minMaxBox = mainViewer.createMinMaxBox();
        minMaxBox.setSpacing(5);
        
        Button confirm = (Button) minMaxBox.getChildren().get(2);
        
        ComboBox<String> minBox = (ComboBox<String>) minMaxBox.getChildren().get(0);
        ComboBox<String> maxBox = (ComboBox<String>) minMaxBox.getChildren().get(1);
        
        //Layout of the window
        BorderPane window = new BorderPane(); //root of the window
        VBox titleAndInstruction = new VBox();
        
        //Adding elements to the window
        window.setCenter(minMaxBox);
        window.setTop(titleAndInstruction);

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
    }
    
    public Pane getPane() {
        return priceSelectorPane;
    }
}
