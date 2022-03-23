import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.Period;
import java.util.ArrayList;

/**
 * Write a description of class Booking here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Booking
{
    private AirbnbListing property;
    private int grandTotal;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private long duration;

    /**
     * Constructor for objects of class Booking
     */
    public Booking(AirbnbListing property, int grandTotal, LocalDate checkInDate, LocalDate checkOutDate)
    {
        this.property = property;
        this.grandTotal = grandTotal;
        this.checkInDate = checkInDate;
        this.checkOutDate  = checkOutDate;
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
        line[2] = checkInDate.toString();
        line[3] = checkOutDate.toString();
        
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
        return checkInDate;
    }
    
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }
    
    public long getDuration() {
        return duration;
    }
}
