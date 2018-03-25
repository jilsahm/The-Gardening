package gamelogic;

import blueprints.Tooltip;
import javafx.scene.canvas.GraphicsContext;
import utility.Point;

public class Slot{

    private Point   origin;
    private Flower  flower;
    private Tooltip tooltip;
    
    public Slot( Point origin ){
        this.origin  = origin;
        this.flower  = null;
        this.tooltip = null;
    }
    
    public boolean isEmpty(){
        if ( this.flower == null ){
            return true;
        }
        return false;
    }
    
    public Flower getFlower(){
        return this.flower;
    }
    
    public Tooltip getTooltip(){
        return this.tooltip;
    }
    
    public void spawnFlower( FlowerType type ){
        this.flower = new Flower( type );
        this.tooltip = new Tooltip( type.getImage(), this.origin , this.flower );
    }
    
    public void clearFlower(){
        this.flower  = null;
        this.tooltip = null;
    }
    
    public void drawFlower( GraphicsContext painter ){
        painter.drawImage( this.flower.getImage(), this.origin.getX(), this.origin.getY() );
    }
    
}
