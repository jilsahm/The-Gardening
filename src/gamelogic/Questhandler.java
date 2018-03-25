package gamelogic;

import java.util.ArrayList;

import blueprints.CanvasNumber;
import graphicscontrol.ImageBuffer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import utility.ContentLoader;

public class Questhandler {

	private static final String QUESTS_CONFIG_PATH = "./configs/Quests.ini";
	private static final int    PERCENT_X          = 88;
	private static final int    PERCENT_Y          = 390;
	
	private CanvasNumber level;
	private CanvasNumber progress;
	private Image        percent;
	private Quest[]      quests;   
	
	//TODO
	public Questhandler( CanvasNumber level ){
		this.level   = level;
		this.percent = ImageBuffer.PERCENT.getImage();
		
		ArrayList<String[]> entries = ContentLoader.readConfig( QUESTS_CONFIG_PATH );
		this.quests = new Quest[entries.size()];
		for ( int i = 0; i < this.quests.length; i++ ){
            this.quests[i] = new Quest( entries.get( i ) );
        }
		
		this.progress = new CanvasNumber( this.quests[this.level.getValue()].getProgress(), 350 );
	}
	
	public void updateProgress() {
	    this.progress.setValue( this.quests[this.level.getValue()].getProgress() );
	}
	
	public boolean isCurrentQuestCompleted(){
		this.progress.setValue( this.quests[this.level.getValue()].getProgress() );
		//System.out.println( "Progressvalue = " + this.progress.getValue() );
		return this.quests[this.level.getValue()].isCompleted();
	}
	
	public boolean victory(){
		if ( this.level.getValue() >= 11 ){
			return true;
		}
		return false;
	}
	
	public void draw( GraphicsContext painter ){
	    this.progress.draw( painter );
	    painter.drawImage( this.percent, PERCENT_X, PERCENT_Y );
	}
}
