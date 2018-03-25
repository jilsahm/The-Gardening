package blueprints;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import stagecontrol.Mainframe;
import utility.Point;
import utility.Repeat;
import utility.Velocity;

public class AnimatedBackground extends AnimatedObject{	
	
	protected Repeat repeat;
	
	public AnimatedBackground( Image image, Point origin, Velocity velocity ){
        super ( image, origin, velocity );
    }
    
    public AnimatedBackground( Image image, Point origin ){
        this ( image, origin, new Velocity( 0, 0 ) );
    }
    
    @Override
    public void draw( GraphicsContext painter ) {
    	super.draw( painter );
    	switch ( this.repeat ){
		case REPEAT_X:
			if ( this.velocity.getSpeedX() < 0 ){
				painter.drawImage( this.image, this.origin.getX() + this.image.getWidth(), this.origin.getY() );
			} else {
				painter.drawImage( this.image, this.origin.getX() - this.image.getWidth(), this.origin.getY() );
			}
			break;
		case REPEAT_Y:
			if ( this.velocity.getSpeedY() > 0){
				painter.drawImage( this.image, this.origin.getX(), this.origin.getY() - this.image.getHeight() );
			} else {
				painter.drawImage( this.image, this.origin.getX(), this.origin.getY() + this.image.getHeight() );
			}			
			break;    	
    	}
    	
    }
    
    @Override
    public void update() {
    	super.update();
    	switch ( this.repeat ){
		case REPEAT_X:
			if ( this.velocity.getSpeedX() < 0 && this.origin.getX() + this.image.getWidth() < 0 ){
				this.origin.setX( 0 );
			} else if ( this.velocity.getSpeedX() > 0 && this.origin.getX() > Mainframe.WIDTH ){
				this.origin.setX( 0 );
			}
			break;
		case REPEAT_Y:
			// TODO			
			break;    	
    	}
    }

	public Repeat getRepeat() {
		return repeat;
	}

	public void setRepeat( Repeat repeat ) {
		this.repeat = repeat;
	}
	
}
