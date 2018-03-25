package gamelogic;

import java.util.Random;

import blueprints.Particle;
import graphicscontrol.ImageBuffer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import stagecontrol.Mainframe;
import utility.Point;
import utility.Velocity;

public class Weather {

	private enum State{
		SUN,
		WIND,
		RAIN
	}
	
	private static Weather   instance    = new Weather();
	private static final int LEAFS       = 15;
	private static final int DRIPS       = 500;
	private static final int EXTRA_WATER = 5;
	
	public  static long    timestamp  = 1;
	public  static Random  rnd        = new Random( System.currentTimeMillis() + 1 );
	
	private Point      icon_postition;
	private Image      icon;
	private Particle[] particles;
	private State      state;
	
	private Weather(){
		this.icon_postition = new Point( 64, 245 );
		this.icon           = ImageBuffer.WEATHER_SUN.getImage();
		this.particles      = null;
		this.state          = State.SUN;
	}
	
	public void effect( Patch[] patches ){
		switch( this.state ){
			case RAIN:
				for ( Patch current : patches ){
					if ( !current.isLocked() ){
						current.waterFlowers( Weather.EXTRA_WATER );
					}
				}
				break;
			case WIND:
				for ( Patch current : patches ){
					if ( !current.isLocked() ){
						current.spawnFlower( rnd );
					}
				}
				break;
			default:	
		}			
	}
	
	public void change() {
	    switch ( Weather.rnd.nextInt( 10 ) ) {
	        case 9: 
	        case 8:
	        case 7:
	        	this.setupWind();
                break;
	        case 6: 
            case 5:                
            case 4:
            	this.setupRain();
            	break;
            case 3: 
            case 2:
            case 1:            	
            default:
                this.icon = ImageBuffer.WEATHER_SUN.getImage();
                this.particles = null;
                this.state = State.SUN;
	    }
	}
	
	private void setupWind(){
		this.icon = ImageBuffer.WEATHER_WIND.getImage();
        this.particles = new Particle[Weather.LEAFS];
        for ( int i = 0; i < Weather.LEAFS; i++ ) {
            this.particles[i] = new Particle( new Image[]{ ImageBuffer.PARTICLE_LEAF_01.getImage(),
                                                           ImageBuffer.PARTICLE_LEAF_02.getImage() },
                                                           new Point( Mainframe.WIDTH + Weather.rnd.nextInt( 100 ), Weather.rnd.nextInt( (int)Mainframe.HEIGHT ) ),
                                                           new Velocity( -Weather.rnd.nextInt(2), Weather.rnd.nextDouble() * 1 ),
                                                           Weather.rnd.nextInt( 100 ) + 10 );
        }
        this.state = State.WIND;
	}
	
	private void setupRain(){
		this.icon = ImageBuffer.WEATHER_RAIN.getImage();
		this.particles = new Particle[Weather.DRIPS];
    	for ( int i = 0; i < Weather.DRIPS; i++ ) {
            this.particles[i] = new Particle( new Image[]{ ImageBuffer.PARTICLE_DRIP.getImage() },
                                                           new Point( Weather.rnd.nextInt( (int)Mainframe.WIDTH ), -Weather.rnd.nextInt( (int)Mainframe.HEIGHT ) ),
                                                           new Velocity( ( Weather.rnd.nextDouble() * -5 ) - 1, 10 ),
                                                           0 );
        }
    	this.state = State.RAIN;
	}
	
	public void drawIcon( GraphicsContext painter ){
		painter.drawImage( this.icon, this.icon_postition.getX(), this.icon_postition.getY() );
	}

	public void drawParticles( GraphicsContext painter ) {
	    if ( this.particles != null ) {
	        for ( Particle particle : this.particles ) {
	            particle.draw( painter );
	            particle.update();
	            //System.out.println( particle.whereAmI() );
	        }
	    }
	    Weather.timestamp++;
	}
	
	public static synchronized Weather getInstance(){
		return Weather.instance;
	}
	
}
