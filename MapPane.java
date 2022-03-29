import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.scene.control.ComboBox;
import javafx.scene.Node;
import javafx.scene.shape.*;
import javafx.scene.image.*;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.effect.ColorAdjust;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;


/**
 * Displays the pane containing the map and some stats.
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 29/03/22
 */
public class MapPane extends MainViewerPane
{   
    private static Pane mapPane;
    
    //Holds the hexagonal map of the boroughs
    private static AnchorPane mapView;
    
    // the stage which holds the stats viewer
    private StatisticsViewer statisticsViewer;
    
    /**
     * Constructor for objects of class MapPane
     * 
     * @param MainViewer mainViewer - used to call methods within the MainViewer
     */
    public MapPane(MainViewer mainViewer)
    {
        super(mainViewer);
        titleName = "Map of London";
        hasMinMaxBox = true;
    }

    /**
     * Builds the map pane
     */
    public void makePane() 
    {
        BorderPane window = new BorderPane();
        
            VBox infoPane = new VBox();
                Label titleLabel = new Label("Boroughs of London");
                    titleLabel.getStyleClass().add("windowTitle");
                VBox stats = createStatsPanel();
                GridPane key = createKey();
                
            VBox priceChanger = new VBox();
                HBox minMaxBox = mainViewer.createMinMaxBox();
                    minMaxBox = mainViewer.setInitialMinMaxBoxSelection(minMaxBox);
                
            Button confirm = (Button) minMaxBox.getChildren().get(2);
            
            ComboBox<String> minBox = (ComboBox<String>) minMaxBox.getChildren().get(0);
            ComboBox<String> maxBox = (ComboBox<String>) minMaxBox.getChildren().get(1);
            
            //styling the min and max box as well as the confirm button for the map panel
            window.getStylesheets().add("stylesheet.css");
            confirm.getStyleClass().add("confirmForMap");
            minBox.getStyleClass().add("mapMinMaxBoxes");
            maxBox.getStyleClass().add("mapMinMaxBoxes");
            minMaxBox.getStyleClass().add("mapMinMaxBox");
                
            infoPane.getChildren().addAll(titleLabel, key, stats);
            infoPane.setPadding(new Insets(10, 20, 10, 10));
            infoPane.setSpacing(15);
            
            priceChanger.getChildren().add(minMaxBox);
            priceChanger.setPadding(new Insets(10, 0, 0, 0));
            
            window.setLeft(infoPane);
            
            window.setCenter(mapView);
                window.setAlignment(mapView, Pos.CENTER);
        
        mapPane = window;
    }
    
    /**
     * Makes the borough hexagon map
     */
    public void makeHexagonMap() 
    {
        String[][] mapPositions = StatisticsData.getMapPositions();
        
        int minPrice = mainViewer.getSelectedMinPrice();
        int maxPrice = mainViewer.getSelectedMaxPrice();
        
        NoOfPropertiesStats noOfPropertiesStats = new NoOfPropertiesStats(minPrice, maxPrice);
        
        // calculates the appropriate height and width of the map dependent
        // on the current height and width of the scene
        double sceneWidth = mainViewer.getMainScene().getWidth();
        double sceneHeight = mainViewer.getMainScene().getHeight();
        
        final double WIDTH_TO_HEIGHT_RATIO = 725.0 / 510.0;
        final double HEIGHT_TO_WIDTH_RATIO = 1 / WIDTH_TO_HEIGHT_RATIO;
        
        double newWidth = sceneWidth * 0.655;
        double newHeight = sceneHeight * 0.800;
        
        double newWidthToHeightRatio = newWidth/newHeight;

        // pane is narrow; height needs to be adjusted to fit
        if (newWidthToHeightRatio < WIDTH_TO_HEIGHT_RATIO) {
            newHeight = newWidth * HEIGHT_TO_WIDTH_RATIO;
        }
        // pane is too wide; width needs to be adjusted to fit
        else if (newWidthToHeightRatio > WIDTH_TO_HEIGHT_RATIO) {
            newWidth = newHeight * WIDTH_TO_HEIGHT_RATIO;
        }
        
        // size of the gap between hexagons
        final double gapSize = 7.0;
            
        mapView = new AnchorPane();
            mapView.setMaxSize(newWidth, newHeight);
            
            //calculates the largest amount of hexagons on a line
            double maxHexagonsPerLine = 0;
            for (int i = 0; i < mapPositions.length; i++)
            {
                if (maxHexagonsPerLine < mapPositions[i].length)
                {
                    maxHexagonsPerLine = mapPositions[i].length;
                }
            }
            
            //total amount of gaps on a line
            final double gapsPerLine = gapSize * (maxHexagonsPerLine + 1);
            final double hexagonWidth = (int) ((newWidth - gapsPerLine) / (maxHexagonsPerLine + 0.5) );
            
            //rows
            for (int m = 0; m < mapPositions.length; m++) {
                
                FlowPane row = new FlowPane();
                   row.setHgap(gapSize);
                   row.setMinWidth(newWidth);
                   row.setMaxWidth(newWidth);
                
                // adds an offset rectangle at the start of the row every other
                // line which is half the size of a hexagon
                if (m % 2 == 0) {
                        createInsetRectangle(hexagonWidth, row, gapSize);
                }
                
                //columns
                for (int n = 0; n < mapPositions[m].length; n++) 
                {
                    // adds an invisible spacer to the row if there is no borough in
                    // that location.
                    if (mapPositions[m][n] == null) 
                    {
                        StackPane rowSpace = new StackPane();
                        Rectangle emptySpace = createSpacerRectangle((int) hexagonWidth);
                        rowSpace.getChildren().add(emptySpace);
                        row.getChildren().add(rowSpace);
                    }
                    // if there is a borough in that location, add a borough to that location
                    else
                    {
                        StackPane tempRowSpace = new StackPane();
                        
                        String boroughName = mapPositions[m][n];
                        
                        // creates the map button
                        MapButton boroughButton = new MapButton(boroughName);
                            boroughButton.setShape(new Circle(hexagonWidth));
                            boroughButton.setMinSize(hexagonWidth*0.97, hexagonWidth*0.85);
                            boroughButton.setFont(new Font(boroughButton.getFont().getName(), 0.21 * hexagonWidth));
                            boroughButton.setOnAction(e -> openPropertyViewer(boroughButton.getBoroughName()));
                        
                        // creates the black hexagon outline
                        ImageView hexagonOutline = new ImageView(new Image("/hexagonOutline.png", true));
                            hexagonOutline.setFitWidth(hexagonWidth);
                            hexagonOutline.setFitHeight(hexagonWidth);
                        
                        // creates the infill for the hexagon with the correct colour
                        ImageView hexagonFilledImage = new ImageView(new Image("/hexagonFilledGreen.png"));
                        ImageView hexagonFilled = setHexagonFilledColour(hexagonFilledImage, boroughButton.getBoroughName(), (int) hexagonWidth, noOfPropertiesStats);
                            
                        tempRowSpace.getChildren().addAll(hexagonFilled, hexagonOutline, boroughButton);
                        
                        // adds the correct styling and adds the animation to the stackpane.
                        final StackPane rowSpace = new StackPane();
                        if (StatisticsData.getPropertiesFromBorough(boroughName, minPrice, maxPrice).size() > 0)
                        {
                            Animations animations = new Animations();
                            boroughButton.setOnMouseEntered(e -> animations.spinAndGrowIn(500, rowSpace));
                            boroughButton.getStyleClass().add("boroughButton");
                        }
                        else
                        {
                            // no boroughs in property
                            boroughButton.getStyleClass().add("boroughButtonEmpty");
                        }
                        
                        rowSpace.getChildren().addAll(hexagonFilled, hexagonOutline, boroughButton);
                        row.getChildren().add(rowSpace);
                    }
                }
                
                // adds an offset rectangle at the end of the row every other
                // line which is half the size of a hexagon
                if (m % 2 == 1) 
                {
                   createInsetRectangle(hexagonWidth, row, gapSize);
                }
                
                // sets the height of the row to align with the other rows
                final double heightOffset = m * (0.75 * hexagonWidth + gapSize);
                
                AnchorPane.setTopAnchor(row, heightOffset);
                
                mapView.getChildren().add(row);
            }
        
        // updates the stats if the stats viewer is open
        updateStats();
    }
    
    /**
     * Creates a transparent inset spacer half the size of a normal hexagon
     * 
     * @param hexagonWidth The width of a hexagon
     * @param row The row to add the inset to
     * @param gapSize The size of the gaps between the hexagons
     */
    private void createInsetRectangle(double hexagonWidth, FlowPane row, double gapSize) 
    {
        StackPane rowSpace = new StackPane();
        
        final int insetSpacerWidth = (int) ((hexagonWidth - gapSize) / 2.0);
        Rectangle insetSpace = createSpacerRectangle(insetSpacerWidth);
        
        rowSpace.getChildren().add(insetSpace);
        row.getChildren().add(rowSpace);
    }
    
    /**
     * Creates a transparent rectangle of the size entered to be used as a spacer
     * 
     * @param widthHeight The width and height of the rectangle.
     * 
     * @return The rectangle to be used as a spacer
     */
    private Rectangle createSpacerRectangle(int widthHeight) 
    {
        Rectangle spacerRectangle = new Rectangle(widthHeight, widthHeight);
        spacerRectangle.setFill(Color.TRANSPARENT);
        return spacerRectangle;
    }
    
    /**
     * Opens the property viewer for that borough and shows properties within
     * the selected price range.
     * 
     * @param boroughName The name of the borough to show the properties of.
     */
    private void openPropertyViewer(String boroughName) 
    {
        // opens the property viewer if there are properties within the borough
        try 
        {
            PropertyViewer propertyViewer = new PropertyViewer(boroughName, mainViewer.getSelectedMinPrice(), mainViewer.getSelectedMaxPrice(), null);  
        }
        // otherwise an warning alert is shown
        catch (IndexOutOfBoundsException e) 
        {
            Alert alert = new Alert(AlertType.WARNING);
                alert.setHeaderText("No Available Properties in " + boroughName);
                alert.setContentText("Unfortunately, there are no available properties in this\nborough within your price range. Welcome to the London\nhousing market...");
            alert.show();
        }
    }
    
    /**
     * Applies and adjusts the colour of the hexagon dependent on the borough's properties
     * 
     * @param hexagon The hexagon image to adjust the colour of.
     * @param boroughName The name of the borough to get the colour of.
     * @param heightWidth The width and height of the hexagon.
     * @param noOfPropertiesStats The stats pertaining to the overall boroughs.
     * 
     * @return A filled hexagon with the correct colour applied.
     */
    private ImageView setHexagonFilledColour(ImageView hexagon, String boroughName, int heightWidth, NoOfPropertiesStats noOfPropertiesStats) 
    {
        ColorAdjust shader = StatisticsData.getBoroughMapColour(boroughName, noOfPropertiesStats);
            
        hexagon.setFitWidth(heightWidth);
        hexagon.setFitHeight(heightWidth);
            
        hexagon.setEffect(shader);
        
        return hexagon;
    }
    
    /**
     * Applies and adjusts the colour of the hexagon dependent on the percentile
     * 
     * @param hexagon The hexagon image to adjust the colour of.
     * @param heightWidth The width and height of the hexagon.
     * @param percentile The percentile to get the colour of.
     * 
     * @return A filled hexagon with the correct colour applied.
     */
    private ImageView setHexagonFilledColour(ImageView hexagon, int heightWidth, int percentile) 
    {
        ColorAdjust shader = StatisticsData.getBoroughMapColour(percentile);
            
        hexagon.setFitWidth(heightWidth);
        hexagon.setFitHeight(heightWidth);
            
        hexagon.setEffect(shader);
        
        return hexagon;
    }
    
    /**
     * Creates the key panel within the map pane
     * 
     * @return A GridPane containing the key panel
     */
    private GridPane createKey() 
    {
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
        

        for (int i = 0; i < 5; i++)
        {
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
    
    /**
     * Creates the stats panel within the map pane
     * 
     * @return A VBox containing the stats panel
     */
    private VBox createStatsPanel() 
    {
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
    
    public StatisticsViewer getStatisticsViewer() 
    {
        return statisticsViewer;
    }
    
    /**
     * Used by the 'Show More Stats!' button to display the statistics viewer.
     */
    private void showMoreStats() 
    {
        if (statisticsViewer == null) 
        {
            statisticsViewer = new StatisticsViewer(mainViewer.getSelectedMinPrice(), mainViewer.getSelectedMaxPrice());
            statisticsViewer.show();
            
            //sets the statistics viewer variable in this class to null if the stats viewer is closed
            statisticsViewer.setOnCloseRequest(e -> { statisticsViewer = null; });
        }
        
        updateStats();
    }
    
    /**
     * Updates the statistics viewer when the map is updated/selected price is changed.
     */
    private void updateStats() 
    {
        if (statisticsViewer == null) 
        {
            return;
        }
        
        int selectedMinPrice = mainViewer.getSelectedMinPrice();
        int selectedMaxPrice = mainViewer.getSelectedMaxPrice();
        
        if (statisticsViewer.getCurrentMinPrice() != selectedMinPrice || statisticsViewer.getCurrentMaxPrice() != selectedMaxPrice)
        {
            statisticsViewer.update(selectedMinPrice, selectedMaxPrice);
        }
    }
    
    /**
     * Aligns all the nodes in the grid to be central.
     * 
     * @param grid The gridPane to align the nodes of.
     * 
     * @return GridPane The gridPane with aligned nodes.
     */
    private GridPane alignItemsInGridPane(GridPane grid) 
    {
        for (Node node : grid.getChildren()) 
        {
            grid.setHalignment(node, HPos.CENTER);
            grid.setValignment(node, VPos.CENTER);
            node.maxWidth(Double.MAX_VALUE);
            node.maxHeight(Double.MAX_VALUE);
        }
        
        return grid;
    }
    
    /**
     * returns the pane
     * 
     * @return it will return type Pane
     */
    public Pane getPane() 
    {
        return mapPane;
    }
}
