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
        VBox noHomeAndApartments = new VBox();
        VBox expensive = new VBox(); 
          
        
        // The "title" labels in the window
        Label title = new Label("Statistics");
        Label reviewTitle = new Label("Average Reviews Per Property:");
        Label availableTitle = new Label("Total Available Properties:");
        Label noHomeAndApartmentsTitle = new Label("Entire Home and Apartments:");
        Label expensiveTitle = new Label("Most Expensive Borough:");
        
        // The information labels in the window
        Label reviewInfo = new Label("default");
        Label availableInfo = new Label("default");
        Label noHomeAndApartmentsInfo = new Label("default");
        Label expensiveInfo = new Label("default");
        
        // Adding components 
        window.getChildren().add(title); 
        window.getChildren().add(statsGrid); 
        
        statsGrid.add(reviews, 0, 0);
        statsGrid.add(available, 0, 1);
        statsGrid.add(noHomeAndApartments, 1, 0);
        statsGrid.add(expensive, 1, 1);
        
        
        reviews.getChildren().add(reviewTitle); 
        reviews.getChildren().add(reviewInfo);
        
        available.getChildren().add(availableTitle); 
        available.getChildren().add(availableInfo);
        
        noHomeAndApartments.getChildren().add(noHomeAndApartmentsTitle); 
        noHomeAndApartments.getChildren().add(noHomeAndApartmentsInfo);
        
        expensive.getChildren().add(expensiveTitle); 
        expensive.getChildren().add(expensiveInfo);
        
        //Set the scene and add CSS
        Scene scene = new Scene(window, 500,300);
        
        scene.getStylesheets().add("stylesheet.css");
        statsGrid.setId("statsgrid"); 
        reviews.getStyleClass().add("statsvbox"); 
        available.getStyleClass().add("statsvbox");
        noHomeAndApartments.getStyleClass().add("statsvbox");
        expensive.getStyleClass().add("statsvbox");
        window.getStyleClass().add("statsvbox");
        
        reviewTitle.getStyleClass().add("statslabels"); 
        availableTitle.getStyleClass().add("statslabels"); 
        noHomeAndApartmentsTitle.getStyleClass().add("statslabels"); 
        expensiveTitle.getStyleClass().add("statslabels"); 
        reviewInfo.getStyleClass().add("statslabels"); 
        availableInfo.getStyleClass().add("statslabels"); 
        noHomeAndApartmentsInfo.getStyleClass().add("statslabels"); 
        expensiveInfo.getStyleClass().add("statslabels"); 
        
        title.getStyleClass().add("titlelabel"); 
        
        
        
        setTitle("Statistics");
        setScene(scene);
        
    }
}
