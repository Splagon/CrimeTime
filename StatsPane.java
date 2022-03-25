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
 * Write a description of class StatsPane here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StatsPane extends MainViewerPane
{
    private static Pane statsPane;
    ArrayList<VBox> statsOrder = new ArrayList<>();
    int currentStat = 0;
    VBox statsWindow;
    private Animations animations = new Animations();
    
    public StatsPane(MainViewer mainViewer)
    {
        super(mainViewer);
        titleName = "Statistics";
        hasMinMaxBox = true; 
    }
    
    public void makePane() {
        statsWindow = new VBox();
        
        if(statsOrder.size() > 0)
        {
            statsOrder.clear();
        }
        currentStat = 0; 
        
        // The "title" labels in the window
        Label title = new Label("General Statistics");
        Label reviewTitle = new Label("Average Reviews Per Property:");
        Label availableTitle = new Label("Total Available Properties:");
        Label noHomeAndApartmentsTitle = new Label("Entire Home and Apartments:");
        Label expensiveTitle = new Label("Most Expensive Borough:");
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
        
        
        
        
        statsGrid.setAlignment(Pos.CENTER);
        statsGrid.setId("statsgrid"); 
        statsWindow.getStyleClass().add("statsWindowAndButtons");
        reviews.getStyleClass().add("statsvbox"); 
        available.getStyleClass().add("statsvbox");
        noHomeAndApartments.getStyleClass().add("statsvbox");
        expensive.getStyleClass().add("statsvbox");
        priceSD.getStyleClass().add("statsvbox");
        highAvgReview.getStyleClass().add("statsvbox");
        
        
        reviewInfo.getStyleClass().add("statslabels"); 
        availableInfo.getStyleClass().add("statslabels"); 
        noHomeAndApartmentsInfo.getStyleClass().add("statslabels"); 
        expensiveInfo.getStyleClass().add("statslabels"); 
        priceSDInfo.getStyleClass().add("statslabels"); 
        highAvgReviewInfo.getStyleClass().add("statslabels");
        
        
        
        
        
        
        
        
        
        VBox statsGridVBox = new VBox();
        statsGridVBox.getChildren().add(statsGrid);
        
        VBox AvgPriceBarChartVBox = new VBox();
        BarChart AvgPriceBarChart = createBarChart(getBarChartData(StatisticsData.getAveragePricePerBorough()), "Boroughs", "Average Price", "Average Price per Night per Borough");
        AvgPriceBarChartVBox.getChildren().add(AvgPriceBarChart);
        
        VBox AvgReviewsBarChartVBox = new VBox();
        BarChart AvgReviewsBarChart = createBarChart(getBarChartData(StatisticsData.getAverageReviewsPerBorough()), "Boroughs", "Average Reviews", "Average Number of Reviews per Borough");
        AvgReviewsBarChartVBox.getChildren().add(AvgReviewsBarChart);
    
        statsOrder.add(statsGridVBox);
        statsOrder.add(AvgPriceBarChartVBox);
        statsOrder.add(AvgReviewsBarChartVBox);
        // Adding components 
        statsWindow.setAlignment(Pos.CENTER);
        statsWindow.getChildren().add(title); 
        statsWindow.getChildren().add(getStatsOrderObject(0)); 
        title.setAlignment(Pos.CENTER); 
        title.getStyleClass().add("windowTitle");
        
        
        
        //Set the scene and add CSS
        //Scene scene = new Scene(window, 1200,700);
        
        //scene.getStylesheets().add("stylesheet.css");
    
        
         
        
    
        setText(reviewInfo, StatisticsData.getAverageNoReviews(false));
        setText(noHomeAndApartmentsInfo, StatisticsData.getNoHomeAndApartments(false));
        setText(availableInfo, StatisticsData.getAvailableInfo(false));
        setText(expensiveInfo, StatisticsData.getExpensiveInfo());
        setText(priceSDInfo, StatisticsData.getPriceSDInfo(false));
        setText(highAvgReviewInfo, StatisticsData.getHighAvgReview());
        
        Button leftStatsButton = new Button();
        leftStatsButton.setText("<");
        leftStatsButton.setOnAction(e ->leftStatButtonAction());
        leftStatsButton.setMinSize(10, 100);
        leftStatsButton.setAlignment(Pos.CENTER);
        leftStatsButton.getStyleClass().add("smallWindowButtons");
        
        //Create the right button
        Button rightStatsButton = new Button();
        rightStatsButton.setText(">");
        rightStatsButton.setOnAction(e -> rightStatButtonAction());
        rightStatsButton.setMinSize(10, 100);
        rightStatsButton.setAlignment(Pos.CENTER);
        rightStatsButton.getStyleClass().add("smallWindowButtons");
        
        VBox rightButtonVBox = new VBox();
        rightButtonVBox.getChildren().add(rightStatsButton);
        rightButtonVBox.getStyleClass().add("statsWindowAndButtons");
        rightButtonVBox.setAlignment(Pos.CENTER);
        
        VBox leftButtonVBox = new VBox();
        leftButtonVBox.getChildren().add(leftStatsButton);
        leftButtonVBox.getStyleClass().add("statsWindowAndButtons");
        leftButtonVBox.setAlignment(Pos.CENTER);
        
        
        BorderPane statsBorder = new BorderPane(); 
        statsBorder.setCenter(statsWindow);
        statsBorder.setLeft(leftButtonVBox);
        statsBorder.setRight(rightButtonVBox);
        
        statsPane = statsBorder;
    }
    
    /**
     * Create a Bar Chart holding data 
     * @params XYChart.Series chart - The data to go into the chart. String xAxisLabel - Label of the xAxis; String yAxisLabel - Label of the yAxis; String dataName - The label of the data.
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
     * @params int x - Index of the object needed.
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
     * @params int change - set the current index of Stats Order list to this int. 
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
     * @params Label label - the label which will be affected; double dataToFormat - The data to format into a String
     */
    private void setText(Label label, double dataToFormat) {
        String formattedLabel = String.valueOf(String.format("%.2f", dataToFormat) + " (2 d.p)"); 
        label.setText(formattedLabel);
    }
    
    /**
     * Convert an int data type to String then set it as a Label's text
     * @params Label label - the label which will be affected; int dataToFormat - The data to format into a String
     */
    private void setText(Label label, int dataToFormat) {
        label.setText(String.valueOf(dataToFormat));
    }
    
    /**
     * Set a Label's text to a passed through String
     * @params Label label - the label which will be affected; String dataToFormat - The String that the Label's text will be set to
     */
    private void setText(Label label, String dataToFormat) {
        label.setText(dataToFormat);
    }
    
    /**
     * @params HashMap<String, Integer> map - Containing data for each borough
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