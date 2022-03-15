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
    protected ArrayList<AirbnbListing> listings;
    protected String[][] mapPositions = {{ null, null, null, "Enfield", null, null, null },
                                         { null, null, "Barnet", "Haringey", "Waltham Forest", null, null },
                                         { "Harrow", "Brent", "Camden", "Islington", "Hackney", "Redbridge", "Havering" },
                                         { "Hillingdon", "Ealing", "Kensington and Chelsea", "Westminster", "Tower Hamlets", "Newham", "Barking and Dagenham" },
                                         { "Hounslow", "Hammersmith and Fulham", "Wandsworth", "City of London", "Greenwich", "Bexley", null },
                                         { null, "Richmond upon Thames", "Merton", "Lambeth", "Southwark", "Lewisham", null },
                                         { null, "Kingston upon Thames", "Sutton", "Croydon", "Bromley", null, null },
                                        };

    /**
     * Constructor for objects of class DataHandler
     */
    public DataHandler()
    {
        // initialise instance variables
        listings = dataLoader.load();
    }
    
    public ArrayList<AirbnbListing> getPropertiesFromBorough(String borough, int minPrice, int maxPrice)
    {
        ArrayList<AirbnbListing> listingsFromBorough = new ArrayList<AirbnbListing>();
        Iterator i = listings.iterator();
        
        while (i.hasNext()) 
        {
            AirbnbListing nextListing = (AirbnbListing) i.next();
            
            if((nextListing.getNeighbourhood().toLowerCase()).equals(borough.toLowerCase()) || borough == null) 
            {
                if (nextListing.getPrice() >= minPrice) {
                    if (nextListing.getPrice() <= maxPrice || maxPrice < 0) {
                        listingsFromBorough.add(nextListing);
                    }
                }
            }
        }
        
        return listingsFromBorough;
    }
    
    public ArrayList<AirbnbListing> getPropertiesFromBorough(String borough)
    {
        ArrayList<AirbnbListing> listingsFromBorough = getPropertiesFromBorough(borough, -1, -1);
        
        return listingsFromBorough;
    }
    
    /**
     * @return the Arraylist of Airbnb Listings 
     */
    public ArrayList<AirbnbListing> getData()
    {
        return listings; 
    }
    
    public String[][] getMapPositions() 
    {
        return mapPositions;
    }
    
    /**
     * Searches through every listing and returns the lowest price for one night out of every property
     * 
     * this is used in creating the drop box for the user to select their price range they are looking for
     */
    public Integer getLowestPrice()
    {
        Iterator i = listings.iterator();
        int lowest = -1;
        while (i.hasNext()) 
        {
            AirbnbListing currentProperty = (AirbnbListing) i.next();
            int currentPrice = currentProperty.getPrice();
            if (currentPrice < lowest || lowest < 0) {
                lowest = currentPrice;
            }
        }
        return lowest;
    }
    
    /**
     * Searches through every listing and returns the highest price for one night out of every property
     * 
     * this is used in creating the drop box for the user to select their price range they are looking for
     */
    public Integer getHighestPrice()
    {
        Iterator i = listings.iterator();
        int highest = -1;
        while (i.hasNext()) 
        {
            AirbnbListing currentProperty = (AirbnbListing) i.next();
            int currentPrice = currentProperty.getPrice();
            if (currentPrice > highest) {
                highest = currentPrice;
            }
        }
        return highest;
    }

}
