package gamelogic;

import java.util.ArrayList;
import java.util.Random;

import blueprints.CanvasNumber;
import graphicscontrol.ImageBuffer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import utility.ContentLoader;

public class GameState{

    public  static final int    AMOUNT_OF_WATER     = 12; 
    
    private static final String PATCHES_CONFIG_PATH = "./configs/Patches.ini";
    private static final int    DAYS_Y              = 50;
    private static final int    LEVEL_Y				= 155;
    private static final int    MAX_SHOWN_DAYS      = 9999;
    
    private Patch[] 	 patches;   
    private int     	 startingPatches;
    private int    	 	 startingFlowers;
    private CanvasNumber level;
    private CanvasNumber days;    
    private Random  	 rnd;
    private Questhandler questhandler;
    private boolean 	 beeOrderPossible;
    private boolean      victory;
    
    public GameState( int numberOfPatches, int startingPatches, int startingFlowers ){
        this.patches          = new Patch[numberOfPatches];
        this.startingPatches  = startingPatches;
        this.startingFlowers  = startingFlowers;
        this.level            = new CanvasNumber( startingPatches, LEVEL_Y );
        this.days             = new CanvasNumber( 1, DAYS_Y, MAX_SHOWN_DAYS );
        this.rnd              = new Random( System.currentTimeMillis() );
        this.questhandler     = new Questhandler( this.level );
        this.beeOrderPossible = true;
        this.victory          = false;
        
        loadPatchConfig();
        init();
    }
    
    private void loadPatchConfig(){
        ArrayList<String[]> entries = ContentLoader.readConfig( PATCHES_CONFIG_PATH );
        for ( int i = 0; i < this.patches.length; i++ ){
            this.patches[i] = new Patch( entries.get( i ) );
        }
    }
    
    public void init(){
    	/*
        Patch choosenPatch;
        int   currentPatches = 0;        
        
        while ( currentPatches < this.startingPatches ){
            choosenPatch = this.patches[rnd.nextInt( this.patches.length )];
            if ( choosenPatch.isLocked() ){
                choosenPatch.unlock();
                for( int currentFlowers = 0; currentFlowers < this.startingFlowers; currentFlowers++ ){
                    choosenPatch.spawnFlower( rnd );
                }
                currentPatches++;
            }
        }    */
        
        for ( int i = 0; i < this.startingPatches; i++ ){
        	this.unlockPatch();
        }
        this.questhandler.updateProgress();
    }
    
    public void unlockPatch(){
    	Patch choosenPatch;
        
        while ( true ){
            choosenPatch = this.patches[rnd.nextInt( this.patches.length )];
            if ( choosenPatch.isLocked() ){
                choosenPatch.unlock();
                for( int currentFlowers = 0; currentFlowers < this.startingFlowers; currentFlowers++ ){
                    choosenPatch.spawnFlower( rnd );
                }
                return;
            }
        } 
    }
    
    public void newDay(){        
        this.days.increase();
        this.beeOrderPossible = true;
        Weather.getInstance().effect( this.patches );
        
        for ( Patch patch : this.patches ){
            if ( !patch.isLocked() ){
                patch.updateFlowers();
                if ( patch.isEmpty() ){
	                patch.lock();
	                this.level.decrease();
                }
            }            
        }
        
        if ( this.questhandler.isCurrentQuestCompleted() ){
            //System.out.println( "Unlocking new patch" );
        	this.level.increase();
        	if ( !this.questhandler.victory() ){
	        	this.unlockPatch();        	
	        	this.questhandler.updateProgress();
        	} else {
        		this.victory = true;
        	}
        }        
        
        Weather.getInstance().change();
        //FlowerType.debugg();
    }
    
    public boolean gameOver(){
        for ( Patch patch : this.patches ){
            if ( !patch.isLocked() ){
                return false;
            }
        }
        return true;
    }
    
    public boolean isVictory(){
    	return this.victory;
    }
    
    public void drawPatches( GraphicsContext painter ){
        for ( Patch patch : this.patches ){
            patch.draw( painter );
        }
    }
    
    public void drawFlowers( GraphicsContext painter ){
        for ( Patch patch : this.patches ){
            if ( !patch.isLocked() ){
                patch.drawFlowers( painter );
            }
        }
    }
    
    public void drawSidebar( GraphicsContext painter ){        
        painter.drawImage( ImageBuffer.SIDEBAR.getImage(), 0, 0 );        
        this.days.draw( painter );
        this.level.draw( painter );
        this.questhandler.draw( painter );
    }
    
    public Patch[] getPatches(){
        return this.patches;
    }
    
    public boolean beeOrderPossible(){
    	return this.beeOrderPossible;
    }
    
    public void lockBees(){
    	this.beeOrderPossible = false;
    }
    
}
