import java.awt.*;
public class ShowProperties
{
  public static void main (String[] args)
  {
    String picture = FileChooser.pickAFile();
    Picture pictureObj = new Picture(picture);
    pictureObj.show();
    
    int height = pictureObj.getHeight();
    int width = pictureObj.getWidth();
    
    System.out.println("The picture is " + height + " pixels high by " + width + " pixels wide.");
    
    int xCoord = 320;
    int yCoord = 3000;
    
    Pixel picturePixel = pictureObj.getPixel(xCoord,yCoord);
    
    int redValue = picturePixel.getRed();
    int greenValue = picturePixel.getGreen();
    int blueValue = picturePixel.getBlue();
    
    System.out.println("Colors of pixel at (" + xCoord + "," + yCoord + ") are: red = " + redValue + " green = " + greenValue + " blue = " + blueValue + ".");
  }
}