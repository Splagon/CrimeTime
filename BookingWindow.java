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
import javafx.scene.control.Tooltip;

/**
 * Class responsible for the booking window. 
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 25/03/22
 */
public class BookingWindow extends Stage
{
    private Stage bookingStage;
    // holds an instance of the stage the booking window is opened from.
    private Stage parent;
    // Property that is being booked. 
    private AirbnbListing property;
    
    /**
     * Constructor of booking window.
     * 
     * @param property The property that is being booked.
     * @param parent The stage by which the booking window has been opened.
     */
    public BookingWindow (AirbnbListing property, Stage parent) 
    {
        this.property = property;
        this.parent = parent;
        openBookingWindow(property);
    }
    
    /**
     * Retrieves all the bookings of a particular property.
     * 
     * @AirbnbListing listing The property whose bookings are retrieved.
     */
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
    
    /**
     * Creates the booking window. The name of the property is shown at the top,
     * then two date pickers are used in the center of the pane and finally a 
     * "confirm booking" and "go back" buttons have been added to the bottom. 
     * 
     * @param listing The property that is currently being booked.
     */
    public void openBookingWindow(AirbnbListing listing) 
    {
        ArrayList<Booking> bookingsAtProperty = getBookingsAtProperty(listing);
        
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
                    // we set the initial date of the checkout date picker to the day after the minimum nights of the property.
                    checkOut.setValue(checkIn.getValue().plusDays(listing.getMinimumNights()));
                    gridPane.add(checkOut, 1, 1);
                // Each time the check in date is modified, we set the check out date to: check in date + Min nights 
                checkIn.setOnAction(e -> checkOut.setValue(checkIn.getValue().plusDays(listing.getMinimumNights())));
                    
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
                                        LocalDate checkInDate = booking.getCheckInDate();
                                        LocalDate checkoutDate = booking.getCheckOutDate();
                                        int minNights = booking.getProperty().getMinimumNights();
                                        
                                        if (item.isAfter(checkInDate.minusDays(minNights + 1)) && item.isBefore(checkoutDate))
                                        {
                                            setDisable(true);
                                            setStyle("-fx-background-color: #ffc0cb;");
                                        }
                                    }
                                }      
                            };
                        }
                    };
                    
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
                                        LocalDate checkInDate = booking.getCheckInDate();
                                        LocalDate checkOutDate = booking.getCheckOutDate();
                                        int minNights = booking.getProperty().getMinimumNights();

                                        if (item.isAfter(checkInDate.minusDays(1)) && item.isBefore(checkOutDate))
                                        {
                                            setDisable(true);
                                            setStyle("-fx-background-color: #ffc0cb;");
                                        }
                                    }
                                }      
                            };
                        }
                    };
                
                    // sets the behavior of the date pickers
                    checkIn.setDayCellFactory(dayCellFactoryIn);
                    checkOut.setDayCellFactory(dayCellFactoryOut);
                    
                    Label grandTotalLabel = new Label("The price for your stay is: £" + updateGrandTotal(checkIn.getValue(), checkOut.getValue()));
                        // Each time a check out date has been selected, we update the price of the stay 
                        checkOut.setOnAction(e -> grandTotalLabel.setText("The price for your stay is: £" + updateGrandTotal(checkIn.getValue(), checkOut.getValue())));
                    
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
        // Makes the parent window unclickable when bookingStage is open.
        bookingStage.initModality(Modality.WINDOW_MODAL);
        bookingStage.initOwner(parent);
        bookingStage.show();
    }
    
    /**
     * Get the bookings of a particular property.
     * 
     * @param listing The property we want to get the bookings from.
     */
    private ArrayList<Booking> getBookingsAtProperty(AirbnbListing listing)
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
    
     /**
     * Update the total amount of the user's stay, depending on its check-in and out.
     * 
     * @param checkIn The date the users checks-in.
     * @param checkOut The date the users checks-out.
     * @return The duration in days between two dates
     */
    private int updateGrandTotal(LocalDate checkIn, LocalDate checkOut) 
    {
        return property.getPrice()*(int)(Booking.calculateDuration(checkIn, checkOut));
    }
    
    /**
     * A new Booking has been made. Adds it to booking list, closes booking stage and shows confirmation.
     * 
     * @param grandTotal The price of the stay.
     * @param checkinDate The check-In date selected by the user.
     * @param checkoutDate The check-Out date selected by the user.
    */
    private void confirmationAction(int grandTotal, LocalDate checkinDate, LocalDate checkoutDate) 
    {
        bookingStage.close();
        
        Booking newBooking = new Booking(property, grandTotal, checkinDate, checkoutDate);
        DataHandler.addToBookingList(newBooking);
        
        if (parent.getClass().equals(MainViewer.class)) 
        {
            MainViewer mainViewer = (MainViewer) parent;
            mainViewer.refreshPane();
            showConfirmationStage(true);
        }
        else
        {
            showConfirmationStage(false);
        }
    }
    
    /**
     * Displays confimation window. 
     */
    private void showConfirmationStage(boolean isEdit) {
        Stage confirmationStage = new Stage();
        confirmationStage.setTitle("Confirmation Window");
        
        VBox root = new VBox();
        
            Label confirmationLabel;
            if(isEdit){
                confirmationLabel = new  Label("Your modifications have been taken \n into consideration.");
            }else{
                confirmationLabel = new  Label("Thank you for booking with us !");
            }
            confirmationLabel.getStyleClass().add("subLabels");
            
            Button closeButton = new Button("Close");
                closeButton.setOnAction(e -> confirmationStage.close());
                
        root.getChildren().addAll(confirmationLabel, closeButton);
        root.setSpacing(30);
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("rootPV");
        
        int width = 400;
        
        Scene scene = new Scene(root,width,130);
        scene.getStylesheets().add("stylesheet.css");
            confirmationStage.setX(parent.getX() + (parent.getWidth() - width)/2);
            confirmationStage.setY(parent.getY() + parent.getHeight()/2);
        confirmationStage.setScene(scene);
        confirmationStage.show();
    }
    
    /**
     * Closes the booking window.
     */
    private void goBackAction() {
        bookingStage.close();
    }
}
