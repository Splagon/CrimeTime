import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Pos;

/**
 * Write a description of class WelcomePane here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class WelcomePane extends MainViewerPane
{
    private static Pane welcomePane;
    
    public WelcomePane(MainViewer mainViewer)
    {
        super(mainViewer);
        titleName = "Welcome";
    }
    
    public void makePane() {
        //All labels in the window
        Label title = new Label("Welcome!");
        Label instructionsTitle = new Label("Instructions: ");
        Label instructions1 = new Label("- When you are ready click start, this will send you to the next window where you will be able to enter your price range.");
        Label instructions2 = new Label("- Once your price range has been selected you will then be able to view the map and see where the you be able to find a property. ");
        Label instructions3 = new Label("- In order to view the map you will need to click the confirm button once both your min and max price have been slected.");
        
        //Buttons in the window
        Button startButton = new Button("Start"); 
        startButton.setOnAction(e -> mainViewer.changeToPriceSelectorPane());
        
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
        window.getStylesheets().add("stylesheet.css");
        
        title.getStyleClass().add("windowTitle");
        
        instructions.getStyleClass().add("instructionsTitle"); 
        
        instructions1.getStyleClass().add("instructions"); 
        instructions2.getStyleClass().add("instructions"); 
        instructions3.getStyleClass().add("instructions");
        
        instrcutionsAndStart.getStyleClass().add("instructionsAndStart");
        
        startButton.getStyleClass().add("startButton");
        
        welcomePane = window;
    }
    
    public Pane getPane() {
        return welcomePane;
    }
}
