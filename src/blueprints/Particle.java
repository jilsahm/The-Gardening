package blueprints;

import gamelogic.Weather;
import javafx.scene.image.Image;
import stagecontrol.Mainframe;
import utility.Point;
import utility.Velocity;

public class Particle extends AnimatedObject{

    private boolean repeatX;
    private boolean repeatY;
    private Image[] alternatives;
    private int     swaptimer;
    
    public Particle( Image[] images, Point origin, Velocity velocity, int swaptimer ) {
        super( images[0], origin, velocity );
        this.repeatX = true;
        this.repeatY = true;
        this.alternatives = images;
        this.swaptimer    = swaptimer;
    }
            
    public boolean isRepeatX() {
        return repeatX;
    }

    public void setRepeatX( boolean repeatX ) {
        this.repeatX = repeatX;
    }

    public boolean isRepeatY() {
        return repeatY;
    }

    public void setRepeatY( boolean repeatY ) {
        this.repeatY = repeatY;
    }

    @Override
    public void update() {
        super.update();
        
        
        if ( this.repeatX && ( this.origin.getX() + this.image.getWidth() ) < 0 ) {
            this.origin.setX( Mainframe.WIDTH + this.image.getWidth() );
        }
        if ( this.repeatY && ( this.origin.getY() - this.image.getHeight() ) > Mainframe.HEIGHT ) {
            this.origin.setY( -this.image.getHeight() );
        }
        if ( this.swaptimer > 0  && Weather.timestamp % this.swaptimer == 0 ) {
            this.image = this.alternatives[Weather.rnd.nextInt( this.alternatives.length )];
        }
    }
    
    public String whereAmI() {
        return String.format( "Im at x=%.1f and y=%.1f", this.origin.getX(), this.origin.getY() );
    }
}
