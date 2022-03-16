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

/**
 * JavaFX version of PVGUI in code
 */
public class PropertyViewer extends Stage {
    
    private DataHandler dataHandler = new DataHandler();
    private ArrayList<AirbnbListing> properties;
    
    private int currentPropertyIndex;
    
    private String borough;
    private String sortedBy;
    private int minPrice;
    private int maxPrice;
    
    private GridPane info;
    private Label hostLabel, priceLabel, noOfReviewsLabel, roomTypeLabel, minNightsLabel, descriptionLabel;
    private Button prevButton, nextButton, infoButton;
    
    private final String hostPrefix = "Host: ";
    private final String pricePrefix = "Price: £";
    private final String noOfReviewsPostfix = " review(s)";
    private final String minNightsPostfix = " night(s) minimum";
    
    private WebEngine webEngine;
    
    private Stage descriptionStage;
    
    public PropertyViewer(String borough, int minPrice, int maxPrice, String sortedBy) throws Exception {
        this.borough = borough;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.sortedBy = sortedBy;
        currentPropertyIndex = 0;
        makePropertyViewerScene();
    }

    private void makePropertyViewerScene() throws Exception {
        setTitle("Neighbourhood: " + borough);
        
        if(sortedBy != null){
            properties = dataHandler.getPropertiesSortedBy(borough, minPrice, maxPrice, sortedBy);
        }else {
            properties = dataHandler.getPropertiesFromBorough(borough, minPrice, maxPrice);
        }
        
        BorderPane root = new BorderPane();
        root.setId("rootPV");
        root.setPadding(new Insets(0, 10, 0, 10));
        root.setPrefSize(600, 400);
        
        // Create scene for the Vbox
        Scene scene = new Scene(root);
        scene.getStylesheets().add("stylesheet.css");
        
        HBox topPane = new HBox();
        topPane.setId("hbox");
        
            Label titleLabel = new Label("Welcome to the property viewer!");
            titleLabel.getStyleClass().add("title");
                
            VBox filterPane = new VBox();
            
                ComboBox<String> menu = new ComboBox<>();
                    // menu.getItems().add("Price ↑");
                    // menu.getItems().add("Price ↓");
                    // menu.getItems().add("Name ↑");
                    // menu.getItems().add("Name ↓");
                    // menu.getItems().add("Reviews ↑");
                    // menu.getItems().add("Reviews ↓");
                    menu.getItems().addAll("Price ↑", "Price ↓", "Name ↑", "Name ↓", "Reviews ↑", "Reviews ↓");
                menu.setOnAction(e -> { 
                                            sortedBy = menu.getValue();
                                            try { sortAction(); }
                                            catch (Exception ev) {}
                                      }); 
            
                Label filterLabel =  new Label("Filter by:");
                filterLabel.getStyleClass().add("title");
                
            filterPane.setAlignment(Pos.CENTER);    
            filterPane.getChildren().addAll(filterLabel, menu); 
                
        topPane.setPrefHeight(60);         
        topPane.getChildren().addAll(titleLabel, filterPane);   
        root.setTop(topPane);
        
        
        WebView googleMaps = new WebView();
            root.setMargin(googleMaps, new Insets(0,10,0,10));
            webEngine = googleMaps.getEngine();
        root.setCenter(googleMaps);
        
        prevButton = new Button("Previous");
            prevButton.setOnAction(e -> viewPreviousProperty());
            prevButton.setPrefSize(130, 230);
        prevButton.getStyleClass().add("buttonsPV");
        root.setLeft(prevButton);
        root.setAlignment(prevButton, Pos.CENTER);
        
        nextButton = new Button("Next");
            nextButton.setOnAction(e -> viewNextProperty());
            nextButton.setPrefSize(130, 230);
        nextButton.getStyleClass().add("buttonsPV");
        root.setRight(nextButton);
        root.setAlignment(nextButton, Pos.CENTER);
        
        infoButton = new Button("Description");
            infoButton.setOnAction(e -> popUpAction());
        infoButton.getStyleClass().add("buttonsPV");
        
        info = new GridPane();
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
    
    private void update() {
        AirbnbListing listing = properties.get(currentPropertyIndex);
        
        hostLabel.setText(hostPrefix + listing.getHost_name());
        priceLabel.setText(pricePrefix + listing.getPrice());
        noOfReviewsLabel.setText(listing.getNumberOfReviews() + noOfReviewsPostfix);
        roomTypeLabel.setText(listing.getRoom_type());
        minNightsLabel.setText(listing.getMinimumNights() + minNightsPostfix);
        descriptionLabel.setText(listing.getName());
        
        if(descriptionStage != null){
            descriptionStage.close();
            descriptionStage = null;
        }
        
        String url = "https://api.mapbox.com/styles/v1/mapbox/outdoors-v11/static/pin-s-heart+285A98("+listing.getLongitude()+","+listing.getLatitude()+")/"+listing.getLongitude()+","+listing.getLatitude()+",12,0/600x460@2x?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw";
        webEngine.load(url);
    }
    
    private void viewNextProperty() {
        currentPropertyIndex++;
        
        if (currentPropertyIndex > properties.size() - 1) {
            currentPropertyIndex = 0;
        }
        
        update();
    }
    
    private void viewPreviousProperty() {
        currentPropertyIndex--;
        
        if (currentPropertyIndex < 0) {
            currentPropertyIndex = properties.size() -  1;
        }
        
        update();
    }
    
    private void popUpAction(){
        if(descriptionStage == null){
            createDescriptionStage();
        }else{
            descriptionStage.close();
            descriptionStage = null;
        }
    }
    
    private void createDescriptionStage(){
        descriptionStage = new Stage();
        descriptionStage.setTitle("Description!");
        descriptionStage.setX(200);
        descriptionStage.setY(200);
        
        VBox root = new VBox();
        root.getChildren().add(descriptionLabel);
        root.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(root,300,100);
        descriptionStage.setResizable(false);
        descriptionStage.setScene(scene);
        descriptionStage.showAndWait();        
    }
    
    private void sortAction() throws Exception {
        Stage stage = new PropertyViewer(borough, minPrice, maxPrice, sortedBy);
        this.close();
        stage.show();
    }
}

