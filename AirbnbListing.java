import java.util.Comparator;
import java.util.Iterator;
import java.util.ArrayList;
 

/**
 * Represents one listing of a property for rental on Airbnb.
 * This is essentially one row in the data table. Each column
 * has a corresponding field.
 * 
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 25/03/22
 */ 

public class AirbnbListing {
    /**
     * The id and name of the individual property
     */
    private String id;
    private String name;
    /**
     * The id and name of the host for this listing.
     * Each listing has only one host, but one host may
     * list many properties.
     */
    private String host_id;
    private String host_name;

    /**
     * The grouped location to where the listed property is situated.
     * For this data set, it is a london borough.
     */
    private String neighbourhood;

    /**
     * The location on a map where the property is situated.
     */
    private double latitude;
    private double longitude;

    /**
     * The type of property, either "Private room" or "Entire Home/apt".
     */
    private String room_type;

    /**
     * The price per night's stay
     */
    private int price;

    /**
     * The minimum number of nights the listed property must be booked for.
     */
    private int minimumNights;
    private int numberOfReviews;

    /**
     * The date of the last review, but as a String
     */
    private String lastReview;
    private double reviewsPerMonth;

    /**
     * The total number of listings the host holds across AirBnB
     */
    private int calculatedHostListingsCount;
    /**
     * The total number of days in the year that the property is available for
     */
    private int availability365;

    public AirbnbListing(String id, String name, String host_id,
                         String host_name, String neighbourhood, double latitude,
                         double longitude, String room_type, int price,
                         int minimumNights, int numberOfReviews, String lastReview,
                         double reviewsPerMonth, int calculatedHostListingsCount, int availability365) {
        this.id = id;
        this.name = name;
        this.host_id = host_id;
        this.host_name = host_name;
        this.neighbourhood = neighbourhood;
        this.latitude = latitude;
        this.longitude = longitude;
        this.room_type = room_type;
        this.price = price;
        this.minimumNights = minimumNights;
        this.numberOfReviews = numberOfReviews;
        this.lastReview = lastReview;
        this.reviewsPerMonth = reviewsPerMonth;
        this.calculatedHostListingsCount = calculatedHostListingsCount;
        this.availability365 = availability365;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public String getMailName() {    
        return name.replaceAll("\\s", "%20");
    }

    public String getHost_id() {
        return host_id;
    }

    public String getHost_name() {
        return host_name;
    }
    
    public String getMailHost_name(boolean withSpaces) 
    {
        if (withSpaces)
        {
            return host_name.replaceAll("\\s", "%20");
        }
        else 
        {
            String URL_mail_host_name = host_name;
            String[] unacceptableSymbols = new String[] {"\\s", ",", ";", "<", ">", "/", "'", "#", "~", "-", "_", "=", "&", "^", "$", "£", "!", "|"};
            
            for (String symbol : unacceptableSymbols) 
            {
                URL_mail_host_name = URL_mail_host_name.replaceAll(symbol, "");
            }
            
            return URL_mail_host_name;
        }
    }

    public String getNeighbourhood() 
    {
        return neighbourhood;
    }

    public double getLatitude() 
    {
        return latitude;
    }

    public double getLongitude() 
    {
        return longitude;
    }

    public String getRoom_type() 
    {
        return room_type;
    }

    public int getPrice() 
    {
        return price;
    }

    public int getMinimumNights() 
    {
        return minimumNights;
    }

    public int getNumberOfReviews() 
    {
        return numberOfReviews;
    }

    public String getLastReview() 
    {
        return lastReview;
    }

    public double getReviewsPerMonth() 
    {
        return reviewsPerMonth;
    }

    public int getCalculatedHostListingsCount() 
    {
        return calculatedHostListingsCount;
    }

    public int getAvailability365() 
    {
        return availability365;
    }

    @Override
    public String toString() 
    {
        return "AirbnbListing{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", host_id='" + host_id + '\'' +
                ", host_name='" + host_name + '\'' +
                ", neighbourhood='" + neighbourhood + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", room_type='" + room_type + '\'' +
                ", price=" + price +
                ", minimumNights=" + minimumNights +
                ", numberOfReviews=" + numberOfReviews +
                ", lastReview='" + lastReview + '\'' +
                ", reviewsPerMonth=" + reviewsPerMonth +
                ", calculatedHostListingsCount=" + calculatedHostListingsCount +
                ", availability365=" + availability365 +
                '}';
    }
    
    public static Comparator<AirbnbListing> sortByListingHostName = new Comparator<AirbnbListing>() 
    {
        @Override
        public int compare(AirbnbListing listingOne, AirbnbListing listingTwo)
        {
            String listingOneName = listingOne.getHost_name().toLowerCase();
            String listingTwoName = listingTwo.getHost_name().toLowerCase();
            
            return listingOneName.compareTo(listingTwoName);
        }
    };
    
    public static Comparator<AirbnbListing> sortByListingPrice = new Comparator<AirbnbListing>() 
    {
        @Override
        public int compare(AirbnbListing listingOne, AirbnbListing listingTwo)
        {
            Integer listingOnePrice = listingOne.getPrice();
            Integer listingTwoPrice = listingTwo.getPrice();
            
            return listingOnePrice.compareTo(listingTwoPrice);
        }
    };
    
    public static Comparator<AirbnbListing> sortByListingReviews = new Comparator<AirbnbListing>() 
    {
        @Override
        public int compare(AirbnbListing listingOne, AirbnbListing listingTwo)
        {
            int listingOneReviews = listingOne.getNumberOfReviews();
            int listingTwoReviews = listingTwo.getNumberOfReviews();
            
            return listingOneReviews - listingTwoReviews;
        }
    };
    
    public static Comparator<AirbnbListing> reverseSortByListingHostName = new Comparator<AirbnbListing>() 
    {
        @Override
        public int compare(AirbnbListing listingOne, AirbnbListing listingTwo)
        {
            String listingOneName = listingOne.getHost_name().toLowerCase();
            String listingTwoName = listingTwo.getHost_name().toLowerCase();
            
            return listingTwoName.compareTo(listingOneName);
        }
    };
    
    public static Comparator<AirbnbListing> reverseSortByListingPrice = new Comparator<AirbnbListing>() 
    {
        @Override
        public int compare(AirbnbListing listingOne, AirbnbListing listingTwo)
        {
            Integer listingOnePrice = listingOne.getPrice();
            Integer listingTwoPrice = listingTwo.getPrice();
            
            return listingTwoPrice.compareTo(listingOnePrice);
        }
    };
    
    public static Comparator<AirbnbListing> reverseSortByListingReviews = new Comparator<AirbnbListing>() 
    {
        @Override
        public int compare(AirbnbListing listingOne, AirbnbListing listingTwo)
        {
            Integer listingOneReviews = listingOne.getNumberOfReviews();
            Integer listingTwoReviews = listingTwo.getNumberOfReviews();
            
            return listingTwoReviews.compareTo(listingOneReviews);
        }
    };
}
