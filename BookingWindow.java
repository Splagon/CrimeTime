import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.control.DatePicker;
import javafx.geometry.*;
import java.time.LocalDate;
import javafx.stage.Modality;
import javafx.util.Callback;
import javafx.scene.control.DateCell;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Write a description of class BookingWindow here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BookingWindow extends Stage
{
    private Stage bookingStage;
    private Stage parent;
    private AirbnbListing property;
    private MainViewer mainViewer;
    private boolean isUpdating;
    
    public BookingWindow (AirbnbListing property, Stage parent) 
    {
        this.property = property;
        this.mainViewer = mainViewer;
        this.parent = parent;
        openBookingWindow(property);
    }
    
    public void openBookingWindow(AirbnbListing listing) {
 
        bookingStage = new Stage();
        bookingStage.setTitle("Booking Window");
        
        BorderPane root = new BorderPane();
        root.getStyleClass().add("rootBooking");
        
            Label propertyLabel  = new Label(listing.getName());
            root.setAlignment(propertyLabel, Pos.CENTER);
            propertyLabel.setId("propertyLabel");
            
        root.setTop(propertyLabel);
            
            VBox vbox = new VBox();
            
            GridPane gridPane = new GridPane();
             gridPane.setAlignment(Pos.CENTER);
             gridPane.setHgap(10);
             gridPane.setVgap(10);
                
                Label checkInlabel = new Label("Check-In Date:");
                    gridPane.add(checkInlabel, 0, 0);
                DatePicker checkIn =  new DatePicker();
                    checkIn.setValue(LocalDate.now());
                    gridPane.add(checkIn, 0, 1);
                
                Label checkOutlabel = new Label("Check-Out Date:");
                    gridPane.add(checkOutlabel, 1, 0);
                DatePicker checkOut = new DatePicker();
                    checkOut.setValue(checkIn.getValue().plusDays(listing.getMinimumNights()));
                    gridPane.add(checkOut, 1, 1);
                    
                Label grandTotalLabel = new Label("The price for your stay is: £" + listing.getPrice());
                    checkIn.setOnAction(e -> checkOut.setValue(checkIn.getValue().plusDays(listing.getMinimumNights())));
                    checkOut.setOnAction(e -> grandTotalLabel.setText("The price for your stay is: £" + updateGrandTotal(checkIn.getValue(), checkOut.getValue())));
                    
                    final Callback<DatePicker, DateCell> dayCellFactoryOut = new Callback<DatePicker, DateCell>() {
                        @Override
                        public DateCell call(final DatePicker datePicker) {
                            return new DateCell() {
                                @Override
                                public void updateItem(LocalDate item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (item.isBefore(checkIn.getValue())) {
                                        setDisable(true);
                                        setStyle("-fx-background-color: #ffc0cb;");
                                    } else if(item.isAfter(checkIn.getValue().minusDays(1)) && item.isBefore(checkIn.getValue().plusDays(listing.getMinimumNights()))){
                                        setDisable(true);
                                        setStyle("-fx-background-color: #ffa07a;");
                                    } else if (item.isAfter(checkIn.getValue().plusDays(listing.getMinimumNights()).minusDays(1)) && item.isBefore(checkOut.getValue())){
                                        setStyle("-fx-background-color: #90ee90;");
                                    }
                                }      
                            };
                        }
                    };
                    
                    final Callback<DatePicker, DateCell> dayCellFactoryIn = new Callback<DatePicker, DateCell>() {
                        @Override
                        public DateCell call(final DatePicker datePicker) {
                            return new DateCell() {
                                @Override
                                public void updateItem(LocalDate item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (item.isBefore(LocalDate.now())) {
                                        setDisable(true);
                                        setStyle("-fx-background-color: #ffc0cb;");
                                    }  
                                }      
                            };
                        }
                    };
                    
                    checkIn.setDayCellFactory(dayCellFactoryIn);
                    checkOut.setDayCellFactory(dayCellFactoryOut);
                    
              vbox.setAlignment(Pos.CENTER);
              vbox.getChildren().addAll(gridPane, grandTotalLabel);
              vbox.setSpacing(80);
            
        root.setCenter(vbox);
            
            AnchorPane bottomPane = new AnchorPane();
        
                Button bookButton = new Button("Confirm Booking");
                    bookButton.setOnAction(e -> confirmationAction(updateGrandTotal(checkIn.getValue(), checkOut.getValue()),checkIn.getValue(), checkOut.getValue()));  
                    bookButton.getStyleClass().add("smallWindowButtons");
                bottomPane.setRightAnchor(bookButton, 0.0);

                Button goBackButton = new Button("Go Back");
                    goBackButton.setOnAction(e -> goBackAction());
                    goBackButton.getStyleClass().add("smallWindowButtons");
                bottomPane.setLeftAnchor(goBackButton, 0.0);
            
            bottomPane.getChildren().addAll(bookButton, goBackButton);
            
        root.setBottom(bottomPane);
        
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add("stylesheet.css");
            
        bookingStage.setScene(scene);
        
        bookingStage.initModality(Modality.WINDOW_MODAL);
        bookingStage.initOwner(parent);
        bookingStage.show();
    }
    
    private int updateGrandTotal(LocalDate checkIn, LocalDate checkOut) {
        return property.getPrice()*(int)(Booking.calculateDuration(checkIn, checkOut));
    }
    
    private void confirmationAction(int grandTotal, LocalDate checkinDate, LocalDate checkoutDate) {
        showConfirmationStage();
        bookingStage.close();
        
        Booking newBooking = new Booking(property, grandTotal, checkinDate, checkoutDate);
        DataHandler.addToBookingList(newBooking);
        
        if (parent.getClass().equals(MainViewer.class)) {
            MainViewer mainViewer = (MainViewer) parent;
            mainViewer.refreshPane();
        }
    }
    
    private void updateBooking(Booking booking) {
        ArrayList<Booking> bookingsList = DataHandler.getBookingList();
        
        boolean found = false;
        for (int i = 0; i < bookingsList.size() && found == false; i++) 
        {
            Booking currentBooking = bookingsList.get(i);
            if (currentBooking.getPropertyID() == property.getId()) {
                //Booking updatedBooking
                //bookingsList.set(i, 
                
                found = true;
            }
        }
        
        if (parent.getClass().equals(MainViewer.class)) {
            MainViewer mainViewer = (MainViewer) parent;
            mainViewer.refreshPane();
        }
    }
    
    private void showConfirmationStage() {
        Stage confirmationStage = new Stage();
        confirmationStage.setTitle("Description!");
        
        VBox root = new VBox();
        
            Label confirmationLabel = new  Label("Thank you for booking with us !");
            confirmationLabel.getStyleClass().add("subLabels");
            
            Button closeButton = new Button("Close");
                closeButton.setOnAction(e -> confirmationStage.close());
                
        root.getChildren().addAll(confirmationLabel, closeButton);
        root.setSpacing(30);
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("rootPV");
        
        int width = 300;
        
        Scene scene = new Scene(root,width,100);
        scene.getStylesheets().add("stylesheet.css");
        confirmationStage.setScene(scene);
            confirmationStage.setX(this.getX() + (this.getWidth() - width)/2);
            confirmationStage.setY(this.getY() + this.getHeight()/2);
        confirmationStage.show();
    }
    
    private void goBackAction() {
        MainViewer.setStagePosititon(parent, bookingStage);
        parent.show();
        bookingStage.close();
    }
}
