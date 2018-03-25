package threads;

import java.util.ArrayList;
import java.util.Random;

import blueprints.Bee;
import blueprints.Hint;
import gamelogic.BeeOrder;
import gamelogic.Patch;
import stagecontrol.Gamemenu;
import utility.Point;

public class MovingBeeswarm extends Thread{

    private static final long WAITTIME = 3500;
    
    private ArrayList<Bee> swarm;
    private BeeOrder       order;
    private Patch          destinationPatch;
    private Point          hivelocation;
    private Hint           hint;
    
    public MovingBeeswarm( ArrayList<Bee> swarm, BeeOrder order, Patch destinationPatch, Point hiveLocation, Hint hint ){
        this.setDaemon( true );
        Gamemenu.disabledUI   = true;
        this.swarm            = swarm;
        this.order            = order;
        this.destinationPatch = destinationPatch;
        this.hivelocation     = hiveLocation;
        this.hint             = hint;
    }
    
    @Override
    public void run() {        
        try {
            this.hint.setText( "Swarm is moving..." );
            this.orderSwarm( this.order.getStart() );
            Thread.sleep( WAITTIME );
            this.orderSwarm( this.order.getDestination() );
            Thread.sleep( WAITTIME );
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Gamemenu.disabledUI = false;
            Random rnd = new Random( System.currentTimeMillis() );
            
            this.orderSwarm( this.hivelocation );            
            
            switch ( rnd.nextInt( 10 ) ) {
                case 9: case 8:         this.destinationPatch.spawnFlower( rnd );
                case 7: case 6: case 5: this.destinationPatch.spawnFlower( rnd );
                default:                this.destinationPatch.spawnFlower( rnd );
            }            
            
            this.order.clearFreeze();
            this.hint.setVisible( false );
        }
    }
    
    private void orderSwarm( Point target ){
        for ( Bee bee : this.swarm ){
            bee.setTarget( target );
        }
    }
    
}
