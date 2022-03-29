import java.util.ArrayList;
/**
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
    
    public NoOfPropertiesStats(int minPrice, int maxPrice) 
    {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        sortedNumberOfPropertiesInBorough = StatisticsData.getSortedNumberOfPropertiesInBoroughs(minPrice, maxPrice);
        calculateStats();
    }
    
    public NoOfPropertiesStats(int firstQuartile, int median, int thirdQuartile) 
    {
        this.firstQuartile = firstQuartile;
        this.median = median;
        this.thirdQuartile = thirdQuartile;
    }
    
    private void calculateStats() 
    {
        noOfBoroughs = sortedNumberOfPropertiesInBorough.size();
        
        Borough boroughWithMinNoOfProperties = sortedNumberOfPropertiesInBorough.get(0);
        //BoroughListing boroughWithMedianNoOfProperties;
        Borough boroughWithMaxNoOfProperties = sortedNumberOfPropertiesInBorough.get(sortedNumberOfPropertiesInBorough.size()-1);
        
        minNoOfPropertiesInBorough = boroughWithMinNoOfProperties.getNoOfPropertiesInBorough();
        maxNoOfPropertiesInBorough = boroughWithMaxNoOfProperties.getNoOfPropertiesInBorough();
        
        firstQuartile = calculateQuartile(1);
        median = calculateQuartile(2);
        thirdQuartile = calculateQuartile(3);
    }
    
    private int calculateQuartile(int quartileNumber) 
    {
        double quartileFraction = (double) quartileNumber / 4;
        int quartile;
        
        double quartileIndex = ((double) noOfBoroughs * quartileFraction) - 1;
        
        if (quartileIndex - (int) quartileIndex != 0) 
        {
            Borough boroughLowerOfQuartile = sortedNumberOfPropertiesInBorough.get((int) (noOfBoroughs * quartileFraction)-1);
            Borough boroughUpperOfQuartile = sortedNumberOfPropertiesInBorough.get((int) (noOfBoroughs * quartileFraction));
            
            int sizeLowerOfQuartile = boroughLowerOfQuartile.getNoOfPropertiesInBorough();
            int sizeUpperOfQuartile = boroughUpperOfQuartile.getNoOfPropertiesInBorough();
            
            quartile = (int) Math.round(((sizeUpperOfQuartile - sizeLowerOfQuartile) * quartileFraction) + sizeLowerOfQuartile);
        }
        else 
        {
            Borough boroughWithQuartileNoOfProperties = sortedNumberOfPropertiesInBorough.get((int) (noOfBoroughs * quartileFraction)-1);
            quartile = boroughWithQuartileNoOfProperties.getNoOfPropertiesInBorough();
        }
        
        return quartile;
    }
    
    public int getMedian() 
    {
        return median;
    }
    
    public int getFirstQuartile() 
    {
        return firstQuartile;
    }
    
    public int getThirdQuartile() 
    {
        return thirdQuartile;
    }
    
    public int getMinNoOfPropertiesInBorough() 
    {
        return minNoOfPropertiesInBorough;
    }
    
    public int getMaxNoOfPropertiesInBorough() 
    {
        return maxNoOfPropertiesInBorough;
    }
    
    public int getMinPrice() 
    {
        return minPrice;
    }
    
    public int getMaxPrice() 
    {
        return maxPrice;
    }
}
