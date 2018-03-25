package blueprints;

import javafx.scene.image.Image;
import utility.Point;
import utility.Velocity;

public class AnimatedObject extends GraphicsObject{

    protected Velocity velocity;
    
    public AnimatedObject( Image image, Point origin, Velocity velocity ){
        super ( image, origin );
        this.velocity = velocity;
    }
    
    public AnimatedObject( Image image, Point origin ){
        this ( image, origin, new Velocity( 0, 0 ) );
    }
    
    public void update(){
        this.origin.setX( this.origin.getX() + this.velocity.getSpeedX() );
        this.origin.setY( this.origin.getY() + this.velocity.getSpeedY() );
    }
    
}
