package stagecontrol;

import blueprints.AnimatedBackground;
import blueprints.CanvasButton;
import blueprints.CanvasScene;
import graphicscontrol.ImageBuffer;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import threads.StartmenuEnvironment;
import utility.Point;

public class Startmenu extends CanvasScene implements EventHandler<MouseEvent>{
    
    private StartmenuEnvironment environment;
    
	public Startmenu( Stage stage ){
		super( stage );
		
		this.gridpane.add( this.canvas, 0, 0 );
		
		this.registerEvents();
		this.show();
	}
	
	@Override
	protected void show(){		
		this.environment = new StartmenuEnvironment();
		AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle( long now ) {
                GraphicsContext painter = canvas.getGraphicsContext2D();
                painter.setFill( Mainframe.BACKGROUNG_COLOR );
                painter.fillRect( 0, 0, Mainframe.WIDTH, Mainframe.HEIGHT );
                for ( AnimatedBackground layer : environment.getClouds() ){
                	layer.draw( painter );
                }
                for ( CanvasButton button : buttons ){
                	button.draw( painter );
                }
            }
		};		
		
		this.environment.start();		
		timer.start();
	}
	
	@Override
	protected void registerEvents(){
	    this.buttons.add( new CanvasButton( ImageBuffer.BTN_START.getImage(),
	            ImageBuffer.BTN_START_FOCUSED.getImage(),
                new Point( 330, 211 ),
                "Start")
            );
		this.buttons.add( new CanvasButton( ImageBuffer.BTN_QUIT.getImage(),
		        ImageBuffer.BTN_QUIT_FOCUSED.getImage(),
		        new Point( 373, 411 ),
		        "Quit") 
		    );
		this.canvas.addEventHandler( MouseEvent.MOUSE_CLICKED, this );
		this.canvas.addEventHandler( MouseEvent.MOUSE_MOVED, this );
	}

	@Override
	public void handle( MouseEvent event ) {
		for ( CanvasButton button : buttons ){
			if ( button.isInEventRange( event.getX(), event.getSceneY() ) && event.getButton() == MouseButton.PRIMARY ){
			    switch ( button.getCommand() ){
			        case "Start":
			        	this.environment.drop();
    		            new Gamemenu( stage ).loadScene();
    	                System.out.println( "Start clicked." );
    	                break;
			        case "Quit":
			            System.out.println( "End program." );
			            System.exit( 0 );
			    }			    
				event.consume();
			}
		}
	}

}