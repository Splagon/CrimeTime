import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javafx.scene.paint.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Collections;

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
    String[] boroughs = {"Kingston upon Thames", "Croydon", "Bromley", "Hounslow", "Ealing", "Havering", "Hillingdon", 
            "Harrow", "Brent", "Barnet", "Enfield", "Waltham Forest", "Redbridge", "Sutton", "Lambeth", "Southwark",
            "Lewisham", "Greenwich", "Bexley", "Richmond upon Thames", "Merton", "Wandsworth", "Hammersmith and Fulham", 
            "Kensington and Chelsea", "City of London", "Westminster", "Camden", "Tower Hamlets", "Islington", "Hackney",
            "Haringey", "Newham", "Barking and Dagenham"};

    /**
     * Constructor for objects of class StatisticsData
     */
    public StatisticsData()
    {
        listings = getData(); 
    }

    /**
     * @return Boolean average number of reviews per property. 
     */
    public double getAverageNoReviews()
    {
        double scoreCounter = 0; 
        for (int i = 0; i < listings.size(); i++) {
            scoreCounter += listings.get(i).getNumberOfReviews();
        }
        double average = scoreCounter / listings.size(); 
        return average; 
    }

    /**
     * @return int the number of home and apartment airbnb properties 
     */
    public int getNoHomeAndApartments()
    {
        int counter = 0;
        for (int i = 0; i < listings.size(); i++) {
            AirbnbListing property = listings.get(i);
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
    public int getAvailableInfo()
    {
        int counter = 0; 
        for (int i = 0; i < listings.size(); i++) {
            if(listings.get(i).getAvailability365() != 0)
            {
                counter += 1; 
            }
        }
        return counter; 
    }

    /**
     * @return String the most expensive borough
     */
    public String getExpensiveInfoTest()
    {
        String expensiveBorough = "";
        int lastTotalPrice = 0;
        for(int i = 0; i < boroughs.length; i++) // A for loop iterating through the boroughs array
        {
            ArrayList<AirbnbListing> boroughProperty = getPropertiesFromBorough(boroughs[i]);
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
                expensiveBorough = boroughs[i];
            }
        }
        return expensiveBorough; 
    }

    /**
     * @return String the most expensive borough
     */
    public String getExpensiveInfo()
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
    public double getPriceSDInfo()
    {
        double standardDeviation = 0; 
        int x = 0;
        int y = 0; 
        int size = listings.size(); 
        for (int i = 0; i < size; i++) {
            int price = listings.get(i).getPrice();
            x += (price)*(price);
            y += price;
        }
        standardDeviation = Math.sqrt((x/size) - (y/size)*(y/size));
        return standardDeviation; 
    }
    
    /**
     * @return HashMap information which stores the average price per night in each borough
     */
    public HashMap<String, Integer> getAveragePricePerBorough()
    {
        HashMap<String, Integer> information = new HashMap<>();
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
                        totalPrice += property.getPrice();
                    }
                    Integer averagePrice = new Integer(totalPrice / boroughProperty.size()); 
                    information.put(mapPositions[rows][columns], averagePrice);
                }
            }
        }
        return information; 
    }
    
    // public Color getBoroughMapColour(String boroughName, int minPrice, int maxPrice, ArrayList<BoroughListing> sortedNumberOfPropertiesInBorough) {
        // Random rand = new Random();
        
        // //Color boroughColour = Color.rgb(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
        
        // int noOfBoroughs = sortedNumberOfPropertiesInBorough.size();
        
        // BoroughListing boroughWithMinNoOfProperties = sortedNumberOfPropertiesInBorough.get(0);
        // BoroughListing boroughWithMedianNoOfProperties;
        // BoroughListing boroughWithMaxNoOfProperties = sortedNumberOfPropertiesInBorough.get(sortedNumberOfPropertiesInBorough.size()-1);
        
        // int minNoOfPropertiesInBorough = boroughWithMinNoOfProperties.getNoOfPropertiesInBorough();
        // int median;
        // int maxNoOfPropertiesInBorough = boroughWithMaxNoOfProperties.getNoOfPropertiesInBorough();
        
        // if (noOfBoroughs % 2 == 1) {
            // boroughWithMedianNoOfProperties = sortedNumberOfPropertiesInBorough.get(Math.round(noOfBoroughs/2)-1);
            // median = boroughWithMedianNoOfProperties.getNoOfPropertiesInBorough();
        // }
        // else {
            // BoroughListing boroughLowerOfMedian = sortedNumberOfPropertiesInBorough.get(Math.round(noOfBoroughs/2)-1);
            // BoroughListing boroughUpperOfMedian = sortedNumberOfPropertiesInBorough.get(Math.round(noOfBoroughs/2));
            
            // int boroughLowerOfMedianSize = boroughLowerOfMedian.getNoOfPropertiesInBorough();
            // int boroughUpperOfMedianSize = boroughUpperOfMedian.getNoOfPropertiesInBorough();
            
            // median = (int) ((boroughLowerOfMedianSize + boroughUpperOfMedianSize) / 2);
        // }
        
        // int noOfPropertiesInBorough = getPropertiesFromBorough(boroughName, minPrice, maxPrice).size();
        
        // // double percentile = (double) (noOfPropertiesInBorough - minNoOfPropertiesInBorough) / (double) (maxNoOfPropertiesInBorough - minNoOfPropertiesInBorough);
        
        // // if (percentile > 0.0 && percentile < 0.25) {
            // // percentile = 0.0;
        // // }
        // // else if (percentile < 0.50) {
            // // percentile = 0.375;
        // // }
        // // else if (percentile < 0.75) {
            // // percentile = 0.625;
        // // }
        // // else {
            // // percentile = 1.00;
        // // }
        
        // //Color boroughColour = Color.rgb(0, 255 - (int)(255*percentile), 0);
        
        // double halfOfMedian = median * 0.5;
        // Color boroughColour;
        
        // if (noOfPropertiesInBorough == 0) {
            // boroughColour = Color.rgb(255, 255, 255);
        // }
        // else if (noOfPropertiesInBorough < median - halfOfMedian) {
            // boroughColour = Color.rgb(180, 255, 180);
        // }
        // else if (noOfPropertiesInBorough < median) {
            // boroughColour = Color.rgb(80, 235, 80);
        // }
        // else if (noOfPropertiesInBorough < median + halfOfMedian) {
            // boroughColour = Color.rgb(30, 225, 30);
        // }
        // else {
            // boroughColour = Color.rgb(0, 175, 0);
        // }
        
        // return boroughColour;
    // }
    
    public double getBoroughMapColour(String boroughName, int minPrice, int maxPrice, NoOfPropertiesStats noOfPropertiesStats) {     
        int noOfPropertiesInBorough = getPropertiesFromBorough(boroughName, minPrice, maxPrice).size();
        
        double brightness = getBrightness(noOfPropertiesInBorough, noOfPropertiesStats);
        
        return brightness;
    }
    
    public double getBoroughMapColour(int percentile) throws Exception {
        NoOfPropertiesStats noOfPropertiesStats = new NoOfPropertiesStats(25, 50, 75);
        
        double brightness = getBrightness(percentile, noOfPropertiesStats);
        
        return brightness;
    }
    
    private double getBrightness(int noOfPropertiesInBorough, NoOfPropertiesStats noOfPropertiesStats) 
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
    
    public ArrayList<BoroughListing> getSortedNumberOfPropertiesInBoroughs(int minPrice, int maxPrice) throws Exception
    {
        ArrayList<BoroughListing> sortedNumberOfPropertiesAtPrice = new ArrayList<BoroughListing>();

        for (String boroughName : boroughs) {
            int noOfPropertiesInBorough = getPropertiesFromBorough(boroughName, minPrice, maxPrice).size();
            BoroughListing boroughToAdd = new BoroughListing(boroughName, noOfPropertiesInBorough);
            sortedNumberOfPropertiesAtPrice.add(boroughToAdd);
        }
        
        Collections.sort(sortedNumberOfPropertiesAtPrice, BoroughListing.sortByNoOfPropertiesInBorough);
        
        return sortedNumberOfPropertiesAtPrice;
    }
    
    public String getHighAvgReview()
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
                    averageReview = averageReview / boroughProperty.size();
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
}
