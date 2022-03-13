import java.util.ArrayList;

/**
 * Calculates the data needed for the statistics viewer
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StatisticsData extends DataHandler
{
    // instance variables - replace the example below with your own
    private ArrayList<AirbnbListing> listings; 

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
    
    public String getExpensiveInfo()
    {
        String expensiveBorough = "test";
        String[] boroughs = {"Kingston upon Thames", "Croydon", "Bromley", "Hounslow", "Ealing", "Havering", "Hillingdon", 
            "Harrow", "Brent", "Barnet", "Enfield", "Waltham Forest", "Redbridge", "Sutton", "Lambeth", "Southwark",
            "Lewisham", "Greenwich", "Bexley", "Richmond upon Thames", "Merton", "Wandsworth", "Hammersmith and Fulham", 
            "Kensington and Chelsea", "City of London", "Westminster", "Camden", "Tower Hamlets", "Islington", "Hackney",
            "Haringey", "Newham", "Barking and Dagenham"};

        for(int i = 0; i < boroughs.length; i++) // A for loop iterating through the boroughs array
        {
            ArrayList<AirbnbListing> boroughProperty = getPropertiesFromBorough(boroughs[i]);
            int totalPrice = 0;
            System.out.println(boroughProperty.size());
            int lastTotalPrice = 0;  
            for (int j = 0; j < boroughProperty.size(); j++) {
                AirbnbListing property = boroughProperty.get(j);
                totalPrice += property.getPrice() * property.getMinimumNights();
            }
            System.out.println(totalPrice);
            if(totalPrice > lastTotalPrice)
            {
                lastTotalPrice = totalPrice;
                expensiveBorough = boroughs[i];
            }
        }
        System.out.println("test");
        return expensiveBorough; 
            
        
        
    }
}
