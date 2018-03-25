package blueprints;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import utility.Point;

public class GraphicsObject {

	protected Point origin;
	protected Image image;
	
	public GraphicsObject( Image image, Point origin ){
		this.origin = origin;
		this.image  = image;
	}
	
	public GraphicsObject( Image image ){
		this( image, new Point( 0, 0 ) );
	}
	
	public void draw( GraphicsContext painter ){
		painter.drawImage( this.image, this.origin.getX(), this.origin.getY() );
	}

    public Image getImage() {
        return image;
    }

    public void setImage( Image image ) {
        this.image = image;
    }
    
    public Point getOrigin(){
        return this.origin;
    }
}
