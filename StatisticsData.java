import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javafx.scene.paint.Color;
import java.util.Iterator;

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
            "Haringey", "Newham", "Barking and Dagenham", "test"};

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
    
    public Color getBoroughMapColour(String boroughName) {
        Random rand = new Random();
        Color boroughColour = Color.rgb(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
        
        HashMap<String, Integer> averagePricePerBorough = getAveragePricePerBorough();
        
        return boroughColour;
        
        //ArrayList<String> appb = new ArrayList<String>();
        
        // for (String borough : averagePricePerBorough.keySet()) {
            // int boroughAVpB = averagePricePerBorough.get(borough);
            // if (! appb.isEmpty()) {
                // //ArrayList<String> hold = new ArrayList<String>();
                // boolean spotFound = false;
                // int index = 0;
                // for (Iterator i = appb.iterator(); i.hasNext() && spotFound == false; index++) {
                    // String boroughComparison = (String) i.next();
                    // if (averagePricePerBorough.get(boroughComparison) > boroughAVpB) {
                        // appb.add(index, borough);
                        // spotFound = true;
                    // }
                // }
                // if (spotFound == false) {
                   // appb.add(borough); 
                // }
            // }
            // else {
                // appb.add(borough);
            // }
        // }
        
        
    }
}
