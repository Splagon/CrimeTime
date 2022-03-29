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
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 25/03/22
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
    
    public void openBookingWindow(AirbnbListing listing) 
    {
        ArrayList<Booking> bookingsAtProperty = bookingsAtProperty(listing);
        
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
                    
                Label grandTotalLabel = new Label("The price for your stay is: £" + updateGrandTotal(checkIn.getValue(), checkOut.getValue()));
                    checkIn.setOnAction(e -> checkOut.setValue(checkIn.getValue().plusDays(listing.getMinimumNights())));
                    checkOut.setOnAction(e -> grandTotalLabel.setText("The price for your stay is: £" + updateGrandTotal(checkIn.getValue(), checkOut.getValue())));
                    
                    final Callback<DatePicker, DateCell> dayCellFactoryOut = new Callback<DatePicker, DateCell>() {
                        @Override
                        public DateCell call(final DatePicker datePicker) {
                            return new DateCell() {
                                @Override
                                public void updateItem(LocalDate item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (item.isBefore(checkIn.getValue()))
                                    {
                                        setDisable(true);
                                        setStyle("-fx-background-color: #ffc0cb;");
                                    } 
                                    else if(item.isAfter(checkIn.getValue().minusDays(1)) && item.isBefore(checkIn.getValue().plusDays(listing.getMinimumNights())))
                                    {
                                        setDisable(true);
                                        setStyle("-fx-background-color: #ffa07a;");
                                    } 
                                    else if (item.isAfter(checkIn.getValue().plusDays(listing.getMinimumNights()).minusDays(1)) && item.isBefore(checkOut.getValue()))
                                    {
                                        setStyle("-fx-background-color: #90ee90;");
                                    }
                                    
                                    for (Booking booking : bookingsAtProperty)
                                    {
                                        if (item.isAfter(booking.getCheckInDate().minusDays(1)) && item.isBefore(booking.getCheckOutDate().plusDays(1)))
                                        {
                                            setDisable(true);
                                            setStyle("-fx-background-color: #ffc0cb;");
                                        }
                                    }
                                }      
                            };
                        }
                    };
                    
                    final Callback<DatePicker, DateCell> dayCellFactoryIn = new Callback<DatePicker, DateCell>() 
                    {
                        @Override
                        public DateCell call(final DatePicker datePicker) 
                        {
                            return new DateCell() 
                            {
                                @Override
                                public void updateItem(LocalDate item, boolean empty) 
                                {
                                    super.updateItem(item, empty);
                                    
                                    if (item.isBefore(LocalDate.now())) 
                                    {
                                        setDisable(true);
                                        setStyle("-fx-background-color: #ffc0cb;");
                                    } 
                                    
                                    for (Booking booking : bookingsAtProperty)
                                    {
                                        if (item.isAfter(booking.getCheckInDate().minusDays(1)) && item.isBefore(booking.getCheckOutDate().plusDays(1)))
                                        {
                                            setDisable(true);
                                            setStyle("-fx-background-color: #ffc0cb;");
                                        }
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
    
    private ArrayList<Booking> bookingsAtProperty(AirbnbListing listing)
    {
        ArrayList<Booking> bookingsAtProperties = new ArrayList<Booking>();
        
        for (Booking booking : DataHandler.getBookingList())
        {
            if (booking.getPropertyID().equals(listing.getId()))
            {
                bookingsAtProperties.add(booking);
            }
        }
        
        return bookingsAtProperties;
    }
    
    private int updateGrandTotal(LocalDate checkIn, LocalDate checkOut) {
        return property.getPrice()*(int)(Booking.calculateDuration(checkIn, checkOut));
    }
    
    private void confirmationAction(int grandTotal, LocalDate checkinDate, LocalDate checkoutDate) {
        bookingStage.close();
        
        Booking newBooking = new Booking(property, grandTotal, checkinDate, checkoutDate);
        DataHandler.addToBookingList(newBooking);
        
        if (parent.getClass().equals(MainViewer.class)) {
            MainViewer mainViewer = (MainViewer) parent;
            mainViewer.refreshPane();
        }
    }
    
    private void goBackAction() {
        bookingStage.close();
    }
}
