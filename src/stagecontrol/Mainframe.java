package stagecontrol;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Mainframe extends Application{	

	public static final double WIDTH  			= 960.0;
	public static final double HEIGHT 			= 700.0;
	public static final Color  BACKGROUNG_COLOR = new Color( 0.5, 0.8, 1.0, 1 );
	
	private Stage stage;
	
	@Override
	public void start( Stage stage ) throws Exception {		
		this.stage = stage;
		
		new Startmenu( this.stage ).loadScene();
		
		this.stage.sizeToScene();
		this.stage.setResizable( false );
		this.stage.show();
	}
	
	public void loadScene( Scene scene ){
		this.stage.setScene( scene );
	}
	
	public static void main( String[] args ) {
		launch();
	}

}
