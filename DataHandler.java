import java.util.ArrayList;
import java.util.Iterator;
/**
 * Write a description of class DataHandler here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DataHandler
{
    // instance variables - replace the example below with your own
    private AirbnbDataLoader dataLoader = new AirbnbDataLoader();
    public ArrayList<AirbnbListing> listings;  // Contoller of property viewer needs it temporarly.

    /**
     * Constructor for objects of class DataHandler
     */
    public DataHandler()
    {
        // initialise instance variables
        listings = dataLoader.load();
    }
    
    protected ArrayList<AirbnbListing> getPropertiesFromBorough(String borough) 
    {
        ArrayList<AirbnbListing> listingsFromBorough = new ArrayList<AirbnbListing>();
        Iterator i = listings.iterator();
        
        while (i.hasNext()) 
        {
            AirbnbListing nextListing = (AirbnbListing) i.next();
            
            if (nextListing.getNeighbourhood().toLowerCase() == borough.toLowerCase()) 
            {
                listingsFromBorough.add(nextListing);
            }
        }
        
        return listingsFromBorough;
    }
    
    /**
     * @return the Arraylist of Airbnb Listings 
     */
    protected ArrayList<AirbnbListing> getData()
    {
        return listings; 
    }
}
