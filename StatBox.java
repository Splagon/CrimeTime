import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import javafx.scene.text.TextAlignment;
import javafx.geometry.*;
import javafx.scene.layout.BorderPane;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import java.awt.Font;

/**
 * Create a border pane which will house a list of statistic titles and information in a list. The next or previous stat
 * can be displayed through the use of the right and left buttons. 
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 25/03/22
 */
public class StatBox extends Pane
{
    // list of all titles and stats within the box respectively
    private Label titleLabel = new Label();
    private Label statLabel = new Label();
    
    // the current stat and its title
    private ArrayList<String> titleList; 
    private ArrayList<String> statList; 
    
    // index of the stat.
    private int index = 0; 

    /**
     * Constructor for objects of class StatBox
     */
    public StatBox()
    {
        titleList = new ArrayList<>();
        statList = new ArrayList<>();
        
        statLabel.getStyleClass().add("statslabels");
        
        //Create a VBox and add the title label and stat label to it
        VBox content = new VBox();
            content.getChildren().addAll(titleLabel, statLabel);
            content.setSpacing(10);
            content.setAlignment(Pos.CENTER);
        
        //Create the left button
        Button leftButton = new Button();
            leftButton.setText("<");
            leftButton.setOnAction(e -> leftButtonAction());
            leftButton.setMinSize(10, 90);
            leftButton.setAlignment(Pos.CENTER);
            leftButton.getStyleClass().add("smallWindowButtons");
        
        //Create the right button
        Button rightButton = new Button();
            rightButton.setText(">");
            rightButton.setOnAction(e -> rightButtonAction());
            rightButton.setMinSize(10, 90);
            rightButton.setAlignment(Pos.CENTER);
            rightButton.getStyleClass().add("smallWindowButtons");
        
        //Create the border pane and add elements to it
        BorderPane pane = new BorderPane(); 
            pane.setCenter(content);
            pane.setLeft(leftButton);
            pane.setRight(rightButton); 
            pane.setMinSize(400, 140);
            pane.setPadding(new Insets(20));
        
        this.getChildren().add(pane);
    }
    
    /**
     * Set the stat label and title label to the String at index of the stat list and title list respectively
     * 
     * @index The index to set the labels to.
     */
    private void setLabels(int index)
    {
        if(titleList.size() > 0 && (index >= 0 && index < titleList.size()))
        {
            titleLabel.setText(titleList.get(index));
            statLabel.setText(statList.get(index));
        }
    }
    
    /**
     * Set the first element in both title list and stat list to be displayed
     */
    public void setFirst()
    {
        index = 0;
        setLabels(index); 
    }
    
    /**
     * Set the last element in both title list and stat list to be displayed
     */
    public void setLast()
    {
        index = titleList.size() - 1; 
        setLabels(index); 
    }
    
    /**
     * Add an element to the titleList and statList
     */
    public void addInfo(String title, String stat)
    {
        titleList.add(title); 
        statList.add(stat); 
    }
    
    /**
     * Action for the left button which will go the element at index current - 1 in both
     * title list and stat list. If it reaches the start of both of the lists, set the labels to
     * the last element in the lists. 
     */
    private void leftButtonAction()
    {
        if(index - 1 < 0)
        {
            setLast();
            index = titleList.size() - 1;
        }
        else
        {
            index -= 1;
            setLabels(index); 
        }
    }
    
    /**
     * Action for the right button which will go the element at index current + 1 in both
     * title list and stat list. If it reaches the end of both of the lists, set the labels to
     * the first element in the lists.
     */
    private void rightButtonAction()
    {
        if(index + 1 >= titleList.size())
        {
            setFirst();
            index = 0; 
        }
        else
        {
            index += 1; 
            setLabels(index);
        }
    }
    
    /**
     * Update a particular statistic's information label which displays the data of that statistic
     * 
     * @param String title The title of the stat which data will be updated
     * @param String newStat The updated data as a String
     */
    public void updateInfo(String title, String newStat)
    {
        for(int i=0;i < titleList.size(); i++)
        {
            if(titleList.get(i).equals(title))
            {
                statList.set(i, newStat);
            }
        }
    }
    
}
