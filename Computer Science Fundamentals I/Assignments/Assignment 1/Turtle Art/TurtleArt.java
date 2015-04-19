import java.awt.Color;
public class TurtleArt
{
  public static void main (String[] args)
  {
    World earth = new World(1920,1080);
    Turtle builder = new Turtle(earth);
  
//Colors
    Color colorGround = new Color(153,219,161);
    Color colorSky = new Color(198,229,217);
    Color colorSun = new Color(246,247,189);
    Color colorBuilding = new Color(101,86,67);
    Color colorRoof1 = new Color(191,77,40);
    Color colorRoof2 = new Color(170,0,0);    
    Color colorDoor = new Color(246,247,189);
        
//Ground and Sky
    builder.setPenWidth(3);
    builder.setPenColor(colorGround);
    builder.drawBox(0,1080,1920,540);
    builder.setPenWidth(3);
    builder.setPenColor(colorSky);
    builder.drawBox(0,540,1920,540);
    builder.setPenColor(colorSun);
    builder.drawCircle(288,250,86);
    
//Landscaping:Rear
    builder.setPenWidth(1);
    builder.drawTree(380,630,180);
    
//Main Body
    builder.setPenColor(colorBuilding);
    builder.drawBox(480,672,480,192);
    builder.setPenWidth(2);
    builder.setPenColor(java.awt.Color.black);
    builder.drawRectangle(480,672,480,192);
    
//Roof 1    
    builder.setPenColor(colorRoof1);
    builder.drawRightTriangle(1440,672,-480,432);
    builder.setPenColor(java.awt.Color.black);
    builder.drawRightTriangleFrame(1440,672,-480,432);
    
//Roof 2   
    builder.setPenColor(colorRoof2);
    builder.drawRightTriangle(480,480,480,144);
    builder.setPenColor(java.awt.Color.black);
    builder.drawRightTriangleFrame(480,480,480,144);
    
//Windows
    builder.setPenWidth(1);
    builder.setPenColor(java.awt.Color.white);
    builder.drawBox(618,624,96,120);
    builder.drawBox(724,624,96,120);
    builder.drawBox(830,624,96,120);
    builder.drawBox(936,624,96,120);
    builder.setPenWidth(2);
    builder.setPenColor(java.awt.Color.black);
    builder.drawRectangle(618,624,97,120);
    builder.drawRectangle(724,624,97,120);
    builder.drawRectangle(830,624,97,120);
    builder.drawRectangle(936,624,97,120);
    
//Balcony
    //Columns
    builder.setPenWidth(1);
    builder.setPenColor(colorBuilding.darker());
    builder.drawBox(624,672,24,96); 
    builder.drawBox(984,672,24,96);
    builder.setPenWidth(2);
    builder.setPenColor(java.awt.Color.black);
    builder.drawRectangle(624,672,25,96);
    builder.drawRectangle(984,672,25,96);
    
    //Balcony
    builder.setPenWidth(1);
    builder.setPenColor(colorBuilding.darker());
    builder.drawBox(576,576,480,24);
    builder.setPenWidth(2);
    builder.setPenColor(java.awt.Color.black);
    builder.drawRectangle(576,576,481,24);
    
    //Railing
    builder.setPenWidth(1);
    builder.drawRailing(576,552,24,480,10);
    
//Doors
    //Front Door
    builder.setPenWidth(1);
    builder.setPenColor(colorDoor);
    builder.drawSquareFilled(516,672,72);
    builder.setPenWidth(2);
    builder.setPenColor(java.awt.Color.black);
    builder.drawSquare(516,672,72);
    builder.drawEqualateral(2);
        
    //Garage Door
    builder.setPenWidth(1);
    builder.setPenColor(colorBuilding.brighter());
    builder.drawBox(1075,672,192,96);
    builder.setPenWidth(2);
    builder.setPenColor(java.awt.Color.black);
    builder.drawRectangle(1075,672,193,96);
    
    //Garage Door Windows
    builder.setPenWidth(1);
    builder.setPenColor(java.awt.Color.white);
    builder.drawBox(1075,566,144,48);
    builder.drawRightTriangle(1268,566,-49,49);
    builder.setPenWidth(2);
    builder.setPenColor(java.awt.Color.black);
    builder.drawSquare(1075,566,48);
    builder.drawSquare(1123,566,48);
    builder.drawSquare(1171,566,48);
    builder.drawRightTriangleFrame(1268,566,-49,49);
    
//Landscapeing:Front
    builder.setPenWidth(1);
    //Left Side
    builder.drawTree(2,650,200);
    builder.drawTree(300,660,150);
    builder.drawTree(130,675,200);
    builder.drawTree(240,679,170);   
    
    //Right Side
    builder.drawTree(1730,650,200);
    builder.drawTree(1560,675,200);
    builder.drawTree(1820,679,170);
    builder.drawTree(1430,680,180);
    builder.drawTree(1300,693,220);    
    builder.drawTree(1690,700,150);    
  }
}