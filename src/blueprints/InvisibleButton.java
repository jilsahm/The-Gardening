package blueprints;

import utility.Point;

public class InvisibleButton{

    private final double WIDTH;
    private final double HEIGHT;
    private final String COMMAND;
    private Point        origin;
    private boolean      disabled;
    private boolean      focused;
    
    public InvisibleButton( Point origin, double width, double height, String command ){        
        this.WIDTH    = width;
        this.HEIGHT   = height;
        this.COMMAND  = command;
        this.origin   = origin;
        this.disabled = false;
        this.focused  = false;
    }
    
    public boolean isInEventRange( double eventX, double eventY ){
        if (    eventX >= this.origin.getX() && eventX <= this.origin.getX() + this.WIDTH
             && eventY >= this.origin.getY() && eventY <= this.origin.getY() + this.HEIGHT ){
            return true;
        }
        return false;
    }
    
    public String getCommand(){
        return this.COMMAND;
    }

    public boolean isDisabled() {
        return disabled;
    }
    
    public void setFocused( boolean focused ){
        this.focused = focused;
    }
    
    public boolean isFocused(){
        return this.focused;
    }

    public void setDisabled( boolean disabled ) {
        this.disabled = disabled;
    }   
    
    public Point getOrigin(){
        return this.origin;
    }
    
}
