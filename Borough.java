import java.util.Comparator;
import java.util.ArrayList;

/**
 * Write a description of class Borough here.
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 25/03/22
 */
public class Borough
{
    // Name of the borough
    private String boroughName;
    // List of all properties in the borough
    private ArrayList<AirbnbListing> listingsInBorough;
    
    public Borough(String boroughName, ArrayList<AirbnbListing> listingsInBorough)
    {
        this.boroughName = boroughName;
        //this.noOfPropertiesInBorough = noOfPropertiesInBorough;
        this.listingsInBorough = listingsInBorough;
    }
    
    public String getName() 
    {
        return boroughName;
    }
    
    public ArrayList<AirbnbListing> getBoroughListings() 
    {
        return listingsInBorough;
    }
    
    public void addListingToBorough(AirbnbListing listing) 
    {
        listingsInBorough.add(listing);
    }
    
    public void addListingToBorough(ArrayList<AirbnbListing> listings) 
    {
        listingsInBorough.addAll(listings);
    }
    
    public int getNoOfPropertiesInBorough() 
    {
        return listingsInBorough.size();
    }
    
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
