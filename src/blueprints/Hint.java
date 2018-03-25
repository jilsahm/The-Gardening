package blueprints;

import graphicscontrol.ImageBuffer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import utility.Point;

public class Hint extends GraphicsObject{

    private boolean visible;
    private String  text;
    
    public Hint(){
        super( ImageBuffer.TOOLTIP.getImage(), new Point( 0, 0 ) );
        this.visible = false;
        this.text    = "";
    }   
    
    public boolean isVisible() {
        return visible;
    }
    
    public void setVisible( boolean visible ) {
        this.visible = visible;
    }

    public String getText() {
        return text;
    }

    public void setText( String text ) {
        this.text = text;
    }

    @Override
    public void draw( GraphicsContext painter ) {
        if ( this.visible ){
            super.draw( painter );
            painter.setStroke( Color.GREENYELLOW );
            painter.strokeText( this.text, this.origin.getX() + 25, this.origin.getY() + 35 );
        }
    }
    
}
