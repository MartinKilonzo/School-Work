package Main;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import Dependencies.*;


/**
 *  This class provides methods for manipulating individual pixels of
 *  an image. The original image can be read from a file in JPEG, GIF,
 *  or PNG format, or the user can create a blank image of a given size.
 *  This class includes methods for displaying the image in a window on
 *  the screen or saving to a file.
 *  <p>
 *  By default, pixel (x, y) is column x, row y, where (0, 0) is upper left.
 *  The method setOriginLowerLeft() change the origin to the lower left.
 *  <p>
 *  For additional documentation, see
 *  <a href="http://introcs.cs.princeton.edu/31datatype">Section 3.1</a> of
 *  <i>Introduction to Programming in Java: An Interdisciplinary Approach</i>
 *  by Robert Sedgewick and Kevin Wayne.
 */
public final class MyPicture2 implements ActionListener {
    private BufferedImage image;               // the rasterized image
    private JFrame frame;                      // on-screen view
    private String filename;                   // name of file
    private boolean isOriginUpperLeft = false;  // location of origin
    private boolean isOriginLowerLeft = false;  // location of origin
    private final int width, height;           // width and height
    // drawing for one region
    private Integer[][] blackboard = new Integer[512][512];
    // drawing for all accumulated regions
    private Integer[][] whiteboard = new Integer[512][512];

   /**
     * Create a blank w-by-h picture, where each pixel is black.
     */
    public MyPicture2(int w, int h) {
        width = w;
        height = h;
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        // set to TYPE_INT_ARGB to support transparency
        filename = w + "-by-" + h;
    }

   /**
     * Copy constructor.
     */
    public MyPicture2(MyPicture2 pic) {
        width = pic.width();
        height = pic.height();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        filename = pic.filename;
        for (int i = 0; i < width(); i++)
        for (int j = 0; j < height(); j++)
                image.setRGB(i, j, pic.get(i, j).getRGB());
    }

   /**
     * Create a picture by reading in a .png, .gif, or .jpg from
     * the given filename or URL name.
     */
    public MyPicture2(String filename) {
        this.filename = filename;
        try {
            // try to read from file in working directory
            File file = new File(filename);
            if (file.isFile()) {
                image = ImageIO.read(file);
            }

            // now try to read from file in same directory as this .class file
            else {
                URL url = getClass().getResource(filename);
			if (url == null) { url = new URL(filename); }
			image = ImageIO.read(url);
		    }
		    width  = image.getWidth(null);
		    height = image.getHeight(null);
		}
		catch (IOException e) {
		    // e.printStackTrace();
		    throw new RuntimeException("Could not open file: " + filename);
		}
	    }

	   /**
	     * Create a picture by reading in a .png, .gif, or .jpg from a File.
	     */
	    public MyPicture2(File file) {
		try { image = ImageIO.read(file); }
		catch (IOException e) {
		    e.printStackTrace();
		    throw new RuntimeException("Could not open file: " + file);
		}
		if (image == null) {
		    throw new RuntimeException("Invalid image file: " + file);
		}
		width  = image.getWidth(null);
		height = image.getHeight(null);
		filename = file.getName();
	    }

	   /**
	     * Return a JLabel containing this Picture, for embedding in a JPanel,
	     * JFrame or other GUI widget.
	     */
	    public JLabel getJLabel() {
		if (image == null) { return null; }         // no image available
		ImageIcon icon = new ImageIcon(image);
		return new JLabel(icon);
	    }

	   /**
	     * Set the origin to be the upper left pixel.
	     */
	    public void setOriginUpperLeft() {
		isOriginUpperLeft = true;
	    }

	   /**
	     * Set the origin to be the lower left pixel.
	     */
	    public void setOriginLowerLeft() {
		isOriginLowerLeft = true;
	    }

	   /**
	     * Display the picture in a window on the screen.
	     */
	    public void show() {

		// create the GUI for viewing the image if needed
		if (frame == null) {
		    frame = new JFrame();

		    JMenuBar menuBar = new JMenuBar();
		    JMenu menu = new JMenu("File");
		    menuBar.add(menu);
		    JMenuItem menuItem1 = new JMenuItem(" Save...   ");
		    menuItem1.addActionListener(this);
		    menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					     Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		    menu.add(menuItem1);
		    frame.setJMenuBar(menuBar);



		    frame.setContentPane(getJLabel());
		    // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    frame.setTitle(filename);
		    frame.setResizable(false);
		    frame.pack();
		    frame.setVisible(true);
		}

		// draw
		frame.repaint();
	    }

	   /**
	     * Return the height of the picture in pixels.
	     */
	    public int height() {
		return height;
	    }

	   /**
	     * Return the width of the picture in pixels.
	     */
	    public int width() {
		return width;
	    }

	   /**
	     * Return the color of pixel (i, j).
	     */
	    public Color get(int i, int j) {
		if (isOriginUpperLeft) return new Color(image.getRGB(i, j));
		else                   return new Color(image.getRGB(i, height - j - 1));
	    }

/**
  * Set the color of pixel (i, j) to c.
  */
public void set(int i, int j, Color c) {
  if (c == null) { throw new RuntimeException("can't set Color to null"); }
  if (isOriginUpperLeft) image.setRGB(i, j, c.getRGB());
  else                   
  image.setRGB(i, height - j - 1, c.getRGB());
}


// new added methods for CS1027b, 2013
public void drawPixel(int x,int y,int red,int green,int blue){
  Color myColor = new Color(red,green,blue);
  int rgb=myColor.getRGB();
  // Get right and bottom lines squares adjacent to these border to draw
  if(x==512) x=511; 
  if(y==512) y=511;
  if(isOriginUpperLeft)
    {
    if(x<this.width() && y<this.height) image.setRGB(x,y,rgb);
    }
  else 
    {
    if(x<this.width() && (height-y-1)<this.height) image.setRGB(x,height-y-1,rgb);
    }
}


public void drawLine(int x1,int y1,int x2,int y2,int red,int green,int blue) {
	int i,j,temp;
  Color myColor = new Color(red,green,blue);
  int rgb=myColor.getRGB();

  // The line to be drawn must be horizontal or vertical
  if(x1!=x2 && y1!=y2)
    {
    System.out.println("Fatal error: not a vertical or horizontal line");
    System.out.println("x1=" + x1 + " y1=" + y1 + " x2=" + x2 + " y2=" + y2);
    System.exit(1);
    }
	  
  // Draw a pixel
  if(x1==x2 && y1==y2)
    {
    if(isOriginUpperLeft) image.setRGB(x1,y1,rgb);
    else image.setRGB(x1,height-y1-1,rgb);
    }
  else 
    if(y1==y2) // Horizontal line
    {
    if(x2<x1)
      {
      temp=x1;
      x1=x2;
      x2=temp;
      }
    for(i=x1;i<x2;i++)
     {
     if(isOriginUpperLeft) drawPixel(i,y1,red,green,blue); //image.setRGB(i,y1,rgb);
     else drawPixel(i,height-y1-1,red,green,blue); //image.setRGB(i,height-y1-1,rgb);
     }
   }
else 
   if(x1==x2) // Vertical line
   {
   if(y2<y1)
     {
     temp=y1;
     y1=y2;
     y2=temp;
     }
   for(j=y1;j<y2;j++)
     {
     if(isOriginUpperLeft) drawPixel(x1,j,red,green,blue); //image.setRGB(x1,j,rgb);
     else drawPixel(x1,height-j-1,red,green,blue); //image.setRGB(x1,height-j-1,rgb);
     }
   }
}

public void drawWhiteSquare(int x,int y,int sideLength){
  // x,y is lowerleft corner of square
  // 255,255,255 is white
  this.drawLine(x,y,x,y+sideLength,255,255,255);
  this.drawLine(x,y+sideLength,x+sideLength,y+sideLength,255,255,255);
  this.drawLine(x+sideLength,y+sideLength,x+sideLength,y,255,255,255);
  this.drawLine(x+sideLength,y,x,y,255,255,255);
  this.show();
}

public double[] simpleStatistics(int x,int y,int sideLength){
  int i,j;
  double[] parameters = new double[6]; 
  double sumRed=0.0,sumBlue=0.0,sumGreen=0.0;
  double aveRed,aveBlue,aveGreen;
  double diffRed,diffBlue,diffGreen;
  double sigmaRed,sigmaBlue,sigmaGreen;

  Integer redValues[][]   = new Integer[sideLength][sideLength];
  Integer greenValues[][] = new Integer[sideLength][sideLength];
  Integer blueValues[][]  = new Integer[sideLength][sideLength];


  for(i=0;i<sideLength;i++)
  for(j=0;j<sideLength;j++)
    {
    redValues[i][j]   = this.get(i+x,j+y).getRed();
    greenValues[i][j] = this.get(i+x,j+y).getGreen();
    blueValues[i][j]  = this.get(i+x,j+y).getBlue();
    }
  for(i=0;i<sideLength;i++)
  for(j=0;j<sideLength;j++)
    {
    sumRed+=redValues[i][j];
    sumGreen+=greenValues[i][j];
    sumBlue+=blueValues[i][j];
    }
  aveRed=sumRed/(1.0*sideLength*sideLength);
  aveGreen=sumGreen/(1.0*sideLength*sideLength);
  aveBlue=sumBlue/(1.0*sideLength*sideLength);
  parameters[0]=aveRed;
  parameters[1]=aveGreen;
  parameters[2]=aveBlue;

  sumRed=0.0;
  sumBlue=0.0;
  sumGreen=0.0;
  for(i=0;i<sideLength;i++)
  for(j=0;j<sideLength;j++)
    {
    diffRed = (redValues[i][j]-aveRed);
    sumRed += diffRed*diffRed;
    diffGreen = (greenValues[i][j]-aveGreen);
    sumGreen += diffGreen*diffGreen;
    diffBlue = (blueValues[i][j]-aveBlue);
    sumBlue += diffBlue*diffBlue;
    }
  sigmaRed=Math.sqrt(sumRed/((1.0*sideLength*sideLength)-1.0));
  sigmaGreen=Math.sqrt(sumGreen/((1.0*sideLength*sideLength)-1.0));
  sigmaBlue=Math.sqrt(sumBlue/((1.0*sideLength*sideLength)-1.0));
  parameters[3]=sigmaRed;
  parameters[4]=sigmaGreen;
  parameters[5]=sigmaBlue;
  return parameters;
}

public void paintSegment(int x,int y,int sideLength,
			 double red,double green,double blue){
  // Round and then truncate
  int redI=(int) (red+0.5);
  int greenI=(int) (green+0.5);
  int blueI=(int) (blue+0.5);

  for(int i=0;i<sideLength;i++)
  for(int j=0;j<sideLength;j++)
    {
    if(isOriginUpperLeft) drawPixel(i+x,j+y,redI,greenI,blueI); 
    else drawPixel(i+x,height-(j+y)-1,redI,greenI,blueI);
    }
  this.show();
}

public void prettyPrintStatistics(int x,int y,int sideLength,double[] results){

  System.out.println("\nsimpleStatistics results\n");
  System.out.format("Segment: x=%d y=%d sideLength=%d\n",x,y,sideLength);
  System.out.format("meanRed=%13.3f sigmaRed=%13.3f\n",results[0],results[3]);
  System.out.format("meanGreen=%11.3f sigmaGreen=%11.3f\n",results[1],results[4]);
  System.out.format("meanBlue=%12.3f sigmaBlue=%12.3f\n",results[2],results[5]);
  System.out.flush();
}

public void initializeRegionDrawing(Boolean PRINT){
// assume the 2D array is the same dimensions in x and y
for(int i=0;i<512;i++)
for(int j=0;j<512;j++)
      whiteboard[i][j]=blackboard[i][j]=0;
}

public void reInitializeBlackBoard(){
for(int i=0;i<512;i++)
for(int j=0;j<512;j++)
      blackboard[i][j]=0;
}

public void performRegionDrawing(Boolean PRINT){
// assume the 2D array is the same dimensions in x and y
// if blackboard[i][j]==0 || blackboard[i][j] >= 2 don't draw a line
for(int i=0;i<512;i++)
for(int j=0;j<512;j++)
      {
      if(blackboard[i][j].equals(1)) 
         whiteboard[i][j]=1;
      }
for(int i=0;i<512;i++)
for(int j=0;j<512;j++)
      {
      if(whiteboard[i][j].equals(1)) 
         {
         drawPixel(i,j,255,255,255);
         if(PRINT) System.out.println("Drawing pixel (" + i + "," + j + ")");
         }
      }
// Wipe out region drawing in preparation for next region
reInitializeBlackBoard();
}
 
public void drawRegionLine(Integer x1,Integer y1,Integer x2,Integer y2,Boolean PRINT){
int i,j,temp;
//System.out.println("In drawRegionLine: line from (" + x1 + "," + y1 + ") to (" + x2 + "," + y2 + ")");
if(PRINT) {
if(x1.equals(x2)) System.out.println("A vertical line");
if(y1.equals(y2)) System.out.println("A horizontal line");
if(!x1.equals(x2) && !y1.equals(y2)) 
  {
  System.out.println("Something wrong: not vertical or horizontal");
  System.out.println(x1 + "," + y1 + " and " + x2 + "," + y2);
  }
}
if(x1.equals(512)) x1=511;
if(x2.equals(512)) x2=511;
if(y1.equals(512)) y1=511;
if(y2.equals(512)) y2=511;

  // Draw a pixel on the blackboard
  if(x1.equals(x2) && y1.equals(y2))
    {
    if(isOriginUpperLeft) blackboard[x1][y1]=1;
    else blackboard[x1][height-y1-1]=1;
    if(PRINT) System.out.println("blackboard[" + x1 + "][" + y1 + "]=" + blackboard[x1][y1]+"\n");
    }
  else 
    if(y1.equals(y2)) // Draw horizontal line on the blackboard
    {
    if(x2.compareTo(x1)<0)
      {
      temp=x1;
      x1=x2;
      x2=temp;
      }
    for(i=x1.intValue();i<x2.intValue();i++)
     {
     if(isOriginUpperLeft) blackboard[i][y1]++; 
     else blackboard[i][height-y1-1]++;
     if(PRINT) System.out.println("H: blackboard[" + i + "][" + y1 + "]=" + blackboard[i][y1]);
     }
   }
else 
   if(x1.equals(x2)) // Draw vertical line on the blackboard
   {
   if(y2.compareTo(y1)<0)
     {
     temp=y1;
     y1=y2;
     y2=temp;
     }
   for(j=y1.intValue();j<y2.intValue();j++)
     {
     if(isOriginUpperLeft) blackboard[x1][j]++; 
     else blackboard[x1][height-j-1]++;
     if(PRINT) System.out.println("V: blackboard[" + x1 + "][" + j + "]=" + blackboard[x1][j]);
     }
   }
// Force line endpoints to be drawn by setting count to 1
if(isOriginUpperLeft) blackboard[x1][y1]=1;
    else blackboard[x1][height-y1-1]=1;
if(isOriginUpperLeft) blackboard[x2][y2]=1;
    else blackboard[x2][height-y2-1]=1;
}


/**
 * Is this Picture equal to obj?
 */
public boolean equals(Object obj) {
if (obj == this) return true;
if (obj == null) return false;
if (obj.getClass() != this.getClass()) return false;
MyPicture2 that = (MyPicture2) obj;
if (this.width()  != that.width())  return false;
if (this.height() != that.height()) return false;
for (int x = 0; x < width(); x++)
for (int y = 0; y < height(); y++)
if (!this.get(x, y).equals(that.get(x, y))) return false;
return true;
}


/**
 * Save the picture to a file in a standard image format.
 * The filetype must be .png or .jpg.
 */
public void save(String name) {
save(new File(name));
}

/**
 * Save the picture to a file in a standard image format.
*/
public void save(File file) {
this.filename = file.getName();
if (frame != null) { frame.setTitle(filename); }
String suffix = filename.substring(filename.lastIndexOf('.') + 1);
suffix = suffix.toLowerCase();
if (suffix.equals("jpg") || suffix.equals("png")) {
  try { ImageIO.write(image, suffix, file); }
  catch (IOException e) { e.printStackTrace(); }
}
else {
  System.out.println("Error: filename must end in .jpg or .png");
}
}

/**
  * Opens a save dialog box when the user selects "Save As" from the menu.
  */
public void actionPerformed(ActionEvent e) {
FileDialog chooser = new FileDialog(frame,
    "Use a .png or .jpg extension", FileDialog.SAVE);
chooser.setVisible(true);
if (chooser.getFile() != null) {
save(chooser.getDirectory() + File.separator + chooser.getFile());
}
}
}

class TestMyPicture2{ 
/**
 * Test client. Reads a picture specified by the command-line argument,
 * and shows it in a window on the screen.
 */
public static void main(String[] args) {
MyPicture2 pic = new MyPicture2(args[0]);
pic.show();
System.out.printf("%d-by-%d\n", pic.width(), pic.height());
pic.setOriginUpperLeft();

int x=256,y=256,side=32,i,j;
double [] results = new double[6];
results=pic.simpleStatistics(x,y,side);
pic.prettyPrintStatistics(x,y,side,results);
pic.paintSegment(x,y,side,results[0],results[1],results[2]);
pic.drawWhiteSquare(x,y,side);
System.out.println("\npaintSegment finished\n");

x=128; y=128;
results=pic.simpleStatistics(x,y,side);
pic.prettyPrintStatistics(x,y,side,results);
pic.paintSegment(x,y,side,results[0],results[1],results[2]);
pic.drawWhiteSquare(x,y,side);
System.out.println("\npaintSegment finished\n");

x=128; y=128+256;
results=pic.simpleStatistics(x,y,side);
pic.prettyPrintStatistics(x,y,side,results);
pic.paintSegment(x,y,side,results[0],results[1],results[2]);
pic.drawWhiteSquare(x,y,side);
System.out.println("\npaintSegment finished\n");

x=128; y=128; side=256;
System.out.printf("\nStarting with new copy of Picture\n");
MyPicture2 pic2 = new MyPicture2(args[0]);
pic2.show();
pic2.setOriginUpperLeft();
System.out.printf("%d-by-%d\n", pic2.width(), pic2.height());
results=pic2.simpleStatistics(x,y,side);
pic2.prettyPrintStatistics(x,y,side,results);
pic2.paintSegment(x,y,side,results[0],results[1],results[2]);
System.out.println("\npaintSegment finished\n");
pic2.drawWhiteSquare(x,y,side);


x=0; y=0; side=512;
System.out.printf("\nStarting with new copy of Picture\n");
MyPicture2 pic3 = new MyPicture2(args[0]);
pic3.show();
pic3.setOriginUpperLeft();
System.out.printf("%d-by-%d\n", pic3.width(), pic3.height());
results=pic3.simpleStatistics(x,y,side);
pic3.prettyPrintStatistics(x,y,side,results);
pic3.paintSegment(x,y,side,results[0],results[1],results[2]);
System.out.println("\npaintSegment finished\n");
pic3.drawWhiteSquare(x,y,side);

MyPicture2 pic4 = new MyPicture2(args[0]);
pic4.show();
pic4.setOriginUpperLeft();

pic4.initializeRegionDrawing(true);

pic4.drawRegionLine(100,100,300,100,true);
pic4.drawRegionLine(300,100,300,300,true);
pic4.drawRegionLine(300,300,100,300,true);
pic4.drawRegionLine(100,300,100,100,true);

pic4.drawRegionLine(100,150,100,250,true);
pic4.drawRegionLine(100,250,50,250,true);
pic4.drawRegionLine(50,250,50,150,true);
pic4.drawRegionLine(50,150,100,150,true);

pic4.drawRegionLine(300,150,300,250,true);
pic4.drawRegionLine(300,250,350,250,true);
pic4.drawRegionLine(350,250,350,150,true);
pic4.drawRegionLine(350,150,300,150,true);

pic4.drawRegionLine(150,100,250,100,true);
pic4.drawRegionLine(250,100,250,50,true);
pic4.drawRegionLine(250,50,150,50,true);
pic4.drawRegionLine(150,50,150,100,true);

pic4.drawRegionLine(150,300,250,300,true);
pic4.drawRegionLine(250,300,250,350,true);
pic4.drawRegionLine(250,350,150,350,true);
pic4.drawRegionLine(150,350,150,300,true);
 
pic4.performRegionDrawing(true);
pic4.show();

pic4.drawRegionLine(400,400,500,400,true);
pic4.drawRegionLine(500,400,500,500,true);
pic4.drawRegionLine(500,500,400,500,true);
pic4.drawRegionLine(400,500,400,400,true);

pic4.drawRegionLine(400,400,450,400,true);
pic4.drawRegionLine(450,400,450,300,true);
pic4.drawRegionLine(450,300,400,300,true);
pic4.drawRegionLine(400,300,400,400,true);

pic4.performRegionDrawing(true);
pic4.show();

pic4.drawRegionLine(400,450,400,350,true);
pic4.drawRegionLine(400,350,350,350,true);
pic4.drawRegionLine(350,350,350,450,true);
pic4.drawRegionLine(350,450,400,450,true);

pic4.drawRegionLine(175,350,225,350,true);
pic4.drawRegionLine(225,350,225,400,true);
pic4.drawRegionLine(225,400,175,400,true);
pic4.drawRegionLine(175,400,175,350,true);
pic4.performRegionDrawing(true);
pic4.show();
}
}
