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
        statLabel.getStyleClass().add("statslabels");
        
        VBox content = new VBox();
        content.getChildren().addAll(titleLabel, statLabel);
        //content.setPadding(new Insets(20));
        //content.setMinSize(150, 150);
        content.setSpacing(10);
        content.setAlignment(Pos.CENTER);
        
        
        Button leftButton = new Button();
        leftButton.setText("<");
        leftButton.setOnAction(this::leftButtonAction);
        leftButton.setMinSize(10, 70);
        leftButton.setAlignment(Pos.CENTER);
        
        Button rightButton = new Button();
        rightButton.setText(">");
        rightButton.setOnAction(this::rightButtonAction);
        rightButton.setMinSize(10, 70);
        rightButton.setAlignment(Pos.CENTER);
        
        
        BorderPane pane = new BorderPane(); 
        pane.setCenter(content);
        pane.setLeft(leftButton);
        pane.setRight(rightButton); 
        pane.setMinSize(400, 100);
        pane.setPadding(new Insets(20));
        
        
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
