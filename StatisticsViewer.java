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
    private StatisticsData data = new StatisticsData(); 
    
    // The information labels in the window
    Label reviewInfo = new Label("default");
    Label availableInfo = new Label("default");
    Label noHomeAndApartmentsInfo = new Label("default");
    Label expensiveInfo = new Label("default");
    Label priceSDInfo = new Label("default");

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
        VBox priceSD = new VBox(); 
          
        
        // The "title" labels in the window
        Label title = new Label("Statistics");
        Label reviewTitle = new Label("Average Reviews Per Property:");
        Label availableTitle = new Label("Total Available Properties:");
        Label noHomeAndApartmentsTitle = new Label("Entire Home and Apartments:");
        Label expensiveTitle = new Label("Most Expensive Borough:");
        Label priceSDTitle = new Label("Standard Deviation of Price (£):");
        
        
        // Adding components 
        window.getChildren().add(title); 
        window.getChildren().add(statsGrid); 
        
        statsGrid.add(reviews, 0, 0);
        statsGrid.add(available, 0, 1);
        statsGrid.add(noHomeAndApartments, 1, 0);
        statsGrid.add(expensive, 1, 1);
        statsGrid.add(priceSD, 0, 2); 
        
        
        reviews.getChildren().add(reviewTitle); 
        reviews.getChildren().add(reviewInfo);
        
        available.getChildren().add(availableTitle); 
        available.getChildren().add(availableInfo);
        
        noHomeAndApartments.getChildren().add(noHomeAndApartmentsTitle); 
        noHomeAndApartments.getChildren().add(noHomeAndApartmentsInfo);
        
        expensive.getChildren().add(expensiveTitle); 
        expensive.getChildren().add(expensiveInfo);
        
        priceSD.getChildren().add(priceSDTitle); 
        priceSD.getChildren().add(priceSDInfo);
        
        //Set the scene and add CSS
        Scene scene = new Scene(window, 600,300);
        
        scene.getStylesheets().add("stylesheet.css");
        statsGrid.setId("statsgrid"); 
        reviews.getStyleClass().add("statsvbox"); 
        available.getStyleClass().add("statsvbox");
        noHomeAndApartments.getStyleClass().add("statsvbox");
        expensive.getStyleClass().add("statsvbox");
        priceSD.getStyleClass().add("statsvbox");
        window.getStyleClass().add("statsvbox");
        
        reviewTitle.getStyleClass().add("statslabels"); 
        availableTitle.getStyleClass().add("statslabels"); 
        noHomeAndApartmentsTitle.getStyleClass().add("statslabels"); 
        expensiveTitle.getStyleClass().add("statslabels"); 
        priceSDTitle.getStyleClass().add("statslabels");
        
        reviewInfo.getStyleClass().add("statslabels"); 
        availableInfo.getStyleClass().add("statslabels"); 
        noHomeAndApartmentsInfo.getStyleClass().add("statslabels"); 
        expensiveInfo.getStyleClass().add("statslabels"); 
        priceSDInfo.getStyleClass().add("statslabels"); 
        
        title.getStyleClass().add("titlelabel"); 
        
        setInfo();
        
        setTitle("Information");
        setScene(scene);
        
    }
    
    /**
     * Update all of the info labels
     */
    private void setInfo()
    {
        setReviewInfo(); 
        setNoHomeAndApartmentsInfo();
        setAvailableInfo();
        getExpensiveInfo();
        getPriceSDInfo();
    }
    
    /**
     * Get the review info data, round it to 2 d.p and set the label to the data
     */
    private void setReviewInfo()
    {
        String x = "" + String.format("%.2f", data.getAverageNoReviews()) + " (2 d.p)"; 
        reviewInfo.setText(x);
    }
    
    /**
     * Get the number of homes and apartments properties and set the info label to the value received
     */
    private void setNoHomeAndApartmentsInfo()
    {
        String x = "" + data.getNoHomeAndApartments();
        noHomeAndApartmentsInfo.setText(x); 
    }
    
    /**
     * Get the number of available properties and set the available info label to the value received
     */
    private void setAvailableInfo()
    {
        String x = "" + data.getAvailableInfo();
        availableInfo.setText(x); 
    }
    
    /**
     * Get the most expensive borough and set the expensive info label to the value received
     */
    private void getExpensiveInfo()
    {
        String x = "" + data.getExpensiveInfo();
        expensiveInfo.setText(x); 
    }
    
    /**
     * Get the standard deviation of price and set the pricesd info label to the value received
     */
    private void getPriceSDInfo()
    {
        String x = "" + String.format("%.2f", data.getPriceSDInfo()) + " (2 d.p)";
        priceSDInfo.setText(x); 
    }
}
