import java.awt.*;
public class ShowProperties1
{
  public static void main (String[] args)
  {
    String picture = FileChooser.pickAFile();
    Picture pictureObj = new Picture(picture);
    pictureObj.show();
    
    System.out.println("The picture is " + pictureObj.getHeight() + " pixels high by " + pictureObj.getWidth() + " pixels wide.");
    
    int xCoord = 320;
    int yCoord = 240;
    
    Pixel picturePixel = pictureObj.getPixel(xCoord,yCoord);
    
    System.out.println("Colors of pixel at (" + xCoord + "," + yCoord + ") are: red = " + picturePixel.getRed() + " green = " + picturePixel.getGreen() + " blue = " + picturePixel.getBlue() + ".");
  }
}