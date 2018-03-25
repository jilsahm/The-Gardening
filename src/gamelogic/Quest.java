package gamelogic;

import java.util.ArrayList;

public class Quest {
	
	private int level;
	private int neededFlowers;
	private int neededTypes;
	private int flowersPerType;
	
	public Quest( String[] config ){
		this.level 		    = Integer.parseInt( config[0] );
		this.neededFlowers  = Integer.parseInt( config[1] );
		this.neededTypes    = Integer.parseInt( config[2] );
		this.flowersPerType = Integer.parseInt( config[3] );
	}
	
	private int[] calcProgress(){	    	    
	    ArrayList<FlowerType> herbarium      = FlowerType.getHerbarium();
        int                   currentFlowers = 0;
        int                   finishedTypes  = 0;
        
        for ( FlowerType type : herbarium ){
            currentFlowers += type.getPopulation();
            if ( type.getPopulation() >= this.flowersPerType && finishedTypes < this.neededTypes ){
                finishedTypes++;
            }
        }
	    
	    return new int[]{ currentFlowers, finishedTypes };
	}
	
	public boolean isCompleted(){	    
	    int[] progress = this.calcProgress();
	    
		if ( this.neededFlowers <= progress[0] && this.neededTypes <= progress[1] ){
			return true;
		}		
		return false;
	}
	
	public void printProgress(){
		int[] progress = this.calcProgress();
		
		System.out.printf( "Needed flowers: %d/%d\nFinished Types: %d/%d\nFlowers per type: %d\n",
		        progress[0],
		        this.neededFlowers,
		        progress[1],
		        this.neededTypes,
		        this.flowersPerType );
	}
	
	public int getProgress(){
	    int[] progress = this.calcProgress();
	    // nF+nT   pF+pT
	    // ----- = -----
	    //  100%    ?%
	    // ?% = (pF+pT) * 100 / (nF+nT)
	    //double var = (double)( progress[0] + progress[1] ) * 100.0 / (double)( this.neededFlowers + this.neededTypes );	    
	    //return (int)var;
	    
	    double percentFlowers = ( progress[0] * 100d ) / this.neededFlowers;
	    double percentTypes   = ( progress[1] * 100d ) / this.neededTypes;
	    //System.out.println( "getProgress() = " + (int)( ( percentFlowers + percentTypes ) / 2d ) );
	    return (int)( ( percentFlowers + percentTypes ) / 2d );
	}
	
}
