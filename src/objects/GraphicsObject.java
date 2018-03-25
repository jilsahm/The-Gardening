package objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class GraphicsObject {

	protected double originX;
	protected double originY;
	protected Image  image;
	
	public GraphicsObject( double x, double y, Image image ){
		this.originX = x;
		this.originY = y;
		this.image   = image;
	}
	
	public void draw( GraphicsContext painter ){
		painter.drawImage( this.image, this.originX, this.originY );
	}
}
