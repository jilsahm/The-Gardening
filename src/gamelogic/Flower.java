package gamelogic;

import javafx.scene.image.Image;

public class Flower{
    
    private final FlowerType type;    
    private int              currentWaterCapacity;
    
    public Flower( FlowerType type ){        
        this.type                 = type;
        this.currentWaterCapacity = type.getMaxWaterCapacity() / 3;
    }
    
    public void consumeWater(){
        this.currentWaterCapacity -= this.type.getWaterConsumption();
    }
    
    public void obtainWater( final int AMOUNT){
        this.currentWaterCapacity += AMOUNT;
    }
    
    public boolean isDead(){
        if ( this.currentWaterCapacity < 0 || this.currentWaterCapacity > this.type.getMaxWaterCapacity() ){
            return true;
        }
        return false;
    }
    
    public Image getImage(){
        return this.type.getImage();
    }
    
    public FlowerType getFlowerType(){
    	return this.type;
    }
    
    @Override
    public String toString() {
        return this.type.getFlowername() 
                + "\n\nWater:\t\t" + this.currentWaterCapacity + "/" + this.type.getMaxWaterCapacity()
                + "\nConsumption:\t" + this.type.getWaterConsumption();
    }
    
}
