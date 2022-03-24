import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.scene.text.Font;
import javafx.scene.shape.*;
import javafx.scene.image.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.scene.effect.ColorAdjust;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;

/**
 * Write a description of class MapPane here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MapPane extends MainViewerPane
{   
    private static Pane mapPane;
    private static AnchorPane mapView;
    
    public MapPane(MainViewerNEW mainViewer)
    {
        super(mainViewer);
        titleName = "Map of London";
    }
    
    public void makePane() {
        BorderPane window = new BorderPane();
        
        VBox infoPane = new VBox();
            Label titleLabel = new Label("Boroughs of London");
                titleLabel.getStyleClass().add("windowTitle");
            HBox minMaxBox = mainViewer.createMinMaxBox();
                minMaxBox = mainViewer.setInitialMinMaxBoxSelection(minMaxBox);
            VBox stats = createStatsPanel();
            GridPane key = createKey();
            
        Button confirm = (Button) minMaxBox.getChildren().get(2);
        
        ComboBox<String> minBox = (ComboBox<String>) minMaxBox.getChildren().get(0);
        ComboBox<String> maxBox = (ComboBox<String>) minMaxBox.getChildren().get(1);
        
        //styling the min and max box as well as the confirm button for the map panel
        window.getStylesheets().add("stylesheet.css");
        confirm.getStyleClass().add("confirmForMap");
        minBox.getStyleClass().add("mapMinMaxBoxes");
        maxBox.getStyleClass().add("mapMinMaxBoxes");
        minMaxBox.getStyleClass().add("mapMinMaxBox");
            
        infoPane.getChildren().addAll(titleLabel, minMaxBox, key, stats);
        infoPane.setPadding(new Insets(10, 20, 10, 10));
        infoPane.setSpacing(15);
        
        mapView.setPadding(new Insets(20));
        
        window.setLeft(infoPane);
        window.setCenter(mapView);
        
        mapPane = window;
    }
    
    public void makeHexagonMap()  {
        String[][] mapPositions = StatisticsData.getMapPositions();
        
        NoOfPropertiesStats noOfPropertiesStats = new NoOfPropertiesStats(mainViewer.getSelectedMinPrice(), mainViewer.getSelectedMaxPrice());
        
        double sceneWidth = mainViewer.getMainScene().getWidth();
        double sceneHeight = mainViewer.getMainScene().getHeight();
        
        final double WIDTH_TO_HEIGHT_RATIO = 725.0 / 510.0;
        final double HEIGHT_TO_WIDTH_RATIO = 1 / WIDTH_TO_HEIGHT_RATIO;
        
        double newWidth = sceneWidth * 0.655;
        double newHeight = sceneHeight * 0.804;
        
        double newWidthToHeightRatio = newWidth/newHeight;
        
        final double gapSize = 5.0;

        // pane is too narrow
        if (newWidthToHeightRatio < WIDTH_TO_HEIGHT_RATIO) {
            newHeight = newWidth * HEIGHT_TO_WIDTH_RATIO;
        }
        // pane is too wide
        else if (newWidthToHeightRatio > WIDTH_TO_HEIGHT_RATIO) {
            newWidth = newHeight * WIDTH_TO_HEIGHT_RATIO;
        }
        
        //newHeight = 510;
        //newWidth = 720;
            
        mapView = new AnchorPane();
            mapView.setPrefSize(newWidth, newHeight);
            mapView.setMinSize(670, 310);
            
            setMinWidth(1050);
            setMinHeight(620);
            
            //double hexagonWidth = 94.0;
            double hexagonWidth = (int) (newWidth / 7.5);
            //rows
            for (int m = 0; m < mapPositions.length; m++) {
                
                FlowPane row = new FlowPane();
                   row.setHgap(gapSize);
                   row.setMinWidth(Double.MAX_VALUE);
                    
                StackPane rowSpace;
                
                if (m % 2 == 0) {
                        createInsetRectangle(hexagonWidth, row, gapSize);
                }
                
                //columns
                for (int n = 0; n < mapPositions[m].length; n++) {
                    rowSpace = new StackPane();
                    if (mapPositions[m][n] != null) {
                        MapButton boroughButton = new MapButton(mapPositions[m][n]);
                        boroughButton.setShape(new Circle(hexagonWidth));
                        boroughButton.setMinSize(hexagonWidth*0.97, hexagonWidth*0.85);
                        boroughButton.setFont(new Font(boroughButton.getFont().getName(), 0.21 * hexagonWidth));
                        //boroughButton.setStyle("-fx-font-size: " + String.valueOf(20.0/94.0 * hexagonWidth) + ";");
                        boroughButton.getStyleClass().add("boroughButton");
                        boroughButton.setOnAction(e -> openPropertyViewer(boroughButton.getBoroughName()));
                        
                        ImageView hexagonOutline = new ImageView(new Image("/hexagonOutline.png", true));
                            hexagonOutline.setFitWidth(hexagonWidth);
                            hexagonOutline.setFitHeight(hexagonWidth);
                        
                        ImageView hexagonFilledImage = new ImageView(new Image("/hexagonFilledGreen.png"));
                        ImageView hexagonFilled = setHexagonFilledColour(hexagonFilledImage, boroughButton.getBoroughName(), (int) hexagonWidth, noOfPropertiesStats);
                        
                        // Rectangle bgTest = new Rectangle(hexagonWidth,hexagonWidth);
                            // bgTest.setFill(Color.FUCHSIA);
                        
                        rowSpace.getChildren().addAll(hexagonFilled, hexagonOutline, boroughButton);                        
                    }
                    else {
                        Rectangle emptySpace = createSpacerRectangle((int) hexagonWidth);
                        rowSpace.getChildren().add(emptySpace);
                    }
                    row.getChildren().add(rowSpace);
                }
                
                if (m % 2 == 1) {
                   createInsetRectangle(hexagonWidth, row, gapSize);
                }
                AnchorPane.setTopAnchor(row, m * (newHeight/mapPositions.length + gapSize));
                mapView.getChildren().add(row);
            }
            
        updateStats();
    }
    
    private void createInsetRectangle(double hexagonWidth, FlowPane row, double gapSize) {
        StackPane rowSpace = new StackPane();
        Rectangle insetSpace = createSpacerRectangle((int) ((hexagonWidth - gapSize) / 2.0));
        rowSpace.getChildren().add(insetSpace);
        row.getChildren().add(rowSpace);
    }
    
    private Rectangle createSpacerRectangle(int widthHeight) {
        Rectangle spacerRectangle = new Rectangle(widthHeight, widthHeight);
        spacerRectangle.setFill(Color.TRANSPARENT);
        return spacerRectangle;
    }
    
    private void openPropertyViewer(String boroughName) {
        try {
            PropertyViewer propertyViewer = new PropertyViewer(boroughName, mainViewer.getSelectedMinPrice(), mainViewer.getSelectedMaxPrice(), null);
            if(propertyViewer.getInternetConnection() == true){
                propertyViewer.show();
            } else {
                propertyViewer.noConnectionAlert();
            }  
        }
        catch (Exception e) {
            Alert alert = new Alert(AlertType.WARNING);
                alert.setHeaderText("No Available Properties in " + boroughName);
                alert.setContentText("Unfortunately, there are no available properties in this\nborough within your price range. Welcome to the London\nhousing market...");
            alert.show();
        };
    }
    
    private ImageView setHexagonFilledColour(ImageView hexagon, String boroughName, int heightWidth, NoOfPropertiesStats noOfPropertiesStats) {
        ColorAdjust shader = new ColorAdjust();
            shader.setBrightness(StatisticsData.getBoroughMapColour(boroughName, mainViewer.getSelectedMinPrice(), mainViewer.getSelectedMaxPrice(), noOfPropertiesStats));
            
        hexagon.setFitWidth(heightWidth);
        hexagon.setFitHeight(heightWidth);
            
        hexagon.setEffect(shader);
        
        return hexagon;
    }
    
    private ImageView setHexagonFilledColour(ImageView hexagon, int heightWidth, int percentile) {
        ColorAdjust shader = new ColorAdjust();
            shader.setBrightness(StatisticsData.getBoroughMapColour(percentile));
            
        hexagon.setFitWidth(heightWidth);
        hexagon.setFitHeight(heightWidth);
            
        hexagon.setEffect(shader);
        
        return hexagon;
    }
    
    private GridPane createKey() {
        GridPane key = new GridPane();
        key.getStyleClass().add("infoGrid");
        
        int hexagonKeyHeightWidth = 20;
        
        Label keyTitleLabel = new Label("Key");
        Label keyDescriptionLabel = new Label("(Sorted by number of\nProperties in Borough)");
            keyDescriptionLabel.getStyleClass().add("labelSmall");
        Label keyLabelEmpty = new Label("No Properties in Borough");
        Label keyLabelPercentile25 = new Label("Below Lower Quartile");
        Label keyLabelPercentile50 = new Label("Between Lower Quartile\n and Median");
        Label keyLabelPercentile75 = new Label("Between Median\n and Lower Quartile");
        Label keyLabelPercentile100 = new Label("Above Upper Quartile");
        

        for (int i = 0; i < 5; i++) {
            StackPane completeHexagon = new StackPane();
            
            ImageView hexagonFilledImage = new ImageView(new Image("/hexagonFilledGreen.png"));
            ImageView hexagonOutline = new ImageView(new Image("/hexagonOutlineThick.png"));
            
            ImageView hexagonFilled = setHexagonFilledColour(hexagonFilledImage, hexagonKeyHeightWidth, i*20);
            
            hexagonOutline.setFitHeight(hexagonKeyHeightWidth);
            hexagonOutline.setFitWidth(hexagonKeyHeightWidth);
            
            completeHexagon.getChildren().addAll(hexagonFilled, hexagonOutline);
            
            key.add(completeHexagon, 0, i+1);
        }
        
        key.add(keyTitleLabel, 0, 0);
        key.add(keyDescriptionLabel, 1, 0);
            
        key.add(keyLabelEmpty, 1, 1);
        key.add(keyLabelPercentile25, 1, 2);
        key.add(keyLabelPercentile50, 1, 3);
        key.add(keyLabelPercentile75, 1, 4);
        key.add(keyLabelPercentile100, 1, 5);
        
        key = alignItemsInGridPane(key);
        
        return key;
    }
    
    private VBox createStatsPanel() {
        NoOfPropertiesStats noOfPropertiesStats = new NoOfPropertiesStats(mainViewer.getSelectedMinPrice(), mainViewer.getSelectedMaxPrice());
        
        VBox statsBox = new VBox();
        statsBox.setSpacing(20);
        
        GridPane statsPanel = new GridPane();
        statsPanel.getStyleClass().add("infoGrid");
        
        Label statsTitleLabel = new Label("Statistics");
        Label statsDescriptionLabel = new Label("(number of\nproperties in borough)");
            statsDescriptionLabel.getStyleClass().add("labelSmall");
        Label statsLabel1 = new Label("Minimum");
        Label statsLabel2 = new Label("Lower Quartile");
        Label statsLabel3 = new Label("Median");
        Label statsLabel4 = new Label("Upper Quartile");
        Label statsLabel5 = new Label("Maximum");
        
        statsPanel.add(statsTitleLabel, 0, 0);
        statsPanel.add(statsDescriptionLabel, 1, 0);
        
        statsPanel.add(new Label(String.valueOf(noOfPropertiesStats.getMinNoOfPropertiesInBorough())), 1, 1);
        statsPanel.add(new Label(String.valueOf(noOfPropertiesStats.getFirstQuartile())), 1, 2);
        statsPanel.add(new Label(String.valueOf(noOfPropertiesStats.getMedian())), 1, 3);
        statsPanel.add(new Label(String.valueOf(noOfPropertiesStats.getThirdQuartile())), 1, 4);
        statsPanel.add(new Label(String.valueOf(noOfPropertiesStats.getMaxNoOfPropertiesInBorough())), 1, 5);
        
        statsPanel = alignItemsInGridPane(statsPanel);
        
        statsPanel.add(statsLabel1, 0, 1);
        statsPanel.add(statsLabel2, 0, 2);
        statsPanel.add(statsLabel3, 0, 3);
        statsPanel.add(statsLabel4, 0, 4);
        statsPanel.add(statsLabel5, 0, 5);
        
        Button moreStatsButton = new Button("Show more stats!");
        moreStatsButton.setOnAction(e -> showMoreStats());
        moreStatsButton.getStyleClass().add("smallWindowButtons");
        
        statsBox.getChildren().addAll(statsPanel, moreStatsButton);
            
        return statsBox;
    }
    
    private void showMoreStats() {
        StatisticsViewer statisticsViewer = mainViewer.getStatisticsViewer();
        
        if (statisticsViewer == null) {
            statisticsViewer = new StatisticsViewer(mainViewer.getSelectedMinPrice(), mainViewer.getSelectedMaxPrice());
            statisticsViewer.show();
        }
        updateStats();
    }
    
    private void updateStats() {
        StatisticsViewer statisticsViewer = mainViewer.getStatisticsViewer();
        
        if (statisticsViewer == null) {
            return;
        }
        
        int selectedMinPrice = mainViewer.getSelectedMinPrice();
        int selectedMaxPrice = statisticsViewer.getCurrentMaxPrice();
        
        if (statisticsViewer.getCurrentMinPrice() != selectedMinPrice || statisticsViewer.getCurrentMaxPrice() != selectedMaxPrice) {
            statisticsViewer.update(selectedMinPrice, selectedMaxPrice);
        }
    }
    
    private GridPane alignItemsInGridPane(GridPane grid) {
        for (Node node : grid.getChildren()) {
            grid.setHalignment(node, HPos.CENTER);
            grid.setValignment(node, VPos.CENTER);
            node.maxWidth(Double.MAX_VALUE);
            node.maxHeight(Double.MAX_VALUE);
        }
        return grid;
    }
    
    public Pane getPane() {
        return mapPane;
    }
}
