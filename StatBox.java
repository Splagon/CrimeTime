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

/**
 * Write a description of class StatBox here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StatBox extends Pane
{
    // instance variables - replace the example below with your own
    private Label titleLabel = new Label("default");
    private Label statLabel = new Label("default");
    private ArrayList<String> titleList; 
    private ArrayList<String> statList; 
    private int current = 0; 

    /**
     * Constructor for objects of class StatBox
     */
    public StatBox()
    {
        titleList = new ArrayList<>();
        statList = new ArrayList<>();
        
        
        
        VBox content = new VBox();
        content.getChildren().addAll(titleLabel, statLabel);
        
        
        Button leftButton = new Button();
        leftButton.setText("<");
        leftButton.setOnAction(this::leftButtonAction);
        
        Button rightButton = new Button();
        rightButton.setText(">");
        rightButton.setOnAction(this::rightButtonAction);
        
        
        BorderPane pane = new BorderPane(); 
        pane.setCenter(content);
        pane.setLeft(leftButton);
        pane.setRight(rightButton); 
        
        
        this.getChildren().add(pane);
    }
    
    private void setLabels(int index)
    {
        if(titleList.size() > 0 && (index >= 0 && index < titleList.size()))
        {
            titleLabel.setText(titleList.get(index));
            statLabel.setText(statList.get(index));
        }
    }
    
    public void setFirst()
    {
        current = 0;
        setLabels(current); 
    }
    
    public void setLast()
    {
        current = titleList.size() - 1; 
        setLabels(current); 
    }
    
    public void addInfo(String title, String stat)
    {
        titleList.add(title); 
        statList.add(stat); 
    }
    
    public void leftButtonAction(ActionEvent event)
    {
        if(current - 1 < 0)
        {
            setLast();
            current = titleList.size() - 1;
        }
        else
        {
            current -= 1;
            setLabels(current); 
        }
    }
    
    public void rightButtonAction(ActionEvent event)
    {
        if(current + 1 >= titleList.size())
        {
            setFirst();
            current = 0; 
        }
        else
        {
            current += 1; 
            setLabels(current);
        }
    }
    
    
}
