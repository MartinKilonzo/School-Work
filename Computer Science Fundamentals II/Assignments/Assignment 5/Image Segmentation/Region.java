package Main;
import Dependencies.*;

public class Region {

private Integer area=0;
private Double meanRed=0.0;
private Double meanBlue=0.0;
private Double meanGreen=0.0;
private Double sigmaRed=0.0;
private Double sigmaBlue=0.0;
private Double sigmaGreen=0.0;
private QueueADT<QuadNode> quadqueue;

// Contructor creates empty region (empty queue)
// region is an empthy queue

public Region() {
quadqueue = new LinkedQueue<QuadNode>();
this.meanRed=0.0;
this.meanGreen=0.0;
this.meanBlue=0.0;
this.sigmaRed=0.0;
this.sigmaGreen=0.0;
this.sigmaBlue=0.0;
this.area=0;
}

// Constructor adds 1 quadnode to the list of quadnodes
// so the region is still square at this point
// The private variables for area, means and sigmas are set

public Region(QuadNode q) {
quadqueue = new LinkedQueue<QuadNode>();
this.quadqueue.enqueue(q);
this.meanRed=q.getMeanRed();
this.meanGreen=q.getMeanGreen();
this.meanBlue=q.getMeanBlue();
this.sigmaRed=q.getSigmaRed();
this.sigmaGreen=q.getSigmaGreen();
this.sigmaBlue=q.getSigmaBlue();
this.area=q.getSideLength()*q.getSideLength();
}

// Helper method to add a quadnode to a non-square region
// method not used - not debugged but pretty sure it is ok
public void add(QuadNode q) {
// local variables
Integer quadNodeArea=0; // local variable
Integer newArea;
Double sum2Red,sum2Green,sum2Blue;

quadNodeArea=q.getSideLength()*q.getSideLength();
newArea=this.area+quadNodeArea;
this.quadqueue.enqueue(q);

// Multiply mean colours of existing region and new quadnode by the area
// of that region and the quadnote and then divide by new total area

this.meanRed=(this.area*this.meanRed+quadNodeArea*q.getMeanRed())/newArea;
this.meanGreen=(this.area*this.meanGreen+quadNodeArea*q.getMeanGreen())/newArea;
this.meanBlue=(this.area*this.meanBlue+quadNodeArea*q.getMeanBlue())/newArea;

// Defn of variance: sigma^2=\frac{\sum_{i=1}^n (x_i-\bar{x})^2}{n}
// This can be shown to be equal to: \frac{(\sum_{i=1}^n x_i^2 - \bar{x}^2)}{n}
// Therefore the \sum_{i=1}^n x_i^2 can be computed from n(\sigma^2+\bar{x}^2)
// for the region and the new quadnode. This allows a new standard deviation (sigma)
// to be computed for the new region after the quadnode is added to it.

sum2Red=this.area*(this.sigmaRed*this.sigmaRed+this.meanRed*this.meanRed)+
        quadNodeArea*(q.getSigmaRed()*q.getSigmaRed()+q.getMeanRed()*q.getMeanRed());
sum2Green=this.area*(this.sigmaGreen*this.sigmaGreen+this.meanGreen*this.meanGreen)+
          quadNodeArea*(q.getSigmaGreen()*q.getSigmaGreen()+q.getMeanGreen()*q.getMeanGreen());
sum2Blue=this.area*(this.sigmaBlue*this.sigmaBlue+this.meanBlue*this.meanBlue)+
         quadNodeArea*(q.getSigmaBlue()*q.getSigmaBlue()+q.getMeanBlue()*q.getMeanBlue());

// Adjust size of new region to accomodate the new quadnode's size
this.area+=quadNodeArea;

// Now use the \sum_{i=1}^n x_i^2 computed for the region and
// the quadnode to compute the new red, green and blue sigma values for the region

this.area=this.area+quadNodeArea;

sigmaRed=Math.sqrt((sum2Red/this.area)-this.meanRed*this.meanRed);
sigmaGreen=Math.sqrt((sum2Green/this.area)-this.meanGreen*this.meanGreen);
sigmaBlue=Math.sqrt((sum2Blue/this.area)-this.meanBlue*this.meanBlue);
}

public void drawRegion(MyPicture2 pic,Boolean PRINT){
Integer x1,x2,y1,y2,side;
QuadNode q;
pic.initializeRegionDrawing(PRINT);
if(PRINT) 
{
System.out.println("queue size: " + this.quadqueue.size());
}
for(int i=0;i<this.quadqueue.size();i++)
   {
   q=this.quadqueue.dequeue();
   this.quadqueue.enqueue(q); // Don't destroy queue
   x1=q.getX();
   y1=q.getY();
   side=q.getSideLength();
   x2=x1+side;
   y2=y1;
   if(PRINT) System.out.println("(x1,y1)=(" + x1 + "," + y1 + ") (x2,y2)=(" + x2 + "," + y2 + ")");
   pic.drawRegionLine(x1,y1,x2,y2,PRINT);
   x1=x2;
   y1=y2;
   x2=x1;
   y2=y1+side;
   if(PRINT) System.out.println("(x1,y1)=(" + x1 + "," + y1 + ") (x2,y2)=(" + x2 + "," + y2 + ")");
   pic.drawRegionLine(x1,y1,x2,y2,PRINT);
   x1=x2;
   y1=y2;
   x2=x1-side;
   y2=y1;
   if(PRINT) System.out.println("(x1,y1)=(" + x1 + "," + y1 + ") (x2,y2)=(" + x2 + "," + y2 + ")");
   pic.drawRegionLine(x1,y1,x2,y2,PRINT);
   x1=x2;
   y1=y2;
   x2=x1;
   y2=y1-side;
   if(PRINT) System.out.println("(x1,y1)=(" + x1 + "," + y1 + ") (x2,y2)=(" + x2 + "," + y2 + ")");
   pic.drawRegionLine(x1,y1,x2,y2,PRINT);
   }
pic.performRegionDrawing(PRINT);
}

// Determine if 2 regions are adjacent by checking if
// any of the quadnodes in the two regions os adjacent
// The same quadnode cannot be in two regions at the same time
public boolean adjacent(Region r,Boolean PRINT) {
//   X1,Y1      X1+side,Y1
//
//   X1,Y1+side X1+side,Y1+side
//
//   X2,Y2      X2+side,Y2
//
//   X2,Y2+side X2+side,Y2+side
QuadNode q1,q2;

if(PRINT) {
System.out.println("In adjacent:");
System.out.println("quadqueue size:" + this.quadqueue.size()); }
for(int i=0;i<this.quadqueue.size();i++)
{
q1=this.quadqueue.dequeue();
this.quadqueue.enqueue(q1); // Don't destroy the queue
int q1Side=q1.getSideLength();
int q1X1=q1.getX();
int q1X2=q1X1+q1Side;
int q1X3=q1X2;
int q1X4=q1X1;
int q1Y1=q1.getY();
int q1Y2=q1Y1;
int q1Y3=q1Y1+q1Side;
int q1Y4=q1Y1+q1Side;
if(PRINT) {
System.out.println("r1 quadnode corners:");
System.out.println("(" + q1X1 + "," + q1Y1 + ") " + "(" + q1X2 + "," + q1Y2 + ") " +
                   "(" + q1X3 + "," + q1Y3 + ") " + "(" + q1X4 + "," + q1Y4 + ") "); }

for(int j=0;j<r.quadqueue.size();j++)
{
q2=r.quadqueue.dequeue();
r.quadqueue.enqueue(q2); // Don't destroy the queue
  int q2Side=q2.getSideLength();
  int q2X1=q2.getX();
  int q2X2=q2X1+q2Side;
  int q2X3=q2X2;
  int q2X4=q2X1;
  int q2Y1=q2.getY();
  int q2Y2=q2Y1;
  int q2Y3=q2Y1+q2Side;
  int q2Y4=q2Y1+q2Side;
  if(PRINT) {
  System.out.println("r2 quadnode corners:");
  System.out.println("(" + q2X1 + "," + q2Y1 + ") " + "(" + q2X2 + "," + q2Y2 + ") " +
                     "(" + q2X3 + "," + q2Y3 + ") " + "(" + q2X4 + "," + q2Y4 + ") "); }

  // If 2 QuadNodes are neighbouring 1 of the following
  // 4 conditions must be true
  Boolean result=false;

  // Left adjacency
  if(result==false && (q1X1+q1Side==q2X1) &&
     ((q1Y1<=q2Y1 && q1Y1>=q1Y2) ||
      (q2Y1<=q1Y1 && q2Y1>=q2Y2))) result=true;
  if(result && PRINT) System.out.println("Adjaceny Condition 1 is true");
  
  // Right adjacency
  if(result==false && (q2X1+q2Side==q1X1) &&
     ((q2Y1<=q1Y1 && q2Y1>=q2Y2) ||
      (q1Y1<=q2Y1 && q1Y1>=q1Y2))) result=true;
  if(result && PRINT) System.out.println("Adjaceny Condition 2 is true");
  
  // Top adjacency
  if(result==false && (q1Y1+q1Side==q2Y1) &&
     ((q1X1<=q2X1 && q1X1>=q1X2) ||
      (q2X1<=q1X1 && q2X1>=q2X2))) result=true;
  if(result && PRINT) System.out.println("Adjaceny Condition 3 is true");
  
  // Bottom adjacency
  if(result==false && (q2Y1+q2Side==q1Y1) &&
     ((q2X1<=q1X1 && q2X1>=q2X2) ||
      (q1X1<=q2X1 && q1X1>=q1X2))) result=true;
  if(result && PRINT) System.out.println("Adjaceny Condition 4 is true");

  if(!result && PRINT) System.out.println("Regions not adjacent"+"\n");
  if(result) return true;
}
}
return false;
}

//////////////////////////////////////////////////////////////////////
// Merge region r with this region
public void mergeRegion(Region r,Boolean PRINT){
QuadNode q;
Integer regionArea,newRegionArea;

regionArea=r.getArea();

for(int i=0;i<r.quadqueue.size();i++)
    {
    q=r.quadqueue.dequeue(); 
    r.quadqueue.enqueue(q); // Don't destroy r queue
    this.quadqueue.enqueue(q); // size of queue will be updated automatically
    }
if(PRINT)
{
System.out.println("After loop: r.quadqueue.size()=" + r.quadqueue.size());
System.out.println("After loop: this.quadqueue.size()=" + this.quadqueue.size());
System.out.println("this.area=" + this.area);
}

// Now update this object's mean and standard deviation values
Double sum2Red,sum2Green,sum2Blue;

// Adjust area of new region to accomodate the merged regions size

newRegionArea=this.area+regionArea;
if(PRINT)
{
System.out.println("this.area=" + this.area + " regionArea=" + regionArea);
System.out.println("newRegionArea=" + newRegionArea);
}

// Defn of variance: sigma^2=\frac{\sum_{i=1}^n (x_i-\bar{x})^2}{n}
// This can be shown to be equal to: \frac{(\sum_{i=1}^n x_i^2 - \bar{x}^2)}{n}
// Therefore the \sum_{i=1}^n x_i^2 can be computed from n*(\sigma^2)+\bar{x}^2
// for the region and the new quadnode. This allows a new standard deviation (sigma)
// to be computed for the new region after the quadnode is added to it.

if(PRINT)
{
System.out.println("sum2Red term1:" + this.area*(this.sigmaRed*this.sigmaRed+this.meanRed*this.meanRed));
System.out.println("sum2Red term2:" + regionArea*(r.getSigmaRed()*r.getSigmaRed()+r.getMeanRed()*r.getMeanRed()));
System.out.println("regionArea=" + regionArea);
System.out.println("r.getSigmaRed()=" + r.getSigmaRed());
System.out.println("r.getMeanRed()=" + r.getMeanRed());
}
sum2Red=
    this.area*(this.sigmaRed*this.sigmaRed+this.meanRed*this.meanRed)+
    regionArea*(r.getSigmaRed()*r.getSigmaRed()+r.getMeanRed()*r.getMeanRed());
sum2Green=
    this.area*(this.sigmaGreen*this.sigmaGreen+this.meanGreen*this.meanGreen)+
    regionArea*(r.getSigmaGreen()*r.getSigmaGreen()+r.getMeanGreen()*r.getMeanGreen());
sum2Blue=
    this.area*(this.sigmaBlue*this.sigmaBlue+this.meanBlue*this.meanBlue)+
    regionArea*(r.getSigmaBlue()*r.getSigmaBlue()+r.getMeanBlue()*r.getMeanBlue());
if(PRINT)
{
System.out.println("sum2Red=" + sum2Red);
System.out.println("sum2Green=" + sum2Green);
System.out.println("sum2Blue=" + sum2Blue);
}

// Multiply mean colours of existing region and new quadnode by the size
// of that region and the quadnote and then divide by new total size

this.meanRed=(this.area*this.meanRed+regionArea*r.getMeanRed())/newRegionArea;
this.meanGreen=(this.area*this.meanGreen+regionArea*r.getMeanGreen())/newRegionArea;
this.meanBlue=(this.area*this.meanBlue+regionArea*r.getMeanBlue())/newRegionArea;
this.area=newRegionArea;
if(PRINT)
{
System.out.println("this.meanRed=" + this.meanRed);
System.out.println("this.meanGreen=" + this.meanGreen);
System.out.println("this.meanBlue=" + this.meanBlue);
System.out.println("this.area=" + this.area);
}

// Now use the \sum_{i=1}^n x_i^2 computed for the region and
// the quadnode to compute the new red, green and blue sigma values for the region

this.sigmaRed=Math.sqrt((sum2Red/this.area)-this.meanRed*this.meanRed);
this.sigmaGreen=Math.sqrt((sum2Green/this.area)-this.meanGreen*this.meanGreen);
this.sigmaBlue=Math.sqrt((sum2Blue/this.area)-this.meanBlue*this.meanBlue);
if(PRINT)
{
System.out.println("this.sigmaRed=" + this.sigmaRed);
System.out.println("sum2Red=" + sum2Red);
System.out.println("this.meanRed^2=" + this.meanRed*this.meanRed);
System.out.println("sqrt term:" + (sum2Red-this.meanRed*this.meanRed));
System.out.println("this.area=" + this.area);
System.out.println("this.sigmaGreen=" + this.sigmaGreen);
System.out.println("this.sigmaBlue=" + this.sigmaBlue);
//System.exit(0);
}
}

// getters for Region class - don't need setters
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
public Integer getArea(){
return this.area;
}
public Integer getQuadQueueSize(){
return this.quadqueue.size();
}
}
