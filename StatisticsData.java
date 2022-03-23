import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javafx.scene.paint.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Map;

/**
 * Calculates the data needed for the statistics viewer
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StatisticsData extends DataHandler
{
    // instance variables - replace the example below with your own
    //private ArrayList<AirbnbListing> listings; 
    private static ArrayList<AirbnbListing> boroughListings = new ArrayList<>();
    // private static String[] boroughs = {"Kingston upon Thames", "Croydon", "Bromley", "Hounslow", "Ealing", "Havering", "Hillingdon", 
            // "Harrow", "Brent", "Barnet", "Enfield", "Waltham Forest", "Redbridge", "Sutton", "Lambeth", "Southwark",
            // "Lewisham", "Greenwich", "Bexley", "Richmond upon Thames", "Merton", "Wandsworth", "Hammersmith and Fulham", 
            // "Kensington and Chelsea", "City of London", "Westminster", "Camden", "Tower Hamlets", "Islington", "Hackney",
            // "Haringey", "Newham", "Barking and Dagenham"};
            

    /**
     * Constructor for objects of class StatisticsData
     */
    public StatisticsData()
    {
        //listings = getData(); 
    }
    
    public static void initialiseHandler() {
        DataHandler.initialiseHandler();
        listings = getData();
        boroughListings = listings;
    }

    public static void setBoroughListings(int selectedMinPrice, int selectedMaxPrice)
    {
        boroughListings = DataHandler.getPropertiesAtPrice(selectedMinPrice, selectedMaxPrice);
    }
    
    // public static void setPropertyList(String borough)
    // {
        // boroughListings = sortBoroughs().get(borough);
    // }
    
    /**
     * @return Boolean average number of reviews per property. 
     */
    public static double getAverageNoReviews(boolean value)
    {
        ArrayList<AirbnbListing> data = determineList(value);
        double scoreCounter = 0; 
        for (int i = 0; i < data.size(); i++) {
            scoreCounter += data.get(i).getNumberOfReviews();
        }
        double average = scoreCounter / listings.size(); 
        return average; 
    }

    /**
     * @return int the number of home and apartment airbnb properties 
     */
    public static int getNoHomeAndApartments(boolean value)
    {
        ArrayList<AirbnbListing> data = determineList(value);
        int counter = 0;
        for (int i = 0; i < data.size(); i++) {
            AirbnbListing property = data.get(i);
            if(property.getRoom_type().equals("Entire home/apt"))
            {
                counter++; 
            }
        }
        return counter; 
    }

    /**
     * @return int the number of available properties
     */
    public static int getAvailableInfo(boolean value)
    {
        ArrayList<AirbnbListing> data = determineList(value);
        int counter = 0; 
        for (int i = 0; i < data.size(); i++) {
            if(data.get(i).getAvailability365() != 0)
            {
                counter += 1; 
            }
        }
        return counter; 
    }

    /**
     * @return String the most expensive borough
     */
    public static String getExpensiveInfoTest()
    {
        String expensiveBorough = "";
        int lastTotalPrice = 0;
        for(int i = 0; i < boroughs.size(); i++) // A for loop iterating through the boroughs array
        {
            ArrayList<AirbnbListing> boroughProperty = getPropertiesFromBorough(boroughs.get(i));
            int totalPrice = 0;
            for (int j = 0; j < boroughProperty.size(); j++) 
            {
                AirbnbListing property = boroughProperty.get(j);
                totalPrice += property.getPrice() * property.getMinimumNights();
            }
            System.out.println(totalPrice);
            System.out.println(lastTotalPrice);
            if(totalPrice > lastTotalPrice)
            {
                lastTotalPrice = totalPrice;
                expensiveBorough = boroughs.get(i);
            }
        }
        return expensiveBorough; 
    }

    /**
     * @return String the most expensive borough
     */
    public static String getExpensiveInfo()
    {
        String expensiveBorough = "";
        int lastTotalPrice = 0; 
        for(int rows = 0; rows < mapPositions.length; rows++) // A for loop iterating through the boroughs array
        {
            for(int columns = 0; columns < mapPositions[rows].length; columns++)
            {   
                if(mapPositions[rows][columns] != null)
                {
                    ArrayList<AirbnbListing> boroughProperty = getPropertiesFromBorough(mapPositions[rows][columns]);
                    int totalPrice = 0;
                    for (int j = 0; j < boroughProperty.size(); j++) 
                    {
                        AirbnbListing property = boroughProperty.get(j);
                        totalPrice += property.getPrice() * property.getMinimumNights();
                    }

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
     * @return double The standard deviation of price from all of the airbnb properties
     */
    public static double getPriceSDInfo(boolean value)
    {
        ArrayList<AirbnbListing> data = determineList(value);
        double standardDeviation = 0; 
        int x = 0;
        int y = 0; 
        int size = data.size(); 
        for (int i = 0; i < size; i++) {
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
     * @return HashMap information which stores the average price per night in each borough
     */
    public static HashMap<String, Integer> getAveragePricePerBorough()
    {
        HashMap<String, Integer> information = new HashMap<>();
        for(String boroughName : sortedBoroughs.keySet()) {
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
     * @return HashMap information which stores the average reviews per each borough
     */
    public static HashMap<String, Integer> getAverageReviewsPerBorough()
    {
        HashMap<String, Integer> information = new HashMap<>();
        for(String boroughName : sortedBoroughs.keySet()) {
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
    
    public static int getAveragePrice(boolean value)
    {
        ArrayList<AirbnbListing> data = determineList(value);
        int totalPrice = 0;
        int average = 0;
        for(int i = 0; i > data.size(); i++)
        {
            totalPrice += data.get(i).getPrice();
        }
        average = totalPrice / data.size(); 
        return average;
    }
    
    public static double getBoroughMapColour(String boroughName, int minPrice, int maxPrice, NoOfPropertiesStats noOfPropertiesStats) {     
        int noOfPropertiesInBorough = getPropertiesFromBorough(boroughName, minPrice, maxPrice).size();
        
        double brightness = getBrightness(noOfPropertiesInBorough, noOfPropertiesStats);
        
        return brightness;
    }
    
    public static double getBoroughMapColour(int percentile) {
        NoOfPropertiesStats noOfPropertiesStats = new NoOfPropertiesStats(25, 50, 75);
        
        double brightness = getBrightness(percentile, noOfPropertiesStats);
        
        return brightness;
    }
    
    private static double getBrightness(int noOfPropertiesInBorough, NoOfPropertiesStats noOfPropertiesStats) 
    {
        int median = noOfPropertiesStats.getMedian();
        int firstQuartile = noOfPropertiesStats.getFirstQuartile();
        int thirdQuartile = noOfPropertiesStats.getThirdQuartile();
        
        double brightness;
        
        if (noOfPropertiesInBorough == 0) {
            brightness = 0.00;
        }
        else if (noOfPropertiesInBorough < firstQuartile) {
            brightness = 0.1;
        }
        else if (noOfPropertiesInBorough < median) {
            brightness = 0.4;
        }
        else if (noOfPropertiesInBorough < thirdQuartile) {
            brightness = 0.65;
        }
        else {
            brightness = 0.8;
        }
        
        brightness = (brightness-0.5) * -2;
        
        return brightness;
    }
    
    public static ArrayList<Borough> getSortedNumberOfPropertiesInBoroughs(int minPrice, int maxPrice)
    {
        ArrayList<Borough> sortedNumberOfPropertiesAtPrice = new ArrayList<Borough>();

        for (String boroughName : boroughs) {
            ArrayList<AirbnbListing> propertiesInBorough = getPropertiesFromBorough(boroughName, minPrice, maxPrice);
            Borough boroughToAdd = new Borough(boroughName, propertiesInBorough);
            sortedNumberOfPropertiesAtPrice.add(boroughToAdd);
        }
        
        Collections.sort(sortedNumberOfPropertiesAtPrice, Borough.sortByNoOfPropertiesInBorough);
        
        return sortedNumberOfPropertiesAtPrice;
    }
    
    public static String getHighAvgReview()
    {
        String HighAvgReviewBorough = "";
        int lastHighAvgReview = 0; 
        for(int rows = 0; rows < mapPositions.length; rows++) // A for loop iterating through the boroughs array
        {
            for(int columns = 0; columns < mapPositions[rows].length; columns++)
            {   
                if(mapPositions[rows][columns] != null)
                {
                    ArrayList<AirbnbListing> boroughProperty = getPropertiesFromBorough(mapPositions[rows][columns]);
                    int averageReview = 0;
                    for (int j = 0; j < boroughProperty.size(); j++) 
                    {
                        AirbnbListing property = boroughProperty.get(j);
                        averageReview += property.getNumberOfReviews();
                    }
                    
                    if(boroughProperty.size() >= 1){
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
    
    private static ArrayList<AirbnbListing> determineList(boolean value)
    {
        if(value)
        {
            return boroughListings;
        }
        else
        {
            return listings;
        }
    }
}
