/*
 * QuadNode represents a node in a quadtree with 4 children
 */

package Dependencies;

import Main.MyPicture2;

public class QuadNode
{
protected double meanRed,meanBlue,meanGreen;
protected double sigmaRed,sigmaBlue,sigmaGreen;
protected QuadNode northEast,northWest,southEast,southWest,parent;
protected int x,y,sideLength,level,size;


/**
  * Constructor
  */
// Generate a single quadnode with parameters[6])
public QuadNode(MyPicture2 pic,int x,int y,int sideLength,int level,double [] parameters) //Changed to public! May need to overload constructors for the assignment
{
this.x=x;
this.y=y;
this.level=level;
this.sideLength=sideLength;
this.meanRed=parameters[0];
this.meanGreen=parameters[1];
this.meanBlue=parameters[2];
this.sigmaRed=parameters[3];
this.sigmaGreen=parameters[4];
this.sigmaBlue=parameters[5];
this.parent=null;
this.northEast=null;
this.northWest=null;
this.southEast=null;
this.southWest=null;
}

// setters
public void setX(int x){
this.x=x;
}
public void setY(int y){
this.y=y;
}
public void setMeanRed(double ave_red){
this.meanRed=ave_red;
}
public void setMeanGreen(double ave_green){
this.meanGreen=ave_green;
}
public void setMeanBlue(double ave_blue){
this.meanBlue=ave_blue;
}
public void setSigmaRed(double sigma_red){
this.meanRed=sigma_red;
}
public void setSigmaGreen(double sigma_green){
this.meanGreen=sigma_green;
}
public void setSigmaBlue(double sigma_blue){
this.meanBlue=sigma_blue;
}
public void setParent(QuadNode node){
this.parent=node;
}
public void setLevel(int level){
this.level=level;
}
public void setSideLength(int n){
this.sideLength=(int) (Math.pow(2,(n-this.getLevel())));
}
public void setNorthEast(QuadNode node){
this.northEast=node;
}
public void setNorthWest(QuadNode node){
this.northWest=node;
}
public void setSouthEast(QuadNode node){
this.southEast=node;
}
public void setSouthWest(QuadNode node){
this.southWest=node;
}
// getters
public int getX(){
return this.x;
}
public int getY(){
return this.y;
}
public double getMeanRed(){
return this.meanRed;
}
public double getMeanGreen(){
return this.meanGreen;
}
public double getMeanBlue(){
return this.meanBlue;
}
public double getSigmaRed(){
return this.sigmaRed;
}
public double getSigmaGreen(){
return this.sigmaGreen;
}
public double getSigmaBlue(){
return this.sigmaBlue;
}
public QuadNode getParent(){
return this.parent;
}
public int getLevel(){
return this.level;
}
public int getSideLength(){
return this.sideLength;
}
public QuadNode getNorthEast(){
return northEast;
}
public QuadNode getNorthWest(){
return northWest;
}
public QuadNode getSouthEast(){
return southEast;
}
public QuadNode getSouthWest(){
return southWest;
}

}


