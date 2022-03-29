import java.util.Comparator;
import java.util.ArrayList;

/**
 * This class handles all the properties in a borough
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 29/03/22
 */
public class Borough
{
    // Name of the borough
    private String boroughName;
    // List of all properties in the borough
    private ArrayList<AirbnbListing> listingsInBorough;
    
    /**
     * Constructor for borough objects
     */
    public Borough(String boroughName, ArrayList<AirbnbListing> listingsInBorough)
    {
        this.boroughName = boroughName;
        this.listingsInBorough = listingsInBorough;
    }
    
    /**
     * returns the name of the borough
     */
    public String getName() 
    {
        return boroughName;
    }
    
    /**
     * returns an ArrayList of AirbnbListing which is a list of all properties within a borough
     */
    public ArrayList<AirbnbListing> getBoroughListings() 
    {
        return listingsInBorough;
    }
    
    /**
     * Adds an AirbnbListing to the ArrayList of all Airbnb Listings within the borough
     */
    public void addListingToBorough(AirbnbListing listing) 
    {
        listingsInBorough.add(listing);
    }
    
    /**
     * Adds multiple AirbnbListings to the ArrayList of all Airbnb Listings within the borough
     */
    public void addListingToBorough(ArrayList<AirbnbListing> listings) 
    {
        listingsInBorough.addAll(listings);
    }
    
    /**
     * Returns the number of properties within a borough by return size of the ArrayList which
     * holds all Airbnb Listings of a borough
     */
    public int getNoOfPropertiesInBorough() 
    {   
        return listingsInBorough.size();
    }
    
    /**
     * Sorts the ArrayList of boroughs by name in ascending order
     */
    public static Comparator<Borough> sortByBoroughName = new Comparator<Borough>() 
    {
        @Override
        public int compare(Borough boroughOne, Borough boroughTwo)
        {
            String boroughOneName = boroughOne.getName().toLowerCase();
            String boroughTwoName = boroughTwo.getName().toLowerCase();
            
            return boroughOneName.compareTo(boroughTwoName);
        }
    };
    
    /**
     * Sorts the ArrayList of boroughs by the number of properties in the borough in 
     * ascending order
     */
    public static Comparator<Borough> sortByNoOfPropertiesInBorough = new Comparator<Borough>() 
    {
        @Override
        public int compare(Borough boroughOne, Borough boroughTwo)
        {
            int noOfPropertiesInBoroughOne = boroughOne.getNoOfPropertiesInBorough();
            int noOfPropertiesInBoroughTwo = boroughTwo.getNoOfPropertiesInBorough();
            
            return noOfPropertiesInBoroughOne - noOfPropertiesInBoroughTwo;
        }
    };
}
