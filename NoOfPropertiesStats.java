import java.util.ArrayList;
/**
 * Calculates the min, first quartile, median, upper quartile, and max
 * stats dependent on the min and max selected price.
 * 
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 25/03/22
 */

public class NoOfPropertiesStats
{
    private ArrayList<Borough> sortedNumberOfPropertiesInBorough;
    
    private int minPrice;
    private int maxPrice;
    
    private int minNoOfPropertiesInBorough;
    private int maxNoOfPropertiesInBorough;
    
    private int firstQuartile;
    private int median;
    private int thirdQuartile;

    private int noOfBoroughs;
    
    /**
     * Calculates the stats based on the min and max selected price
     * 
     * @param minPrice The min price selected
     * @param maxPrice The max price selected
     */
    public NoOfPropertiesStats(int minPrice, int maxPrice) 
    {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        
        // gets a list of boroughs sorted by the number of properties
        // within the borough between the selected prices
        sortedNumberOfPropertiesInBorough = StatisticsData.getSortedNumberOfPropertiesInBoroughs(minPrice, maxPrice);
        
        calculateStats();
    }
    
    /**
     * Sets the lower quartile, median, and upper quartile with precalculated values.
     * 
     * @param firstQuartile The inputed lower quartile
     * @param median The inputed median
     * @param thirdQuartile The inputed upper quartile
     */
    public NoOfPropertiesStats(int firstQuartile, int median, int thirdQuartile) 
    {
        this.firstQuartile = firstQuartile;
        this.median = median;
        this.thirdQuartile = thirdQuartile;
    }
    
    /**
     * Calculates the min, lower quartile, median, upper quartile, and max.
     */
    private void calculateStats() 
    {
        noOfBoroughs = sortedNumberOfPropertiesInBorough.size();
        
        Borough boroughWithMinNoOfProperties = sortedNumberOfPropertiesInBorough.get(0);
        Borough boroughWithMaxNoOfProperties = sortedNumberOfPropertiesInBorough.get(sortedNumberOfPropertiesInBorough.size()-1);
        
        minNoOfPropertiesInBorough = boroughWithMinNoOfProperties.getNoOfPropertiesInBorough();
        maxNoOfPropertiesInBorough = boroughWithMaxNoOfProperties.getNoOfPropertiesInBorough();
        
        firstQuartile = calculateQuartile(1);
        median = calculateQuartile(2);
        thirdQuartile = calculateQuartile(3);
    }
    
    /**
     * Calculate the quartile of the index entered
     * 
     * @param quartileNumber The quartile number to calculate.
     * 
     * @return The quartile value.
     */
    private int calculateQuartile(int quartileNumber) 
    {
        double quartileFraction = (double) quartileNumber / 4;
        int quartile;
        
        double quartileIndex = ((double) noOfBoroughs * quartileFraction) - 1;
        
        // if the quartile index is not a whole number, then calculate the
        // quartile based on the fraction between the values either side of 
        // the quartile
        if (quartileIndex - (int) quartileIndex != 0) 
        {
            Borough boroughLowerOfQuartile = sortedNumberOfPropertiesInBorough.get((int) (noOfBoroughs * quartileFraction)-1);
            Borough boroughUpperOfQuartile = sortedNumberOfPropertiesInBorough.get((int) (noOfBoroughs * quartileFraction));
            
            int sizeLowerOfQuartile = boroughLowerOfQuartile.getNoOfPropertiesInBorough();
            int sizeUpperOfQuartile = boroughUpperOfQuartile.getNoOfPropertiesInBorough();
            
            quartile = (int) Math.round(((sizeUpperOfQuartile - sizeLowerOfQuartile) * quartileFraction) + sizeLowerOfQuartile);
        }
        // else find the number of properties in the borough at the index
        else 
        {
            Borough boroughWithQuartileNoOfProperties = sortedNumberOfPropertiesInBorough.get((int) (noOfBoroughs * quartileFraction)-1);
            quartile = boroughWithQuartileNoOfProperties.getNoOfPropertiesInBorough();
        }
        
        return quartile;
    }
    
    /**
     * @return The median value of the lisitings.
     */
    public int getMedian() 
    {
        return median;
    }
    
    /**
     * @return The first/lower quartile value of the lisitings.
     */
    public int getFirstQuartile() 
    {
        return firstQuartile;
    }
    
    /**
     * @return The third/upper quartile value of the lisitings.
     */
    public int getThirdQuartile() 
    {
        return thirdQuartile;
    }
    
    /**
     * @return The lowest number of properties in a borough in the listings.
     */
    public int getMinNoOfPropertiesInBorough() 
    {
        return minNoOfPropertiesInBorough;
    }
    
    /**
     * @return The highest number of properties in a borough in the listings.
     */
    public int getMaxNoOfPropertiesInBorough() 
    {
        return maxNoOfPropertiesInBorough;
    }
    
    /**
     * @return The minimum selected price used to calculate the statistics.
     */
    public int getMinPrice() 
    {
        return minPrice;
    }
    
    /**
     * @return The maximum selected price used to calculate the statistics.
     */
    public int getMaxPrice() 
    {
        return maxPrice;
    }
}
