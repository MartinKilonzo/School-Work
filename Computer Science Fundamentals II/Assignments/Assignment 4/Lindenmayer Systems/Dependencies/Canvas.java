package Dependencies;
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
public final class Canvas implements ActionListener {
    private BufferedImage image;               // the rasterized image
    private JFrame frame;                      // on-screen view
    private String filename;                   // name of file
    private boolean isOriginUpperLeft = false;  // location of origin
    private boolean isOriginLowerLeft = false;  // location of origin
    private final int width, height;           // width and height

   /**
     * Create a blank w-by-h picture, where each pixel is black.
     */
    public Canvas(int w, int h) {
        width = w;
        height = h;
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        // set to TYPE_INT_ARGB to support transparency
        for(int i=0;i<h;i++) 
        for(int j=0;j<w;j++) 
           this.drawPixel(i,j,255,255,255);
    }

   /**
     * Copy constructor.
     */
    public Canvas(Canvas pic) {
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
    public Canvas(String filename) {
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
	    public Canvas(File file) {
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

           public int getHeight1(){
              return(this.height);
              }
           public int getWidth1(){
              return(this.width);
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
  if(x==this.height) x=height-1; 
  if(y==this.width) y=width-1;
  if(isOriginUpperLeft)
    {
    if(x<this.width() && y<this.height) image.setRGB(x,y,rgb);
    }
  else 
    {
    if(x<this.width() && (height-y-1)<this.height) image.setRGB(x,height-y-1,rgb);
    }
}


public void drawLine(int x1,int y1,int x2,int y2,int red,int green,int blue) 
{
int i,j;

//System.out.println("\nDraw line from " + x1 + "," + y1 + " to " + x2 + "," + y2);
// Draw a pixel
if(x1==x2 && y1==y2)
    {
    if(isOriginUpperLeft) drawPixel(x1,y1,red,green,blue);
    else drawPixel(x1,height-y1-1,red,green,blue);
    }
else 
   if(y1==y2) // Horizontal line
    {
    //System.out.println("Horizontal line");
    for(i=Math.min(x1,x2);i<=Math.max(x1,x2);i++)
     {
     if(isOriginUpperLeft) drawPixel(i,y1,red,green,blue); //image.setRGB(i,y1,rgb);
     else drawPixel(i,height-y1-1,red,green,blue); //image.setRGB(i,height-y1-1,rgb);
     }
   }
else 
   if(x1==x2) // Vertical line
   {
   //System.out.println("Vertical line");
   for(j=Math.min(y1,y2);j<=Math.max(y1,y2);j++)
     {
     if(isOriginUpperLeft) drawPixel(x1,j,red,green,blue); //image.setRGB(x1,j,rgb);
     else drawPixel(x1,height-j-1,red,green,blue); //image.setRGB(x1,height-j-1,rgb);
     }
   }
else 
   // Line is not a single pixel not a vertical/horizontal line
   {
   if(Math.abs(x1-x2) >= Math.abs(y1-y2))
   {
   double slope=((double) (y2-y1))/((double) (x2-x1));
   for(i=Math.min(x1,x2);i<=Math.max(x1,x2);i++)
        {
        j = (int) (slope*(i-x1) + y1);
        if(isOriginUpperLeft) drawPixel(i,j,red,green,blue);
        else drawPixel(i,height-j-1,red,green,blue);
        }
   }
   else if(Math.abs(y1-y2) > Math.abs(x1-x2))
   {
   double inverseSlope=((double) (x2-x1))/((double) (y2-y1));
   for(j=Math.min(y1,y2);j<=Math.max(y1,y2);j++)
        {
        i = (int) (inverseSlope*(j-y1) + x1);
        if(isOriginUpperLeft) drawPixel(i,j,red,green,blue);
        else drawPixel(i,height-j-1,red,green,blue);
        }
   }
   }
} // drawLine

/**
 * Is this Picture equal to obj?
 */
public boolean equals(Object obj) {
if (obj == this) return true;
if (obj == null) return false;
if (obj.getClass() != this.getClass()) return false;
Canvas that = (Canvas) obj;
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
filesave(new File(name));
return;
}

/**
 * Save the picture to a file in a standard image format.
*/
public void filesave(File file) {
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

class TestCanvas { 
/**
 * Test client. Reads a picture specified by the command-line argument,
 * and shows it in a window on the screen.
 */
public static void main(String[] args) {
Canvas pic = new Canvas(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
pic.show();
System.out.printf("%d-by-%d", pic.width(), pic.height());
pic.setOriginLowerLeft();

// Box image
int x1=pic.getWidth1()/2-256;
int x2=pic.getWidth1()/2+256;
int y1=pic.getHeight1()/2-256;
int y2=pic.getHeight1()/2+256;
System.out.println("\nHeight=" + pic.getHeight1() + " Width=" + pic.getWidth1());
System.out.println("(x1,y1)=(" + x1 + "," + y1 + ")");
System.out.println("(x2,y2)=(" + x2 + "," + y2 + ")");
// Draw the box
pic.drawLine(x1,y1,x2,y1,0,0,0);
pic.drawLine(x1,y2,x2,y2,0,0,0);
pic.drawLine(x1,y1,x1,y2,0,0,0);
pic.drawLine(x2,y1,x2,y2,0,0,0);
// left and right diagonal lines
pic.drawLine(x1,y2,x2,y1,0,0,0);
pic.drawLine(x1,y1,x2,y2,0,0,0);
// left and right angles at non 45 degrees
pic.drawLine(x1,y2-100,x2,y1,0,0,0);
pic.drawLine(x1,y1+100,x2,y2,0,0,0);
System.out.println("Data from Kock snowflake");
pic.drawLine(374,214,404,214,0,0,0);
pic.drawLine(404,214,420,237,0,0,0);
pic.drawLine(420,237,404,260,0,0,0);
pic.drawLine(404,260,374,260,0,0,0);
pic.show();
String filename="test.jpg";
pic.save(filename);
System.out.println("File: " + filename + " saved");
System.out.println("TesTCanvas completed");
}
}
