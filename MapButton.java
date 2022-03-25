import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

/**
 * 
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 25/03/22
 */
public class MapButton extends Button
{
    private String boroughName;
    
    public MapButton(String boroughName)
    {
        super(boroughName.substring(0, 4).toUpperCase());
        this.boroughName = boroughName;
        setTooltip(new Tooltip(boroughName));
    }
    
    public String getBoroughName() {
        return boroughName;
    }
}
