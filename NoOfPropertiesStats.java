import java.util.ArrayList;

public class NoOfPropertiesStats
{
    private ArrayList<BoroughListing> sortedNumberOfPropertiesInBorough;
    
    private int minPrice;
    private int maxPrice;
    
    private int minNoOfPropertiesInBorough;
    private int maxNoOfPropertiesInBorough;
    
    private int firstQuartile;
    private int median;
    private int thirdQuartile;
    
    private int noOfBoroughs;
    
    public NoOfPropertiesStats(StatisticsData dataHandler, int minPrice, int maxPrice) throws Exception {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        sortedNumberOfPropertiesInBorough = dataHandler.getSortedNumberOfPropertiesInBoroughs(minPrice, maxPrice);
        calculateStats();
    }
    
    public NoOfPropertiesStats(int firstQuartile, int median, int thirdQuartile) throws Exception {
        this.firstQuartile = firstQuartile;
        this.median = median;
        this.thirdQuartile = thirdQuartile;
    }
    
    private void calculateStats() {
        noOfBoroughs = sortedNumberOfPropertiesInBorough.size();
        
        BoroughListing boroughWithMinNoOfProperties = sortedNumberOfPropertiesInBorough.get(0);
        //BoroughListing boroughWithMedianNoOfProperties;
        BoroughListing boroughWithMaxNoOfProperties = sortedNumberOfPropertiesInBorough.get(sortedNumberOfPropertiesInBorough.size()-1);
        
        minNoOfPropertiesInBorough = boroughWithMinNoOfProperties.getNoOfPropertiesInBorough();
        maxNoOfPropertiesInBorough = boroughWithMaxNoOfProperties.getNoOfPropertiesInBorough();
        
        firstQuartile = calculateQuartile(1);
        median = calculateQuartile(2);
        thirdQuartile = calculateQuartile(3);
    }
    
    private int calculateQuartile(int quartileNumber) {
        double quartileFraction = (double) quartileNumber / 4;
        int quartile;
        
        double quartileIndex = ((double) noOfBoroughs * quartileFraction) - 1;
        
        if (quartileIndex - (int) quartileIndex != 0) {
            BoroughListing boroughLowerOfQuartile = sortedNumberOfPropertiesInBorough.get((int) (noOfBoroughs * quartileFraction)-1);
            BoroughListing boroughUpperOfQuartile = sortedNumberOfPropertiesInBorough.get((int) (noOfBoroughs * quartileFraction));
            
            int sizeLowerOfQuartile = boroughLowerOfQuartile.getNoOfPropertiesInBorough();
            int sizeUpperOfQuartile = boroughUpperOfQuartile.getNoOfPropertiesInBorough();
            
            quartile = (int) Math.round(((sizeUpperOfQuartile - sizeLowerOfQuartile) * quartileFraction) + sizeLowerOfQuartile);
        }
        else {
            BoroughListing boroughWithQuartileNoOfProperties = sortedNumberOfPropertiesInBorough.get((int) (noOfBoroughs * quartileFraction)-1);
            quartile = boroughWithQuartileNoOfProperties.getNoOfPropertiesInBorough();
        }
        
        return quartile;
    }
    
    public int getMedian() {
        return median;
    }
    
    public int getFirstQuartile() {
        return firstQuartile;
    }
    
    public int getThirdQuartile() {
        return thirdQuartile;
    }
    
    public int getMinNoOfPropertiesInBorough() {
        return minNoOfPropertiesInBorough;
    }
    
    public int getMaxNoOfPropertiesInBorough() {
        return maxNoOfPropertiesInBorough;
    }
}
