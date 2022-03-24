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
    VBox statsWindow = new VBox();
    
    public StatsPane(MainViewerNEW mainViewer)
    {
        super(mainViewer);
        titleName = "Statistics";
    }
    
    public void makePane() {
        if(statsOrder.size() > 0)
        {
            statsOrder.clear();
        }
        currentStat = 0; 
        
        Label reviewInfo = new Label("default");
        Label availableInfo = new Label("default");
        Label noHomeAndApartmentsInfo = new Label("default");
        Label expensiveInfo = new Label("default");
        Label priceSDInfo = new Label("default");
        Label highAvgReviewInfo = new Label("default:"); 
        XYChart.Series averagePriceData = new XYChart.Series();
    
        // The layout of the window
        GridPane statsGrid = new GridPane(); 
        VBox reviews = new VBox();
        VBox available = new VBox(); 
        VBox noHomeAndApartments = new VBox();
        VBox expensive = new VBox(); 
        VBox priceSD = new VBox(); 
        VBox averagePrice = new VBox(); 
        VBox highAvgReview = new VBox();
        
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart barChart = new BarChart(xAxis, yAxis);
          
        
        // The "title" labels in the window
        Label title = new Label("General Statistics");
        Label reviewTitle = new Label("Average Reviews Per Property:");
        Label availableTitle = new Label("Total Available Properties:");
        Label noHomeAndApartmentsTitle = new Label("Entire Home and Apartments:");
        Label expensiveTitle = new Label("Most Expensive Borough:");
        Label priceSDTitle = new Label("Standard Deviation of Price (£):");
        Label highAvgReviewTitle = new Label("Borough with the Highest \nAverage Amount of Reviews:");
        
        VBox statsGridVBox = new VBox();
        statsGridVBox.getChildren().add(statsGrid);
        
        VBox AvgPriceBarCharVBox = new VBox();
        AvgPriceBarCharVBox.getChildren().add(createAvgPriceBarChart());
        
        VBox AvgReviewsBarCharVBox = new VBox();
        AvgReviewsBarCharVBox.getChildren().add(createAvgReviewsBarChart());
        
        statsOrder.add(statsGridVBox);
        statsOrder.add(AvgPriceBarCharVBox);
        statsOrder.add(AvgReviewsBarCharVBox);
        // Adding components 
        statsWindow.setAlignment(Pos.CENTER);
        statsWindow.getChildren().add(title); 
        statsWindow.getChildren().add(getStatsIndex(0)); 
        //window.getChildren().add(barChart);
        title.setAlignment(Pos.CENTER);
        statsGrid.setAlignment(Pos.CENTER); 
        
        statsGrid.add(reviews, 0, 0);
        statsGrid.add(available, 0, 1);
        statsGrid.add(noHomeAndApartments, 1, 0);
        statsGrid.add(expensive, 1, 1);
        statsGrid.add(priceSD, 0, 2); 
        statsGrid.add(highAvgReview, 1 , 2); 
        
        reviews.setAlignment(Pos.CENTER);
        reviews.getChildren().add(reviewTitle); 
        reviews.getChildren().add(reviewInfo);
        
        available.setAlignment(Pos.CENTER);
        available.getChildren().add(availableTitle); 
        available.getChildren().add(availableInfo);
        
        noHomeAndApartments.setAlignment(Pos.CENTER);
        noHomeAndApartments.getChildren().add(noHomeAndApartmentsTitle); 
        noHomeAndApartments.getChildren().add(noHomeAndApartmentsInfo);
        
        expensive.setAlignment(Pos.CENTER);
        expensive.getChildren().add(expensiveTitle); 
        expensive.getChildren().add(expensiveInfo);
        
        priceSD.setAlignment(Pos.CENTER);
        priceSD.getChildren().add(priceSDTitle); 
        priceSD.getChildren().add(priceSDInfo);
        
        highAvgReview.setAlignment(Pos.CENTER);
        highAvgReview.getChildren().add(highAvgReviewTitle); 
        highAvgReview.getChildren().add(highAvgReviewInfo);
        
        //Set the scene and add CSS
        //Scene scene = new Scene(window, 1200,700);
        
        //scene.getStylesheets().add("stylesheet.css");
        
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
        
        title.getStyleClass().add("windowTitle"); 
        
    
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
    
    private BarChart createAvgPriceBarChart()
    {
        XYChart.Series averagePriceData = new XYChart.Series();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart barChart = new BarChart(xAxis, yAxis);
        xAxis.setLabel("Borough");
        yAxis.setLabel("Average Price");
        averagePriceData = getAveragePricePerBoroughData();
        averagePriceData.setName("Average Price per Night per Borough");
        barChart.getData().add(averagePriceData);
        
        return barChart;
    }
    
    private BarChart createAvgReviewsBarChart()
    {
        XYChart.Series averageReviewsData = new XYChart.Series();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart barChart = new BarChart(xAxis, yAxis);
        xAxis.setLabel("Borough");
        yAxis.setLabel("Average Review Count");
        averageReviewsData = getAverageReviewsPerBoroughData();
        averageReviewsData.setName("Average Number of Reviews per Borough");
        barChart.getData().add(averageReviewsData);
        
        return barChart;
    }
    
    private VBox getStatsIndex(int x)
    {
        if(statsOrder.size() > 0 && x >= 0 && x < statsOrder.size())
        {
            return statsOrder.get(x);
        }
        VBox box = new VBox();
        return box;
    }
    
    /**
     * Action for the left button which will go the element at index current - 1 in both
     * title list and stat list. If it reaches the start of both of the lists, set the labels to
     * the last element in the lists. 
     */
    public void leftStatButtonAction()
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
     * Action for the right button which will go the element at index current + 1 in both
     * title list and stat list. If it reaches the end of both of the lists, set the labels to
     * the first element in the lists.
     */
    public void rightStatButtonAction()
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
    
    private void swapWindowContent(int change)
    {
        statsWindow.getChildren().remove(statsOrder.get(currentStat));
        currentStat = change; 
        statsWindow.getChildren().add(statsOrder.get(currentStat));
    }
    
    
    private void setText(Label label, double dataToFormat) {
        String formattedLabel = String.valueOf(String.format("%.2f", dataToFormat) + " (2 d.p)"); 
        label.setText(formattedLabel);
    }
    
    private void setText(Label label, int dataToFormat) {
        label.setText(String.valueOf(dataToFormat));
    }
    
    private void setText(Label label, String dataToFormat) {
        label.setText(dataToFormat);
    }
    
    /**
     * Hola Matthew! this should probably one method with a variable. Gracias
     */
    private XYChart.Series getAveragePricePerBoroughData()
    {
        XYChart.Series data = new XYChart.Series();
        HashMap<String, Integer> information = StatisticsData.getAveragePricePerBorough();
        for (String entry : information.keySet())
        {
            data.getData().add(new XYChart.Data(entry, information.get(entry)));
        }
        return data; 
    }
    
    private XYChart.Series getAverageReviewsPerBoroughData()
    {
        XYChart.Series data = new XYChart.Series();
        HashMap<String, Integer> information = StatisticsData.getAverageReviewsPerBorough();
        for (String entry : information.keySet())
        {
            data.getData().add(new XYChart.Data(entry, information.get(entry)));
        }
        return data; 
    }
    
    public Pane getPane() {
        return statsPane;
    }
}