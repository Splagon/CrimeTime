import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

/**
 * This class represents a button in the hexagon map
 * 
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 25/03/22
 */
public class MapButton extends Button
{
    private String boroughName;
    
    /**
     * Constructor for objects of type MapButton
     */
    public MapButton(String boroughName)
    {
        // Sets the name of the button to be the first 4 letters of the borough name
        super(boroughName.substring(0, 4).toUpperCase());
        
        this.boroughName = boroughName;
        
        // When hovering over a borough, a tooltip with the borough's name appears
        setTooltip(new Tooltip(boroughName));
    }
    
    /**
     * returns the name of the borough
     * 
     * @return String - the name of the borough
     */
    public String getBoroughName() 
    {
        return boroughName;
    }
}
