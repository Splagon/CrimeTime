import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.io.FileReader;
import com.opencsv.CSVReader;
import java.time.LocalDate;
import java.net.URISyntaxException;
import java.io.IOException;

/**
 * Write a description of class BookingsDataLoader here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BookingsDataLoader
{
        public ArrayList<Booking> load() {
        System.out.print("Begin loading Airbnb london dataset...");
        ArrayList<Booking> bookings = new ArrayList<Booking>();
        try{
            URL url = getClass().getResource("airbnb-london.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String propertyID = line[0];
                String grandTotal = line[1];
                LocalDate checkInDate = LocalDate.parse(line[2]);
                LocalDate checkOutDate = LocalDate.parse(line[3]);

                Booking booking = new Booking(DataHandler.getProperty(propertyID), grandTotal, checkInDate, checkOutDate);
                bookings.add(booking);
            }
        } catch(IOException | URISyntaxException e){
            System.out.println("Failure! Something went wrong with loading bookings");
            e.printStackTrace();
        }
        System.out.println("Success! Number of loaded bookings: " + bookings.size());
        return bookings;
    }
}
