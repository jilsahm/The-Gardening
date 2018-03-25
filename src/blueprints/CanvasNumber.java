package blueprints;

import graphicscontrol.ImageBuffer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class CanvasNumber {

	private static final int ONE_DIGIT_X         = 90;
    private static final int TWO_DIGIT_X         = 72;
    private static final int THREE_DIGIT_X       = 57;
    private static final int FOUR_DIGIT_X        = 40;
    private static final int NUMBER_WIDTH        = 30;
	
	private int       value;
	private String    stringified;
	private int       length;
	private final int MAX;
	private final int POS_Y;
	
	/**
	 * Creates an Numberobject, witch can be drawed nicely.
	 * @param starting The starting value
	 * @param y The y-Position, where the number will be drawn
	 * @param max The maxvalue of the number
	 */
	public CanvasNumber( int starting, int y, int max ){
		this.value = starting;
		this.MAX   = max;
		this.POS_Y = y;
		this.stringify();
	}
	
	public CanvasNumber( int starting, int y ){
		this( starting, y, Integer.MAX_VALUE );
	}
	
	public CanvasNumber( int y ){
		this( 0, y, Integer.MAX_VALUE );
	}
	
	public void increase(){
		if ( this.value < this.MAX ){
			this.value++;
			this.stringify();
		}
	}
	
	public void decrease(){
		if ( 0 < this.value ){
			this.value--;
			this.stringify();
		}
	}
	
	public void setValue( int value ){
	    this.value = value;
	    this.stringify();
	}
	
	private void stringify(){
		this.stringified = Integer.toString( this.value );
		this.length      = this.stringified.length();
	}
	
	public void draw( GraphicsContext painter ){
		int startingX = 0;
		
		switch ( this.length ){
	        case 1:  startingX = ONE_DIGIT_X; break;
	        case 2:  startingX = TWO_DIGIT_X; break; 
	        case 3:  startingX = THREE_DIGIT_X; break;            
	        default: startingX = FOUR_DIGIT_X;
		}
	    for ( int i = 0; i < this.length; i++ ){
	        painter.drawImage( gainNumber( this.stringified.charAt( i ) ), startingX, this.POS_Y );
	        startingX += NUMBER_WIDTH;
	    }
	}
	
	private static Image gainNumber( char number ){
        switch( number ){
        case '0': return ImageBuffer.SIDEBAR_NUMBER_0.getImage();
        case '1': return ImageBuffer.SIDEBAR_NUMBER_1.getImage();
        case '2': return ImageBuffer.SIDEBAR_NUMBER_2.getImage();
        case '3': return ImageBuffer.SIDEBAR_NUMBER_3.getImage();
        case '4': return ImageBuffer.SIDEBAR_NUMBER_4.getImage();
        case '5': return ImageBuffer.SIDEBAR_NUMBER_5.getImage();
        case '6': return ImageBuffer.SIDEBAR_NUMBER_6.getImage();
        case '7': return ImageBuffer.SIDEBAR_NUMBER_7.getImage();
        case '8': return ImageBuffer.SIDEBAR_NUMBER_8.getImage();
        case '9': return ImageBuffer.SIDEBAR_NUMBER_9.getImage();
        }
    return null;
	}	
	
	public int getValue(){
		return this.value;
	}
	
}
