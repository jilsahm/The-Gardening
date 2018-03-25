package objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import utility.ContentLoader;

public class CanvasButton extends GraphicsObject{

	private final double WIDTH;
	private final double HEIGHT;
	private boolean      focused;
	private Image        focusImage;
	
	public CanvasButton( double x, double y, Image image, Image focusImage ){
		super( x, y, image );		
		this.WIDTH      = image.getWidth();
		this.HEIGHT     = image.getHeight();
		this.focused    = false;
		this.focusImage = focusImage;
	}
	
	public boolean isInEventRange( double eventX, double eventY ){
		if (    eventX >= this.originX && eventX <= this.originX + this.WIDTH
		     && eventY >= this.originY && eventY <= this.originY + this.HEIGHT ){
			this.focused = true;
		} else {
			this.focused = false;
		}		
		return this.focused;
	}
	
	@Override
	public void draw( GraphicsContext painter ) {
		if ( !focused ){
			super.draw( painter );
		} else {
			painter.drawImage( this.focusImage, this.originX, this.originY );
		}
	}
}
