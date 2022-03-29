import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Pos;

/**
 * This creates the welcome pane
 * On the pane there will be a title, instrcutions and a start button
 * This pane will be placed into the main window and dispalyed to the user
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 25/03/22
 */
public class WelcomePane extends MainViewerPane
{
    private static Pane welcomePane;
    
    //used in the main viewer to disable the paneswitcher button until the start button has been pressed
    private boolean startHasBeenPressed = false;
    
    /**
     * Constructor for objects of class WelcomePane
     * 
     * @param MainViewer mainViewer - used to call methods within the MainViewer
     */
    public WelcomePane(MainViewer mainViewer)
    {
        super(mainViewer);
        titleName = "Welcome";
        hasMinMaxBox = false;
    }
    
    /**
     * This constructs the pane and its functionality and adds any styling
     */
    public void makePane() 
    {
        //All labels in the window
        Label title = new Label("Welcome!");
        Label instructionsTitle = new Label("Please read the following instructions: ");
        Label instruction1 = new Label("- The pane switcher buttons at the bottom of the window will be disabled until you have clicked the start button.");
        Label instruction2 = new Label("- Once you have clicked start you are then able to select your price range using the min max boxes in the middle of your sceen. ");
        Label instruction3 = new Label("- In order to view the map you will need to click the confirm button once both your min and max price have been slected.");
        Label instruction4 = new Label("- You will able to click on a borough on the map and view the properties available within your selected price range.");
        Label instruction5 = new Label("- The property viewer has a few different features: you can sort the list of poperties, book a property, move through the list and view some detials on each property");
        Label instruction6 = new Label("- You are able to book this property by clicking the book property button in the property viewer window.");
        Label instruction7 = new Label("- You can view all your booking in your bookings panel.");
        Label instruction8 = new Label("- If you want to view different more stats then in the map panel you can click the show more stats button.");
        Label instruction9 = new Label("- A pop up should appear where you are able to view different stats.");
        Label instruction10 = new Label("- You are able to move through the differnet panes by using the buttons in the bottom left and right corners.");
        Label instruction11 = new Label("- Once you are ready please click start.");
        
        //Buttons in the window
        Button startButton = new Button("Start");
            startButton.setOnMouseEntered(e -> Animations.spin(1000, startButton));
            startButton.setOnAction(e -> mainViewer.changeToPriceSelectorPane());
        
        //layout of the whole window
        VBox window = new VBox();
            VBox instructions = new VBox();
                instructions.setPrefSize(1000, 400);
                instructions.setSpacing(5);
                instructions.setAlignment(Pos.CENTER_LEFT);
                instructions.getChildren().addAll(instructionsTitle, instruction1, instruction2, instruction3, instruction4, 
                                            instruction5, instruction6, instruction7, instruction8, instruction9,
                                            instruction10, instruction11);
            
            BorderPane instrcutionsAndStart = new BorderPane();
                instrcutionsAndStart.setLeft(instructions);
                instrcutionsAndStart.setCenter(startButton);

            //adding elements to the window
            window.getChildren().addAll(title, instrcutionsAndStart);
            window.setAlignment(Pos.CENTER);
            
            //add the CSS styling
            window.getStylesheets().add("stylesheet.css");
            
            title.getStyleClass().add("windowTitle");
            
            instructionsTitle.getStyleClass().add("instructionsTitle"); 
            
            instruction1.getStyleClass().add("instructions"); 
            instruction2.getStyleClass().add("instructions"); 
            instruction3.getStyleClass().add("instructions"); 
            instruction4.getStyleClass().add("instructions"); 
            instruction5.getStyleClass().add("instructions"); 
            instruction6.getStyleClass().add("instructions"); 
            instruction7.getStyleClass().add("instructions"); 
            instruction8.getStyleClass().add("instructions");
            instruction9.getStyleClass().add("instructions");
            instruction10.getStyleClass().add("instructions");
            instruction11.getStyleClass().add("instructions");
            
            instrcutionsAndStart.getStyleClass().add("instructionsAndStart");
            
            startButton.getStyleClass().add("startButton");
    
        welcomePane = window;
    }
    
    /**
     * returns the pane
     * 
     * @return it will return type Pane
     */
    public Pane getPane() 
    {
        return welcomePane;
    }
    
    /**
     * will set that the value of startHasBeenPressed to true once it is called
     */
    public void startPressed()
    {
        startHasBeenPressed = true;
    }
    
    /**
     * returns the current value of startHasBeenPressed
     */
    public boolean hasStartBeenPressed()
    {
        return startHasBeenPressed;
    }
}
