import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * Write a description of class PriceSelectorPane here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PriceSelectorPane extends MainViewerPane
{
    private static Pane priceSelectorPane;
    Label statusLabel;
    
    public PriceSelectorPane(MainViewerNEW mainViewer)
    {
        super(mainViewer);
    }
    
    public void makePane() {
        // setTitle("Price Selection Screen");
        
        //All labels in the window
        Label title = new Label("Price Selection!");
        Label instruction = new Label("Please select a min and max for your price range: ");
        
        HBox minMaxBox = mainViewer.createMinMaxBox();
        minMaxBox.setSpacing(5);
        
        Button confirm = (Button) minMaxBox.getChildren().get(2);
        
        ComboBox<String> minBox = (ComboBox<String>) minMaxBox.getChildren().get(0);
        ComboBox<String> maxBox = (ComboBox<String>) minMaxBox.getChildren().get(1);
        
        statusLabel = new Label(mainViewer.showStatus(confirm));
        
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
        
        title.getStyleClass().add("welcomeTitle");
        
        instruction.getStyleClass().add("priceInstruction");
        
        titleAndInstruction.getStyleClass().add("priceTitleAndTitle");
        
        confirm.getStyleClass().add("priceConfirm");
        
        minBox.getStyleClass().add("priceMinMaxBoxes");
        maxBox.getStyleClass().add("priceMinMaxBoxes");
        
        minMaxBox.getStyleClass().add("priceMinMaxBox");
        
        statusLabel.getStyleClass().add("priceStatusLabel");
    }
    
    public Pane getPane() {
        return priceSelectorPane;
    }
}