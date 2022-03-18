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
    private StatisticsData dataHandler = new StatisticsData(); 

    // The information labels in the window
    Label reviewInfo = new Label("default");
    Label availableInfo = new Label("default");
    Label noHomeAndApartmentsInfo = new Label("default");
    Label expensiveInfo = new Label("default");
    Label priceSDInfo = new Label("default");
    Label highAvgReviewInfo = new Label("default:"); 
    XYChart.Series averagePriceData = new XYChart.Series();

    /**
     * Constructor for objects of class StatisticsViewer
     */
    public StatisticsViewer(int selectedMinPrice, int selectedMaxPrice)
    {
        // The layout of the window
        VBox window = new VBox();
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
        Scene scene = new Scene(window, 1200,700);

        scene.getStylesheets().add("stylesheet.css");

        statsGrid.setId("statsgrid"); 
        window.getStyleClass().add("statsvbox");
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

        title.getStyleClass().add("titlelabel"); 

        xAxis.setLabel("Borough");
        yAxis.setLabel("Average Price");
        averagePriceData.setName("Average Price per Night per Borough");
        setAveragePricePerBorough();
        barChart.getData().add(averagePriceData);

        setText(reviewInfo, dataHandler.getAverageNoReviews());
        setText(noHomeAndApartmentsInfo, dataHandler.getNoHomeAndApartments());
        setText(availableInfo, dataHandler.getAvailableInfo());
        setText(expensiveInfo, dataHandler.getExpensiveInfo());
        setText(priceSDInfo, dataHandler.getPriceSDInfo());
        setText(highAvgReviewInfo, dataHandler.getHighAvgReview());
        
        StatBox test = new StatBox(); 
        
        statsGrid.add(test, 2, 2);
        test.addInfo("title test", "stat test");
        test.addInfo("2", "2");
        test.addInfo("3", "3");
        test.setFirst();
        
        setTitle("Information");
        setScene(scene);

    }



    private void setText(Label label, double dataToFormat) {
        String x = String.valueOf(String.format("%.2f", dataToFormat) + " (2 d.p)"); 
        label.setText(x);
    }

    private void setText(Label label, int dataToFormat) {
        label.setText(String.valueOf(dataToFormat));
    }

    private void setText(Label label, String dataToFormat) {
        label.setText(dataToFormat);
    }

    private void setAveragePricePerBorough()
    {
        Map<String, Integer> information = dataHandler.getAveragePricePerBorough();
        for (Map.Entry<String, Integer> set : information.entrySet())
        {
            averagePriceData.getData().add(new XYChart.Data(set.getKey(), set.getValue()));
        }
    }

}
