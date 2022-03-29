import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.Period;
import java.util.ArrayList;

/**
 * A class that represents one booking of the user on the application.
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 25/03/22
 */
public class Booking
{
    // the property associated with the booking
    private AirbnbListing property;
    // the total price of the stay
    private int grandTotal;
    // the check in date
    private LocalDate checkIn;
    // the check out date
    private LocalDate checkOut;
    // the duration of the stay
    private long duration;

    /**
     * Constructor for objects of class Booking.
     * 
     * @param property Property concerned by booking.
     * @param grandTotal Price of the stay.
     * @param checkInDate  The check in date of the stay
     * @param checkOutDate The check out date of the stay
     */
    public Booking(AirbnbListing property, int grandTotal, LocalDate checkInDate, LocalDate checkOutDate)
    {
        this.property = property;
        this.grandTotal = grandTotal;
        this.checkIn = checkInDate;
        this.checkOut  = checkOutDate;
        duration = calculateDuration(checkInDate, checkOutDate);
    }
    
    /**
     * @return The period length between to dates in days.
     */
    public static long calculateDuration(LocalDate checkInDate, LocalDate checkOutDate) 
    {
        long days = checkInDate.until(checkOutDate, ChronoUnit.DAYS);
        return days;
    }
    
    /**
     * Convert booking fields into an array of strings.
     * @return Array of strings containing porperty id, grand total, check in and check out date.
     */
    public String[] convertToCSV() 
    {
        String[] line = new String[4];
        
        line[0] = property.getId();
        line[1] = String.valueOf(grandTotal);
        line[2] = checkIn.toString();
        line[3] = checkOut.toString();
        
        return line;
    }
    
    /**
     * @return The grand total
     */
    public int getGrandTotal() 
    {
        return grandTotal;
    }
    
    /**
     * @return The property concerned by the booking.
     */
    public AirbnbListing getProperty() 
    {
        return property;
    }
    
    /**
     * @return The property ID 
     */
    public String getPropertyID() 
    {
        return property.getId();
    }
    
    /**
     * @return The check In date 
     */
    public LocalDate getCheckInDate() 
    {
        return checkIn;
    }
    
    /**
     * @return The check out date
     */
    public LocalDate getCheckOutDate() 
    {
        return checkOut;
    }
    
    /**
     * @return The duration of the stay
     */
    public long getDuration() 
    {
        return duration;
    }
}
