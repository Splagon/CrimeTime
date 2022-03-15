import java.util.Comparator;

/**
 * Write a description of class Borough here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BoroughListing
{
    private String boroughName;
    private int noOfPropertiesInBorough;
    public BoroughListing(String boroughName, int noOfPropertiesInBorough)
    {
        this.boroughName = boroughName;
        this.noOfPropertiesInBorough = noOfPropertiesInBorough;
    }
    
    public String getName() {
        return boroughName;
    }
    
    public int getNoOfPropertiesInBorough() {
        return noOfPropertiesInBorough;
    }
    
    public static Comparator<BoroughListing> sortByBoroughName = new Comparator<BoroughListing>() 
    {
        @Override
        public int compare(BoroughListing boroughOne, BoroughListing boroughTwo)
        {
            String boroughOneName = boroughOne.getName().toLowerCase();
            String boroughTwoName = boroughTwo.getName().toLowerCase();
            
            return boroughOneName.compareTo(boroughTwoName);
        }
    };
    
    public static Comparator<BoroughListing> sortByNoOfPropertiesInBorough = new Comparator<BoroughListing>() 
    {
        @Override
        public int compare(BoroughListing boroughOne, BoroughListing boroughTwo)
        {
            int noOfPropertiesInBoroughOne = boroughOne.getNoOfPropertiesInBorough();
            int noOfPropertiesInBoroughTwo = boroughTwo.getNoOfPropertiesInBorough();
            
            return noOfPropertiesInBoroughOne - noOfPropertiesInBoroughTwo;
        }
    };
}
