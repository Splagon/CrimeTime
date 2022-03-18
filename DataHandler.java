import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Write a description of class DataHandler here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DataHandler
{
    // instance variables - replace the example below with your own
    private static AirbnbDataLoader dataLoader = new AirbnbDataLoader();
    protected static ArrayList<AirbnbListing> listings;
    protected static String[][] mapPositions = {{ null, null, null, "Enfield", null, null, null },
                                                { null, null, "Barnet", "Haringey", "Waltham Forest", null, null },
                                                { "Harrow", "Brent", "Camden", "Islington", "Hackney", "Redbridge", "Havering" },
                                                { "Hillingdon", "Ealing", "Kensington and Chelsea", "Westminster", "Tower Hamlets", "Newham", "Barking and Dagenham" },
                                                { "Hounslow", "Hammersmith and Fulham", "Wandsworth", "City of London", "Greenwich", "Bexley", null },
                                                { null, "Richmond upon Thames", "Merton", "Lambeth", "Southwark", "Lewisham", null },
                                                { null, "Kingston upon Thames", "Sutton", "Croydon", "Bromley", null, null },
                                               };

    /**
     * Constructor for objects of class DataHandler
     */
    public DataHandler()
    {
        // initialise instance variables
        listings = dataLoader.load();
    }
    
    public static ArrayList<AirbnbListing> getPropertiesFromBorough(String borough, int minPrice, int maxPrice)
    {
        ArrayList<AirbnbListing> listingsFromBorough = new ArrayList<AirbnbListing>();
        Iterator i = listings.iterator();
        
        while (i.hasNext()) 
        {
            AirbnbListing nextListing = (AirbnbListing) i.next();
            
            if((nextListing.getNeighbourhood().toLowerCase()).equals(borough.toLowerCase()) || borough == null) 
            {
                if (nextListing.getPrice() >= minPrice) {
                    if (nextListing.getPrice() <= maxPrice || maxPrice < 0) {
                        listingsFromBorough.add(nextListing);
                    }
                }
            }
        }
        
        return listingsFromBorough;
    }
    
    public static Map<String, ArrayList<AirbnbListing>> sortBoroughs()
    {
        Map<String, ArrayList<AirbnbListing>> data = new HashMap<>(); 
        for(int rows = 0; rows < mapPositions.length; rows++) // A for loop iterating through the boroughs array
        {
            for(int columns = 0; columns < mapPositions[rows].length; columns++)
            {   
                if(mapPositions[rows][columns] != null)
                {
                    ArrayList<AirbnbListing> properties = new ArrayList<>();
                    data.put(mapPositions[rows][columns], properties);
                }
            }
        }
        
        Iterator i = listings.iterator();
        while(i.hasNext())
        {
            AirbnbListing nextListing = (AirbnbListing) i.next();
            for(Map.Entry<String, ArrayList<AirbnbListing>> set : data.entrySet()) {
                String key = set.getKey();
                
                if (key != null) {
                    if(set.getKey().toLowerCase().equals(nextListing.getNeighbourhood().toLowerCase()))
                    {
                        ArrayList<AirbnbListing> list = set.getValue();
                        list.add(nextListing);
                        data.put(key, list);
                    }
                }
            }
        }
        
        return data; 
    }
    
    public ArrayList<AirbnbListing> getPropertiesSortedBy(String borough, int minPrice, int maxPrice,String sortingElement) {
        ArrayList<AirbnbListing> unsortedListing = getPropertiesFromBorough(borough, minPrice, maxPrice);
        return selectionSort(unsortedListing, sortingElement);
    }
    
    private ArrayList<AirbnbListing> selectionSort(ArrayList<AirbnbListing> unsortedList, String sortingElement){
        switch (sortingElement) 
        {
            case "Price ↑":
                Collections.sort(unsortedList, AirbnbListing.sortByListingPrice);
                break;
            case "Reviews ↑":
                Collections.sort(unsortedList, AirbnbListing.sortByListingReviews);
                break;
            case "Name ↑":
                Collections.sort(unsortedList, AirbnbListing.sortByListingHostName);
                break;
            case "Name ↓":
                Collections.sort(unsortedList, AirbnbListing.reverseSortByListingHostName);
                break;
            case "Price ↓":
                Collections.sort(unsortedList, AirbnbListing.reverseSortByListingPrice);
                break;
            case "Reviews ↓":
                Collections.sort(unsortedList, AirbnbListing.reverseSortByListingReviews);
                break;
            } 
        return unsortedList;
    }
    
    public ArrayList<AirbnbListing> getPropertiesFromBorough(String borough)
    {
        ArrayList<AirbnbListing> listingsFromBorough = getPropertiesFromBorough(borough, -1, -1);
        
        return listingsFromBorough;
    }
    
    /**
     * @return the Arraylist of Airbnb Listings 
     */
    public ArrayList<AirbnbListing> getData()
    {
        return listings; 
    }
    
    public String[][] getMapPositions() 
    {
        return mapPositions;
    }
    
    /**
     * Searches through every listing and returns the lowest price for one night out of every property
     * 
     * this is used in creating the drop box for the user to select their price range they are looking for
     */
    public Integer getLowestPrice()
    {
        Iterator i = listings.iterator();
        int lowest = -1;
        while (i.hasNext()) 
        {
            AirbnbListing currentProperty = (AirbnbListing) i.next();
            int currentPrice = currentProperty.getPrice();
            if (currentPrice < lowest || lowest < 0) {
                lowest = currentPrice;
            }
        }
        return lowest;
    }
    
    /**
     * Searches through every listing and returns the highest price for one night out of every property
     * 
     * this is used in creating the drop box for the user to select their price range they are looking for
     */
    public Integer getHighestPrice()
    {
        Iterator i = listings.iterator();
        int highest = -1;
        while (i.hasNext()) 
        {
            AirbnbListing currentProperty = (AirbnbListing) i.next();
            int currentPrice = currentProperty.getPrice();
            if (currentPrice > highest) {
                highest = currentPrice;
            }
        }
        return highest;
    }
}
