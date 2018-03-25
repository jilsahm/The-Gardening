package threads;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import stagecontrol.Gamemenu;
import utility.ContentLoader;

public class Nightcycle{

	private static final int LAYERS   = 10;
	private static final int WAITTIME = 100;
		
	private Image[] night;
	private int     activeLayer;
	private boolean visible;
	
	public Nightcycle(){
		this.night       = new Image[10];
		this.activeLayer = 0;
		this.visible     = false;
		
		for ( int i = 0; i < Nightcycle.LAYERS; i++ ){
			this.night[i] = ContentLoader.loadImage( "./nightcycle/Nightcycle_" + i + ".png" );
		}
	}
	
	public void start(){
		new Thread(){
			@Override
			public void run() {
				Gamemenu.disabledUI = true;
				visible             = true;
				
				try {
					for ( int i = 1; i < Nightcycle.LAYERS; i++ ){
						Thread.sleep( Nightcycle.WAITTIME );
						activeLayer++;
					}
					for ( int i = 1; i < Nightcycle.LAYERS; i++ ){
						Thread.sleep( Nightcycle.WAITTIME );
						activeLayer--;
					}			
				} catch ( InterruptedException e ) {
					e.printStackTrace();
				} finally {
					activeLayer         = 0;
					Gamemenu.disabledUI = false;	
					visible             = false;
				}
			}
		}.start();
	}
	
	public void draw( GraphicsContext painter ) {
		if ( this.visible ){
			painter.drawImage( this.night[this.activeLayer], 0, 0 );
		}
	}
	
}
