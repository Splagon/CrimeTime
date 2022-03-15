
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
}
