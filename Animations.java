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
            
        double currentRotation = node.rotateProperty().getValue() % 360.0;
        
        if (currentRotation == 0.0)
        {
            effect.setByAngle(360.0);
            effect.setCycleCount(1);
            effect.play();
        }
    }
    
    public void spinAndGrowIn(long time, Node node) 
    {   
        double currentRotationu = node.rotateProperty().getValue();
        double currentRotation = node.rotateProperty().getValue() % 360.0;
        
        if (currentRotation == 0.0)
        {
            node.setViewOrder(-1.0);
            node.getParent().setViewOrder(-1.0);
            
            RotateTransition rotateEffect = new RotateTransition(Duration.millis(time), node);
            ScaleTransition scaleEffect = new ScaleTransition(Duration.millis(time), node);
            
            rotateEffect.setToAngle(360.0);
            
            scaleEffect.setToX(1.3);
            scaleEffect.setToY(1.3);
            
            ParallelTransition parallelTransitionOne = new ParallelTransition();
                parallelTransitionOne.getChildren().addAll(rotateEffect, scaleEffect);
                
                node.setOnMouseExited(e -> parallelTransitionOne.setOnFinished(ex -> spinAndGrowOut(time, node)));
                parallelTransitionOne.setOnFinished(e -> node.setOnMouseExited(ex -> spinAndGrowOut(time, node)));
                
                parallelTransitionOne.play();
        }
    }
    
    public void spinAndGrowOut(long time, Node node) 
    {    
        double currentRotation = node.rotateProperty().getValue() % 360.0;
        
        RotateTransition rotateEffect = new RotateTransition(Duration.millis(time), node);
        ScaleTransition scaleEffect = new ScaleTransition(Duration.millis(time), node);
        
        rotateEffect.setToAngle(0.0);
            
        scaleEffect.setToX(1);
        scaleEffect.setToY(1);
            
        ParallelTransition parallelTransitionOne = new ParallelTransition();
            parallelTransitionOne.getChildren().addAll(rotateEffect, scaleEffect);
            parallelTransitionOne.play();
            
        node.setViewOrder(1.0);
        node.getParent().setViewOrder(1.0);
    }
}
