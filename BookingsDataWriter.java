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

/**
 * Write a description of class BookingsDataWriter here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BookingsDataWriter
{
    public void write(Booking booking, String bookingsDataFileName) {
        try 
        {
            URL bookingsDataURL = getClass().getResource(bookingsDataFileName);
            CSVWriter writer = new CSVWriter(new FileWriter(new File(bookingsDataURL.toURI()).getAbsolutePath(), true));

            writer.writeNext(booking.convertToCSV());
            
            writer.close();
        }
        catch (IOException | URISyntaxException e)
        {
            System.out.println("Failure! Something went wrong with loading bookings file");
        }
        catch (NullPointerException e)
        {
            System.out.println("Bookings file not found");
        }
    }
    
    public void write(ArrayList<Booking> bookingsList, String bookingsDataFileName) {
        try 
        {
            URL bookingsDataURL = getClass().getResource(bookingsDataFileName);
            
            String file = new File(bookingsDataURL.toURI()).getAbsolutePath();
            
            CSVReader reader = new CSVReader(new FileReader(file));
            String[] columnHeaders = reader.readNext();
            reader.close();
            
            CSVWriter writer = new CSVWriter(new FileWriter(file));
            writer.writeNext(columnHeaders);
            
            for (Iterator i = bookingsList.iterator(); i.hasNext();) {
                Booking nextBooking = (Booking) i.next();
                writer.writeNext(nextBooking.convertToCSV());
            }
            writer.close();
        }
        catch (IOException | URISyntaxException e)
        {
            System.out.println("Failure! Something went wrong with loading bookings file");
        }
    }
}
