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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * Write a description of class StatisticsViewer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StatisticsViewer extends Stage
{
    // instance variables - replace the example below with your own
    
    // The information labels in the window
    private XYChart.Series averagePriceData = new XYChart.Series();
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
        // The layout of the window
        try{ noOfPropertiesStats = new NoOfPropertiesStats(selectedMinPrice, selectedMaxPrice); }
        catch (Exception ex) {};   
        
        //StatisticsData.initialiseHandler();
        StatisticsData.setBoroughListings(selectedMinPrice, selectedMaxPrice);
        window = new VBox();
        GridPane statsGrid = new GridPane(); 
        
        stat1.addInfo("Average Reviews Per Property:", formatData(StatisticsData.getAverageNoReviews(true)));
        stat1.addInfo("Total Available Properties:", formatData(StatisticsData.getAvailableInfo(true)));
        stat1.setFirst();
        
        stat2.addInfo("Entire Home and Apartments:", formatData(StatisticsData.getNoHomeAndApartments(true)));
        stat2.addInfo("Most Expensive Borough:", formatData(StatisticsData.getExpensiveInfo()));
        stat2.setFirst();
        
        stat3.addInfo("Standard Deviation of Price (£):", formatData(StatisticsData.getPriceSDInfo(true)));
        stat3.addInfo("Borough with the Highest \nAverage Amount of Reviews:", formatData(StatisticsData.getHighAvgReview()));
        stat3.setFirst();
        
        stat4.addInfo("No. of Properties in Borough\nMinimum:", String.valueOf(noOfPropertiesStats.getMinNoOfPropertiesInBorough()));
        stat4.addInfo("No. of Properties in Borough\nLower Quartile:", String.valueOf(noOfPropertiesStats.getFirstQuartile()));
        stat4.addInfo("No. of Properties in Borough\nMedian:", String.valueOf(noOfPropertiesStats.getMedian()));
        stat4.addInfo("No. of Properties in Borough\nUpper Quartile:", String.valueOf(noOfPropertiesStats.getThirdQuartile()));
        stat4.addInfo("No. of Properties in Borough\nMaximum:", String.valueOf(noOfPropertiesStats.getMaxNoOfPropertiesInBorough()));
        stat4.setFirst();
        
        
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart barChart = new BarChart(xAxis, yAxis);

        // The "title" labels in the window
        Label title = new Label("Statistics");
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
        window.getChildren().add(barChart);
        title.setAlignment(Pos.CENTER);
        statsGrid.setAlignment(Pos.CENTER); 
        
        statsGrid.add(stat1, 0, 0);
        statsGrid.add(stat2, 0, 1);
        statsGrid.add(stat3, 1, 0);
        statsGrid.add(stat4, 1, 1);

        //Set the scene and add CSS
        Scene scene = new Scene(window, 1200,700);

        scene.getStylesheets().add("stylesheet.css");

        statsGrid.setId("statsgrid"); 


        title.getStyleClass().add("titlelabel"); 

        xAxis.setLabel("Borough");
        yAxis.setLabel("Average Price");
        averagePriceData.setName("Average Price per Night per Borough");
        setAveragePricePerBorough();
        barChart.getData().add(averagePriceData);

        
        setTitle("Information");
        setScene(scene);

    }
    
    private void constructScene(int selectedMinPrice, int selectedMaxPrice)  {
        // The layout of the window
        noOfPropertiesStats = new NoOfPropertiesStats(selectedMinPrice, selectedMaxPrice);
        
        //StatisticsData.initialiseHandler();
        StatisticsData.setBoroughListings(selectedMinPrice, selectedMaxPrice);
        window = new VBox();
        GridPane statsGrid = new GridPane();
        
        StatBox stat1 = new StatBox();
        StatBox stat2 = new StatBox();
        StatBox stat3 = new StatBox();
        StatBox stat4 = new StatBox();
        
        stat1.addInfo("Average Reviews Per Property:", formatData(StatisticsData.getAverageNoReviews(true)));
        stat1.addInfo("Total Available Properties:", formatData(StatisticsData.getAvailableInfo(true)));
        stat1.setFirst();
        
        stat2.addInfo("Entire Home and Apartments:", formatData(StatisticsData.getNoHomeAndApartments(true)));
        stat2.addInfo("Most Expensive Borough:", formatData(StatisticsData.getExpensiveInfo()));
        stat2.setFirst();
        
        stat3.addInfo("Standard Deviation of Price (£):", formatData(StatisticsData.getPriceSDInfo(true)));
        stat3.addInfo("Borough with the Highest \nAverage Amount of Reviews:", formatData(StatisticsData.getHighAvgReview()));
        stat3.setFirst();
        
        stat4.addInfo("No. of Properties in Borough\nMinimum:", String.valueOf(noOfPropertiesStats.getMinNoOfPropertiesInBorough()));
        stat4.addInfo("No. of Properties in Borough\nLower Quartile:", String.valueOf(noOfPropertiesStats.getFirstQuartile()));
        stat4.addInfo("No. of Properties in Borough\nMedian:", String.valueOf(noOfPropertiesStats.getMedian()));
        stat4.addInfo("No. of Properties in Borough\nUpper Quartile:", String.valueOf(noOfPropertiesStats.getThirdQuartile()));
        stat4.addInfo("No. of Properties in Borough\nMaximum:", String.valueOf(noOfPropertiesStats.getMaxNoOfPropertiesInBorough()));
        stat4.setFirst();
        
        
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart barChart = new BarChart(xAxis, yAxis);

        // The "title" labels in the window
        Label title = new Label("Statistics");
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
        window.getChildren().add(barChart);
        title.setAlignment(Pos.CENTER);
        statsGrid.setAlignment(Pos.CENTER); 
        
        statsGrid.add(stat1, 0, 0);
        statsGrid.add(stat2, 0, 1);
        statsGrid.add(stat3, 1, 0);
        statsGrid.add(stat4, 1, 1);

        //Set the scene and add CSS
        Scene scene = new Scene(window, 1200,700);

        scene.getStylesheets().add("stylesheet.css");

        statsGrid.setId("statsgrid"); 


        title.getStyleClass().add("titlelabel"); 

        xAxis.setLabel("Borough");
        yAxis.setLabel("Average Price");
        averagePriceData.setName("Average Price per Night per Borough");
        setAveragePricePerBorough();
        barChart.getData().add(averagePriceData);

        
        setTitle("Information");
        setScene(scene);
    }
    
    private String formatData(double dataToFormat) {
        String formatedData = String.format("%.2f", dataToFormat) + " (2 d.p)"; 
        return formatedData;
    }

    private String formatData(int dataToFormat) {
        String formatedData = String.valueOf(dataToFormat);
        return formatedData;
    }

    private String formatData(String dataToFormat) {
        return dataToFormat;
    }

    private void setAveragePricePerBorough()
    {
        Map<String, Integer> information = StatisticsData.getAveragePricePerBorough();
        for (Map.Entry<String, Integer> set : information.entrySet())
        {
            averagePriceData.getData().add(new XYChart.Data(set.getKey(), set.getValue()));
        }
    }

    public void updateInfo()
    {
        stat1.updateInfo("Average Reviews Per Property:", formatData(StatisticsData.getAverageNoReviews(true)));
        stat1.updateInfo("Total Available Properties:", formatData(StatisticsData.getAvailableInfo(true)));
        stat2.updateInfo("Entire Home and Apartments:", formatData(StatisticsData.getNoHomeAndApartments(true)));
        stat3.updateInfo("Standard Deviation of Price (£):", formatData(StatisticsData.getPriceSDInfo(true)));
    }
    
    public void update(int selectedMinPrice, int selectedMaxPrice) {
        noOfPropertiesStats = new NoOfPropertiesStats(selectedMinPrice, selectedMaxPrice);
        
        //updateInfo();
        
        constructScene(selectedMinPrice, selectedMaxPrice);
    }
    
    public int getCurrentMinPrice() {
        return noOfPropertiesStats.getMinPrice();
    }
    
    public int getCurrentMaxPrice() {
        return noOfPropertiesStats.getMaxPrice();
    }
}
