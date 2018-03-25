package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class ContentLoader {
	
    private static final String SPLITTOKEN = ",";
    
	private ContentLoader(){}
	
	public static Image loadImage( String source ){
		File f = new File(source);
		Image img = new Image("file:///" + f.getAbsolutePath().replace("\\", "/"));
		return img;
	}
	
	public static String loadData( String source ){
		File f = new File( source );
		String path = "file:///" + f.getAbsolutePath().replace("\\", "/");
		return path;
	}
	
	public static ArrayList<String[]> readConfig( String source ){
	    ArrayList<String[]> entries = new ArrayList<String[]>();
	    String currentLine          = "";

        try ( BufferedReader br = new BufferedReader( new FileReader( source ) ) ) {

            while ( ( currentLine = br.readLine() ) != null ) {
                if ( !currentLine.startsWith( "#" ) ){
                    entries.add( currentLine.split( SPLITTOKEN ) );
                }
            }

        } catch ( IOException e ) {            
        	e.printStackTrace();
        }
        
        entries.trimToSize();
        return entries;
	}
	
}
