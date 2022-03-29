import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import com.opencsv.CSVReader;
import java.net.URISyntaxException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Loads the data from the Airbnb csv file
 * 
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 25/03/22
 */
public class AirbnbDataLoader {
    /** 
     * Return an ArrayList containing the rows in the AirBnB London data set csv file.
     */
    public ArrayList<AirbnbListing> load() {
        //System.out.print("Begin loading Airbnb london dataset...");
        ArrayList<AirbnbListing> listings = new ArrayList<AirbnbListing>();
        try 
        {
            URL url = getClass().getResource("airbnb-london.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            
            String [] line;
            
            //skip the first row (column headers)
            reader.readNext();
            
            while ((line = reader.readNext()) != null) 
            {
                String id = line[0];
                String name = line[1];
                String host_id = line[2];
                String host_name = line[3];
                String neighbourhood = line[4];
                double latitude = convertDouble(line[5]);
                double longitude = convertDouble(line[6]);
                String room_type = line[7];
                int price = convertInt(line[8]);
                int minimumNights = convertInt(line[9]);
                int numberOfReviews = convertInt(line[10]);
                String lastReview = line[11];
                double reviewsPerMonth = convertDouble(line[12]);
                int calculatedHostListingsCount = convertInt(line[13]);
                int availability365 = convertInt(line[14]);

                AirbnbListing listing = new AirbnbListing(id, name, host_id,
                        host_name, neighbourhood, latitude, longitude, room_type,
                        price, minimumNights, numberOfReviews, lastReview,
                        reviewsPerMonth, calculatedHostListingsCount, availability365
                    );
                listings.add(listing);
            }
        } 
        catch(IOException | URISyntaxException e)
        {
            Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Failure! Something went wrong");
                alert.setContentText("Unfortunately, the application is unable to\nopen the list of properties.");
            alert.show();
        }
        //System.out.println("Success! Number of loaded records: " + listings.size());
        return listings;
    }

    /**
     *
     * @param doubleString the string to be converted to Double type
     * @return the Double value of the string, or -1.0 if the string is 
     * either empty or just whitespace
     */
    private Double convertDouble(String doubleString){
        if(doubleString != null && !doubleString.trim().equals("")){
            return Double.parseDouble(doubleString);
        }
        return -1.0;
    }

    /**
     *
     * @param intString the string to be converted to Integer type
     * @return the Integer value of the string, or -1 if the string is 
     * either empty or just whitespace
     */
    private Integer convertInt(String intString){
        if(intString != null && !intString.trim().equals("")){
            return Integer.parseInt(intString);
        }
        return -1;
    }

}
