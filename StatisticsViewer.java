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

/**
 * Write a description of class StatisticsViewer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StatisticsViewer extends Stage
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class StatisticsViewer
     */
    public StatisticsViewer()
    {
        // The layout of the window
        VBox window = new VBox();
        GridPane statsGrid = new GridPane(); 
        VBox reviews = new VBox();
        VBox available = new VBox(); 
        VBox noHomeAndAppartments = new VBox();
        VBox expensive = new VBox(); 
          
        
        // The "title" labels in the window
        Label title = new Label("Statistics");
        Label reviewTitle = new Label("Average Reviews Per Property:");
        Label availableTitle = new Label("Total Available Properties:");
        Label noHomeAndAppartmentsTitle = new Label("Entire Home and Apartments:");
        Label expensiveTitle = new Label("Most Expensive Borough:");
        
        // The information labels in the window
        Label reviewInfo = new Label("default");
        Label availableInfo = new Label("default");
        Label noHomeAndAppartmentsInfo = new Label("default");
        Label expensiveInfo = new Label("default");
        
        // Adding components 
        window.getChildren().add(title); 
        window.getChildren().add(statsGrid); 
        
        statsGrid.add(reviews, 0, 0);
        statsGrid.add(available, 0, 1);
        statsGrid.add(noHomeAndAppartments, 1, 0);
        statsGrid.add(expensive, 1, 1);
        
        
        reviews.getChildren().add(reviewTitle); 
        reviews.getChildren().add(reviewInfo);
        
        available.getChildren().add(availableTitle); 
        available.getChildren().add(availableInfo);
        
        noHomeAndAppartments.getChildren().add(noHomeAndAppartmentsTitle); 
        noHomeAndAppartments.getChildren().add(noHomeAndAppartmentsInfo);
        
        expensive.getChildren().add(expensiveTitle); 
        expensive.getChildren().add(expensiveInfo);
        
        //initStyle(StageStyle.DECORATED);
        Scene scene = new Scene(window, 300,100);
        scene.getStylesheets().add("stylesheet.css");
        setTitle("Statistics");
        setScene(scene);
        
    }
}
