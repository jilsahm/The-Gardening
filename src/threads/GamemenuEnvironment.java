package threads;

import blueprints.AnimatedBackground;
import blueprints.GraphicsObject;
import graphicscontrol.ImageBuffer;
import utility.Point;
import utility.Repeat;
import utility.Velocity;

public class GamemenuEnvironment extends Thread{
	
	private static final short DELAY = 16;
	
	private AnimatedBackground[] clouds = {
        	new AnimatedBackground( ImageBuffer.CLOUDS_NORMAL_BACK.getImage(), new Point( 0, 126 ), new Velocity( -0.1, 0 ) ),
        	new AnimatedBackground( ImageBuffer.CLOUDS_NORMAL_CENTER.getImage(), new Point( 0, 190 ), new Velocity( -0.25, 0 ) ),
        	new AnimatedBackground( ImageBuffer.CLOUDS_NORMAL_FRONT.getImage(), new Point( 0, 350 ), new Velocity( -0.5, 0 ) )
        };
	
	private boolean        running;
	private GraphicsObject gameisle;
	
    public GamemenuEnvironment(){
    	this.running = false;
    	this.setDaemon( true );
    	this.gameisle = new GraphicsObject( ImageBuffer.GAMEISLE_NORMAL.getImage() );
    	for ( AnimatedBackground layer : clouds ){
    		layer.setRepeat( Repeat.REPEAT_X );
    	}
    }
    
    @Override
    public void run() {
    	this.running    = true;
		
		while( running ){
			
			try {
				Thread.sleep( DELAY );				
			} catch (InterruptedException e) {
				System.err.println("Threadsleep failed!");
			}
			for ( AnimatedBackground layer : this.clouds ){
				layer.update();
			}
						
		} 
    }
    
    public AnimatedBackground[] getClouds() {
		return clouds;
	}

	public void drop(){
    	this.running = false;
    }

	public GraphicsObject getGameisle() {
		return gameisle;
	}
}
