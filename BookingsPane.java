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

/**
 * Write a description of class BookingsPane here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BookingsPane extends MainViewerPane
{  
    private static Pane bookingsPane;
    
    public BookingsPane(MainViewerNEW mainViewer)
    {
        super(mainViewer);
        titleName = "Bookings";
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
            editButton.setOnAction(e -> editBooking());
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
        bookingListing.setPadding(new Insets(10));
        bookingListing.setMargin(centerPane, new Insets(0,110,0,0));
        
        return bookingListing;
    }
    
    private void editBooking()  {

    
    }
    
    private void contactAction(Booking booking)  {
        Desktop desktop;
        if (Desktop.isDesktopSupported() 
            && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
            try {
                URI mailto = new URI("mailto:" + booking.getProperty().getMailHost_name(false) + "@kcl.ac.uk?subject=About%20the%20booking%20between%20" + booking.getCheckInDate().toString() + "%20and%20" + booking.getCheckOutDate().toString() + "&body=Hello%20" + booking.getProperty().getMailHost_name(true) + ",%0A%0DI%20need%20to%20edit%20the%20booking%20between%20" + booking.getCheckInDate().toString() + "%20and%20" + booking.getCheckOutDate().toString() + "%20in%20the%20property%20called%20'" + booking.getProperty().getMailName() + "'%20(Property%20iD%20:" + booking.getProperty().getId() + ")%0A%0D");
                desktop.mail(mailto);
            }
            catch (Exception e) {}
        } else {
          // TODO fallback to some Runtime.exec(..) voodoo?
          throw new RuntimeException("desktop doesn't support mailto; mail is dead anyway ;)");
        }
    }
    private void cancelBookingAction(Booking booking)  {
        DataHandler.removeToBookingList(booking);
        mainViewer.setPane(4);
    }
    
    public Pane getPane() {
        return bookingsPane;
    }
}
