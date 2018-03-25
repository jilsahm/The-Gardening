package gamelogic;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;
import utility.ContentLoader;

public class FlowerType{
    
    private static final short  FLOWERNAME             = 0;
    private static final short  IMAGEPATH              = 1;
    private static final short  MAX_WATER_CAPACITY     = 2;
    private static final short  WATER_CONSUMPTION      = 3;
    private static final String FLOWERTYPS_CONFIG_PATH = "./configs/FlowerTypes.ini";   
    
    private static ArrayList<FlowerType> herbarium = new ArrayList<FlowerType>();
    
    static {
        ArrayList<String[]> entries = ContentLoader.readConfig( FLOWERTYPS_CONFIG_PATH );
        for ( String[] entry : entries ){
            herbarium.add( new FlowerType( entry ) );
        }
        herbarium.trimToSize();
    }    
    
    public static FlowerType obtainRandomFlowerType( Random rnd ){
    	FlowerType choosen = herbarium.get( rnd.nextInt( herbarium.size() ) );
    	
    	choosen.increasePopulation();    	
        return choosen;
    }
    
    public static ArrayList<FlowerType> getHerbarium(){
    	return herbarium;
    }
    
    public static void debugg(){
    	for ( FlowerType type : herbarium ){
    		System.out.println( type.getFlowername() + " : " + type.population );
    	}
    }
    
    private final String flowername;
    private final Image  image;
    private final int    maxWaterCapacity;
    private final int    waterConsumption;
    private int          population;
    
    private FlowerType( String[] config ){
        this.flowername       = config[FLOWERNAME];
        this.image            = ContentLoader.loadImage( config[IMAGEPATH] );
        this.maxWaterCapacity = Integer.parseInt( config[MAX_WATER_CAPACITY] );
        this.waterConsumption = Integer.parseInt( config[WATER_CONSUMPTION] );
        this.population       = 0;
    }

    public void increasePopulation(){
    	this.population++;
    }
    
    public void decreasePopulation(){
    	this.population--;
    }
    
    public int getPopulation(){
    	return this.population;
    }
    
    public String getFlowername() {
        return flowername;
    }
    
    public Image getImage() {
        return image;
    }

    public int getMaxWaterCapacity() {
        return maxWaterCapacity;
    }

    public int getWaterConsumption() {
        return waterConsumption;
    }
    
}
