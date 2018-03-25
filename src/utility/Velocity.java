package utility;

public class Velocity{

    private double speedX;
    private double speedY;
    
    public Velocity( double speedX, double speedY ){
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void modSpeedX( double speedX ){
        this.speedX += speedX;
    }
    
    public void modSpeedY( double speedY ){
        this.speedY += speedY;
    }
    
    public double getSpeedX() {
        return speedX;
    }

    public void setSpeedX( double speedX ) {
        this.speedX = speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public void setSpeedY( double speedY ) {
        this.speedY = speedY;
    }
    
}
