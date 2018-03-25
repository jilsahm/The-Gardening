package graphicscontrol;

import javafx.scene.image.Image;
import utility.ContentLoader;

public enum ImageBuffer{

    BTN_START         ( "./images/start.png" ),    
    BTN_START_FOCUSED ( "./images/start_focused.png" ),
    BTN_QUIT          ( "./images/quit.png" ),
    BTN_QUIT_FOCUSED  ( "./images/quit_focused.png"),
    
    BTN_BEEHIVE         ( "./images/Beehive.png" ),
    BTN_BEEHIVE_FOCUSED ( "./images/Beehive_focused.png" ),
    BTN_END_DAY         ( "./images/End_Day.png" ),
    BTN_END_DAY_FOCUSED ( "./images/End_Day_focused.png" ),
    BTN_WATERING        ( "./images/Watering.png" ),
    
    LBL_GAME_OVER ( "./images/Game_Over.png" ),
    LBL_VICTORY   ( "./images/Victory.png" ),
    
    CLOUDS_NORMAL_FRONT  ( "./images/Clouds_Normal_Front.png" ),
    CLOUDS_NORMAL_CENTER ( "./images/Clouds_Normal_Center.png" ),
    CLOUDS_NORMAL_BACK   ( "./images/Clouds_Normal_Back.png" ),
    
    WEATHER_SUN  ( "./images/Weather_Sun.png" ),
    WEATHER_WIND ( "./images/Weather_Wind.png" ),
    WEATHER_RAIN ( "./images/Weather_Rain.png"),
	
	GAMEISLE_NORMAL ( "./images/Gameisle_Normal.png" ),
	SIDEBAR         ( "./images/Sidebar.png" ),
    PATCH_NORMAL    ( "./images/Patch_Normal.png" ),
	PATCH_LOCKED    ( "./images/Patch_Locked.png" ),
	TOOLTIP         ( "./images/Tooltip.png"),
	BEE_LEFT        ( "./images/Bee_Left.png" ),
	BEE_RIGHT       ( "./images/Bee_Right.png" ),
	BEE_GHOST_LEFT  ( "./images/Bee_Ghost_Left.png" ),
    BEE_GHOST_RIGHT ( "./images/Bee_Ghost_Right.png" ),
    
    SIDEBAR_NUMBER_0 ( "./numbers/Sidebar_Number_0.png" ),
    SIDEBAR_NUMBER_1 ( "./numbers/Sidebar_Number_1.png" ),
    SIDEBAR_NUMBER_2 ( "./numbers/Sidebar_Number_2.png" ),
    SIDEBAR_NUMBER_3 ( "./numbers/Sidebar_Number_3.png" ),
    SIDEBAR_NUMBER_4 ( "./numbers/Sidebar_Number_4.png" ),
    SIDEBAR_NUMBER_5 ( "./numbers/Sidebar_Number_5.png" ),
    SIDEBAR_NUMBER_6 ( "./numbers/Sidebar_Number_6.png" ),
    SIDEBAR_NUMBER_7 ( "./numbers/Sidebar_Number_7.png" ),
    SIDEBAR_NUMBER_8 ( "./numbers/Sidebar_Number_8.png" ),
    SIDEBAR_NUMBER_9 ( "./numbers/Sidebar_Number_9.png" ),
    PERCENT          ( "./numbers/Percent.png"),
    
    PARTICLE_LEAF_01 ( "./particles/Leaf_01.png"),
    PARTICLE_LEAF_02 ( "./particles/Leaf_02.png"),
    PARTICLE_DRIP    ( "./particles/Drip.png");
    
    private final String PATH;
    private final Image  IMAGE;
    
    private ImageBuffer( String path ){
        this.PATH = path;
        this.IMAGE = ContentLoader.loadImage( this.PATH );
    }
    
    public Image getImage(){
        return IMAGE;
    }
    
}
