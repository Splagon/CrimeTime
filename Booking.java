import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.Period;
import java.util.ArrayList;

/**
 * Write a description of class Booking here.
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 25/03/22
 */
public class Booking
{
    // the property associated with the booking
    private AirbnbListing property;
    private int grandTotal;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private long duration;

    /**
     * Constructor for objects of class Booking
     */
    public Booking(AirbnbListing property, int grandTotal, LocalDate checkInDate, LocalDate checkOutDate)
    {
        this.property = property;
        this.grandTotal = grandTotal;
        this.checkIn = checkInDate;
        this.checkOut  = checkOutDate;
        duration = calculateDuration(checkInDate, checkOutDate);
    }
    
    public static long calculateDuration(LocalDate checkInDate, LocalDate checkOutDate) {
        //Period period = Period.between(checkInDate, checkOutDate);
        long days = checkInDate.until(checkOutDate, ChronoUnit.DAYS);
        return days;
    }
    
    public String[] convertToCSV() {
        String[] line = new String[4];
        
        line[0] = property.getId();
        line[1] = String.valueOf(grandTotal);
        line[2] = checkIn.toString();
        line[3] = checkOut.toString();
        
        return line;
    }

    public int getGrandTotal() {
        return grandTotal;
    }
    
    public AirbnbListing getProperty() {
        return property;
    }
    
    public String getPropertyID() {
        return property.getId();
    }
    
    public LocalDate getCheckInDate() {
        return checkIn;
    }
    
    public LocalDate getCheckOutDate() {
        return checkOut;
    }
    
    public long getDuration() {
        return duration;
    }
}
