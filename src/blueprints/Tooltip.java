package blueprints;

import gamelogic.Flower;
import graphicscontrol.ImageBuffer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import utility.Point;

public class Tooltip{

    private final double WIDTH;
    private final double HEIGHT;
    
    private Image   background; 
    private Point   eventOrigin;
    private Point   origin;
    private Flower  content;
    private boolean focused;
    
    public Tooltip( Image target, Point targetOrigin , Flower content ){
        this.WIDTH       = target.getWidth();
        this.HEIGHT      = target.getHeight() - 12;
        this.background  = ImageBuffer.TOOLTIP.getImage();
        this.eventOrigin = targetOrigin;
        this.origin      = new Point( targetOrigin.getX() + this.WIDTH / 2, targetOrigin.getY() + this.HEIGHT / 2 );
        this.content     = content;
        this.focused     = false;
    }
    
    public boolean isInEventRange( double eventX, double eventY ){
        if (    eventX >= this.eventOrigin.getX() && eventX <= this.eventOrigin.getX() + this.WIDTH
             && eventY >= this.eventOrigin.getY() && eventY <= this.eventOrigin.getY() + this.HEIGHT ){
            this.focused = true;
        } else {
            this.focused = false;
        }       
        return this.focused;
    }
    
    public void draw( GraphicsContext painter ){
        if ( focused ){
            /*
            painter.fillRect( this.origin.getX(), this.origin.getY(), 180, 130 );*/
            painter.setStroke( Color.GREENYELLOW );
            painter.drawImage( this.background, this.origin.getX(), this.origin.getY() );
            painter.strokeText( this.content.toString(), this.origin.getX() + 25, this.origin.getY() + 35 );
        }
    }
    
}
