import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.geometry.*;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import java.net.URL;
import javax.script.*;
import javafx.scene.Node;
import javafx.stage.Popup;
import javafx.scene.layout.VBox;
import javafx.scene.control.ComboBox;
import javafx.collections.ObservableList;
import javafx.stage.*;
import javafx.event.EventHandler;

/**
 * Property viewer is the class that is responsible 
 * for building the property viewer stage in the application. 
 * 
 * @author AppMakers
 * @version 1.0
 */
public class PropertyViewer extends Stage {
    private DataHandler dataHandler = new DataHandler();
    // List that will hold all the properties displayed
    private ArrayList<AirbnbListing> properties;
    // The index of the current property displayed in "properties"
    private int currentPropertyIndex;
    // The borough to which the properties displayed belong
    private String borough;
    // The element by which the "properties" list is sorted
    private String sortedBy;
    // The minimum porperty price
    private int minPrice;
    // The maximum porperty price
    private int maxPrice;
    // Labels needed to display a property's characteristics  
    private Label hostLabel, priceLabel, noOfReviewsLabel, roomTypeLabel, minNightsLabel, descriptionLabel;
    //Prefixes displayed on scene
    private final String hostPrefix = "Host: ";
    private final String pricePrefix = "Price: £";
    private final String noOfReviewsPostfix = " review(s)";
    private final String minNightsPostfix = " night(s) minimum";
    // WebEngine object capable of managing one web page
    private WebEngine webEngine;
    // Stage object for managing properties' description window
    private Stage descriptionStage;
    /**
     * Constructor of property viewer stage.
     * 
     * @param borough The borough whose properties are displayed
     * @param minPrice The minimum price of the properties displayed
     * @param maxprice The maximum price of the properties displayed
     * @param sortedBy The element by which the "properties" list is sorted
     */
    public PropertyViewer(String borough, int minPrice, int maxPrice, String sortedBy) throws Exception {
        // Initialize the Property Viewer fields
        this.borough = borough;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.sortedBy = sortedBy;
        currentPropertyIndex = 0;
        
        makePropertyViewerScene();
        
        this.setOnCloseRequest(windowEvent -> this.closePropertyViewer());
    }
    
    /**
     * The property viewer architecture is as follows. The root of the stage 
     * is a border pane that contains contains 5 nodes: at the top there is a 
     * Hbox, at the center a map, at the right and left buttons and finally 
     * at the bottom a grid pane.
     */
    private void makePropertyViewerScene() throws Exception {
        setTitle("Neighbourhood: " + borough);
        // Generates the list of properties that will be displayed depending 
        // on the borough, price range and sorting parameter selected by the user.
        if(sortedBy != null){
            properties = dataHandler.getPropertiesSortedBy(borough, minPrice, maxPrice, sortedBy);
        }else {
            properties = dataHandler.getPropertiesFromBorough(borough, minPrice, maxPrice);
        }
        
        BorderPane root = new BorderPane();
        root.setId("rootPV");
        root.setPadding(new Insets(0, 10, 0, 10)); // Sets the right and left padding of the pane
        
        // Create scene for the border pane with width = 600 and height = 400.
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add("stylesheet.css"); // Set the scene's stylesheet
        
        HBox topPane = new HBox();
        topPane.setId("hbox");
        
            Label titleLabel = new Label("Welcome to the property viewer!");
            titleLabel.getStyleClass().add("title"); // add a ".title" class in scene's stylesheet
            
            ComboBox<String> menu = new ComboBox<>();
            menu.setId("menu");
                menu.getItems().addAll("Price ↑", "Price ↓", "Name ↑", "Name ↓", "Reviews ↑", "Reviews ↓");
                // Makes the sorting element chosen written in the box
                if (sortedBy != null) {
                    menu.getSelectionModel().select(sortedBy);
                } 
                else {
                    menu.getSelectionModel().select("Filter by:");
                }
            menu.setOnAction(e -> { 
                                      sortedBy = menu.getValue(); // Initialize sortedby variable
                                      try { sortAction(); }
                                      catch (Exception ev) {}
                                  }); 
                  
        topPane.setPrefHeight(60);         
        topPane.getChildren().addAll(titleLabel, menu);   
        root.setTop(topPane);
        
        
        WebView map = new WebView();
            root.setMargin(map, new Insets(0,10,0,10));
            webEngine = map.getEngine();
        root.setCenter(map);
    
        
        Button prevButton = new Button("Previous");
            prevButton.setOnAction(e -> viewPreviousProperty());
            prevButton.setPrefSize(130, 230);
        prevButton.getStyleClass().add("buttonsPV");
        root.setAlignment(prevButton, Pos.CENTER);
        root.setLeft(prevButton);
      
        
        Button nextButton = new Button("Next");
            nextButton.setOnAction(e -> viewNextProperty());
            nextButton.setPrefSize(130, 230);
        nextButton.getStyleClass().add("buttonsPV");
        root.setAlignment(nextButton, Pos.CENTER);
        root.setRight(nextButton);
        
        
        GridPane info = new GridPane();
        
            info.setAlignment(Pos.CENTER);
            info.setPrefWidth(600);
            
            hostLabel = new Label();
            priceLabel = new Label();
            noOfReviewsLabel = new Label();
            roomTypeLabel = new Label();
            minNightsLabel = new Label();
            descriptionLabel = new Label();
            
            hostLabel.getStyleClass().add("propertyViewerInfoLabels");
            priceLabel.getStyleClass().add("propertyViewerInfoLabels");
            noOfReviewsLabel.getStyleClass().add("propertyViewerInfoLabels");
            roomTypeLabel.getStyleClass().add("propertyViewerInfoLabels");
            minNightsLabel.getStyleClass().add("propertyViewerInfoLabels");
            
            Button infoButton = new Button("Description");
                infoButton.setOnAction(e -> popUpAction());
            infoButton.getStyleClass().add("buttonsPV");
        
            info.add(hostLabel, 0, 0);
            info.add(priceLabel, 0, 1);
            info.add(infoButton, 1, 0);
            info.add(noOfReviewsLabel, 1, 1);
            info.add(roomTypeLabel, 2, 0);
            info.add(minNightsLabel, 2, 1);
            
            info.setConstraints(hostLabel, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER);
            info.setConstraints(priceLabel, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER);
            info.setConstraints(infoButton, 1, 0, 1, 1, HPos.CENTER, VPos.CENTER);
            info.setConstraints(noOfReviewsLabel, 1, 1, 1, 2, HPos.CENTER, VPos.CENTER);
            info.setConstraints(roomTypeLabel, 2, 0, 1, 1, HPos.CENTER, VPos.CENTER);
            info.setConstraints(minNightsLabel, 2, 1, 1, 1, HPos.CENTER, VPos.CENTER);
            
            ColumnConstraints column1 = new ColumnConstraints();
            ColumnConstraints column2 = new ColumnConstraints();
            ColumnConstraints column3 = new ColumnConstraints();
            column1.setPercentWidth(100/3);
            column2.setPercentWidth(100/3);
            column3.setPercentWidth(100/3);
            info.getColumnConstraints().addAll(column1, column2, column3);
            
            info.setPadding(new Insets(20));

        root.setBottom(info);
        
        update();
        
        setResizable(false);
        setScene(scene);
        show();
    }
    
    /**
     * Updates the variables of the property displayed.  
     */
    private void update() {
        // first, we retrieve the pproperty at the current index
        AirbnbListing listing = properties.get(currentPropertyIndex);
        // then, we update the variables
        hostLabel.setText(hostPrefix + listing.getHost_name());
        priceLabel.setText(pricePrefix + listing.getPrice());
        noOfReviewsLabel.setText(listing.getNumberOfReviews() + noOfReviewsPostfix);
        roomTypeLabel.setText(listing.getRoom_type());
        minNightsLabel.setText(listing.getMinimumNights() + minNightsPostfix);
        descriptionLabel.setText(listing.getName());
        // if the user didn't close the description window of the previous property, we close it.
        if(descriptionStage != null){
            closeDescription();
        }
        // Used MapBox api to load a map pointing the location of current property displayed.
        String url = "https://api.mapbox.com/styles/v1/mapbox/outdoors-v11/static/pin-s-heart+285A98("+listing.getLongitude()+","+listing.getLatitude()+")/"+listing.getLongitude()+","+listing.getLatitude()+",12,0/600x460@2x?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw";
        webEngine.load(url);
    }
    
    /**
     * Displays the next property. 
     */
    private void viewNextProperty() {
        currentPropertyIndex++;
        
        if (currentPropertyIndex > properties.size() - 1) {
            currentPropertyIndex = 0;
        }
        
        update();
    }
    
    /**
     * Displays the previous property.
     */
    private void viewPreviousProperty() {
        currentPropertyIndex--;
        
        if (currentPropertyIndex < 0) {
            currentPropertyIndex = properties.size() -  1;
        }
        
        update();
    }
    
    /**
     *  Open or close the description window of the property displayed.
     */
    private void popUpAction(){
        if (descriptionStage == null) 
        {
            createDescriptionStage();
        }
        else
        {
            // the description window is already opened, we close it.
            closeDescription();
        }
    }
    
    /**
     * Creates the stage of the description window.
     */
    private void createDescriptionStage() {
        descriptionStage = new Stage();
        descriptionStage.setTitle("Description!");
        
        VBox root = new VBox();
            root.getChildren().add(descriptionLabel);
        root.setAlignment(Pos.CENTER);
        
        int width = 300;
        int height = 100;
        
        Scene scene = new Scene(root,width,height);
        descriptionStage.setScene(scene);
        
        descriptionStage.setX((this.getWidth() - width) / 2 + this.getX());
        descriptionStage.setY(this.getY() + this.getHeight() + 20);
        
        descriptionStage.showAndWait();        
    }
    
    /**
     * On sorting request, a new property wiewer stage is 
     * created with the sorting condition taken into account.
     */
    private void sortAction() throws Exception {
        // We open the stage at the saame positions of the initial one, for better UX.
        double currentStagePositionX = this.getX();
        double currentStagePositionY = this.getY();
        // New property viewer stage is created.
        Stage stage = new PropertyViewer(borough, minPrice, maxPrice, sortedBy);
        stage.setX(currentStagePositionX);
        stage.setY(currentStagePositionY);
        // if description window is opened, we close it for better UX.
        closeDescription();
        
        this.close();
        stage.show();
    }
    
    /**
     * Method executed when the user closes the PV window.
     */
    public void closePropertyViewer() {
        // We close description window whenever PV is closed for better UX.
        closeDescription();
    }
    
    /**
     * Close the description window.
     */
    private void closeDescription() {
        if (descriptionStage != null) {
            descriptionStage.close();
            descriptionStage = null;
        }
    }
}

