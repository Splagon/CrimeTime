import java.util.ArrayList;

/**
 * Write a description of class DataHandler here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DataHandler
{
    private AirbnbDataLoader dataLoader;
    // Create a list holding all AirbnbListings
    private ArrayList<AirbnbListing> propertiesList;
    
    /**
     * Constructor for objects of class DataHandler
     */
    public DataHandler()
    {
        dataLoader = new AirbnbDataLoader();
        propertiesList = dataLoader.load();
    }
    
    public String getPropertiesFromNeighbourhood(String neighbourhood) {
        return neighbourhood;
    }
    
    /**
     * Query the database to retrieve all properties from one neighbourhood.  
     */
    public ArrayList<AirbnbListing> getPropertiesFrom(String neighbourhood){
        return null;
    }
}
