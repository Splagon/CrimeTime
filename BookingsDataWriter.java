import java.util.ArrayList;
import java.net.URL;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.io.FileReader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Writes new bookings to the data file
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 25/03/22
 */
public class BookingsDataWriter
{
    /**
     * Writes a singular booking to the file.
     * 
     * @param booking Booking to write.
     * @param bookingsDataFileName File name of the bookings data.
     */
    public void write(Booking booking, String bookingsDataFileName) 
    {
        // try writing the booking to the file
        try 
        {
            URL bookingsDataURL = getClass().getResource(bookingsDataFileName);
            CSVWriter writer = new CSVWriter(new FileWriter(new File(bookingsDataURL.toURI()).getAbsolutePath(), true));

            writer.writeNext(booking.convertToCSV());
            
            writer.close();
        }
        catch (IOException | URISyntaxException | NullPointerException e)
        {
            showFileNotFoundAlert();
        }
    }
    
    /**
     * Clears and rewrites the bookings in the list to the file
     * 
     * @param bookingList List of bookings to write.
     * @param bookingsDataFileName File name of the bookings data.
     * @param itemsRemoved How many items have been removed from the list.
     */
    public void write(ArrayList<Booking> bookingsList, String bookingsDataFileName, int itemsRemoved) 
    {
        try 
        {
            URL bookingsDataURL = getClass().getResource(bookingsDataFileName);
            
            String file = new File(bookingsDataURL.toURI()).getAbsolutePath();
            
            //skip column headers
            CSVReader reader = new CSVReader(new FileReader(file));
            String[] columnHeaders = reader.readNext();
            reader.close();
            
            CSVWriter writer = new CSVWriter(new FileWriter(file));
            writer.writeNext(columnHeaders);
            int numOfColums = columnHeaders.length;
            
            // write bookings to file
            for (Iterator i = bookingsList.iterator(); i.hasNext();) 
            {
                Booking nextBooking = (Booking) i.next();
                writer.writeNext(nextBooking.convertToCSV());
            }
            
            // clear further columns that may contain data
            for (int j = 0; j < itemsRemoved; j++) 
            {
                writer.writeNext(new String[numOfColums]);
            }
            
            writer.close();
        }
        catch (IOException | URISyntaxException e)
        {
            showFileNotFoundAlert();
        }
    }
    
    /**
     * Displays an alert if the file hasn't been found.
     */
    private void showFileNotFoundAlert() 
    {
        Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText("Failure! Something went wrong");
            alert.setContentText("Unfortunately, the application is unable to\nopen the bookings file.");
        alert.show();
    }
}
