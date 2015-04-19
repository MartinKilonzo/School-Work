/* 
 * Remmy Martin Kilonzo
 * 250750759
 * The methods included in this file are to be used for 
 * the collage, video and caption assignments and include
 * manipulations for pictures used for the assignments
 */

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.text.*;

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * Copyright Georgia Institute of Technology 2004-2005
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param width the width of the desired picture
   * @param height the height of the desired picture
   */
  public Picture(int width, int height)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
 /**
  * Modified version of method from page 154 of the textbook for copying pictures
  */
 public void copyPictureTo(Picture sourcePicture, int xStart, int yStart)
 {
   Pixel sourcePixel = null;
   Pixel targetPixel = null;
   
   //loop through the columns
   try{
   for (int sourceX = 0, targetX = xStart;
        sourceX < sourcePicture.getWidth();
        sourceX++, targetX++)
   {
     //loop through the rows
     for (int sourceY = 0,
          targetY = yStart;
          sourceY < sourcePicture.getHeight();
          sourceY++, targetY++)
     {
       sourcePixel = sourcePicture.getPixel(sourceX,sourceY);
       targetPixel = this.getPixel(targetX,targetY);
       targetPixel.setColor(sourcePixel.getColor());
     } 
   }
  }catch(IndexOutOfBoundsException ex){System.out.println("Either xStart or yStart is out of bounds");System.exit(0);}
} 

//This method halves all red values in a picture
 public void decreaseRed()
 {
   Pixel[] pixelArray = this.getPixels();
   Pixel pixelObj = null;
   int index = 0;
   int value = 0;
// loop through all the pixels
   while(index < pixelArray.length)
   {
// get the current pixel
     pixelObj = pixelArray[index];
// get the red value
     value = pixelObj.getRed();
// decrease the red value
     value = (int) (value * 0.5);
// set the pixel’s red value
     pixelObj.setRed(value);
// increment the index
     index++;
   }
 }

//This method reduces the amount of red in a picture by a specified amount 
 public void decreaseRed(double howMuch)
 {
   Pixel[] pixelArray = this.getPixels();
   Pixel pixelObj = null;
   
   for (int i = 0; i < pixelArray.length; i ++)
   {
     pixelObj = pixelArray[i];
     pixelObj.setRed((int)((1-howMuch/100) * pixelObj.getRed()));
   }
 }

//This method removes all the blue values from all pixels of a picture 
 public void clearBlue2()
 {
   Pixel pixelObj = null;
   for (int x = 0; x < this.getWidth(); x++)
   {
     for (int y = 0; y < this.getHeight(); y++)
     {
       pixelObj = this.getPixel(x,y);
       pixelObj.setBlue(0);
     }
   }
 } 

//This method removes all blue and green values, leaving a red image behind  
 public void colourRed()
 {
   Pixel pixelObj = null;
   for (int x = 0; x < this.getWidth(); x++)
   {
     for (int y = 0; y < this.getHeight(); y++)
     {
       pixelObj = this.getPixel(x,y);
       pixelObj.setBlue(0);
       pixelObj.setGreen(0);
     }
   }
 }

//This method removes all red and blue values, leaving a green image behind  
 public void colorGreen()
 {
   Pixel pixelObj = null;
   for (int x = 0; x < this.getWidth(); x++)
   {
     for (int y = 0; y < this.getHeight(); y++)
     {
       pixelObj = this.getPixel(x,y);
       pixelObj.setBlue(0);
       pixelObj.setRed(0);
     }
   }
 }

//This method removes all red and green values, leaving a blue image behind 
 public void colourBlue()
 {
   Pixel pixelObj = null;
   for (int x = 0; x < this.getWidth(); x++)
   {
     for (int y = 0; y < this.getHeight(); y++)
     {
       pixelObj = this.getPixel(x,y);
       pixelObj.setRed(0);
       pixelObj.setGreen(0);
     }
   }
 }

//This method removes blue from every pixel that has a blue value less than 128, and otherwise gives the pixel a maximum blue value
 public void blueExtremes()
 {
   Pixel pixelObj = null;
   
   for (int x = 0; x < this.getWidth(); x++)
   {
     for (int y = 0; y < this.getHeight(); y++)
     {
       pixelObj = this.getPixel(x,y);
       if (pixelObj.getBlue() < 128)
       {
         pixelObj.setBlue(0);
       }
       else
       {
         pixelObj.setBlue(255);
       }
     }
   }
 }
 
//This method tints an entire picture a specific colour
 public void colourAll(int red, int green, int blue)
 {
   Pixel pixelObj = null;
   for (int x = 0; x < this.getWidth(); x++)
   {
     for (int y = 0; y < this.getHeight(); y++)
     {
       pixelObj = this.getPixel(x,y);
       pixelObj.setRed((int)((red / 255.0) * pixelObj.getRed()));
       pixelObj.setGreen((int)((green / 255.0) * pixelObj.getGreen()));
       pixelObj.setBlue((int)((blue / 255.0) * pixelObj.getBlue()));
     }
   }
 }
 
//This method makes an entire picture brighter 
 public void brighter()
 {
   Pixel pixelObj = null;
   Color value = null;
   int x = 0, y = 0;
   while (x < this.getWidth())
   {
     while (y < this.getHeight())
     {
       pixelObj = this.getPixel(x,y);
       value = pixelObj.getColor();
       pixelObj.setColor((value.brighter()).brighter());
       y++;
     }
     x++;
   }
 }

//This method copies a picture to a specified image
 public void copyTo(Picture sourcePicture, int sourceEndX, int sourceEndY, int targetX, int targetY)
 {
   Pixel sourcePixel = null;
   Pixel targetPixel = null;
   for (int x = 0, tx = targetX; x < sourcePicture.getWidth() && tx < this.getWidth(); x++, tx++)
   {
     for (int y = 0, ty = targetY; y < sourcePicture.getHeight() && ty < this.getHeight(); y++, ty++)
     {
       sourcePixel = sourcePicture.getPixel(x,y);
       targetPixel = this.getPixel(tx,ty);
       targetPixel.setColor(sourcePixel.getColor());
     }
   }
 }
 
//This method copies an entire picture barring its white pixels to a specific image: INCOMPLETE
 public void copyExceptWhite(Picture sourcePicture, int sourceEndX, int sourceEndY, int targetX, int targetY)
 {
   Pixel sourcePixel = null;
   Pixel targetPixel = null;
   for (int x = 0, tx = targetX; x < sourcePicture.getWidth() && tx < this.getWidth(); x++, tx++)
   {
     for (int y = 0, ty = targetY; y < sourcePicture.getHeight() && ty < this.getHeight(); y++, ty++)
     {
       sourcePixel = sourcePicture.getPixel(x,y);
       targetPixel = this.getPixel(tx,ty);
       if (sourcePixel.getRed() < 255 && 
           sourcePixel.getGreen() < 255 && 
           sourcePixel.getBlue() < 255)
       {
          targetPixel.setColor(sourcePixel.getColor());
       }
     }
   }
 } 
 
//This method mirrors the first half of an image over the second
 public void mirrorVerticalLeftToRight()
 {
   int mirrorPoint = this.getWidth()/2;
   Pixel leftPixel = null;
   Pixel rightPixel = null;
   
   for (int y = 0; y < this.getHeight(); y++)
   {
     for ( int x = 0; x < mirrorPoint; x++)
     {
       leftPixel = this.getPixel(x,y);
       rightPixel = this.getPixel(this.getWidth() - 1 - x, y);
       rightPixel.setColor(leftPixel.getColor());
     }
   }
 }

//This method mirrors the second half of an image over the first
 public void mirrorVerticalRightToLeft()
 {
   int mirrorPoint = this.getWidth()/2;
   Pixel leftPixel = null;
   Pixel rightPixel = null;
   
   for (int y = 0; y < this.getHeight(); y++)
   {
     for ( int x = 0; x < mirrorPoint; x++)
     {
       rightPixel = this.getPixel(this.getWidth() - 1 - x,y);
       leftPixel = this.getPixel(x,y);
       leftPixel.setColor(rightPixel.getColor());
     }
   }
 } 
 
//This method mirros over the diagonal (starting at bottom corner) axis of a picture: INCOMPLETE
 public void mirrorDiagonalBottom()
 {
   int mirrorPoint = (int)(this.getWidth() / this.getHeight());
   Pixel leftPixel = null;
   Pixel rightPixel = null;
   
   for (int y = 0; y < this.getHeight(); y++)
   {
     for (int x = 0; x < this.getWidth() - y; x++)
     {
       leftPixel = this.getPixel(x,y);
       rightPixel = this.getPixel(this.getWidth() - y, this.getHeight() - x);
       rightPixel.setColor(leftPixel.getColor());
     }
   }
 }
 
 /* This Method replaces each pixel in an image with a picture of the same colour as the picture
  * to make a "picture mosaic" (a picture made up of smaller pictures) */
 public void makeMosaic(int numPictures, int scale, String outputLocation)
 {
   Picture canvas = new Picture(scale * this.getWidth(), scale * this.getHeight());
   
   //To choose the pictures that make up the new picture:
   Pixel pixelObj = null;
   Picture tile = null;
   Picture[] tileArray = new Picture[numPictures];
   for (int i = 0; i < numPictures; i ++)
   {
     tileArray[i] = new Picture(FileChooser.pickAFile());    
   }
   
   //To recolour each picture and replace existing pixels with the re-coloured picture
   for (int x = 0, i = (int)((numPictures - 0.01) * (Math.random() )); x < this.getWidth(); x++)
   {
     for (int y = 0; y < this.getHeight(); y++, i = (int)((numPictures - 0.01) * (Math.random() )))
     {
       pixelObj = this.getPixel(x,y);
       //Assigns a variable "tile" to a randomly picked picture from the tileArray and resizes it:
       tile = tileArray[i].getPictureWithHeight(scale);
       //Tints the entire "tile" with the colour of the pixel from the original photo:
       tile.colourAll(pixelObj.getRed(), pixelObj.getGreen(), pixelObj.getBlue());
       //Copies the resized and tinted "tile" onto the canvas, where the "tile" now acts as a pixel:
       canvas.copyTo(tile, tile.getWidth(), tile.getHeight(), x * tile.getWidth(), y * tile.getHeight() );
     }
   }
   canvas.write(outputLocation);
   canvas.explore();
 }
 
 
 public Picture produceMoasic(int numPictures, int scale)
 {
   Picture canvas = new Picture(scale * this.getWidth(), scale * this.getHeight());
   
   //To choose the pictures that make up the new picture:
   Pixel pixelObj = null;
   Picture tile = null;
   Picture[] tileArray = new Picture[numPictures];
   for (int i = 0; i < numPictures; i ++)
   {
     tileArray[i] = new Picture(FileChooser.pickAFile());    
   }
   
   //To recolour each picture and replace existing pixels with the re-coloured picture
   for (int x = 0, i = (int)((numPictures - 0.01) * (Math.random() )); x < this.getWidth(); x++)
   {
     for (int y = 0; y < this.getHeight(); y++, i = (int)((numPictures - 0.01) * (Math.random() )))
     {
       pixelObj = this.getPixel(x,y);
       //Assigns a variable "tile" to a randomly picked picture from the tileArray and resizes it:
       tile = tileArray[i].getPictureWithHeight(scale);
       //Tints the entire "tile" with the colour of the pixel from the original photo:
       tile.colourAll(pixelObj.getRed(), pixelObj.getGreen(), pixelObj.getBlue());
       //Copies the resized and tinted "tile" onto the canvas, where the "tile" now acts as a pixel:
       canvas.copyTo(tile, tile.getWidth(), tile.getHeight(), x * tile.getWidth(), y * tile.getHeight() );
     }
   }
   return canvas;
 }

//This method returns the number of white pixels in a picture 
 public int countWhitePixels()
 {
   Pixel pixelObj;
   int counter = 0;
   
   for (int x = 0; x < this.getWidth(); x++)
   {
     for (int y = 0; y < this.getHeight(); y++)
     {
       pixelObj = this.getPixel(x,y);
       
       if (pixelObj.getRed() == 255 &&
           pixelObj.getGreen() == 255 &&
           pixelObj.getBlue() == 255)
       {
         counter++;
       }
     }
   }
   return counter;
 }
 
//This method returns the number of non-white pixels in a picture
 public int countNonWhitePixels()
 {
   Pixel pixelObj;
   int counter = 0;
   
   for (int x = 0; x < this.getWidth(); x++)
   {
     for (int y = 0; y < this.getHeight(); y++)
     {
       pixelObj = this.getPixel(x,y);
       
       if (!(pixelObj.getRed() == 255 &&
           pixelObj.getGreen() == 255 &&
           pixelObj.getBlue() == 255))
       {
         counter++;
       }
     }
   }
   return counter;
 }

//An alternate method that returns the number of non-white pixels in a picture
 public int countNonWhitePixels2()
 {
   return (this.getHeight() * this.getWidth() - this.countWhitePixels());
 }
 
//This method  determines if a picture is the same size as another
 public Boolean equalPictureSize(Picture otherPicture)
 {
   Boolean equalSize;
   equalSize = this.getHeight() == otherPicture.getHeight() && this.getWidth() == otherPicture.getWidth();
   return equalSize;
 }
     
//This method copies the left half of a picture to a target picture    
 public void copyLeftHalf(Picture sourcePicture)
 {
   Pixel sourcePixel, targetPixel;
   for (int x = 0; x < sourcePicture.getWidth() / 2 && x < this.getWidth() / 2; x++)
   {
     for (int y = 0; y < sourcePicture.getHeight() && y < this.getHeight(); y++)
     {
       sourcePixel = sourcePicture.getPixel(x,y);
       targetPixel = this.getPixel(x,y);
       targetPixel.setColor(sourcePixel.getColor());
     }
   }
 }
 
//This method returns a picture of the left half of a source picture.
//This method is invoked on the source picture.
 public Picture copyLeftHalf()
 {
   Pixel sourcePixel, targetPixel;
   Picture product = new Picture (this.getWidth() / 2,this.getHeight());
   for (int x = 0; x < this.getWidth() / 2 && x < product.getWidth(); x++)
   {
     for (int y = 0; y < this.getHeight() && y < product.getHeight(); y++)
     {
       sourcePixel = this.getPixel(x,y);
       targetPixel = product.getPixel(x,y);
       targetPixel.setColor(sourcePixel.getColor());
     }
   }
   return product;
 }  
 
//This method returns a picture of the left half of a source picture.
//This method is invoked on the source picture.
 public Picture cropLeftHalf()
 {
   Pixel sourcePixel, targetPixel;
   Picture product = new Picture (this.getWidth() / 2,this.getHeight());
   for (int x = 0; x < this.getWidth() / 2 && x < product.getWidth(); x++)
   {
     for (int y = 0; y < this.getHeight() && y < product.getHeight(); y++)
     {
       sourcePixel = this.getPixel(x,y);
       targetPixel = product.getPixel(x,y);
       targetPixel.setColor(sourcePixel.getColor());
     }
   }
   return product;
 }

//This method returns a picture of a specified size (size - 1) of a source picture 
//This method is invoked on the source picture.
 public Picture crop(int px, int py)
 {
   Pixel sourcePixel, targetPixel;
   Picture product = new Picture (px,py);
   for (int x = 0, tx = 0; x < this.getWidth() && x < product.getWidth(); x++, tx++)
   {
     for (int y = 0, ty = 0; y < this.getHeight() && y < product.getHeight(); y++, ty++)
     {
       sourcePixel = this.getPixel(x,y);
       targetPixel = product.getPixel(tx,ty);
       targetPixel.setColor(sourcePixel.getColor());
     }
   }
   return product;
 }
 
public void morphStage(Picture startPicture, Picture endPicture, int numStages, int k)
{
  if (startPicture.getWidth() == endPicture.getWidth() && startPicture.getHeight() == endPicture.getHeight())
  {
    int red, green, blue;
    Pixel startPixel, endPixel, targetPixel;
    
    for (int x = 0; x < startPicture.getWidth() && x < endPicture.getWidth(); x++)
    {
      for (int y = 0; y < startPicture.getHeight() && y < endPicture.getHeight(); y++)
      {
        startPixel = startPicture.getPixel(x,y);
        endPixel = endPicture.getPixel(x,y);
        
        red = startPixel.getRed() + ((endPixel.getRed() - startPixel.getRed())/(numStages + 1)*k);
        green = startPixel.getGreen() + ((endPixel.getGreen() - startPixel.getGreen())/(numStages + 1)*k);
        blue = startPixel.getBlue() + ((endPixel.getBlue() - startPixel.getBlue())/(numStages + 1)*k);
        
        targetPixel = this.getPixel(x,y);
        targetPixel.setRed(red);
        targetPixel.setGreen(green);
        targetPixel.setBlue(blue);
      }
    }
  }
  
  else
  {
   SimpleOutput.showInformation("Pictures must be the same size");
  }
}

//This method returns a "morphed" picture
 public Picture produceMorphStagePicture(Picture startPicture, Picture endPicture, int numStages, int k)
 {
   Picture product = null;
   
   if (startPicture.getWidth() == endPicture.getWidth() && startPicture.getHeight() == endPicture.getHeight())
   {
     int red, green, blue;
     Pixel startPixel, endPixel, targetPixel;
     product = new Picture (startPicture.getWidth(),startPicture.getHeight());
     
     for (int x = 0; x < startPicture.getWidth() && x < endPicture.getWidth(); x++)
     {
       for (int y = 0; y < startPicture.getHeight() && y < endPicture.getHeight(); y++)
       {
         startPixel = startPicture.getPixel(x,y);
         endPixel = endPicture.getPixel(x,y);
         
         red = startPixel.getRed() + ((endPixel.getRed() - startPixel.getRed())/(numStages + 1)*k);
         green = startPixel.getGreen() + ((endPixel.getGreen() - startPixel.getGreen())/(numStages + 1)*k);
         blue = startPixel.getBlue() + ((endPixel.getBlue() - startPixel.getBlue())/(numStages + 1)*k);
         
         targetPixel = product.getPixel(x,y);
         targetPixel.setRed(red);
         targetPixel.setGreen(green);
         targetPixel.setBlue(blue);
       }
     }
   }
   
   else
   {
     SimpleOutput.showInformation("Pictures must be the same size");
   }
   return product;
 }

//This method returns an array of "morphed" pictures INCOMPLETE
 public Picture[] produceMorphStageArray(int numStages)
 {
   Picture startPicture = new Picture
     ("C:/Users/Martin/Documents/Java/My Labs/Assignments/Movie/Pictures/Saints Row Day.jpg");
   Picture endPicture   = new Picture
     ("C:/Users/Martin/Documents/Java/My Labs/Assignments/Movie/Pictures/Saints Row Night.jpg");
//   int numStages = SimpleInput.getIntNumber("How many intermediate pictures would you like?");
//   Picture startPicure = new Picture(FileChooser.pickAFile());
//   Picture endPicture = new Picture(FileChooser.pickAFile());
   Picture product = new Picture(startPicture.getWidth(),startPicture.getHeight());
   Picture[] productArray = new Picture[numStages + 2];
   
   for (int k = 0; k < productArray.length; k++)
   {
     //To check for same dimensions
     if (startPicture.getWidth() == endPicture.getWidth() && startPicture.getHeight() == endPicture.getHeight())
     {
       int red, green, blue;
       Pixel startPixel, endPixel, targetPixel;
       //To recolour the picture to "morph" 
       for (int x = 0; x < startPicture.getWidth() && x < endPicture.getWidth(); x++)
       {
         for (int y = 0; y < startPicture.getHeight() && y < endPicture.getHeight(); y++)
         {
           startPixel = startPicture.getPixel(x,y);
           endPixel = endPicture.getPixel(x,y);
           
           red = startPixel.getRed() + ((endPixel.getRed() - startPixel.getRed())/(numStages + 1) * k);
           green = startPixel.getGreen() + ((endPixel.getGreen() - startPixel.getGreen())/(numStages + 1) * k);
           blue = startPixel.getBlue() + ((endPixel.getBlue() - startPixel.getBlue())/(numStages + 1) * k);
           
           targetPixel = product.getPixel(x,y);
           targetPixel.setRed(red);
           targetPixel.setGreen(green);
           targetPixel.setBlue(blue);
         }
       }
       productArray[k] = product;       
     }
     //To return an error message if pictures are not the same dimensions
     else
     {
       SimpleOutput.showInformation("Pictures must be the same size");
       k = numStages + 3; //To end the loop so the user can re-select pictures
     }
   }
   return productArray;
 }
 
//This method adds text to a photo
 public void drawString(String text, int xPos, int yPos, String font, int fontSize, int red, int green, int blue)
 {
   Graphics g = this.getGraphics();
   if (red > 210 && green > 210 && blue > 210)
   {
     red = 210;
     green = 210;
     blue = 210;
   }
   
   
   g.setColor(new Color(red, green, blue));
   g.setFont(new Font(font, Font.BOLD, fontSize));
   g.drawString(text, xPos, yPos);
 }   
 
} // end of class Picture, put all new methods before this
 
