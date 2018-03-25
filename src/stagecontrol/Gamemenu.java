package stagecontrol;

import java.util.ArrayList;

import blueprints.AnimatedBackground;
import blueprints.Bee;
import blueprints.CanvasButton;
import blueprints.CanvasScene;
import blueprints.Hint;
import blueprints.InvisibleButton;
import blueprints.Tooltip;
import gamelogic.BeeOrder;
import gamelogic.GameState;
import gamelogic.Patch;
import gamelogic.Slot;
import gamelogic.Weather;
import graphicscontrol.ImageBuffer;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import threads.GamemenuEnvironment;
import threads.MovingBeeswarm;
import threads.Nightcycle;
import utility.Point;

public class Gamemenu extends CanvasScene implements EventHandler<MouseEvent>{

    public  static final int SIDEBAR_WIDTH  = 200;
    private static final int NUMBER_OF_BEES = 15;
    private static final double GAMELABEL_X_LOSE = Mainframe.WIDTH / 2 - ImageBuffer.LBL_GAME_OVER.getImage().getWidth() / 2;
    private static final double GAMELABEL_X_WIN  = Mainframe.WIDTH / 2 - ImageBuffer.LBL_VICTORY.getImage().getWidth() / 2;
    private static final double GAMELABEL_Y      = Mainframe.HEIGHT / 2 - ImageBuffer.LBL_GAME_OVER.getImage().getHeight() / 2;
    
    public static volatile boolean disabledUI = false;
    
    private Canvas                     sidebar;
    private GamemenuEnvironment        mainEnvironment;
    private GameState                  gamestate;
    private ArrayList<CanvasButton>    ingameButtons;
    private ArrayList<InvisibleButton> invisibleButtons;
    private ArrayList<Tooltip>         tooltips;
    private ArrayList<Bee>             bees;
    private Nightcycle		           nightcycle;
    private Weather                    weather;
    private CanvasButton               beehive;
    private Point                      beehivelocation;
    private Point                      beehivecenter;
    private boolean                    orderingBees;
    private boolean                    gameOver;
    private boolean                    victory;
    private Hint                       hint;
    private BeeOrder                   beeOrder;
    
    public Gamemenu( Stage stage ){
        super( stage );
        this.sidebar          = new Canvas( SIDEBAR_WIDTH, Mainframe.HEIGHT );
        this.gamestate        = new GameState( 10, 3, 3 );
        this.ingameButtons    = new ArrayList<CanvasButton>();
        this.invisibleButtons = new ArrayList<InvisibleButton>();
        this.tooltips         = new ArrayList<Tooltip>();  
        this.nightcycle       = new Nightcycle();
        this.weather          = Weather.getInstance();
        this.beehivelocation  = new Point( 150, 150 );
        this.beehivecenter    = new Point( 
                this.beehivelocation.getX() + ImageBuffer.BTN_BEEHIVE.getImage().getWidth() / 1.5d,
                this.beehivelocation.getY() + ImageBuffer.BTN_BEEHIVE.getImage().getHeight() / 1.5d
             );
        this.orderingBees     = false;
        this.gameOver         = false;
        this.victory          = false;
        this.hint             = new Hint();
        this.beeOrder         = new BeeOrder();
        
        this.gridpane.add( this.sidebar, 0, 0 );
        this.gridpane.add( this.canvas, 1, 0 );  
        
        this.registerEvents();
        
        this.bees = new ArrayList<Bee>();
        for ( int i = 0; i < NUMBER_OF_BEES; i++ ){
            this.bees.add( new Bee( this.beehivecenter ) );
        }        
        
        this.show();
    }

    @Override
    protected void show() {
    	this.mainEnvironment = new GamemenuEnvironment();
        AnimationTimer mainTimer = new AnimationTimer() {
            @Override
            public void handle( long now ) {
                GraphicsContext painter = canvas.getGraphicsContext2D();
                painter.setFill( Mainframe.BACKGROUNG_COLOR );
                painter.fillRect( 0, 0, Mainframe.WIDTH, Mainframe.HEIGHT );
                for ( AnimatedBackground layer : mainEnvironment.getClouds() ){
                	layer.draw( painter );
                }
                
                gamestate.drawPatches( painter );
                mainEnvironment.getGameisle().draw( painter );                
                gamestate.drawFlowers( painter );
                
                for ( CanvasButton button : ingameButtons ){
                    if ( !button.isDisabled() ){
                        button.draw( painter );
                    }
                }     
                
                beehive.draw( painter );
                
                if ( !gameOver ){                
                    for ( Tooltip tooltip : tooltips ){
                        if ( null != tooltip){
                            tooltip.draw( painter );
                        }
                    }                    
                    for ( Bee bee : bees ){
                        bee.draw( painter );
                        bee.update();
                    }
                    if ( orderingBees ){
                        beeOrder.draw( painter );
                    }
                } else {
                    painter.drawImage( ImageBuffer.LBL_GAME_OVER.getImage(),
                                       Gamemenu.GAMELABEL_X_LOSE,
                                       Gamemenu.GAMELABEL_Y );
                }
                
                if ( victory ){
                	painter.drawImage( ImageBuffer.LBL_VICTORY.getImage(),
                            Gamemenu.GAMELABEL_X_WIN,
                            Gamemenu.GAMELABEL_Y );
                }
                
                weather.drawParticles( painter );
                nightcycle.draw( painter );                
                hint.draw( painter );
            }
        };
        
        mainEnvironment.start();
        mainTimer.start();        
        
        AnimationTimer sidebarTimer = new AnimationTimer() {
            @Override
            public void handle( long now ) {
                GraphicsContext painter = sidebar.getGraphicsContext2D();
                gamestate.drawSidebar( painter );
                for ( CanvasButton button : buttons ){
                    button.draw( painter );
                } 
                weather.drawIcon( painter );
            }
        };        
        
        sidebarTimer.start();
    }

    @Override
    protected void registerEvents() {
        this.beehive = new CanvasButton( ImageBuffer.BTN_BEEHIVE.getImage(),
                ImageBuffer.BTN_BEEHIVE_FOCUSED.getImage(),
                this.beehivelocation,
                "Hive" 
                );
        /*
        this.ingameButtons.add( new CanvasButton( ImageBuffer.BTN_BEEHIVE.getImage(),
                ImageBuffer.BTN_BEEHIVE_FOCUSED.getImage(),
                this.beehivelocation,
                "Hive" )
                );*/
        for ( Patch patch : this.gamestate.getPatches() ){
            //if ( !patch.isLocked() ){
                this.ingameButtons.add( patch.getWatering() );
                this.invisibleButtons.add( patch.getBeetarget() );
            //}
            for ( Slot slot : patch.getSlots() ){
                this.tooltips.add( slot.getTooltip() );
            }
        }
        this.canvas.addEventHandler( MouseEvent.MOUSE_CLICKED, this );
        this.canvas.addEventHandler( MouseEvent.MOUSE_MOVED, this );
        
        this.buttons.add( new CanvasButton( ImageBuffer.BTN_END_DAY.getImage(),
                ImageBuffer.BTN_END_DAY_FOCUSED.getImage(),
                new Point( 13, 632 ),
                "End Day" ) 
                );
        this.sidebar.addEventHandler( MouseEvent.MOUSE_CLICKED, this );
        this.sidebar.addEventHandler( MouseEvent.MOUSE_MOVED, this );        
    }

    private void waterPatch( final int HASH ){
        for ( Patch patch : this.gamestate.getPatches() ){
            if ( HASH == patch.getWatering().hashCode() && !patch.isWatered() ){
                patch.waterFlowers( GameState.AMOUNT_OF_WATER );
                return;
            }
        }
    }
    
    private void previewBeeTarget( final int HASH ){
        for ( Patch patch : this.gamestate.getPatches() ){
            if ( HASH == patch.getBeetarget().hashCode() ){
                if ( !this.beeOrder.isStartFrozen() ){
                    this.beeOrder.setStart( patch.getCenter() );
                } else if ( !this.beeOrder.isDestinationFrozen() ){
                    this.beeOrder.setDestination( patch.getCenter() );
                }
                return;
            }
        }
    }
    
    private void setBeeTarget( final int HASH ){
        for ( Patch patch : this.gamestate.getPatches() ){
            if ( HASH == patch.getBeetarget().hashCode() ){
                if ( !this.beeOrder.isStartFrozen() ){
                    this.beeOrder.freezeStart();
                } else if ( !this.beeOrder.isDestinationFrozen() ){
                    this.beeOrder.freezeDestination();
                    new MovingBeeswarm( this.bees, this.beeOrder, patch, this.beehivecenter, this.hint ).start();
                    this.orderingBees = false;
                    this.gamestate.lockBees();
                }
                return;
            }
        }
    }
    
    public void resetTooltips(){
        this.tooltips.clear();
        for ( Patch patch : this.gamestate.getPatches() ){
            for ( Slot slot : patch.getSlots() ){
                this.tooltips.add( slot.getTooltip() );
            }
        }
    }
    
    @Override
    public void handle( MouseEvent event ) { 
        
    	if ( !gameOver && !victory ){
    	
	        if ( !Gamemenu.disabledUI && !this.orderingBees ){        
	            for ( CanvasButton button : buttons ){
	                if ( button.isInEventRange( event.getX(), event.getSceneY() ) && event.getButton() == MouseButton.PRIMARY ){
	                    switch ( button.getCommand() ){
	                        case "End Day":
	                            //System.out.println( "End day clicked." );
	                            this.gamestate.newDay();
	                            this.resetTooltips();
	                            this.nightcycle.start();
	                            if ( this.gamestate.gameOver() ){
	                                Gamemenu.disabledUI = true;
	                                this.gameOver   = true;
	                            } else if ( this.gamestate.isVictory() ){
	                            	Gamemenu.disabledUI = true;
	                            	this.victory = true;
	                            }
	                            break;
	                    }               
	                    event.consume();
	                }
	            } 
	            for ( CanvasButton button : ingameButtons ){
	                if ( !Gamemenu.disabledUI 
	                        && !button.isDisabled() 
	                        && button.isInEventRange( event.getX(), event.getSceneY() ) 
	                        && event.getButton() == MouseButton.PRIMARY ){
	                    switch ( button.getCommand() ){
	                        case "Hive":
	                            this.orderingBees = true;
	                            this.hint.setText( "Click on the\nStartingpatch.\nClick on the Hive\nto cancel." );
	                            this.hint.setVisible( true );
	                            break;
	                        case "Watering":
	                            this.waterPatch( button.hashCode() );
	                            this.resetTooltips();
	                            break;
	                    }  
	                    event.consume();
	                }
	            }
	            for ( Tooltip tooltip : tooltips ){
	                if ( null != tooltip ){
	                    tooltip.isInEventRange( event.getX(), event.getSceneY() );
	                }
	            }    
	            
	            // Beehivebutton
	            if ( this.gamestate.beeOrderPossible()
	            		&& !this.beehive.isDisabled() 
	                    && this.beehive.isInEventRange( event.getX(), event.getSceneY() ) 
	                    && event.getButton() == MouseButton.PRIMARY
	                    && this.beehive.getCommand() == "Hive" ){
	                //System.out.println( "BEES!" );
	                this.orderingBees = true;
	                this.hint.setText( "Click on the\nStartingpatch.\nClick on the Hive\nto cancel." );
	                this.hint.setVisible( true );
	            }
	        } // End of UICheck
	        else if ( !Gamemenu.disabledUI && this.orderingBees ){
	            if ( !this.beehive.isDisabled() 
	                    && this.beehive.isInEventRange( event.getX(), event.getSceneY() ) 
	                    && event.getButton() == MouseButton.PRIMARY
	                    && this.beehive.getCommand() == "Hive" ){
	                this.orderingBees = false;
	                this.beeOrder.clearFreeze();
	                this.hint.setVisible( false );
	                event.consume();
	            }
	            if ( event.getButton() == MouseButton.PRIMARY ){
	                for ( InvisibleButton button : this.invisibleButtons ){
	                    if ( !button.isDisabled() && button.isInEventRange( event.getX(), event.getSceneY() ) ){
	                        switch ( button.getCommand() ){
	                            case "Ordering Bees":
	                                this.setBeeTarget( button.hashCode() );
	                                this.hint.setText( "Click on the\nDestinationpatch.\nClick on the Hive\nto cancel." );
	                                break;
	                        }  
	                        event.consume();
	                    }
	                }
	            } else {
	                for ( InvisibleButton button : this.invisibleButtons ){
	                    if ( !button.isDisabled() && button.isInEventRange( event.getX(), event.getSceneY() ) ){
	                        if ( !button.isFocused() ){
	                            button.setFocused( true );
	                            this.previewBeeTarget( button.hashCode() );
	                        }
	                    } else {
	                        button.setFocused( false );
	                    }
	                    event.consume();                    
	                }
	            }
	        } // End of Ordering Bees
	    }    	
    }
    
    
}
