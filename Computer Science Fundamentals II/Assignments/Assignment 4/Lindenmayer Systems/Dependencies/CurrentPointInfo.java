package Dependencies;
/** This creates an object with the current x,y, drawing directions and length
  * This object can be pushed/popped onto/from a stack and contains
  * the branching point and current drawing direction and line length 
  * for the tree fractal
  */
public class CurrentPointInfo {
private double x;
private double y;
private double currentDrawingDirection;
private double currentLength;

// Constructor
public CurrentPointInfo(double x,double y,double dir,double length) {
this.x=x;
this.y=y;
this.currentDrawingDirection=dir;
this.currentLength=length;
}

// getters
public double getX(){
     return this.x;
}

public double getY(){
     return this.y;
}

public double getCurrentDrawingDirection(){
     return this.currentDrawingDirection;
}

public double getCurrentLength(){
     return this.currentLength;
}

// toString
public String toString(){
return("(" + this.x + "," + this.y + ") in direction:" +  
        this.currentDrawingDirection + " degrees with length:" + this.currentLength);
}

}

