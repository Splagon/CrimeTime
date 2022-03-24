import java.util.Comparator;
import java.util.ArrayList;

/**
 * Write a description of class Borough here.
 *
 * @author Michael Higham (k21051343),
 * @version (a version number or a date)
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
    
    public String getName() {
        return boroughName;
    }
    
    public ArrayList<AirbnbListing> getBoroughListings() {
        return listingsInBorough;
    }
    
    public void addListingToBorough(AirbnbListing listing) {
        listingsInBorough.add(listing);
    }
    
    public void addListingToBorough(ArrayList<AirbnbListing> listings) {
        listingsInBorough.addAll(listings);
    }
    
    public int getNoOfPropertiesInBorough() {
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
