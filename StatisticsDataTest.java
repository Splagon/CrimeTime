import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import javafx.scene.effect.ColorAdjust;

/**
 * The test class StatisticsDataTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class StatisticsDataTest
{
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        StatisticsData.initialiseHandler();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
        StatisticsData.clear();
    }

    @Test
    public void loadData()
    {
        assertNotNull(DataHandler.getData());
    }

    @Test
    public void getListingsAtPriceDifferentValidValues()
    {
        StatisticsData.setListingsAtPrice(25, 40);
        
        getListingsAtPriceTest(true);
    }
    
    @Test
    public void getListingsAtPriceEqualValidValues()
    {
        StatisticsData.setListingsAtPrice(40, 40);
        
        getListingsAtPriceTest(true);
    }
    
    @Test
    /**
     * If the min price is greater than the max price, which due to button
     * implementation should not occur), then no listings should be returned.
     */
    public void getListingsAtPriceDifferentInvalidValues()
    {
        StatisticsData.setListingsAtPrice(40, 25);
        
        getListingsAtPriceTest(false);
    }
    
    @Test
    /**
     * When the max price is negative, the max price value checked against is
     * negative and returns all listings above the min price selected.
     */
    public void getListingsAtPriceSelectedMinPriceNoMaxPrice()
    {
        StatisticsData.setListingsAtPrice(25, -1);
        
        getListingsAtPriceTest(true);
    }
    
    @Test
    /**
     * When the min price is negative, the min price value checked against is
     * negative and returns all listings below the max price selected.
     */
    public void getListingsAtPriceNoMinPriceSelectedMaxPrice()
    {
        StatisticsData.setListingsAtPrice(-1, 40);
        
        getListingsAtPriceTest(true);
    }
    
    @Test
    /**
     * When the min or max price is negative, all of the listings are returned
     */
    public void getListingsAtPriceNoMinPriceNoMaxPrice()
    {
        StatisticsData.setListingsAtPrice(-1, -1);
        
        getListingsAtPriceTest(true);
    }
    
    private void getListingsAtPriceTest(boolean containsValue)
    {
        ArrayList<AirbnbListing> listings = StatisticsData.determineList(true);
        assertNotNull(listings);
        
        if (containsValue) 
        {
            assertTrue(listings.size() <= StatisticsData.determineList(false).size());
        }
        else
        {
            assertTrue(listings.size() == 0);
        }
    }
    
    @Test
    public void getAverageNoReviewsOfListingsAtPrice()
    {
        StatisticsData.setListingsAtPrice(25, 40);
        assertNotNull(StatisticsData.getAverageNoReviews(true));
    }
    
    @Test
    public void getAverageNoReviewsOfAllListings()
    {
        assertNotNull(StatisticsData.getAverageNoReviews(false));
    }
    
    @Test
    public void getNoHomeAndApartmentsOfListingsAtPrice()
    {
        StatisticsData.setListingsAtPrice(25, 40);
        assertNotNull(StatisticsData.getNoHomeAndApartments(true));
    }
    
    @Test
    public void getNoHomeAndApartmentsOfAllListings()
    {
        assertNotNull(StatisticsData.getNoHomeAndApartments(false));
    }
    
    @Test
    public void getAvailableInfoOfListingsAtPrice()
    {
        StatisticsData.setListingsAtPrice(25, 40);
        assertNotNull(StatisticsData.getAvailableInfo(true));
    }
    
    @Test
    public void getAvailableInfoOfAllListings()
    {
        assertNotNull(StatisticsData.getAvailableInfo(false));
    }
    
    @Test
    public void getExpensiveInfo()
    {
        String mostExpensiveBorough = StatisticsData.getExpensiveInfo();
        assertNotNull(mostExpensiveBorough);
        
        ArrayList<String> boroughNames = StatisticsData.getBoroughNames();
        assertTrue(boroughNames.contains(mostExpensiveBorough));
    }
    
    @Test
    public void getPriceSDInfoOfListingsAtPrice()
    {
        StatisticsData.setListingsAtPrice(25, 40);
        assertNotNull(StatisticsData.getPriceSDInfo(true));
    }
    
    @Test
    public void getPriceSDInfoOfAllListings()
    {
        assertNotNull(StatisticsData.getPriceSDInfo(false));
    }
    
    @Test
    public void getAveragePricePerBorough()
    {
        HashMap<String, Integer> information = StatisticsData.getAveragePricePerBorough();
        ArrayList<String> boroughNames = StatisticsData.getBoroughNames();
        
        for (String boroughName : boroughNames) {
            assertNotNull(information.get(boroughName));
        }
    }
    
    @Test
    public void getAverageReviewsPerBorough()
    {
        HashMap<String, Integer> information = StatisticsData.getAverageReviewsPerBorough();
        ArrayList<String> boroughNames = StatisticsData.getBoroughNames();
        
        for (String boroughName : boroughNames) {
            assertNotNull(information.get(boroughName));
        }
    }
    
    @Test
    public void getAveragePriceOfListingsAtPrice()
    {
        StatisticsData.setListingsAtPrice(25, 40);
        assertNotNull(StatisticsData.getAveragePrice(true));
    }
    
    @Test
    public void getAveragePriceOfAllListings()
    {
        assertNotNull(StatisticsData.getAveragePrice(false));
    }
    
    @Test
    public void getBoroughMapColourCheckAllPercentiles()
    {
        for (int percentile = 0; percentile <= 100; percentile++)
        {
            checkPercentileTest(percentile);
        }
    }
    
    @Test
    public void getBoroughMapColourCheckNegativePercentile()
    {
        checkPercentileTest(-20);
    }
    
    @Test
    public void getBoroughMapColourCheckPercentileOver100()
    {
        checkPercentileTest(120);
    }
    
    private void checkPercentileTest(int percentile) 
    {
        ColorAdjust colourAdjust = StatisticsData.getBoroughMapColour(percentile);
        assertTrue(colourAdjust.getBrightness() == StatisticsData.getBrightness(percentile));
    }
    
    @Test
    public void getSortedNumberOfPropertiesInBoroughsDifferentValidValues()
    {
        ArrayList<Borough> listings = StatisticsData.getSortedNumberOfPropertiesInBoroughs(25, 40);
        
        getSortedNumberOfPropertiesInBoroughsTest(listings, true);
    }
    
    @Test
    public void getSortedNumberOfPropertiesInBoroughsEqualValidValues()
    {
        ArrayList<Borough> listings = StatisticsData.getSortedNumberOfPropertiesInBoroughs(40, 40);
        
        getSortedNumberOfPropertiesInBoroughsTest(listings, true);
    }
        
    
    @Test
    public void getSortedNumberOfPropertiesInBoroughsDifferentInvalidValues()
    {
        ArrayList<Borough> listings = StatisticsData.getSortedNumberOfPropertiesInBoroughs(40, 25);
        
        getSortedNumberOfPropertiesInBoroughsTest(listings, false);
    }
    
    @Test
    /**
     * When the max price is negative, the max price value checked against is
     * negative and returns all listings above the min price selected.
     */
    public void getSortedNumberOfPropertiesInBoroughsSelectedMinPriceNoMaxPrice()
    {
        ArrayList<Borough> listings = StatisticsData.getSortedNumberOfPropertiesInBoroughs(25, -1);
        
        getSortedNumberOfPropertiesInBoroughsTest(listings, true);
    }
    
    @Test
    /**
     * When the min price is negative, the min price value checked against is
     * negative and returns all listings below the max price selected.
     */
    public void getSortedNumberOfPropertiesInBoroughsNoMinPriceSelectedMaxPrice()
    {
        ArrayList<Borough> listings = StatisticsData.getSortedNumberOfPropertiesInBoroughs(-1, 40);
        
        getSortedNumberOfPropertiesInBoroughsTest(listings, true);
    }
    
    @Test
    /**
     * When the min or max price is negative, all of the listings are returned
     */
    public void getSortedNumberOfPropertiesInBoroughsNoMinPriceNoMaxPrice()
    {
        ArrayList<Borough> listings = StatisticsData.getSortedNumberOfPropertiesInBoroughs(-1, -1);
        
        getSortedNumberOfPropertiesInBoroughsTest(listings, true);
    }
    
    private void getSortedNumberOfPropertiesInBoroughsTest(ArrayList<Borough> listings, boolean containsValue)
    {
        assertNotNull(listings);
        assertTrue(listings.size() == StatisticsData.getBoroughNames().size());
        
        for (int i = 0; i < listings.size(); i++) 
        {
            Borough boroughBeingChecked = listings.get(i);
            
            if (containsValue)
            {
                assertTrue(boroughBeingChecked.getNoOfPropertiesInBorough() >= 0);
            }
            else
            {
                assertTrue(boroughBeingChecked.getNoOfPropertiesInBorough() == 0);
            }

            if (i != 0) {
                // makes sure the boroughs are sorted
                assertTrue(boroughBeingChecked.getNoOfPropertiesInBorough() >= listings.get(i-1).getNoOfPropertiesInBorough());
            }
        }
    }
    
    @Test
    public void getHighAvgReview()
    {
        String boroughWithHighestAvgReview = StatisticsData.getHighAvgReview();
        assertNotNull(boroughWithHighestAvgReview);
        
        ArrayList<String> boroughNames = StatisticsData.getBoroughNames();
        assertTrue(boroughNames.contains(boroughWithHighestAvgReview));
    }
}




