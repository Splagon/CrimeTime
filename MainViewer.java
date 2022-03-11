import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Write a description of class MapViewer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MainViewer extends Stage
{
    // instance variables - replace the example below with your o
    Scene welcomeScene;
    Scene mapScene;

    /**
     * Constructor for objects of class MapViewer
     */
    public MainViewer()
    {
        makeWelcomeScene();
        makeMapScene();
        
        setScene(mapScene);
        //setScene(welcomeScene);
    }
    
    private void makeWelcomeScene() {
        
    }
    
    private void makeMapScene() {
        setTitle("Map of London");
        
        Pane root = new FlowPane();
        
        GridPane stats = new GridPane();
            Label statsLabel = new Label("Stats");
            Label statsLabel1 = new Label("Stat 1");
            Label statsLabel2 = new Label("Stat 2");
            Label statsLabel3 = new Label("Stat 3");
            Label statsLabel4 = new Label("Stat 4");
            
            stats.add(statsLabel, 0, 0);
            stats.add(statsLabel1, 0, 1);
            stats.add(statsLabel2, 0, 2);
            stats.add(statsLabel3, 0, 3);
            stats.add(statsLabel4, 0, 4);
        
        GridPane map = new GridPane();
            Button nb0 = new Button("nb0");
            Button nb1 = new Button("nb1");
            Button nb2 = new Button("nb2");
            Button nb3 = new Button("nb3");
            Button nb4 = new Button("nb4");
            
            map.add(nb0, 1, 0);
            map.add(nb1, 0, 1);
            map.add(nb2, 1, 1);
            map.add(nb3, 2, 1);
            map.add(nb4, 1, 2);
            
        
        GridPane key = new GridPane();
            Label keyLabel = new Label("Key");
            Label keyLabel1 = new Label("Value 1");
            Label keyLabel2 = new Label("Value 2");
            Label keyLabel3 = new Label("Value 3");
            Label keyLabel4 = new Label("Value 4");
            
            stats.add(keyLabel, 0, 0);
            stats.add(keyLabel1, 1, 0);
            stats.add(keyLabel2, 2, 0);
            stats.add(keyLabel3, 3, 0);
            stats.add(keyLabel4, 4, 0);
        
        root.getChildren().add(stats);
        root.getChildren().add(map);
        root.getChildren().add(key);
        
        Scene scene = new Scene(root, 500, 500);
        
        setScene(scene);
        show();
    }
}
