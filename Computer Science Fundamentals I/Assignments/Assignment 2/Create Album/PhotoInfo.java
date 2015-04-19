/* 
 * Remmy Martin Kilonzo
 * 250750759
 * The methods included in this file are to be
 * used for the caption assignments and include
 * manipulations for pictures used for the assignments
 */

import java.awt.*;
public class PhotoInfo
{
  ///* Attributes *///
  private String id, category, caption, photoFile;
  private int day, month, year;
  private Picture photoPic;
  
  ///* Constructors *///
  public PhotoInfo(String ident, int nday, int nmonth, int nyear, String cat, String cap, String picFile)
  {
    this.id = ident;
    this.day = nday;
    this.month = nmonth;
    this.year = nyear;
    this.category = cat;
    this.caption = cap;
    this.photoFile = picFile;
    photoPic = new Picture(picFile);
  }
  
  ///*  Methods  *///
  
   //* Accessors *//
  /* Fetch the file ID */
  public String getIdentifier()
  {
    return this.id;
  }
  /* Fetch the day */
  public int getDay()
  {
    return this.day;
  }
  /* Fetch the month's name */
  public String getMonth()
  {
    String[] monthArray = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    return monthArray[this.month - 1];
  }
  /* Fetch the month */
  public int getIntMonth()
  {
    return this.month;
  }
  /* Fetch the year */  
  public int getYear()
  {
    return this.year;
  }
  /* Fetch the category */
  public String getCategory()
  {
    return this.category;
  }
  /* Fetch the caption */
  public String getCaption()
  {
    return this.caption.replaceAll("_", " ");
  }
  /* Change the caption of a picture */
  public void changeCaption(String caption)
  {
    this.caption = caption;
  }
  /* Fetch the file path */
  public String getPath()
  {
    return this.photoFile;
  }
  /* Returns the date */
  public String getDate()
  {
    return this.getMonth() + " " + this.day + ", " + this.year;
  }
  /* Returns the information of the picture */
  public String toString()
  {
    return this.id + "; "+ this.category + "; " + this.photoFile + "; " + this.day + "/" + this.month + "/" + this.year;
  }
  /* Create the captioned picture */
  public Picture caption(String font, int fontSize)
  {
    //Declaring variables
    Picture canvas = new Picture(photoPic.getWidth(), photoPic.getHeight() + fontSize * 5 + 15);
    String fileInfo = this.getCategory() + ": " + this.getIdentifier();
    Pixel pixelObj = photoPic.getPixel((int)((photoPic.getWidth() - 1)/2), photoPic.getHeight() - 1);
    
    //Applying the picture to a larger canvas and then adding the caption beneath it
    canvas.copyPictureTo(photoPic, 0,0);
    canvas.drawString(fileInfo, photoPic.getWidth()/2 - (int)(fontSize/2) * fileInfo.length()/2, photoPic.getHeight() + fontSize * 2 , font, fontSize, pixelObj.getRed(), pixelObj.getGreen(), pixelObj.getBlue());
    canvas.drawString(this.getCaption(), photoPic.getWidth()/2 - (int)(fontSize/2) * this.getCaption().length()/2, photoPic.getHeight() + fontSize * 3 + 5, font, fontSize, pixelObj.getRed(), pixelObj.getGreen(), pixelObj.getBlue());
    canvas.drawString(this.getDate(), photoPic.getWidth()/2 - (int)(fontSize/2) * this.getDate().length()/2, photoPic.getHeight() + fontSize * 4 + 10, font, fontSize, pixelObj.getRed(), pixelObj.getGreen(), pixelObj.getBlue());
    
    return canvas;
  }
  public static void main(String[] args)
  {
    PhotoInfo test = new PhotoInfo
      ("DBS", 5, 6, 2013, "Aston Martin", "Beautiful_and_Elegant", FileChooser.pickAFile());
    //Testing accessor methods
    System.out.println(test.getIdentifier());
    System.out.println(test.getDay());
    System.out.println(test.getMonth());
    System.out.println(test.getIntMonth());
    System.out.println(test.getYear());
    System.out.println(test.getCategory());
    System.out.println(test.getCaption());
    System.out.println(test.getDate());
    System.out.println(test.toString());
    
    //Testing caption
    Picture captionTest = new Picture(test.caption("Arial", 14));
//    captionTest.write("C:/Users/Martin/Desktop/Captioned Test.jpg");
    captionTest.explore();
  }
}