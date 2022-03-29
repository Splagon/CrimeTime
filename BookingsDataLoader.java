import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.io.FileReader;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.time.LocalDate;
import java.net.URISyntaxException;
import java.io.IOException;
import java.io.FileWriter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Loads the data concerning the user's bookings
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 25/03/22
 */
public class BookingsDataLoader
{
    /**
     * Loads the bookings data file to the application.
     * 
     * @param bookingsDataFileName The file name of the bookings data file.
     */
    public ArrayList<Booking> load(String bookingsDataFileName) 
    {
        ArrayList<Booking> bookings = new ArrayList<Booking>();
        
        try 
        {
            URL bookingsDataURL = getClass().getResource(bookingsDataFileName);
            CSVReader reader = new CSVReader(new FileReader(new File(bookingsDataURL.toURI()).getAbsolutePath()));
            
            String [] line;
            
            //skip the first row (column headers)
            reader.readNext();
            
            while ((line = reader.readNext()) != null) 
            {
                if (line[0].length() > 0)
                {
                    String propertyID = line[0];
                    int grandTotal = Integer.valueOf(line[1]);
                    LocalDate checkInDate = LocalDate.parse(line[2]);
                    LocalDate checkOutDate = LocalDate.parse(line[3]);
    
                    Booking booking = new Booking(DataHandler.getProperty(propertyID), grandTotal, checkInDate, checkOutDate);
                    
                    //adds to the list of bookings
                    bookings.add(booking);
                }
            }
        }
        // throws an error if there is an interruption or the URI is wrong
        catch(IOException | URISyntaxException e) 
        {
            Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Failure! Something went wrong");
                alert.setContentText("Unfortunately, the application is unable to\nopen the bookings file.");
            alert.show();
        }
        // if the file does not exist, create one
        catch(NullPointerException e) 
        {
            File file = new File(System.getProperty("user.dir") + "/bookingsData.csv");
            
            try 
            {
                FileWriter fileWriter = new FileWriter(file);
                CSVWriter writer = new CSVWriter(fileWriter);
                
                // adds column headers
                writer.writeNext(new String[] {"Property ID", "Grand Total of Booking", "Check In Date", "Check Out Date"});
                writer.close();
            }
            catch (IOException ex) 
            {
                Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("Failure! Something went wrong");
                    alert.setContentText("Unfortunately, the application is unable to\ncreate a new bookings file.");
                alert.show();
            }
        }
        
        return bookings;
    }
}
