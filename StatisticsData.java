import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.effect.ColorAdjust;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Map;

/**
 * Calculates the data needed for the statistics viewer
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 29/03/22
 */
public class StatisticsData extends DataHandler
{
    // instance variables - replace the example below with your own
    private static ArrayList<AirbnbListing> listingsAtPrice = new ArrayList<>();
    
    /**
     * Used to initialise the values and allows for this to be done
     * statically.
     */
    public static void initialiseHandler() 
    {
        DataHandler.initialiseHandler();
        listingsAtPrice = listings;
    }

    /**
     * Used to produce a list of all properties between the minimum
     * and maximum user-selected price.
     * 
     * @param selectedMinPrice The minimum price selected by the user
     * @param selectedMaxPrice The maximum price selected by the user
     */
    public static void setListingsAtPrice(int selectedMinPrice, int selectedMaxPrice)
    {
        listingsAtPrice = DataHandler.getPropertiesAtPrice(selectedMinPrice, selectedMaxPrice);
    }
    
    /**
     * Produces a double conveying the average number of reviews according to 
     * the parameters.
     * 
     * @param useListingsAtPrice If true, the average is of the listings
     *                           between the user-selected min and max price.
     *                           If false, the average is of all properties.
     * @return average number of reviews per property. 
     */
    public static double getAverageNoReviews(boolean useListingsAtPrice)
    {
        ArrayList<AirbnbListing> data = determineList(useListingsAtPrice);
        double scoreCounter = 0; 
        double average = 0; 
        for (int i = 0; i < data.size(); i++) 
        {
            scoreCounter += data.get(i).getNumberOfReviews();
        }
        if(data.size() > 0)
        {
            average = scoreCounter / data.size(); 
        }
        return average; 
    }

    /**
     * Gets the total number of home and apartments according to the parameters.
     * 
     * @param useListingsAtPrice If true, the total is of the listings
     *                           between the user-selected min and max price.
     *                           If false, the total is of all properties.
     * @return The number of home and apartment airbnb properties 
     */
    public static int getNoHomeAndApartments(boolean useListingsAtPrice)
    {
        ArrayList<AirbnbListing> data = determineList(useListingsAtPrice);
        
        int counter = 0;
        for (int i = 0; i < data.size(); i++) 
        {
            AirbnbListing property = data.get(i);
            if(property.getRoom_type().equals("Entire home/apt"))
            {
                counter++; 
            }
        }
        return counter; 
    }

    /**
     * Produces the number of properties currently available of the list of properties.
     * 
     * @param useListingsAtPrice If true, the information is of the listings
     *                           between the user-selected min and max price.
     *                           If false, the information is of all properties.
     * 
     * @return the number of available properties
     */
    public static int getAvailableInfo(boolean useListingsAtPrice)
    {
        ArrayList<AirbnbListing> data = determineList(useListingsAtPrice);
        
        int counter = 0; 
        for (int i = 0; i < data.size(); i++) 
        {
            if(data.get(i).getAvailability365() != 0)
            {
                counter += 1; 
            }
        }
        
        return counter; 
    }

    /**
     * @param boolean useListingsAtPrice Determine whether the price selected will affect the statistic
     * @param int min The minimum price selected
     * @param int max The maximum price selected
     * @return The name of the most expensive borough
     */
    public static String getExpensiveInfo(boolean useListingsAtPrice, int min, int max)
    {
        String expensiveBorough = "";
        
        int lastTotalPrice = 0; 
        for(int rows = 0; rows < mapPositions.length; rows++) // A for loop iterating through the boroughs array
        {
            for(int columns = 0; columns < mapPositions[rows].length; columns++)
            {   
                if(mapPositions[rows][columns] != null)
                {
                    ArrayList<AirbnbListing> boroughProperty = new ArrayList<>();
                    if(useListingsAtPrice){
                        boroughProperty = getPropertiesFromBorough(mapPositions[rows][columns], min, max);
                    }
                    else{
                        boroughProperty = getPropertiesFromBorough(mapPositions[rows][columns]);
                    }
                    int totalPrice = 0;
                    String test = new String();
                    for (int j = 0; j < boroughProperty.size(); j++) 
                    {
                        AirbnbListing property = boroughProperty.get(j);
                        totalPrice += property.getPrice() * property.getMinimumNights();
                    }
                    totalPrice = totalPrice / boroughProperty.size(); 
                    if(totalPrice > lastTotalPrice)
                    {
                        lastTotalPrice = totalPrice;
                        expensiveBorough = mapPositions[rows][columns];
                    }
                }
            }
        }
        return expensiveBorough; 
    }

    /**
     * Produces the standard deviation of the list of properties.
     * 
     * @param useListingsAtPrice If true, the standard deviation is of the listings
     *                           between the user-selected min and max price.
     *                           If false, the standard deviation is of all properties.
     * 
     * @return The standard deviation of price from all of the airbnb properties
     */
    public static double getPriceSDInfo(boolean useListingsAtPrice)
    {
        ArrayList<AirbnbListing> data = determineList(useListingsAtPrice);
        double standardDeviation = 0; 
        int x = 0;
        int y = 0; 
        
        int size = data.size(); 
        for (int i = 0; i < size; i++) 
        {
            int price = data.get(i).getPrice();
            x += (price)*(price);
            y += price;
        }
        
        if(size >= 1)
        {
            standardDeviation = Math.sqrt((x/size) - (y/size)*(y/size));
        }
        
        return standardDeviation; 
    }
    
    
    /**
     * @return A hashmap containing the average price per night in each borough
     */
    public static HashMap<String, Integer> getAveragePricePerBorough()
    {
        HashMap<String, Integer> information = new HashMap<>();
        
        for (String boroughName : sortedBoroughs.keySet()) 
        {
            ArrayList<AirbnbListing> properties = sortedBoroughs.get(boroughName).getBoroughListings();
            int totalPrice = 0; 
            for(int i = 0; i < properties.size(); i++)
            {
                totalPrice += properties.get(i).getPrice(); 
            }
            
            if(properties.size() >= 1)
            {
                Integer averagePrice = new Integer(totalPrice / properties.size());
                information.put(boroughName, averagePrice);
            }
        }
        
        return information;
    }
    
    /**
     * @return A hashmap containing the average reviews per each borough
     */
    public static HashMap<String, Integer> getAverageReviewsPerBorough()
    {
        HashMap<String, Integer> information = new HashMap<>();
        
        for (String boroughName : sortedBoroughs.keySet()) 
        {
            ArrayList<AirbnbListing> properties = sortedBoroughs.get(boroughName).getBoroughListings();
            int totalReviews = 0; 
            for(int i = 0; i < properties.size(); i++)
            {
                totalReviews += properties.get(i).getNumberOfReviews(); 
            }
            
            if(properties.size() >= 1)
            {
                Integer averageReviews = new Integer(totalReviews / properties.size());
                information.put(boroughName, averageReviews);
            }
        }
        
        return information;
    }
    
    /**
     * @param useListingsAtPrice If true, the average is of the listings
     *                           between the user-selected min and max price.
     *                           If false, the average is of all properties.
     * @return int - the average price 
     */
    public static int getAveragePrice(boolean useListingsAtPrice)
    {
        ArrayList<AirbnbListing> data = determineList(useListingsAtPrice);
        
        int totalPrice = 0;
        int average = 0;
        for(int i = 0; i > data.size(); i++)
        {
            totalPrice += data.get(i).getPrice();
        }
        average = totalPrice / data.size();
        
        return average;
    }
    
    /**
     * Calculates how the colour of the hexagon should adjust dependendent
     * on the number of properties within the respective borough and in
     * respect to the other boroughs.
     * 
     * @param boroughName The name of the respective borough to check
     * @param noOfPropertiesStats The data container for the stats about
     *                            the boroughs and the number of properties
     *                            within them.
     *                            
     * @return The colour adjust used to adjust the colour of the hexagon.
     */
    public static ColorAdjust getBoroughMapColour(String boroughName, NoOfPropertiesStats noOfPropertiesStats) 
    {   
        int minPrice = noOfPropertiesStats.getMinPrice();
        int maxPrice = noOfPropertiesStats.getMaxPrice();
        int noOfPropertiesInBorough = getPropertiesFromBorough(boroughName, minPrice, maxPrice).size();
        
        return getColourAdjust(noOfPropertiesInBorough, noOfPropertiesStats);
    }
    
    /**
     * Calculates how the colour of the hexagon should adjust dependendent
     * on the percentile passed in and an even distribution. This is used
     * to make the key and automatically adjust the colour if the colour of
     * the hexagons are changed
     */
    public static ColorAdjust getBoroughMapColour(int percentile) 
    {
        NoOfPropertiesStats noOfPropertiesStats = new NoOfPropertiesStats(25, 50, 75);
        return getColourAdjust(percentile, noOfPropertiesStats);
    }
    
    /**
     * Calculates how the colour of the hexagon should adjust dependendent
     * on the number of properties within the respective borough and in
     * respect to the other boroughs.
     * 
     * @param noOfPropertiesInBorough The number of properties in the 
     *                                respective borough.
     * @param noOfPropertiesStats The data container for the stats about
     *                            the boroughs and the number of properties
     *                            within them.
     *                            
     * @return The colour adjust used to adjust the colour of the hexagon.
     */
    protected static ColorAdjust getColourAdjust(int noOfPropertiesInBorough, NoOfPropertiesStats noOfPropertiesStats) 
    {
        int median = noOfPropertiesStats.getMedian();
        int firstQuartile = noOfPropertiesStats.getFirstQuartile();
        int thirdQuartile = noOfPropertiesStats.getThirdQuartile();
        
        ColorAdjust colourAdjust = new ColorAdjust();
        
        double brightness;
        
        // Checks which quartile the borough lies between and sets
        // the appropriate colour.
        if (noOfPropertiesInBorough <= 0) 
        {
            brightness = 0.0;
        }
        else if (noOfPropertiesInBorough < firstQuartile) 
        {
            brightness = 0.1;
        }
        else if (noOfPropertiesInBorough < median) 
        {
            brightness = 0.4;
        }
        else if (noOfPropertiesInBorough < thirdQuartile) 
        {
            brightness = 0.65;
        }
        else 
        {
            brightness = 0.8;
        }
        
        brightness = (brightness-0.5) * -2;
        
        colourAdjust.setBrightness(brightness);
        
        return colourAdjust;
    }
    
    /**
     * @param minPrice The user-selected minimum price.
     * @param maxPrice The user-selected maximum price.
     * 
     * @return An Arraylist of boroughs sorted by the number of
     *         properties in the borough.
     */
    public static ArrayList<Borough> getSortedNumberOfPropertiesInBoroughs(int minPrice, int maxPrice)
    {
        ArrayList<Borough> sortedNumberOfPropertiesAtPrice = new ArrayList<Borough>();
        
        // Adds all the boroughs to the list unordered
        for (String boroughName : boroughs) 
        {
            ArrayList<AirbnbListing> propertiesInBorough = getPropertiesFromBorough(boroughName, minPrice, maxPrice);
            Borough boroughToAdd = new Borough(boroughName, propertiesInBorough);
            sortedNumberOfPropertiesAtPrice.add(boroughToAdd);
        }
        
        // Sorts the list using the specified comparator (sorts by number of properties)
        Collections.sort(sortedNumberOfPropertiesAtPrice, Borough.sortByNoOfPropertiesInBorough);
        
        return sortedNumberOfPropertiesAtPrice;
    }
    
    /**
     * Calculates the borough which has on average the highest number of reviews
     * @param boolean useListingsAtPrice Determine whether the price selected will affect the statistic
     * @param int min The minimum price selected
     * @param int max The maximum price selected
     * @return static String - the name of the borough
     */
    public static String getHighAvgReview(boolean useListingsAtPrice, int min, int max)
    {
        String HighAvgReviewBorough = "";
        int lastHighAvgReview = 0;
        
        // A for loop iterating through the boroughs array
        for(int rows = 0; rows < mapPositions.length; rows++) // rows
        {
            for(int columns = 0; columns < mapPositions[rows].length; columns++) // columns
            {   
                if(mapPositions[rows][columns] != null)
                {
                    ArrayList<AirbnbListing> boroughProperty = new ArrayList<>();
                    if(useListingsAtPrice){
                        boroughProperty = getPropertiesFromBorough(mapPositions[rows][columns], min, max);
                    }
                    else{
                        boroughProperty = getPropertiesFromBorough(mapPositions[rows][columns]);
                    }
                    int averageReview = 0;
                    for (int j = 0; j < boroughProperty.size(); j++) 
                    {
                        AirbnbListing property = boroughProperty.get(j);
                        averageReview += property.getNumberOfReviews();
                    }
                    
                    if(boroughProperty.size() >= 1)
                    {
                        averageReview = averageReview / boroughProperty.size();
                    }

                    if(averageReview > lastHighAvgReview )
                    {
                        lastHighAvgReview  = averageReview;
                        HighAvgReviewBorough = mapPositions[rows][columns];
                    }
                }
            }
        }
        return HighAvgReviewBorough;
    }
    
    protected static ArrayList<AirbnbListing> determineList(boolean useListingsAtPrice)
    {
        if(useListingsAtPrice)
        {
            return listingsAtPrice;
        }
        else
        {
            return listings;
        }
    }
    
    /**
     * Used to clear the current values in the class and in the DataHandler.
     * Only used for testing.
     */
    protected static void clear()
    {
        DataHandler.clear();
        listingsAtPrice = new ArrayList<AirbnbListing>();
    }
}