import javafx.scene.layout.Pane;
import java.util.ArrayList;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.util.HashMap;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
import javafx.animation.*;
import javafx.util.Duration;

/**
 * Makes the Statistics Window and displays general statistics that are not influenced 
 * by the price selected.
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 25/03/22
 */
public class StatsPane extends MainViewerPane
{
    private static Pane statsPane;
    ArrayList<VBox> statsOrder = new ArrayList<>();
    int currentStat = 0;
    VBox statsWindow;
    private Animations animations = new Animations();
    
    /**
     * Set the title Name to Statistics
     */
    public StatsPane(MainViewer mainViewer)
    {
        super(mainViewer);
        titleName = "Statistics";
        hasMinMaxBox = false; 
    }
    
    /**
     * Make the statistics pane
     */
    public void makePane() {
        statsWindow = new VBox();
        
        if(statsOrder.size() > 0)
        {
            statsOrder.clear();
        }
        currentStat = 0; 
        
        // Create a border pane
        BorderPane statsBorder = new BorderPane(); 
        
        // The "title" labels in the window
        Label title = new Label("General Statistics");
        Label reviewTitle = new Label("Average Reviews Per Property:");
        Label availableTitle = new Label("Total Available Properties:");
        Label noHomeAndApartmentsTitle = new Label("Entire Home and Apartments:");
        Label expensiveTitle = new Label("Most Expensive Borough\non Average:");
        Label priceSDTitle = new Label("Standard Deviation of Price (£):");
        Label highAvgReviewTitle = new Label("Borough with the Highest \nAverage Amount of Reviews:"); 
        
        // The "info" labels for each statistic which displays the data value
        Label reviewInfo = new Label("default");
        Label availableInfo = new Label("default");
        Label noHomeAndApartmentsInfo = new Label("default");
        Label expensiveInfo = new Label("default");
        Label priceSDInfo = new Label("default");
        Label highAvgReviewInfo = new Label("default:");
    
        // A grid holding some statistics
        GridPane statsGrid = new GridPane(); 
        VBox reviews = new VBox();
        VBox available = new VBox(); 
        VBox noHomeAndApartments = new VBox();
        VBox expensive = new VBox(); 
        VBox priceSD = new VBox(); 
        VBox averagePrice = new VBox(); 
        VBox highAvgReview = new VBox();
        
        // Add Labels to the reviews VBox
        reviews.setAlignment(Pos.CENTER);
        reviews.getChildren().add(reviewTitle); 
        reviews.getChildren().add(reviewInfo);
        
        // Add Labels to the available VBox
        available.setAlignment(Pos.CENTER);
        available.getChildren().add(availableTitle); 
        available.getChildren().add(availableInfo);
        
        // Add Labels to the noHomeAndApartments VBox
        noHomeAndApartments.setAlignment(Pos.CENTER);
        noHomeAndApartments.getChildren().add(noHomeAndApartmentsTitle); 
        noHomeAndApartments.getChildren().add(noHomeAndApartmentsInfo);
        
        // Add Labels to the expensive VBox
        expensive.setAlignment(Pos.CENTER);
        expensive.getChildren().add(expensiveTitle); 
        expensive.getChildren().add(expensiveInfo);
        
        // Add Labels to the priceSD VBox
        priceSD.setAlignment(Pos.CENTER);
        priceSD.getChildren().add(priceSDTitle); 
        priceSD.getChildren().add(priceSDInfo);
        
        // Add Labels to the highAvgReview VBox
        highAvgReview.setAlignment(Pos.CENTER);
        highAvgReview.getChildren().add(highAvgReviewTitle); 
        highAvgReview.getChildren().add(highAvgReviewInfo);
        
        // Add elements to the Statistics Grid
        statsGrid.add(reviews, 0, 0);
        statsGrid.add(available, 0, 1);
        statsGrid.add(noHomeAndApartments, 1, 0);
        statsGrid.add(expensive, 1, 1);
        statsGrid.add(priceSD, 0, 2); 
        statsGrid.add(highAvgReview, 1 , 2);
        
        // Create a VBox to hold the grid pane
        VBox statsGridVBox = new VBox();
        statsGridVBox.getChildren().add(statsGrid);
        
        // Create a VBox to hold the Average Price Bar Chart
        VBox AvgPriceBarChartVBox = new VBox();
        // Create the Average Price Bar Chart
        BarChart AvgPriceBarChart = createBarChart(getBarChartData(StatisticsData.getAveragePricePerBorough()), "Boroughs", "Average Price", "Average Price per Night per Borough");
        // Add the Bar Chart to the Average Price Bar Chart
        AvgPriceBarChartVBox.getChildren().add(AvgPriceBarChart);
        
        // Create a VBox to hold the Average Reviews Bar Chart
        VBox AvgReviewsBarChartVBox = new VBox();
        // Create the Average Reviews Bar Chart
        BarChart AvgReviewsBarChart = createBarChart(getBarChartData(StatisticsData.getAverageReviewsPerBorough()), "Boroughs", "Average Reviews", "Average Number of Reviews per Borough");
        // Add the Bar Chart to the Average Reviews Bar Chart
        AvgReviewsBarChartVBox.getChildren().add(AvgReviewsBarChart);
    
        //Add the VBoxes to the StatsOrder array list
        statsOrder.add(statsGridVBox);
        statsOrder.add(AvgPriceBarChartVBox);
        statsOrder.add(AvgReviewsBarChartVBox);
        // Adding components to the statsWindow VBox
        statsWindow.setAlignment(Pos.CENTER);
        statsWindow.getChildren().add(title); 
        statsWindow.getChildren().add(getStatsOrderObject(0)); 
        title.setAlignment(Pos.CENTER);     
        
        // Set the Info Labels to the data. 
        setText(reviewInfo, StatisticsData.getAverageNoReviews(false));
        setText(noHomeAndApartmentsInfo, StatisticsData.getNoHomeAndApartments(false));
        setText(availableInfo, StatisticsData.getAvailableInfo(false));
        setText(expensiveInfo, StatisticsData.getExpensiveInfo(false, 0, 0));
        setText(priceSDInfo, StatisticsData.getPriceSDInfo(false));
        setText(highAvgReviewInfo, StatisticsData.getHighAvgReview(false, 0, 0));
        
        //Create the left button
        Button leftStatsButton = new Button();
        leftStatsButton.setText("<");
        leftStatsButton.setOnAction(e ->leftStatButtonAction());
        leftStatsButton.setMinSize(10, 100);
        leftStatsButton.setAlignment(Pos.CENTER);
        
        //Create the right button
        Button rightStatsButton = new Button();
        rightStatsButton.setText(">");
        rightStatsButton.setOnAction(e -> rightStatButtonAction());
        rightStatsButton.setMinSize(10, 100);
        rightStatsButton.setAlignment(Pos.CENTER);
        
        // Create a VBox to hold the right button
        VBox rightButtonVBox = new VBox();
        // Add the right stats button to the VBox
        rightButtonVBox.getChildren().add(rightStatsButton);
        rightButtonVBox.setAlignment(Pos.CENTER);
        
        // Create a VBox to hold the left button
        VBox leftButtonVBox = new VBox();
        // Add the left stats button to the VBox
        leftButtonVBox.getChildren().add(leftStatsButton);
        leftButtonVBox.setAlignment(Pos.CENTER);
        
        // Add elements to the statsBorder pane
        statsBorder.setCenter(statsWindow);
        statsBorder.setLeft(leftButtonVBox);
        statsBorder.setRight(rightButtonVBox);
        
        // Add CSS styling
        statsGrid.setAlignment(Pos.CENTER);
        statsGrid.setId("statsgrid"); 
        statsWindow.getStyleClass().add("statsWindowAndButtons");
        reviews.getStyleClass().add("statsvbox"); 
        available.getStyleClass().add("statsvbox");
        noHomeAndApartments.getStyleClass().add("statsvbox");
        expensive.getStyleClass().add("statsvbox");
        priceSD.getStyleClass().add("statsvbox");
        highAvgReview.getStyleClass().add("statsvbox");
        // reviews.setMinSize(200,150);
        // available.setMinSize(200,150);
        // noHomeAndApartments.setMinSize(200,150);
        // expensive.setMinSize(200,150);
        // priceSD.setMinSize(200,150);
        // highAvgReview.setMinSize(200,150);
        
        
        title.getStyleClass().add("windowTitle"); 
        reviewTitle.getStyleClass().add("statsLabelTitle"); 
        availableTitle.getStyleClass().add("statsLabelTitle"); 
        noHomeAndApartmentsTitle.getStyleClass().add("statsLabelTitle");
        expensiveTitle.getStyleClass().add("statsLabelTitle"); 
        priceSDTitle.getStyleClass().add("statsLabelTitle"); 
        highAvgReviewTitle.getStyleClass().add("statsLabelTitle");
        
        reviewInfo.getStyleClass().add("statsLabels"); 
        availableInfo.getStyleClass().add("statsLabels"); 
        noHomeAndApartmentsInfo.getStyleClass().add("statsLabels"); 
        expensiveInfo.getStyleClass().add("statsLabels"); 
        priceSDInfo.getStyleClass().add("statsLabels"); 
        highAvgReviewInfo.getStyleClass().add("statsLabels");
        
        leftButtonVBox.getStyleClass().add("statsWindowAndButtons");
        rightButtonVBox.getStyleClass().add("statsWindowAndButtons");
        leftStatsButton.getStyleClass().add("smallWindowButtons");
        rightStatsButton.getStyleClass().add("smallWindowButtons");
        
        // Set the pane
        statsPane = statsBorder;
    }
    
    /**
     * Create a Bar Chart holding data 
     * @param XYChart.Series chart The data to go into the chart. 
     * @param String xAxisLabel Label of the xAxis.
     * @param String yAxisLabel Label of the yAxis.
     * @param String dataName The label of the data.
     * @return A BarChart object
     */
    private BarChart createBarChart(XYChart.Series chart, String xAxisLabel, String yAxisLabel, String dataName)
    {
        XYChart.Series data = new XYChart.Series();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart barChart = new BarChart(xAxis, yAxis);
        xAxis.setLabel(xAxisLabel);
        yAxis.setLabel(yAxisLabel);
        data = chart;
        data.setName(dataName);
        barChart.getData().add(data);
        return barChart;
    }
    
    /**
     * Get a VBox object from the Stats Order List at a certain index. Index must be within the List's bounds. 
     * @param int x Index of the object needed.
     * @return VBox Object from the Stats Order List.
     */
    private VBox getStatsOrderObject(int x)
    {
        if(statsOrder.size() > 0 && x >= 0 && x < statsOrder.size())
        {
            return statsOrder.get(x);
        }
        VBox box = new VBox();
        return box;
    }
    
    /**
     * Action for the left button which will go the element at index current - 1. If the first element is reached,
     * it will set the window content to the last element in the Stats Order list.  
     */
    private void leftStatButtonAction()
    {
        if(currentStat - 1 < 0)
        {
            swapWindowContent(statsOrder.size() - 1);
        }
        else
        {
            swapWindowContent(currentStat - 1);
        }
    }
    
    /**
     * Action for the right button which will go the element at index current + 1. If the last element is reached,
     * it will set the window content to the first element in the Stats Order list.  
     */
    private void rightStatButtonAction()
    {
        if(currentStat + 1 >= statsOrder.size())
        {
            swapWindowContent(0);
        }
        else
        {
            swapWindowContent(currentStat + 1);
        }
    }
    
    /**
     * Used to swap window content for the different Statistics displays (e.g. grid, barchat). 
     * @param int change Set the current index of Stats Order list to this int. 
     */
    private void swapWindowContent(int change)
    {
        //fadeOut(statsWindow, 1000);
        statsWindow.getChildren().remove(getStatsOrderObject(currentStat));
        currentStat = change; 
        statsWindow.getChildren().add(getStatsOrderObject(currentStat));
        animations.fadeIn(statsWindow, 1000);
    }
    
    /**
     * Convert a double data type to String then set it as a Label's text
     * @param Label label The label which will be affected.
     * @param double dataToFormat The data to format into a String.
     */
    private void setText(Label label, double dataToFormat) {
        String formattedLabel = String.valueOf(String.format("%.2f", dataToFormat) + " (2 d.p)"); 
        label.setText(formattedLabel);
    }
    
    /**
     * Convert an int data type to String then set it as a Label's text
     * @param Label label The label which will be affected.
     * @param int dataToFormat The data to format into a String.
     */
    private void setText(Label label, int dataToFormat) {
        label.setText(String.valueOf(dataToFormat));
    }
    
    /**
     * Set a Label's text to a passed through String
     * @param Label label The label which will be affected.
     * @param String dataToFormat The String that the Label's text will be set to.
     */
    private void setText(Label label, String dataToFormat) {
        label.setText(dataToFormat);
    }
    
    /**
     * @param HashMap<String, Integer> Map Containing data for each borough
     * @return XYChart.Series object
     */
    private XYChart.Series getBarChartData(HashMap<String, Integer> map)
    {
        XYChart.Series data = new XYChart.Series();
        for (String entry : map.keySet())
        {
            data.getData().add(new XYChart.Data(entry, map.get(entry)));
        }
        return data; 
    }
    
    /**
     * @return The statsPane as a Pane object
     */
    public Pane getPane() {
        return statsPane;
    }
}