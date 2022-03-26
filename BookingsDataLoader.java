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
 * Write a description of class BookingsDataLoader here.
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 25/03/22
 */
public class BookingsDataLoader
{
    public ArrayList<Booking> load(String bookingsDataFileName) {
        //System.out.print("Begin loading Airbnb london dataset...");
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
                    bookings.add(booking);
                }
            }
        } 
        catch(IOException | URISyntaxException e) 
        {
            Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Failure! Something went wrong");
                alert.setContentText("Unfortunately, the application is unable to\nopen the bookings file.");
            alert.show();
        } 
        catch(NullPointerException e) 
        {
            File file = new File(System.getProperty("user.dir") + "/bookingsData.csv");
            try 
            {
                FileWriter fileWriter = new FileWriter(file);
                CSVWriter writer = new CSVWriter(fileWriter);
                
                //column headers
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
        
        //System.out.println("Success! Number of loaded bookings: " + bookings.size());
        
        return bookings;
    }
}
