import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Write a description of class DataHandler here.
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 29/03/22
 */
public class DataHandler
{
    // instance variables - replace the example below with your own
    private static AirbnbDataLoader airbnbDataLoader;
    private static BookingsDataLoader bookingsDataLoader;
    private static String bookingsDataFileName = "bookingsData.csv";
    
    protected static ArrayList<AirbnbListing> listings;
    
    private static ArrayList<Booking> bookingList = new ArrayList<Booking>();
    
    protected static ArrayList<String> boroughs = new ArrayList<String>();
    
    protected static HashMap<String, Borough> sortedBoroughs;
    
    protected static final String[][] mapPositions = {{ null, null, null, "Enfield", null, null, null },
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
    }

    public static void initialiseHandler() {
        if (listings == null || listings.isEmpty())
        {
            airbnbDataLoader = new AirbnbDataLoader();
            listings = airbnbDataLoader.load();
            sortedBoroughs = sortBoroughs();
        }
        
        loadBookingsData();
    }
    
    private static void loadBookingsData() {
        bookingsDataLoader = new BookingsDataLoader();
        bookingList = bookingsDataLoader.load(bookingsDataFileName);
    }
    
    public static AirbnbListing getProperty(String iD) {
        try
        {
            for (AirbnbListing listing : listings)
            {
                if (listing.getId().equals(iD)) 
                {
                    return listing;
                }
            }
        }
        catch (NullPointerException e) 
        {
            DataHandler.initialiseHandler();
        }
        
        return null;
    }

    public static ArrayList<AirbnbListing> getPropertiesFromBorough(String borough, int minPrice, int maxPrice)
    {
        ArrayList<AirbnbListing> listingsFromBoroughWithinParameters = new ArrayList<>();
        
        if (boroughs.contains(borough))
        {
            ArrayList<AirbnbListing> listingsFromBorough = sortedBoroughs.get(borough).getBoroughListings();
            Iterator i = listingsFromBorough.iterator();
    
            if (! (minPrice < 0 && maxPrice < 0)) 
            {        
                while (i.hasNext()) 
                {
                    AirbnbListing nextListing = (AirbnbListing) i.next();
                    if (nextListing.getPrice() >= minPrice) 
                    {
                        if (nextListing.getPrice() <= maxPrice || maxPrice < 0) 
                        {
                            listingsFromBoroughWithinParameters.add(nextListing);
                        }
                    }
                }
            }
            else 
            {
                listingsFromBoroughWithinParameters = listingsFromBorough;
            }
        }
        return listingsFromBoroughWithinParameters;
    }

    public static ArrayList<AirbnbListing> getPropertiesAtPrice(int minPrice, int maxPrice)
    {
        ArrayList<AirbnbListing> listingsAtPrice = new ArrayList<AirbnbListing>();
        Iterator i = listings.iterator();

        while (i.hasNext()) 
        {
            AirbnbListing nextListing = (AirbnbListing) i.next();

            if (nextListing.getPrice() >= minPrice) 
            {
                if (nextListing.getPrice() <= maxPrice || maxPrice < 0) 
                {
                    listingsAtPrice.add(nextListing);
                }
            }
        }
        return listingsAtPrice;
    }

    private static HashMap<String, Borough> sortBoroughs() 
    {
        HashMap<String, Borough> data = new HashMap<>(); 
        ArrayList<AirbnbListing> currentRunOfListingsFromSameBorough = new ArrayList<AirbnbListing>();
        String boroughOfCurrentRunOfListings = null;
        
        Iterator i = listings.iterator();
        while(i.hasNext())
        {
            AirbnbListing nextListing = (AirbnbListing) i.next();
        
            if (currentRunOfListingsFromSameBorough.isEmpty() || nextListing.getNeighbourhood().equalsIgnoreCase(boroughOfCurrentRunOfListings))
            {
                currentRunOfListingsFromSameBorough.add(nextListing);
                boroughOfCurrentRunOfListings = currentRunOfListingsFromSameBorough.get(0).getNeighbourhood();
            }
            else 
            {
                String boroughName = boroughOfCurrentRunOfListings;
                
                if (boroughs.contains(boroughName)) 
                {
                    Borough borough = data.get(boroughName);
                    borough.addListingToBorough(currentRunOfListingsFromSameBorough);
                }                
                else 
                {
                    Borough borough = new Borough(boroughName, currentRunOfListingsFromSameBorough);
                    data.put(boroughName, borough);
                    boroughs.add(boroughName);
                }
                
                currentRunOfListingsFromSameBorough = new ArrayList<AirbnbListing>();
                currentRunOfListingsFromSameBorough.add(nextListing);
                boroughOfCurrentRunOfListings = currentRunOfListingsFromSameBorough.get(0).getNeighbourhood();
            }
        }
        
        return data;
    }
    
    public static ArrayList<AirbnbListing> getPropertiesSortedBy(String borough, int minPrice, int maxPrice,String sortingElement) {
        ArrayList<AirbnbListing> unsortedListing = getPropertiesFromBorough(borough, minPrice, maxPrice);
        return selectionSort(unsortedListing, sortingElement);
    }

    private static ArrayList<AirbnbListing> selectionSort(ArrayList<AirbnbListing> unsortedList, String sortingElement){
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

    public static ArrayList<AirbnbListing> getPropertiesFromBorough(String borough)
    {
        return getPropertiesFromBorough(borough, -1, -1);
    }

    /**
     * @return the Arraylist of Airbnb Listings 
     */
    public static ArrayList<AirbnbListing> getData()
    {
        return listings; 
    }

    public static String[][] getMapPositions() 
    {
        return mapPositions;
    }

    /**
     * Searches through every listing and returns the lowest price for one night out of every property
     * 
     * this is used in creating the drop box for the user to select their price range they are looking for
     */
    public static Integer getLowestPrice()
    {
        Iterator i = listings.iterator();
        int lowest = -1;
        while (i.hasNext()) 
        {
            AirbnbListing currentProperty = (AirbnbListing) i.next();
            int currentPrice = currentProperty.getPrice();
            if (currentPrice < lowest || lowest < 0) 
            {
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
    public static Integer getHighestPrice()
    {
        Iterator i = listings.iterator();
        int highest = -1;
        while (i.hasNext()) 
        {
            AirbnbListing currentProperty = (AirbnbListing) i.next();
            int currentPrice = currentProperty.getPrice();
            if (currentPrice > highest) 
            {
                highest = currentPrice;
            }
        }
        return highest;
    }
    
    public static ArrayList<Booking> getBookingList() {
        loadBookingsData();
        return bookingList;
    }
    
    public static void addToBookingList(Booking booking) {
        bookingList.add(booking);
        saveBooking(booking);
    }
    
    private static void saveBooking(Booking booking) {
        BookingsDataWriter bookingsDataWriter = new BookingsDataWriter();
        bookingsDataWriter.write(booking, bookingsDataFileName);
    }
    
    public static void saveBookingList(int itemsRemoved) {
        BookingsDataWriter bookingsDataWriter = new BookingsDataWriter();
        bookingsDataWriter.write(bookingList, bookingsDataFileName, itemsRemoved);
    }
    
    public static void removeFromBookingList(Booking booking) {
        bookingList.remove(booking);
        saveBookingList(1);
    }
    
    protected static ArrayList<String> getBoroughNames()
    {
        return boroughs;
    }
    
    protected static void clear() {
        bookingList = new ArrayList<Booking>();
    }
}
