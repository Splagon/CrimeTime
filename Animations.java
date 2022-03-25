import javafx.scene.Node;
import javafx.animation.*;
import javafx.util.Duration;

/**
 * Performs animations on nodes
 *
 * @author Charles Suddens-Spiers (K21040272), Michael Higham (K21051343), 
 *         Matthew Palmer (K21005255), Aymen Berbache (K21074588).
 * @version 25/03/22
 */
public class Animations
{
    /**
     * Fade a certain node out
     * @params Node node - The node in which this will happen to; long time - The amount of time that the animation will last for. 
     */
    public static void fadeOut(Node node, long time)
    {
        FadeTransition effect = new FadeTransition(Duration.millis(time), node);
        effect.setFromValue(1.0);
        effect.setToValue(0.0);
        effect.play();
    }
    
    /**
     * Fade a certain node in
     * @params Node node - The node in which this will happen to; long time - The amount of time that the animation will last for. 
     */
    public static void fadeIn(Node node, long time)
    {
        FadeTransition effect = new FadeTransition(Duration.millis(time), node);
        effect.setFromValue(0.0);
        effect.setToValue(1.0);
        effect.play();
    }
    
    /**
     * Spins a certain node
     * @param Node node - The node in which this will happen to
     * @param long time - The amount of time this animation will last for
     */
    public static void spin(long time, Node node) 
    {
         RotateTransition effect = new RotateTransition(Duration.millis(time), node);
         effect.setByAngle(360);
         effect.setCycleCount(1);
         effect.play();
    }
}
