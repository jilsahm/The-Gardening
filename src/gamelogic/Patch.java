package gamelogic;

import java.util.Random;

import blueprints.CanvasButton;
import blueprints.GraphicsObject;
import blueprints.InvisibleButton;
import graphicscontrol.ImageBuffer;
import javafx.scene.canvas.GraphicsContext;
import utility.Point;

public class Patch extends GraphicsObject{  
    
    private static final short MAX_FLOWERS   = 9;
    private static final short CONFIG_X  = 0;
    private static final short CONFIG_Y = 1;
    
    private Slot[]          slots;
    private boolean         locked;
    private boolean         watered;
    private CanvasButton    btn_watering;
    private InvisibleButton btn_beetarget;
    private Point           center;
    
    public Patch( String[] config ){
        super ( ImageBuffer.PATCH_LOCKED.getImage(),
                new Point( Double.parseDouble( config[CONFIG_X] ), Double.parseDouble( config[CONFIG_Y] ) )
               );
        this.slots         = new Slot[MAX_FLOWERS];
        this.generateSlots();
        this.locked        = true;
        this.watered       = false;
        this.btn_watering  = new CanvasButton( ImageBuffer.BTN_WATERING.getImage(),
                                              new Point( Double.parseDouble( config[CONFIG_X] ), Double.parseDouble( config[CONFIG_Y] ) ),
                                              "Watering" );
        this.btn_beetarget = new InvisibleButton( new Point( Double.parseDouble( config[CONFIG_X] ),
                                              Double.parseDouble( config[CONFIG_Y] ) ) ,
                                              this.image.getWidth(), this.image.getHeight(),
                                              "Ordering Bees" );
        this.center        = new Point( this.origin.getX() + this.image.getWidth() / 2d, this.origin.getY() + this.image.getHeight() / 2d );
        
        this.btn_watering.setDisabled( true );    
        this.btn_beetarget.setDisabled( true );
    }
    
    private void generateSlots(){
        final int STEPSIZE_X = 50;
        final int STEPSIZE_Y = 40;
        double    slotX      = this.origin.getX();
        double    slotY      = this.origin.getY() - 10d;
        int       current    = 0;
        
        for ( int y = 0; y <= 2; y ++ ){
            for ( int x = 0; x <= 2; x++ ){
                slots[current] = new Slot( new Point ( slotX + STEPSIZE_X * x, slotY + STEPSIZE_Y * y) );
                //System.out.printf( "X = %.2f | Y = %.2f%n", slotX + STEPSIZE * x, slotY + STEPSIZE * y );
                current++;
            }
        }
    }
    
    public boolean isEmpty(){
        for ( Slot slot : slots ){
            if ( !slot.isEmpty() ){
                return false;
            }
        }
        return true;
    }
    
    public boolean isFull(){
        for ( Slot slot : slots ){
            if ( slot.isEmpty() ){
                return false;
            }
        }
        return true;
    }
    
    public boolean isLocked(){
        return this.locked;
    }    
    
    public boolean isWatered() {
        return watered;
    }
    
    public void setWatered( boolean watered ){
        this.watered = watered;
    }

    public void lock(){
        this.locked = true;
        this.image  = ImageBuffer.PATCH_LOCKED.getImage();
        this.btn_watering.setDisabled( true );
        this.btn_beetarget.setDisabled( true );
    }
    
    public void unlock(){
        this.locked = false;
        this.image  = ImageBuffer.PATCH_NORMAL.getImage();
        this.btn_watering.setDisabled( false );
        this.btn_beetarget.setDisabled( false );
    }
    
    public CanvasButton getWatering(){
        return this.btn_watering;
    }
    
    public InvisibleButton getBeetarget(){
        return this.btn_beetarget;
    }
    
    public Slot[] getSlots(){
        return this.slots;
    }
    
    public void spawnFlower( Random rnd ){
        boolean successful = false;
        int     choosenSlot;
        
        if ( !this.isFull() ){
            do {
                choosenSlot = rnd.nextInt( MAX_FLOWERS );
                if ( slots[choosenSlot].isEmpty() ){
                    slots[choosenSlot].spawnFlower( FlowerType.obtainRandomFlowerType( rnd ) );
                    successful = true;
                }
            } while ( !successful );
        }
    }
    
    public void drawFlowers( GraphicsContext painter ){
        for ( Slot slot : slots ){
            if ( !slot.isEmpty() ){
                slot.drawFlower( painter );
            }
        }
    } 
    
    public void updateFlowers(){
    	Flower current;
    	
        for ( Slot slot : this.slots ){
            if ( !slot.isEmpty() ){
                current = slot.getFlower();
                current.consumeWater();
                if ( current.isDead() ){
                	current.getFlowerType().decreasePopulation();
                    slot.clearFlower();
                }
            }
        }
        this.watered = false;
        this.btn_watering.setDisabled( false );
    }
    
    public void waterFlowers( final int AMOUNT ){
        for ( Slot slot : this.slots ){
            if ( !slot.isEmpty() ){
                slot.getFlower().obtainWater( AMOUNT );
                if ( slot.getFlower().isDead() ){
                    slot.clearFlower();
                }
            }
        }
        this.watered = true;
        this.btn_watering.setDisabled( true );
    }    
    
    public Point getCenter(){
        return this.center;
    }
    
}
