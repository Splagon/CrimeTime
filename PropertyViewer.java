import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.*;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.geometry.*;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import java.net.URL;
//import javax.script.*;
//import javafx.scene.Node;
//import javafx.stage.Popup;
import javafx.scene.layout.VBox;
import javafx.scene.control.ComboBox;
//import javafx.collections.ObservableList;
import javafx.stage.*;
//import javafx.event.EventHandler;
//import java.net.URL;
import java.net.URLConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import javafx.util.Callback;
import javafx.scene.control.DateCell;
import javafx.scene.control.Tooltip;
import java.time.temporal.ChronoUnit;
import java.io.IOException;

/**
 * Property viewer is the class that is responsible for displaying the properties 
 * within each borough. Properties are displayed one by one. The user can sort the 
 * properties by ascending or descending order, by name, price or number of reviews.
 * The location of the property displayed is shown on a map. The user can book a 
 * property through the property viewer.
 * 
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 *         
 * @version 25/03/22
 */
public class PropertyViewer extends Stage {
    // List that holds the properties of a predetermined borough within a particular price range
    private ArrayList<AirbnbListing> properties;
    // The index of the current property displayed in "properties"
    private int currentPropertyIndex;
    // The borough whose properties are displayed
    private String borough;
    // The element by which the "properties" list is sorted
    private String sortedBy;
    // The minimum porperty price
    private int minPrice;
    // The maximum porperty price
    private int maxPrice;
    // Labels needed to display a property's characteristics  
    private Label hostLabel, priceLabel, noOfReviewsLabel, roomTypeLabel, minNightsLabel, descriptionLabel, propertyID;
    // Prefixes displayed on scene
    private final String hostPrefix = "Host: ";
    private final String pricePrefix = "Price: £";
    private final String noOfReviewsPostfix = " review(s)";
    private final String minNightsPostfix = " night(s) minimum";
    // WebEngine object capable of managing one web page
    private WebEngine webEngine;
    // Stage object for managing properties' description and booking windows
    private Stage descriptionStage;
    // Holds applcation's internet connectivity status
    private boolean applicationConnected;
    /**
     * Constructor of property viewer stage.
     * 
     * @param borough The borough whose properties are displayed
     * @param minPrice The minimum price of the properties displayed
     * @param maxprice The maximum price of the properties displayed
     * @param sortedBy The element by which the "properties" list is sorted
     */
    public PropertyViewer(String borough, int minPrice, int maxPrice, String sortedBy) 
    {
        // Initialize the Property Viewer fields
        this.borough = borough;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.sortedBy = sortedBy;
        currentPropertyIndex = 0;
        applicationConnected = true;
       
        makePropertyViewerScene(null);
         
        this.setOnCloseRequest(windowEvent -> this.closePropertyViewer());
    }
    
    public PropertyViewer(Booking booking)
    {
        this.borough = booking.getProperty().getNeighbourhood();
        
        makePropertyViewerScene(booking);
    }
    
    /**
     * The property viewer architecture is as follows: The root of the stage 
     * is a border pane that contains contains 5 nodes: at the top there is a 
     * Hbox containing the title and the sorting comboBox, at the center the map, 
     * at the right and left buttons to let the user change property and finally 
     * at the bottom a grid pane displaying characteristics of the property.
     */
    private void makePropertyViewerScene(Booking booking) 
    {
        setTitle("Neighbourhood: " + borough);
        // Generates the list of properties that will be displayed depending 
        // on the borough, price range and sorting parameter selected by the user.
        
        if (booking != null)
        {
            properties = new ArrayList<AirbnbListing>();
            properties.add(booking.getProperty());
        }
        else if (sortedBy != null)
        {
            properties = DataHandler.getPropertiesSortedBy(borough, minPrice, maxPrice, sortedBy);
        }
        else 
        {
            properties = DataHandler.getPropertiesFromBorough(borough, minPrice, maxPrice);
        }
        
        BorderPane root = new BorderPane();
        root.getStyleClass().add("rootPV");
        root.setPadding(new Insets(0, 10, 0, 10)); // Sets the right and left padding of the pane
        
        // Create scene for the border pane with width = 700 and height = 400.
        Scene scene = new Scene(root, 700, 400);
        scene.getStylesheets().add("stylesheet.css"); // Set the scene's stylesheet
        
        HBox topPane = new HBox();
        topPane.setId("hbox");
        
            Label titleLabel = new Label("Welcome to the property viewer!");
            titleLabel.getStyleClass().add("title"); // add a ".title" class in scene's stylesheet
            
            ComboBox<String> menu = new ComboBox<>();
            menu.setId("menu");
                menu.getItems().addAll("Price ↑", "Price ↓", "Name ↑", "Name ↓", "Reviews ↑", "Reviews ↓");
                // Makes the sorting element chosen written in the box
                if (sortedBy != null) 
                {
                    menu.getSelectionModel().select(sortedBy);
                } 
                else 
                {
                    menu.getSelectionModel().select("Filter by:");
                }
            menu.setOnAction(e -> menuButtonAction(menu));
                  
        topPane.setPrefHeight(60);         
        topPane.getChildren().addAll(titleLabel, menu);   
        root.setTop(topPane);
        
        // ------ Map -------- //
        
        WebView map = new WebView();
            root.setMargin(map, new Insets(0,10,0,10));
            webEngine = map.getEngine();
        root.setCenter(map);
        
        // ------ Next and Previous Buttons -------- //
        
        Button prevButton = new Button("Previous");
            prevButton.setOnAction(e -> viewPreviousProperty());
            prevButton.setPrefSize(130, 230);
        prevButton.getStyleClass().add("smallWindowButtons");
        root.setAlignment(prevButton, Pos.CENTER);
        root.setLeft(prevButton);
        
        Button nextButton = new Button("Next");
            nextButton.setOnAction(e -> viewNextProperty());
            nextButton.setPrefSize(130, 230);
        nextButton.getStyleClass().add("smallWindowButtons");
        root.setAlignment(nextButton, Pos.CENTER);
        root.setRight(nextButton);
        
        if(properties.size() == 1) 
        {
            nextButton.setDisable(true);
            prevButton.setDisable(true);
            // We display a short message to inform the user why the buttons are disabled 
            nextButton.setTooltip(new Tooltip("There are no other properties available in this borough"));
            prevButton.setTooltip(new Tooltip("There are no other properties available in this borough"));
        }
        
        GridPane info = new GridPane();
        
            info.setAlignment(Pos.CENTER);
            
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
            
            Button descriptionButton = new Button("Description");
                descriptionButton.getStyleClass().add("smallWindowButtons");
                descriptionButton.setOnAction(e -> popUpAction());
            
            Button bookingButton = new Button("Book property");
                bookingButton.getStyleClass().add("smallWindowButtons");
                bookingButton.setOnAction(e -> openBookingWindow(properties.get(currentPropertyIndex)));
            
            HBox hbox = new HBox();
                hbox.setSpacing(5);
            hbox.getChildren().addAll(descriptionButton, bookingButton);
            
            info.add(hostLabel, 0, 0);
            info.add(priceLabel, 0, 1);
            info.add(noOfReviewsLabel, 1, 0);
            info.add(hbox, 1, 1);
            info.add(roomTypeLabel, 2, 0);
            info.add(minNightsLabel, 2, 1);
            
            info.setConstraints(hostLabel, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER);
            info.setConstraints(priceLabel, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER);
            info.setConstraints(noOfReviewsLabel, 1, 0, 1, 1, HPos.CENTER, VPos.CENTER);
            info.setConstraints(hbox, 1, 1, 1, 1, HPos.CENTER, VPos.CENTER);
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
        if (applicationConnected) 
        {
            show();
        }
    }
    
    /**
     * Updates the variables of the property displayed.  
     */
    private void update() 
    {
        // first, we retrieve the property at the current index
        AirbnbListing listing = properties.get(currentPropertyIndex);
        // then, we update the variables
        hostLabel.setText(hostPrefix + listing.getHost_name());
        
        if (listing.getHost_name().equals(""))
        {
            hostLabel.setText("No data");
        }
        
        priceLabel.setText(pricePrefix + listing.getPrice());
        noOfReviewsLabel.setText(listing.getNumberOfReviews() + noOfReviewsPostfix);
        roomTypeLabel.setText(listing.getRoom_type());
        minNightsLabel.setText(listing.getMinimumNights() + minNightsPostfix);
        descriptionLabel.setText(listing.getName());
        
        // if the user didn't close the description window of the previous property, we close it.
        if(descriptionStage != null) 
        {
            closeDescription();
        }
        
        // Used MapBox api to load a map pointing the location of current property displayed.
        try  
        {
            URL url = new URL("https://api.mapbox.com/styles/v1/mapbox/outdoors-v11/static/pin-s-heart+285A98("+listing.getLongitude()+","+listing.getLatitude()+")/"+listing.getLongitude()+","+listing.getLatitude()+",13,0/890x500@2x?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw");
            URLConnection connection = url.openConnection();
            connection.connect(); // test internet connectivity, if no connection: throws an exception
            webEngine.load(url.toString());
        } 
        catch (IOException e) 
        {
            noConnectionAlert();
            this.close();
            applicationConnected = false;
        }
    }
    
    private void menuButtonAction(ComboBox<String> menu) 
    {
        sortedBy = menu.getValue(); // Initialize sortedby variable
        sortAction();
    }
    
    /**
     * Displays the next property in the properties' list.
     */
    private void viewNextProperty() 
    {
        currentPropertyIndex++;
        
        if (currentPropertyIndex > properties.size() - 1) 
        {
            currentPropertyIndex = 0;
        }
        
        update();
    }
    
    /**
     * Displays the previous property in the properties' list.
     */
    private void viewPreviousProperty() 
    {
        currentPropertyIndex--;
        
        if (currentPropertyIndex < 0) 
        {
            currentPropertyIndex = properties.size() -  1;
        }
        
        update();
    }
    
    /**
     *  Open or close the description window of the property displayed.
     */
    private void popUpAction()
    {
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
    private void createDescriptionStage() 
    {
        descriptionStage = new Stage();
        descriptionStage.setTitle("Description!");
        
        VBox root = new VBox();
            descriptionLabel.getStyleClass().add("subLabels");
            root.getChildren().add(descriptionLabel);
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("rootPV");
        
        int width = 300;
        int height = 100;
        
        Scene scene = new Scene(root,width,height);
        scene.getStylesheets().add("stylesheet.css");
        descriptionStage.setScene(scene);
        
        descriptionStage.setX((this.getWidth() - width) / 2 + this.getX());
        descriptionStage.setY(this.getY() + this.getHeight() + 20);
        
        descriptionStage.show();        
    }
    
    /**
     * On sorting request, a new property wiewer stage is 
     * created with the sorting condition taken into account.
     */
    private void sortAction() 
    {
        // New property viewer stage is created.
        Stage stage = new PropertyViewer(borough, minPrice, maxPrice, sortedBy);
        // We open the stage at the same positions of the initial one, for better UX.
        stage = MainViewer.setStagePosititon(stage, this);
        // if description window is opened, we close it for better UX.
        closeDescription();
        
        this.close();
        stage.show();
    }
    
    /**
     * Method executed when the user closes the PV window.
     */
    public void closePropertyViewer() 
    {
        // We close description window whenever PV is closed for better UX.
        closeDescription();
    }
    
    /**
     * Close the description window.
     */
    private void closeDescription() 
    {
        if (descriptionStage != null) 
        {
            descriptionStage.close();
            descriptionStage = null;
        }
    }
    
    /**
     * Displays an alert showing the user that he is not connected to internet.
     */
    private void noConnectionAlert()
    {
        Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText("No internet Connection");
            alert.setContentText("Unfortunately you will need a stable internet connection \n to run the property viewer correctly, we apologise \n for the inconvenience caused. \n Come back !");
        alert.show();
    }
    

    /**
     * Creates the booking window. The name of the property is shown at the top,
     * then two date pickers are used in the center of the pane and finally a 
     * "confirm booking" and "go back" buttons have been added to the bottom. 
     * 
     * @param bookingProperty The property that is currently being booked.
     */
    private void openBookingWindow(AirbnbListing bookingProperty) 
    {
        BookingWindow bookingWindow = new BookingWindow(bookingProperty, this);
    }
}
