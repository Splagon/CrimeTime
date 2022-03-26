import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.geometry.*;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import java.awt.Desktop;
import java.net.URI;
import java.time.LocalDate;
import java.io.IOException;
import java.net.URISyntaxException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * A class the represetns the bookings pane in the main viewer.
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 25/03/22
 */
public class BookingsPane extends MainViewerPane
{  
    private static Pane bookingsPane;
    
    /**
     * Constructor of BookingsPane 
     * @param mainViewer The parent of the class
     */
    public BookingsPane(MainViewer mainViewer)
    {
        super(mainViewer);
        titleName = "Bookings";
        hasMinMaxBox = false;
    }
    
    public void makePane() {
            //creating the pane for the bookings and adding any styling
        BorderPane pane = new BorderPane();
        
        VBox contentPane = new VBox();
                
                HBox hbox = new HBox();
                    Label windowTitle = new Label("Your bookings: ");
                    windowTitle.getStyleClass().add("windowTitle");
                hbox.getChildren().add(windowTitle);
                hbox.setAlignment(Pos.CENTER);
                
                ScrollPane scrollPane = new ScrollPane();
                    VBox bookingsPanel = new VBox();
                        ArrayList<Booking> bookingList = DataHandler.getBookingList();
                        if (! bookingList.isEmpty()) {
                            for(Booking booking : bookingList) {
                                BorderPane bookingListing = createBookingListing(booking);
                                    bookingListing.getStyleClass().add("bookingListing");
                                    bookingsPanel.setMargin(bookingListing, new Insets(10));
                                bookingsPanel.getChildren().add(bookingListing);
                            }
                        }
                        else {
                            Label noBookingsLabel = new Label("There are no bookings currently...");
                            bookingsPanel.getChildren().add(noBookingsLabel);
                        }       
                scrollPane.setContent(bookingsPanel);
                scrollPane.setFitToWidth(true);
        
        contentPane.getChildren().add(hbox);
        contentPane.getChildren().add(scrollPane);
        contentPane.setSpacing(20);

        pane.setCenter(contentPane);
        
        bookingsPane = pane;
    }
    
    private BorderPane createBookingListing(Booking booking) {
        AirbnbListing property = booking.getProperty();
        Label propertyName = new Label("Property: " + property.getName());  
        Label hostName = new Label("Host name: " + property.getHost_name());
        Label dates = new Label("Between: " + booking.getCheckInDate().toString()  +  " and " + booking.getCheckOutDate().toString());
        Label durationLabel = new Label("Duration:  " + booking.getDuration() + " night(s)");
        Label priceLabel = new Label("Price:  £" + booking.getGrandTotal());
        
        //styling the labels
        propertyName.getStyleClass().add("bookingListingLabels");
        hostName.getStyleClass().add("bookingListingLabels");
        dates.getStyleClass().add("bookingListingLabels");
        durationLabel.getStyleClass().add("bookingListingLabels");
        priceLabel.getStyleClass().add("bookingListingLabels");
        
        Button editButton = new Button("Edit Booking");
            editButton.setPrefSize(140, 20);
            editButton.setOnAction(e -> editBooking(booking));
            editButton.getStyleClass().add("smallWindowButtons");
        Button contactButton = new Button("Contact Host");
            contactButton.setPrefSize(140, 20);
            contactButton.setOnAction(e -> contactAction(booking));
            contactButton.getStyleClass().add("smallWindowButtons");
        Button cancelButton = new Button("Cancel Booking");
            cancelButton.setPrefSize(140, 20);
            cancelButton.setOnAction(e -> cancelBookingAction(booking));
            cancelButton.getStyleClass().add("smallWindowButtons");
        
        BorderPane bookingListing = new BorderPane();
            VBox centerPane = new VBox(propertyName, hostName, dates, durationLabel, priceLabel);
                centerPane.setSpacing(5);
                centerPane.setAlignment(Pos.CENTER_LEFT);
            VBox rightPane = new VBox(editButton, contactButton, cancelButton);
                rightPane.setSpacing(20);
                rightPane.setAlignment(Pos.CENTER);
        bookingListing.setCenter(centerPane);
        bookingListing.setRight(rightPane);
        
        return bookingListing;
    }
    
    private void editBooking(Booking booking) {
        AirbnbListing bookingProperty = booking.getProperty();
        
        DataHandler.removeFromBookingList(booking);
        
        BookingWindow bookingWindow = new BookingWindow(bookingProperty, mainViewer);
    }
    
    private void contactAction(Booking booking)  {
        Desktop desktop;
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.MAIL))
        {
            try 
            {
                URI mailto = new URI("mailto:" + booking.getProperty().getMailHost_name(false) + "@kcl.ac.uk?subject=About%20the%20booking%20between%20" + booking.getCheckInDate().toString() + "%20and%20" + booking.getCheckOutDate().toString() + "&body=Hello%20" + booking.getProperty().getMailHost_name(true) + ",%0A%0DI%20need%20to%20edit%20the%20booking%20between%20" + booking.getCheckInDate().toString() + "%20and%20" + booking.getCheckOutDate().toString() + "%20in%20the%20property%20called%20'" + booking.getProperty().getMailName() + "'%20(Property%20iD%20:" + booking.getProperty().getId() + ")%0A%0D");
                Desktop.getDesktop().mail(mailto);
            }
            catch (IOException | URISyntaxException e) 
            {
                showEmailNotWorkingAlert("Issue opening Mail");
            }
        } 
        else 
        {
            showEmailNotWorkingAlert("OS is not supported");
        }
    }
    
    private void showEmailNotWorkingAlert(String titleText) 
    {
        Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText(titleText);
            alert.setContentText("Unfortunately, the application is unable to\naccess your email to create a draft email.");
        alert.show(); 
    }
    
    private void cancelBookingAction(Booking booking)  {
        DataHandler.removeFromBookingList(booking);
        mainViewer.refreshPane();
    }
    
    public Pane getPane() {
        return bookingsPane;
    }
}
