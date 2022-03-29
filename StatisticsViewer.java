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
import java.util.Map;
import java.util.HashMap;

/**
 * Makes the Statistics Window and displays statistics based on the price selected
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 25/03/22
 */
public class StatisticsViewer extends Stage
{
    // instance variables - replace the example below with your own
    
    // The information labels in the window
    private NoOfPropertiesStats noOfPropertiesStats;
    StatBox stat1 = new StatBox();
    StatBox stat2 = new StatBox();
    StatBox stat3 = new StatBox();
    StatBox stat4 = new StatBox();
    
    VBox window;

    /**
     * Constructor for objects of class StatisticsViewer
     */
    public StatisticsViewer(int selectedMinPrice, int selectedMaxPrice)
    {
        constructScene(selectedMinPrice, selectedMaxPrice);
    }
    
    /**
     * Constructs the scene
     * @param int selectedMinPrice - The current minimum price selected by the user. int selectedMaxPrice - The current maximum price selected by the user.
     */
    private void constructScene(int selectedMinPrice, int selectedMaxPrice)  {
        // The layout of the window
        noOfPropertiesStats = new NoOfPropertiesStats(selectedMinPrice, selectedMaxPrice);
        
        
        StatisticsData.setListingsAtPrice(selectedMinPrice, selectedMaxPrice);
        window = new VBox();
        GridPane statsGrid = new GridPane();
        
        // Create the boxes that hold the statistics
        StatBox stat1 = new StatBox();
        StatBox stat2 = new StatBox();
        StatBox stat3 = new StatBox();
        StatBox stat4 = new StatBox();
        
        // Add stats to the first box
        stat1.addInfo("Average Reviews Per Property:", formatData(StatisticsData.getAverageNoReviews(true)));
        stat1.addInfo("Total Available Properties:", formatData(StatisticsData.getAvailableInfo(true)));
        stat1.setFirst();
        
        // Add stats to the second box
        stat2.addInfo("Entire Home and Apartments:", formatData(StatisticsData.getNoHomeAndApartments(true)));
        stat2.addInfo("Most Expensive Borough\non Average:", formatData(StatisticsData.getExpensiveInfo(true, selectedMinPrice, selectedMaxPrice)));
        stat2.setFirst();
        
        // Add stats to the third box
        stat3.addInfo("Standard Deviation of Price (£):", formatData(StatisticsData.getPriceSDInfo(true)));
        stat3.addInfo("Borough with the Highest \nAverage Amount of Reviews:", formatData(StatisticsData.getHighAvgReview(true, selectedMinPrice, selectedMaxPrice)));
        stat3.setFirst();
        
        // Add stats to the fourth box
        stat4.addInfo("No. of Properties in Borough\nMinimum:", String.valueOf(noOfPropertiesStats.getMinNoOfPropertiesInBorough()));
        stat4.addInfo("No. of Properties in Borough\nLower Quartile:", String.valueOf(noOfPropertiesStats.getFirstQuartile()));
        stat4.addInfo("No. of Properties in Borough\nMedian:", String.valueOf(noOfPropertiesStats.getMedian()));
        stat4.addInfo("No. of Properties in Borough\nUpper Quartile:", String.valueOf(noOfPropertiesStats.getThirdQuartile()));
        stat4.addInfo("No. of Properties in Borough\nMaximum:", String.valueOf(noOfPropertiesStats.getMaxNoOfPropertiesInBorough()));
        stat4.setFirst();
        
        // The "title" labels in the window
        Label title = new Label("Statistics Based on Price Selected");
        Label reviewTitle = new Label("Average Reviews Per Property:");
        Label availableTitle = new Label("Total Available Properties:");
        Label noHomeAndApartmentsTitle = new Label("Entire Home and Apartments:");
        Label expensiveTitle = new Label("Most Expensive Borough:");
        Label priceSDTitle = new Label("Standard Deviation of Price (£):");
        Label highAvgReviewTitle = new Label("Borough with the Highest \nAverage Amount of Reviews:");

        // Adding components 
        window.setAlignment(Pos.CENTER);
        window.getChildren().add(title); 
        window.getChildren().add(statsGrid); 
        title.setAlignment(Pos.CENTER);
        statsGrid.setAlignment(Pos.CENTER); 
    
        statsGrid.add(stat1, 0, 0);
        statsGrid.add(stat2, 0, 1);
        statsGrid.add(stat3, 1, 0);
        statsGrid.add(stat4, 1, 1);

        // Set the scene and add CSS
        Scene scene = new Scene(window, 900,350);
        scene.getStylesheets().add("stylesheet.css");
        window.getStyleClass().add("mainRoot");
        statsGrid.setId("statsgrid"); 
        title.getStyleClass().add("windowTitle");
        
        // Set the title and set the scene
        setTitle("Information");
        setScene(scene);
    }
    
    /**
     * Convert a double data type to String then set it as a Label's text
     * @param Label label The label which will be affected.
     * @param double dataToFormat The data to format into a String.
     */
    private String formatData(double dataToFormat) 
    {
        String formatedData = String.format("%.2f", dataToFormat) + " (2 d.p)"; 
        return formatedData;
    }

    /**
     * Convert an int data type to String then set it as a Label's text
     * @param Label label The label which will be affected.
     * @param int dataToFormat The data to format into a String
     */
    private String formatData(int dataToFormat) 
    {
        String formatedData = String.valueOf(dataToFormat);
        return formatedData;
    }

    /**
     * Set a Label's text to a passed through String
     * @param Label label The label which will be affected.
     * @param String dataToFormat The String that the Label's text will be set to.
     */
    private String formatData(String dataToFormat) 
    {
        return dataToFormat;
    }

    /**
     * Update the Info labels in the different stat boxes
     */
    public void updateInfo()
    {
        stat1.updateInfo("Average Reviews Per Property:", formatData(StatisticsData.getAverageNoReviews(true)));
        stat1.updateInfo("Total Available Properties:", formatData(StatisticsData.getAvailableInfo(true)));
        stat2.updateInfo("Entire Home and Apartments:", formatData(StatisticsData.getNoHomeAndApartments(true)));
        stat3.updateInfo("Standard Deviation of Price (£):", formatData(StatisticsData.getPriceSDInfo(true)));
    }
    
    /**
     * Recreate the scene with the updated statistics data 
     */
    public void update(int selectedMinPrice, int selectedMaxPrice) 
    {
        noOfPropertiesStats = new NoOfPropertiesStats(selectedMinPrice, selectedMaxPrice);
        //updateInfo();
        constructScene(selectedMinPrice, selectedMaxPrice);
    }
    
    /**
     * @return int the curret minimum price
     */
    public int getCurrentMinPrice() 
    {
        return noOfPropertiesStats.getMinPrice();
    }
    
    /**
     * @return int the current maximum price
     */
    public int getCurrentMaxPrice() 
    {
        return noOfPropertiesStats.getMaxPrice();
    }
}
