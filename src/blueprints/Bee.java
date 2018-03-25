package blueprints;

import java.util.Random;

import graphicscontrol.ImageBuffer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import utility.Point;

public class Bee extends AnimatedObject{

    private static final double DISTANCE_X  = 50;
    private static final double DISTANCE_Y  = 50;
    private static final double MAX_SPEED   = 1.5d;
    private static final double MIN_SPEED   = -1.5d;
    private static final double CALIBRATION = 40d;
    private static final double SLOWDOWN    = 0.1d;
    private static final double TRANSLATE_X = 25d;
    private static final double TRANSLATE_Y = 20d;
    private static final Random RND         = new Random( System.currentTimeMillis() );
    
    private Point target;
    private Image beeToRight;
    
    public Bee( Point target ){
        super( ImageBuffer.BEE_LEFT.getImage(), new Point( target.getX(), target.getY() ) );
        this.beeToRight = ImageBuffer.BEE_RIGHT.getImage();
        this.target     = Point.deepcopy( target );
    }
    
    public Bee( Image right, Image left, Point target ){
        super( left, new Point( target.getX(), target.getY() ) );
        this.beeToRight = right;
        this.target     = new Point( 
                    target.getX() + ImageBuffer.PATCH_NORMAL.getImage().getWidth() / 2 + TRANSLATE_X,
                    target.getY() + ImageBuffer.PATCH_NORMAL.getImage().getHeight() / 2 + TRANSLATE_Y
                );
    }
    
    @Override
    public void draw( GraphicsContext painter ) {
        if ( 0 > this.velocity.getSpeedX() ){
            super.draw( painter );
        } else {
            painter.drawImage( this.beeToRight, this.origin.getX(), this.origin.getY() );
        }
    }
    
    @Override
    public void update() {
        // Recalculate velocity
        if ( this.target.getX() - this.origin.getX() < DISTANCE_X ){
            this.velocity.modSpeedX( RND.nextDouble() / -CALIBRATION );
        } else if ( this.target.getX() - this.origin.getX() > DISTANCE_X ){
            this.velocity.modSpeedX( RND.nextDouble() / CALIBRATION );
        }
        if ( this.target.getY() - this.origin.getY() < DISTANCE_Y ){
            this.velocity.modSpeedY( RND.nextDouble() / -CALIBRATION );
        } else if ( this.target.getY() - this.origin.getY() > DISTANCE_Y  ){
            this.velocity.modSpeedY( RND.nextDouble() / CALIBRATION );
        }
        
        // Cap velocityescalation
        if ( this.velocity.getSpeedX() > MAX_SPEED ){
            this.velocity.modSpeedX( -SLOWDOWN );
        } else if ( this.velocity.getSpeedX() > MAX_SPEED ){
            this.velocity.modSpeedX( SLOWDOWN );
        }
        if ( this.velocity.getSpeedY() > MAX_SPEED ){
            this.velocity.modSpeedY( -SLOWDOWN );
        } else if ( this.velocity.getSpeedY() > MAX_SPEED ){
            this.velocity.modSpeedY( SLOWDOWN );
        }
        
        super.update();
    }

    public void setTarget( Point target ) {
        this.target = Point.deepcopy( target );
        this.target.setX( this.target.getX() + TRANSLATE_X );
        this.target.setY( this.target.getY() + TRANSLATE_Y );
        //System.out.printf( "My target = %.2f|%.2f; My position = %.2f|%.2f%n", this.target.getX(), this.target.getY(), this.origin.getX(), this.origin.getY() );
    }   
    
    public void warp( Point destination ){
        this.velocity.setSpeedX( RND.nextDouble() );
        this.velocity.setSpeedY( RND.nextDouble() );
        this.origin = Point.deepcopy( destination );
    }
    
}
