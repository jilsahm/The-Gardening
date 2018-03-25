package blueprints;

import java.util.Vector;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import stagecontrol.Mainframe;

public abstract class CanvasScene{

    protected Stage                stage;
    protected GridPane             gridpane;
    protected Canvas               canvas;
    protected Vector<CanvasButton> buttons;
    
    public CanvasScene( Stage stage ){
        this.stage    = stage;
        this.canvas   = new Canvas( Mainframe.WIDTH, Mainframe.HEIGHT );
        this.gridpane = new GridPane(); 
        this.buttons  = new Vector<CanvasButton>();
    }
    
    protected abstract void show();
    protected abstract void registerEvents();
    
    public void loadScene(){
        this.stage.setScene( new Scene( this.gridpane ) );
    }
    
}
