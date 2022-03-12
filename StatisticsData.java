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
}
