package blueprints;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import utility.Point;

public class CanvasButton extends GraphicsObject{

	private final double WIDTH;
	private final double HEIGHT;
	private final String COMMAND;
	private boolean      focused;
	private boolean      disabled;
	private Image        focusImage;
	
	public CanvasButton( Image image, Image focusImage, Point origin, String command ){
		super( image, origin );		
		this.WIDTH      = image.getWidth();
		this.HEIGHT     = image.getHeight();
		this.COMMAND    = command;
		this.focused    = false;
		this.disabled   = false;
		this.focusImage = focusImage;
	}
	
	public CanvasButton( Image focusImage, Point origin, String command ){
	    this( focusImage, null, origin, command );
	}
	
	public String getCommand() {
        return COMMAND;
    }	
	
    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled( boolean disabled ) {
        this.disabled = disabled;
    }

    public boolean isInEventRange( double eventX, double eventY ){
		if (    eventX >= this.origin.getX() && eventX <= this.origin.getX() + this.WIDTH
		     && eventY >= this.origin.getY() && eventY <= this.origin.getY() + this.HEIGHT ){
			this.focused = true;
		} else {
			this.focused = false;
		}
		return this.focused;
	}
	
	@Override
	public void draw( GraphicsContext painter ) {
	    if ( null != focusImage ){
	        if ( !focused ){
	            super.draw( painter );
		    } else {
		        painter.drawImage( this.focusImage, this.origin.getX(), this.origin.getY() );
		    }
	    } else {
	        if ( focused ){
	            super.draw( painter );
	        }
	    }
	}
}
