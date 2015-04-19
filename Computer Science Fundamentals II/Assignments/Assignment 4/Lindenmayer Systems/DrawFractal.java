/**
 * Class to draw the fractal images
 * @author Remmy Martin Kilonzo, 250750759
 * @task Assignment 2
 */
import Dependencies.Canvas;
import Dependencies.CurrentPointInfo;
import Dependencies.LineInfo;
import Dependencies.LinkedStack;
import Dependencies.StackADT;

public class DrawFractal
{
	private Double x1=0.0;
	private Double y1=0.0;
	private Double drawingDir=0.0;
	private Double length=1.0;
	private Double angle;
	private Double scalingFactor;
	private Double x2,y2;
	private final static int DEFAULT = 1000;
	private LineInfo[] lines;
	private Integer lineCount=0;
	private CurrentPointInfo CP;
	private Double Xmin=0.0;
	private Double Xmax=0.0;
	private Double Ymin=0.0;
	private Double Ymax=0.0;
	private String symbol;
	private String fractalString;
	
	public DrawFractal(String fractalString,double scalingFactor,double angle) 
	{
		this.fractalString=fractalString;
		this.scalingFactor=scalingFactor;
		this.angle=angle;
	}
	
	public void computeLines() 
	{
		angle*=Math.PI/180.0; // convert to radians
		// Array of line objects
		lines=new LineInfo[DEFAULT];
		Double mag;
		
		StackADT<CurrentPointInfo> s = new LinkedStack<CurrentPointInfo>();
		// Process fractalString character by character
		for(int i=0;i<fractalString.length();i++) 
	    {
		    symbol=fractalString.substring(i,i+1);
		    System.out.println("i=" + i + " symbol=" + symbol);
	    	boolean flag = false;
		    
		    if(symbol.equals("["))
		    {
		    	CP = new CurrentPointInfo(this.x1, this.y1, this.drawingDir, this.length);
		    	s.push(CP);
		    	flag = true;
		    }
		    if(symbol.equals("]"))
		    {
		    	this.x1 = s.peek().getX();
		    	this.y1 = s.peek().getY();
		    	this.drawingDir = s.peek().getCurrentDrawingDirection();
		    	this.length = s.peek().getCurrentLength();
		    	s.pop();
		    	flag = true;
		    }
		    if(symbol.equals("+"))
		    {
		    	this.drawingDir += angle;
		    	flag = true;
		    }
		    if(symbol.equals("-"))
		    {
		    	this.drawingDir -= angle;
		    	flag = true;
		    }
		    if (flag == false)
		    {
		    	//Apply appropriate transformations to the new x and y positions
	    		this.x2 = this.x1 + this.length * Math.cos(this.drawingDir);
		    	this.y2 = this.y1 + this.length * Math.sin(this.drawingDir);
		    	
	    		if(lineCount == lines.length)
	    			this.expandCapacity();
	    		
		    	//Add starting and ending positions to the LineInfo array
		    	lines[lineCount] = new LineInfo(this.x1, this.y1, this.x2, this.y2);
		    	lineCount++;
		    	
		    	//Updating the minimum and maximum values for x and y
		    	this.UpdateXminYminXmaxYmax(x1,y1,x2,y2);
		    	
		    	//Ending position becomes the new starting position for the next instruction
		    	this.x1 = this.x2;
		    	this.y1 = this.y2;
		    	
		    	//Determining magnitude and scaling appropriately
		    	//mag = Math.sqrt((Xmax - Xmin) * (Xmax - Xmin) + (Ymax - Ymin) * (Ymax - Ymin));
		    	
		    }
	   }
	}
	
	public void printAllLines() 
	{
		int i=0;
		for(i=0;i<lineCount;i++)
		{
			System.out.println("i=" + i + " (x1,y1)=(" + lines[i].getX1() + "," + lines[i].getY1() + ") to" +
	                      " (x2,y2)=(" + lines[i].getX2() + "," + lines[i].getY2() +  ")");
		}
	}
	
	public void drawAllLines(Canvas canvas,int lowerX,int lowerY,int upperX,int upperY) 
	{
		Double xOffset=0.1*upperX;
		Double yOffset=0.1*upperY;
		lowerX+=xOffset;
		lowerY+=yOffset;
		upperX-=xOffset;
		upperY-=yOffset;
		int intX1,intX2;
		int intY1,intY2;
		double xFractalLimit=(Xmax-Xmin);
		double yFractalLimit=(Ymax-Ymin);
		double xImageLimit=upperX-lowerX;
		double yImageLimit=upperY-lowerY;
		Double xRatio=(xImageLimit/xFractalLimit); 
		Double yRatio=(yImageLimit/yFractalLimit); 
		System.out.println("In drawAllLines:");
		System.out.println("xOffset=" + xOffset + " yOffset=" + yOffset);
		System.out.println("lowerX=" + lowerX + " lowerY=" + lowerY);
		System.out.println("upperX=" + upperX + " upperY=" + upperY);
		System.out.println("(Xmin,Ymin)=(" + Xmin + "," + Ymin + ")");
		System.out.println("(Xmax,Ymax)=(" + Xmax + "," + Ymax + ")");
		System.out.println("xRatio=" + xRatio + " yRatio=" + yRatio);
		System.out.println(" ");
		
		int k = 1;
		int r, g, b;
		
		for(int i=0;i<lineCount;i++) 
	    {
	    intX1=(int) (upperX+xRatio*(lines[i].getX1()-Xmax));
	    intY1=(int) (upperY+yRatio*(lines[i].getY1()-Ymax));
	    intX2=(int) (upperX+xRatio*(lines[i].getX2()-Xmax));
	    intY2=(int) (upperY+yRatio*(lines[i].getY2()-Ymax));
	    System.out.println("Line " + i + " from (intX1,intY1)=(" + intX1 + "," + intY1 + ")  " +
	                       "to (intX2,intY2)=(" + intX2 + "," + intY2 + ")");
	    
	    // Draw the fractal using different coloured lines (rainbow)
	    if (i <= (lineCount * k) / 7)
	    {
	    	//Red
	    	if (k == 1)
	    	{
	        	r = 130;
	        	g = 0;
	        	b = 0;
	    		canvas.drawLine(intX1,intY1,intX2,intY2,r,g,b);
	    	}
	    	//Orange
	    	if (k == 2)
	    	{
	        	r = 255;
	        	g = 127;
	        	b = 0;
	    		canvas.drawLine(intX1,intY1,intX2,intY2,r,g,b);
	    	}
	    	//Yellow
	    	if (k == 3)
	    	{
	        	r = 255;
	        	g = 255;
	        	b = 0;
	    		canvas.drawLine(intX1,intY1,intX2,intY2,r,g,b);
	    	}
	    	//Green
	    	if (k == 4)
	    	{
	        	r = 0;
	        	g = 255;
	        	b = 0;
	    		canvas.drawLine(intX1,intY1,intX2,intY2,r,g,b);
	    	}
	    	//Blue
	    	if (k == 5)
	    	{
	        	r = 0;
	        	g = 0;
	        	b = 255;
	    		canvas.drawLine(intX1,intY1,intX2,intY2,r,g,b);
	    	}
	    	//Purple
	    	if (k == 6)
	    	{
	        	r = 75;
	        	g = 0;
	        	b = 130;
	    		canvas.drawLine(intX1,intY1,intX2,intY2,r,g,b);
	    	}
	    	//Violet/Indigo
	    	if (k == 7)
	    	{
	        	r = 143;
	        	g = 0;
	        	b = 255;
	    		canvas.drawLine(intX1,intY1,intX2,intY2,r,g,b);
	    	}
	    	
	    	if (i == (lineCount * k) / 7)
	    		k++;

	    }
	    //canvas.drawLine(intX1,intY1,intX2,intY2,0,0,0);
	    }
		canvas.show();
	}
	
	public void UpdateXminYminXmaxYmax(Double x1,Double y1,Double x2,Double y2) 
	{
		if(x1 < Xmin) Xmin=x1;
		if(x2 < Xmin) Xmin=x2;
		if(x1 > Xmax) Xmax=x1;
		if(x2 > Xmax) Xmax=x2;
		if(y1 < Ymin) Ymin=y1;
		if(y2 < Ymin) Ymin=y2;
		if(y1 > Ymax) Ymax=y1;
		if(y2 > Ymax) Ymax=y2;
	}
	
	public void expandCapacity() 
	{
		LineInfo newLines[]= new LineInfo[lines.length*2];
		
		for (int i=0;i < lines.length;i++) 
		{
			newLines[i] = lines[i];
		}
		lines=newLines;
	}
	
} // DrawFractal