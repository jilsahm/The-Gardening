package gamelogic;

import blueprints.Bee;
import graphicscontrol.ImageBuffer;
import javafx.scene.canvas.GraphicsContext;
import utility.Point;

public class BeeOrder{

    private static final int NUMBER_OF_GHOSTS = 5;
    
    private Bee[]   ghostbees_start;
    private Bee[]   ghostbees_destination;
    private Point   start;
    private Point   destination;
    private boolean startFrozen;
    private boolean destinationFrozen;
    
    public BeeOrder(){
        this.ghostbees_start       = new Bee[NUMBER_OF_GHOSTS];
        for ( int i = 0; i < this.ghostbees_start.length; i++ ){
            this.ghostbees_start[i] = new Bee( ImageBuffer.BEE_GHOST_RIGHT.getImage(), ImageBuffer.BEE_GHOST_LEFT.getImage(), new Point( 0, 0 ) );
        }
        this.ghostbees_destination = new Bee[NUMBER_OF_GHOSTS];
        for ( int i = 0; i < this.ghostbees_destination.length; i++ ){
            this.ghostbees_destination[i] = new Bee( ImageBuffer.BEE_GHOST_RIGHT.getImage(), ImageBuffer.BEE_GHOST_LEFT.getImage(), new Point( 0, 0 ) );
        }
        this.start                 = null;
        this.destination           = null;
        this.startFrozen           = false;
        this.destinationFrozen     = false;
    }

    public void setStart( Point start ) {
        this.start = Point.deepcopy( start );  
        for ( Bee bee : this.ghostbees_start ){
            bee.warp( this.start );
            bee.setTarget( this.start );
        }
    }  

    public Point getStart() {
        return this.start;
    }

    public void setDestination( Point destination ) {
        this.destination = Point.deepcopy( destination );
        for ( Bee bee : this.ghostbees_destination ){
            bee.warp( this.destination );
            bee.setTarget( this.destination );
        }
    }

    public Point getDestination() {
        return this.destination;
    }    
    
    public void draw( GraphicsContext painter ){
        if ( this.start != null ){
            for ( Bee bee : this.ghostbees_start ){
                bee.draw( painter );
                bee.update();
            }
        }
        if ( this.destination != null ){
            for ( Bee bee : this.ghostbees_destination ){
                bee.draw( painter );
                bee.update();
            }
        }
    }
    
    public boolean isStartFrozen(){
        return this.startFrozen;
    }
    
    public void freezeStart(){
        this.startFrozen = true;
    }
    
    public boolean isDestinationFrozen(){
        return this.destinationFrozen;
    }
    
    public void freezeDestination(){
        this.destinationFrozen = true;        
    }
    
    public void clearFreeze(){
        this.startFrozen       = false;
        this.destinationFrozen = false;
        this.start             = null;
        this.destination       = null;
    }
    
}
